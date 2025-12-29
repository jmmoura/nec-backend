package org.cong.nec.territory.service;

import lombok.AllArgsConstructor;
import org.cong.nec.block.dto.BlockSummaryDTO;
import org.cong.nec.assignment.model.Assignment;
import org.cong.nec.assignment.repository.AssignmentRepository;
import org.cong.nec.territory.dto.TerritorySummaryDTO;
import org.cong.nec.territory.dto.TerritoryDetailsDTO;
import org.cong.nec.territory.model.Territory;
import org.cong.nec.territory.repository.TerritoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class TerritoryService {

    private TerritoryRepository territoryRepository;

    private AssignmentRepository assignmentService;

    public List<Territory> listTerritories() {
        return territoryRepository.findAll();
    }

    public Territory findByNumber(String number) {
        return territoryRepository.findByNumber(number);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<TerritorySummaryDTO> findAll() {
        List<Territory> territories = territoryRepository.findAll();
        return territories.stream()
                .sorted(Comparator.comparing(territory -> Integer.valueOf(territory.getNumber())))
                .map(territory -> TerritorySummaryDTO.builder()
                        .id(territory.getId())
                        .name(territory.getName())
                        .number(territory.getNumber())
                        .build())
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CONDUCTOR', 'PUBLISHER')")
    public TerritoryDetailsDTO findById(Long id) {
        Territory territory = territoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Territory not found with id: " + id));

        Assignment assignment = assignmentService.findByTerritoryIdAndCompletedAtNull(id).orElse(null);

        int territoryTotalHouses = territory.getBlocks().stream()
                .mapToInt(block -> block.getAddresses().size())
                .sum();

        int territoryVisitedHouses = Math.toIntExact(territory.getBlocks().stream()
                .flatMap(block -> block.getAddresses().stream())
                .filter(address -> address.getVisitedAt() != null).count());

        return TerritoryDetailsDTO.builder()
                .id(territory.getId())
                .territoryName(territory.getName())
                .territoryNumber(territory.getNumber())
                .territoryWarningMessage(territory.getWarningMessage())
                .assignedTo(assignment == null ? "" : assignment.getAssignedTo().getName())
                .assignmentDate(assignment == null ? null : assignment.getAssignedAt())
                .territoryTotalHouses(territoryTotalHouses)
                .territoryVisitedHouses(territoryVisitedHouses)
                .blocks(territory.getBlocks().stream().map(block ->  BlockSummaryDTO.builder()
                        .id(block.getId())
                        .name(block.getName())
                        .build()).toList())
                .build();
    }
}
