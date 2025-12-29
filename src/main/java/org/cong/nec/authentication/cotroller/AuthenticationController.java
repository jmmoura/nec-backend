package org.cong.nec.authentication.cotroller;

import lombok.AllArgsConstructor;
import org.cong.nec.authentication.dto.AuthenticationDTO;
import org.cong.nec.authentication.dto.CredentialsDTO;
import org.cong.nec.authentication.service.SecurityService;
import org.cong.nec.linkgenerator.dto.SharedLinkDTO;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping(AuthenticationController.URL)
@RestController
public class AuthenticationController {

    public static final String URL = "api/v1/login";

    private final SecurityService securityService;

    @PostMapping("/token")
    public AuthenticationDTO authenticate(@RequestBody SharedLinkDTO sharedLinkDTO) {
        return securityService.authenticate(sharedLinkDTO);
    }

    @PostMapping
    public AuthenticationDTO login(@RequestBody CredentialsDTO credentialsDTO) {
        final String username = credentialsDTO.getUsername();
        final String password = credentialsDTO.getPassword();

        return securityService.authenticate(username, password);
    }
}
