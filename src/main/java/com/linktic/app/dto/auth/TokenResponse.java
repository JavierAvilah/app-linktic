package com.linktic.app.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TokenResponse {

    private TokenAccess tokenAccess;
    private TokenRefresh tokenRefresh;
}
