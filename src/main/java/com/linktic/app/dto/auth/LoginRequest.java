package com.linktic.app.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LoginRequest {
    @NotBlank
    @Schema(description = "User name", example = "admin")
    private String userName;
    @NotBlank
    @Schema(description = "Password", example = "admin")
    private String password;
}
