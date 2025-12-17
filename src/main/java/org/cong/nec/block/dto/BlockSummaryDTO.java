package org.cong.nec.block.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockSummaryDTO {

    private Long id;
    private String name;
    private String visitedAt;

}
