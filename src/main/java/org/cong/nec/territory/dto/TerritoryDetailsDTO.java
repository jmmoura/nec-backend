package org.cong.nec.territory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cong.nec.block.dto.BlockSummaryDTO;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TerritoryDetailsDTO {

    private Long id;
    private String territoryName;
    private String territoryNumber;
    private String assignedTo;
    private LocalDate assignmentDate;
    private Integer territoryTotalHouses;
    private Integer territoryVisitedHouses;
    private String territoryWarningMessage;
    private String territoryMapPath;
    private List<BlockSummaryDTO> blocks;

}
