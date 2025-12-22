package org.cong.nec.block.service;

import lombok.AllArgsConstructor;
import org.cong.nec.address.dto.AddressDTO;
import org.cong.nec.address.model.Address;
import org.cong.nec.block.dto.BlockDetailsDTO;
import org.cong.nec.block.model.Block;
import org.cong.nec.block.repository.BlockRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@AllArgsConstructor
public class BlockService {

    private BlockRepository blockRepository;

    public BlockDetailsDTO findDetailsDTOById(Long id) {
        Block block = findById(id);

        return BlockDetailsDTO.builder()
                .id(block.getId())
                .name(block.getName())
                .addressList(block.getAddresses().stream()
                        .sorted(Comparator.comparing(Address::getId))
                        .map(address -> AddressDTO.builder()
                                .id(address.getId())
                                .street(address.getStreet())
                                .number(address.getNumber())
                                .visitedAt(address.getVisitedAt())
                                .visitUnallowed(address.getVisitUnallowedAt() != null)
                                .blockId(block.getId())
                                .build())
                        .toList())
                .build();
    }

    public Block findById(Long id) {
        return blockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Block not found with id: " + id));
    }

}

