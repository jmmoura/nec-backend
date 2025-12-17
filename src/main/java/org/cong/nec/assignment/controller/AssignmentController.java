package org.cong.nec.assignment.controller;

import lombok.AllArgsConstructor;
import org.cong.nec.assignment.dto.AssignmentSummaryDTO;
import org.cong.nec.assignment.service.AssignmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AssignmentController.PATH)
@AllArgsConstructor
public class AssignmentController {

    public static final String PATH = "/api/v1/assignment";

    private AssignmentService assignmentService;

    @GetMapping("/current")
    public List<AssignmentSummaryDTO> findCurrentAssignments() {
        return assignmentService.findCurrentAssignments();
    }

    @PutMapping
    public AssignmentSummaryDTO updateAssignment(@RequestBody AssignmentSummaryDTO assignmentSummaryDTO) {
        return assignmentService.update(assignmentSummaryDTO);
    }

}
