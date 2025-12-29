package org.cong.nec.linkgenerator.controller;

import lombok.AllArgsConstructor;
import org.cong.nec.linkgenerator.dto.LinkRequestDTO;
import org.cong.nec.linkgenerator.dto.SharedLinkDTO;
import org.cong.nec.linkgenerator.service.LinkService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(LinkController.PATH)
@AllArgsConstructor
public class LinkController {

    public static final String PATH = "/api/v1/link";

    private LinkService linkService;

    @PostMapping("/generate")
    public SharedLinkDTO generateLink(@RequestBody final LinkRequestDTO linkRequestDTO) {
        return linkService.generateLink(linkRequestDTO);
    }

}
