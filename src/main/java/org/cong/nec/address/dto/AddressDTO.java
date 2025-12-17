package org.cong.nec.address.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;
    private String street;
    private String number;
    private LocalDateTime visitedAt;
    private boolean visitUnallowed;
    private Long blockId;

}
