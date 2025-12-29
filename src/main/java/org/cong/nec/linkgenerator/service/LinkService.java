package org.cong.nec.linkgenerator.service;

import lombok.AllArgsConstructor;
import org.cong.nec.authentication.enums.Role;
import org.cong.nec.authentication.model.User;
import org.cong.nec.authentication.service.SecurityService;
import org.cong.nec.authentication.utils.JWTUtils;
import org.cong.nec.linkgenerator.dto.LinkRequestDTO;
import org.cong.nec.linkgenerator.dto.SharedLinkDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Service
@AllArgsConstructor
public class LinkService {

    private SecurityService securityService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CONDUCTOR')")
    public SharedLinkDTO generateLink(@RequestBody final LinkRequestDTO linkRequestDTO) {
        String username = "territory_" + linkRequestDTO.getTerritoryNumber() + "_" + linkRequestDTO.getRole().name().toLowerCase();
        User user = securityService.loadUserByUsername(username);
        String token;
        if (user.getRole() == Role.CONDUCTOR) {
            Long loginTime = (new Date().getTime() / 1000) * 1000L;
            user.setLoginTime(loginTime);
            token = JWTUtils.generateToken(
                    user.getId(),
                    user.getUsername(),
                    user.getTerritoryNumber(),
                    user.getRole(),
                    user.getLoginTime());
            securityService.save(user);
        } else {
            token = JWTUtils.generateToken(
                    user.getId(),
                    user.getUsername(),
                    user.getTerritoryNumber(),
                    user.getRole());
        }
        return SharedLinkDTO.builder()
                .access(token)
                .build();
    }

}
