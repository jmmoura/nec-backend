package org.cong.nec.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentSummaryDTO {

    private Long id;
    private String territoryName;
    private String territoryNumber;
    private Long assignedToPersonId;
    private String assignedToPersonName;
    private LocalDate assignmentDate;
    private LocalDate completedDate;
    private String territoryWarningMessage;

}
