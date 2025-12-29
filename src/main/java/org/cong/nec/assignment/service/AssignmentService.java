package org.cong.nec.assignment.service;

import lombok.AllArgsConstructor;
import org.cong.nec.assignment.dto.AssignmentSummaryDTO;
import org.cong.nec.assignment.model.Assignment;
import org.cong.nec.authentication.enums.Role;
import org.cong.nec.linkgenerator.service.LinkService;
import org.cong.nec.person.model.Person;
import org.cong.nec.territory.model.Territory;
import org.cong.nec.assignment.repository.AssignmentRepository;
import org.cong.nec.person.service.PersonService;
import org.cong.nec.territory.service.TerritoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AssignmentService {

    private AssignmentRepository assignmentRepository;

    private TerritoryService territoryService;

    private PersonService personService;

    private LinkService linkService;

    @PreAuthorize("hasRole('ADMIN')")
    public List<AssignmentSummaryDTO> findCurrentAssignments() {
        List<Assignment> assignments = assignmentRepository.findByCompletedAtNull();
        List<Territory> territories = territoryService.listTerritories();

        List<AssignmentSummaryDTO> assignmentSummaryDTOList = new ArrayList<>();

        List<Territory> territoriesWithAssignments = territories.stream()
                .sorted(Comparator.comparing(territory -> Integer.valueOf(territory.getNumber())))
                .filter( territory -> assignments.stream().anyMatch(assignment ->
                        assignment.getTerritory().getId().equals(territory.getId())
                )
        ).toList();

        territoriesWithAssignments.forEach(territory -> {
            AssignmentSummaryDTO assignmentSummaryDTO = new AssignmentSummaryDTO();
            assignmentSummaryDTO.setTerritoryName(territory.getName());
            assignmentSummaryDTO.setTerritoryNumber(territory.getNumber());
            assignmentSummaryDTO.setTerritoryWarningMessage(territory.getWarningMessage());

            assignments.stream()
                    .filter(assignment -> assignment.getTerritory().getId().equals(territory.getId()))
                    .findFirst()
                    .ifPresent(assignment -> {
                        assignmentSummaryDTO.setId(assignment.getId());
                        assignmentSummaryDTO.setAssignedToPersonId(assignment.getAssignedTo().getId());
                        assignmentSummaryDTO.setAssignedToPersonName(assignment.getAssignedTo().getName());
                        assignmentSummaryDTO.setAssignmentDate(assignment.getAssignedAt());
                    });

            assignmentSummaryDTOList.add(assignmentSummaryDTO);
        });

        List<Territory> territoriesWithoutAssignments = territories.stream()
                .sorted(Comparator.comparing(territory -> Integer.valueOf(territory.getNumber())))
                .filter( territory -> assignments.stream().noneMatch(assignment ->
                        assignment.getTerritory().getId().equals(territory.getId())
                )
        ).toList();

        territoriesWithoutAssignments.forEach(territory -> {
            AssignmentSummaryDTO assignmentSummaryDTO = new AssignmentSummaryDTO();
            assignmentSummaryDTO.setTerritoryName(territory.getName());
            assignmentSummaryDTO.setTerritoryNumber(territory.getNumber());

            assignmentSummaryDTOList.add(assignmentSummaryDTO);
        });

        return assignmentSummaryDTOList;
    }

    public AssignmentSummaryDTO create(AssignmentSummaryDTO assignmentSummaryDTO) {
        Person person = personService.findById(assignmentSummaryDTO.getAssignedToPersonId());
        Territory territory = territoryService.findByNumber(assignmentSummaryDTO.getTerritoryNumber());
        territory.setWarningMessage(assignmentSummaryDTO.getTerritoryWarningMessage());
        Assignment assignment = Assignment.builder()
                .assignedTo(person)
                .assignedAt(assignmentSummaryDTO.getAssignmentDate())
                .completedAt(assignmentSummaryDTO.getCompletedDate())
                .territory(territory)
                .build();

        Assignment savedAssignment = assignmentRepository.save(assignment);

        return AssignmentSummaryDTO.builder()
                .id(savedAssignment.getId())
                .territoryNumber(savedAssignment.getTerritory().getNumber())
                .territoryName(savedAssignment.getTerritory().getName())
                .assignedToPersonId(savedAssignment.getAssignedTo().getId())
                .assignmentDate(savedAssignment.getAssignedAt())
                .completedDate(savedAssignment.getCompletedAt())
                .territoryWarningMessage(savedAssignment.getTerritory().getWarningMessage())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public AssignmentSummaryDTO update(AssignmentSummaryDTO assignmentSummaryDTO) {

        Territory territory = territoryService.findByNumber(assignmentSummaryDTO.getTerritoryNumber());

        Optional<Assignment> assignmentOptional = assignmentRepository.findByTerritoryIdAndCompletedAtNull(territory.getId());

        if (assignmentOptional.isEmpty()) {
            return create(assignmentSummaryDTO);
        }

        Assignment assignment = assignmentOptional.get();

        if (assignmentSummaryDTO.getAssignedToPersonId() != null) {
            Person person = personService.findById(assignmentSummaryDTO.getAssignedToPersonId());
            assignment.setAssignedTo(person);
        }

        if (assignmentSummaryDTO.getAssignmentDate() != null) {
            assignment.setAssignedAt(assignmentSummaryDTO.getAssignmentDate());
        }

        if (assignmentSummaryDTO.getCompletedDate() != null) {
            assignment.setCompletedAt(assignmentSummaryDTO.getCompletedDate());
        }

        if (assignmentSummaryDTO.getTerritoryNumber() != null) {
            assignment.setTerritory(territory);
        }

        if (assignmentSummaryDTO.getTerritoryName() != null) {
            territory.setName(assignmentSummaryDTO.getTerritoryName());
        }

        if (assignmentSummaryDTO.getTerritoryWarningMessage() != null) {
            territory.setWarningMessage(assignmentSummaryDTO.getTerritoryWarningMessage());
        }

        Assignment updatedAssignment = assignmentRepository.save(assignment);

        if (updatedAssignment.getCompletedAt() != null) {
            linkService.invalidateLink(updatedAssignment.getTerritory().getNumber(), Role.CONDUCTOR);
        }

        return AssignmentSummaryDTO.builder()
                .id(updatedAssignment.getId())
                .territoryNumber(updatedAssignment.getTerritory().getNumber())
                .territoryName(updatedAssignment.getTerritory().getName())
                .assignedToPersonId(updatedAssignment.getAssignedTo().getId())
                .assignmentDate(updatedAssignment.getAssignedAt())
                .completedDate(updatedAssignment.getCompletedAt())
                .territoryWarningMessage(updatedAssignment.getTerritory().getWarningMessage())
                .build();
    }

}
