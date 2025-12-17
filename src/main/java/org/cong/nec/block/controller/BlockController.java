package org.cong.nec.block.controller;

import lombok.AllArgsConstructor;
import org.cong.nec.block.dto.BlockDetailsDTO;
import org.cong.nec.block.service.BlockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BlockController.PATH)
@AllArgsConstructor
public class BlockController {

    public static final String PATH = "/api/v1/block";

    private BlockService blockService;

    @GetMapping("/{id}")
    public BlockDetailsDTO findById(@PathVariable Long id) {
        return blockService.findDetailsDTOById(id);
    }

}
