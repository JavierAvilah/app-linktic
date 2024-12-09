package com.linktic.app.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TokenRefresh {

    @Schema(description = "token", example = "3L3DBnz4d8T8bMAEk" +
            "X8uityJEDZKXoucVa2Ynde6bdX8nhN8kfbJq8GSTLyY9QfJCwMeZnQSL1kt4EXoxD")
    private String tokenRefresh;
    @Schema(description = "duracion del token", example = "86400")
    private Long expiresIn;
}
