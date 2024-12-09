package com.linktic.app.service;


import com.linktic.app.dto.auth.*;
import com.linktic.app.repository.ProfileRepository;
import com.linktic.app.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private TokenUtils tokenUtils;
    @Value("${auth.jwt.access-token-duration}")
    private Long ACCES_TOKEN_VALIDITY_SECONDS;
    @Value("${auth.jwt.refresh-token-duration}")
    private Long REFRESH_TOKEN_VALIDITY_SECONDS;


    public TokenResponse generateAccesToken(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (request.getUserName(),request.getPassword()));
        String tokenAccess = tokenUtils.generateAccesToken(request.getUserName());
        String tokenRefresh=tokenUtils.generateRefreshToken(request.getUserName());

        return new TokenResponse(new TokenAccess(tokenAccess,ACCES_TOKEN_VALIDITY_SECONDS)
                ,new TokenRefresh(tokenRefresh,REFRESH_TOKEN_VALIDITY_SECONDS));
    }

    public TokenAccess generateTokenWithRefreshToken(RequestTokenRefresh request){
        String tokenAccess= tokenUtils.getAuthRefresh(request.getTokenRefresh());
        return new TokenAccess(tokenAccess,ACCES_TOKEN_VALIDITY_SECONDS);
    }
}
