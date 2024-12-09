package com.linktic.app.controller;


import com.linktic.app.dto.auth.LoginRequest;
import com.linktic.app.dto.auth.RequestTokenRefresh;
import com.linktic.app.dto.auth.TokenAccess;
import com.linktic.app.dto.auth.TokenResponse;
import com.linktic.app.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "Authentication")
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("accessToken")
    @Operation(summary = "Get access token for end points")
    public TokenResponse generateAccesToken(@Valid @RequestBody LoginRequest request){
        return authService.generateAccesToken(request);
    }

    @PostMapping("accessTokenFromRefreshToken")
    @Operation(summary = "Get arefresh token from access token")
    public TokenAccess generateTokenWithRefreshToken(@Valid @RequestBody RequestTokenRefresh request){
        return authService.generateTokenWithRefreshToken(request);
    }
}
