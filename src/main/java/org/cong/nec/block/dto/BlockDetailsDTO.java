package org.cong.nec.block.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cong.nec.address.dto.AddressDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockDetailsDTO {

    private Long id;
    private String name;
    private List<AddressDTO> addressList;

}
