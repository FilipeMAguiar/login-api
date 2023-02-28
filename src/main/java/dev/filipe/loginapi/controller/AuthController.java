package dev.filipe.loginapi.controller;

import dev.filipe.loginapi.domain.AuthenticationDTO;
import dev.filipe.loginapi.domain.TokenJwtDTO;
import dev.filipe.loginapi.entity.UserEntity;
import dev.filipe.loginapi.service.TokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenJwtDTO> doLogin(@RequestBody @Valid AuthenticationDTO authDto) {
        var authToken = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
        var auth = authManager.authenticate(authToken);
        var jwtToken = tokenService.generateToken((UserEntity) auth.getPrincipal());
        return ResponseEntity.ok(new TokenJwtDTO(jwtToken));
    }
}
