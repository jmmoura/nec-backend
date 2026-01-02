package org.cong.nec.address.service;

import lombok.AllArgsConstructor;
import org.cong.nec.address.dto.AddressDTO;
import org.cong.nec.address.dto.AddressListDTO;
import org.cong.nec.address.dto.AddressResetDTO;
import org.cong.nec.address.model.Address;
import org.cong.nec.address.repository.AddressRepository;
import org.cong.nec.block.model.Block;
import org.cong.nec.block.service.BlockService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {

    private AddressRepository addressRepository;

    private BlockService blockService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CONDUCTOR', 'PUBLISHER')")
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

    @PreAuthorize("hasRole('ADMIN')")
    public AddressListDTO resetAddresses(AddressResetDTO addressResetDTO) {
        List<Address> addresses = addressRepository.findByTerritoryNumber(addressResetDTO.getTerritoryNumber());

        List<Address> addressReset = addresses.stream().map(address ->
            Address.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .visitedAt(null)
                .visitUnallowedAt(address.getVisitUnallowedAt())
                .block(address.getBlock())
                .build()
        ).toList();

        List<Address> updatedAddresses = addressRepository.saveAll(addressReset);

        return AddressListDTO.builder()
                .addressIds(updatedAddresses.stream().map(Address::getId).toList())
                .build();
    }
}
