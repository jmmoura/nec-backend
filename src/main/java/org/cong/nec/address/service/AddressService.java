package org.cong.nec.address.service;

import lombok.AllArgsConstructor;
import org.cong.nec.address.dto.AddressDTO;
import org.cong.nec.address.model.Address;
import org.cong.nec.address.repository.AddressRepository;
import org.cong.nec.block.model.Block;
import org.cong.nec.block.service.BlockService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private AddressRepository addressRepository;

    private BlockService blockService;

    public AddressDTO update(AddressDTO addressDTO) {
        Block block = blockService.findById(addressDTO.getBlockId());

        Address address = Address.builder()
                .id(addressDTO.getId())
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .visitedAt(addressDTO.getVisitedAt())
                .visitUnallowedAt(addressDTO.isVisitUnallowed() ? addressDTO.getVisitedAt() : null)
                .block(block)
                .build();

        Address addressUpdated = addressRepository.save(address);

        return AddressDTO.builder()
                .id(addressUpdated.getId())
                .street(addressUpdated.getStreet())
                .number(addressUpdated.getNumber())
                .visitedAt(addressUpdated.getVisitedAt())
                .visitUnallowed(addressUpdated.getVisitUnallowedAt() != null)
                .blockId(addressUpdated.getBlock().getId())
                .build();
    }
}
