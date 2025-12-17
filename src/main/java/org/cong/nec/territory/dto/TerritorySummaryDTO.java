package org.cong.nec.territory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TerritorySummaryDTO {

    private Long id;
    private String name;
    private String number;

}
