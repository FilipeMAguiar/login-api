package dev.filipe.loginapi.domain;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank
        String email,
        @NotBlank
        String password) {
}
