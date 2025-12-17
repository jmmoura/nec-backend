package org.cong.nec.territory.controller;

import lombok.AllArgsConstructor;
import org.cong.nec.territory.dto.TerritoryDetailsDTO;
import org.cong.nec.territory.service.TerritoryService;
import org.cong.nec.territory.dto.TerritorySummaryDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(TerritoryController.PATH)
@AllArgsConstructor
public class TerritoryController {

    public static final String PATH = "/api/v1/territory";

    private TerritoryService territoryService;

    @GetMapping
    public List<TerritorySummaryDTO> findAll() {
        return territoryService.findAll();
    }

    @GetMapping("/{id}")
    public TerritoryDetailsDTO findById(@PathVariable Long id) {
        return territoryService.findById(id);
    }

}
