package org.cong.nec.authentication.service;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.cong.nec.authentication.dto.AuthenticationDTO;
import org.cong.nec.authentication.dto.UserDTO;
import org.cong.nec.authentication.enums.Role;
import org.cong.nec.authentication.model.User;
import org.cong.nec.authentication.repository.UserRepository;
import org.cong.nec.authentication.utils.JWTUtils;
import org.cong.nec.errorhandler.exception.EntityNotFoundException;
import org.cong.nec.linkgenerator.dto.SharedLinkDTO;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SecurityService implements UserDetailsService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public static final String TERRITORY_USERNAME_PATTERN = "territory_%NUMBER%_%ROLE%";

    @Autowired
    public SecurityService(@Lazy AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Transactional
    public AuthenticationDTO authenticate(final String username, final String password) {
        final Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password)
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final User user = getCurrentUser();

        return AuthenticationDTO.builder()
                .accessToken(JWTUtils.generateToken(user.getId(), user.getUsername(), user.getTerritoryNumber() , user.getRole()))
                .tokenType(JWTUtils.TOKEN_PREFIX)
                .expiresIn(JWTUtils.TOKEN_EXPIRATION)
                .user(UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .territoryNumber(user.getTerritoryNumber())
                        .role(user.getRole())
                        .build())
                .build();
    }

    @Transactional
    public void authenticate(final String token) {
        final Claims claims = JWTUtils.parseToken(token);

        User user = new User();
        user.setId(Long.parseLong(claims.getSubject()));
        user.setPassword("");
        user.setUsername(claims.get(JWTUtils.TOKEN_CLAIM_USERNAME).toString());
        user.setTerritoryNumber(claims.get(JWTUtils.TOKEN_CLAIM_TERRITORY_NUMBER).toString());
        user.setRole(Role.valueOf(claims.get(JWTUtils.TOKEN_CLAIM_ROLES).toString()));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
        );
    }

    @Transactional
    public AuthenticationDTO authenticate(final SharedLinkDTO sharedLinkDTO) {
        final Claims claims = JWTUtils.parseToken(sharedLinkDTO.getAccess());
        User user = loadUserByUsername(claims.get(JWTUtils.TOKEN_CLAIM_USERNAME).toString());

        if (user.getRole() == Role.CONDUCTOR && user.getLoginTime() != claims.getIssuedAt().getTime()) {
            throw new CredentialsExpiredException("Invalid token");
        }

        return AuthenticationDTO.builder()
                .accessToken(sharedLinkDTO.getAccess())
                .tokenType(JWTUtils.TOKEN_PREFIX)
                .expiresIn(JWTUtils.TOKEN_EXPIRATION)
                .user(UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .territoryNumber(user.getTerritoryNumber())
                        .role(user.getRole())
                        .build())
                .build();

    }

    @Transactional
    public User getCurrentUser() {
        return (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }

    @Transactional
    @Override
    public User loadUserByUsername(@NonNull String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User save(final User user) {
        return userRepository.save(user);
    }

}
