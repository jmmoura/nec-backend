package org.cong.nec.address.controller;

import lombok.AllArgsConstructor;
import org.cong.nec.address.dto.AddressDTO;
import org.cong.nec.address.service.AddressService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AddressController.PATH)
@AllArgsConstructor
public class AddressController {

    public static final String PATH = "/api/v1/address";

    private AddressService addressService;

    @PutMapping
    public AddressDTO update(@RequestBody AddressDTO addressDTO) {
        return addressService.update(addressDTO);
    }

}
