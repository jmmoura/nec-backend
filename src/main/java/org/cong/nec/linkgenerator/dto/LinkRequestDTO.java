package org.cong.nec.linkgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cong.nec.authentication.enums.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkRequestDTO {

    private String territoryNumber;
    private Role role;

}
