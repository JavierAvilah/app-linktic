package com.linktic.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtils {

    @Value("${auth.jwt.secret}")
    private String ACCES_TOKEN_SECRET;
    @Value("${auth.jwt.access-token-duration}")
    private Long ACCES_TOKEN_VALIDITY_SECONDS;
    @Value("${auth.jwt.refresh-token-duration}")
    private Long REFRESH_TOKEN_VALIDITY_SECONDS;


    public String generateAccesToken(String userName){
        long expirationTime =ACCES_TOKEN_VALIDITY_SECONDS*1000;
        Date expirationDate = new Date(System.currentTimeMillis()+expirationTime);


        return Jwts.builder()
                .setSubject(userName)
                .setHeaderParam("mod", "ACCESS")
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,ACCES_TOKEN_SECRET)
                .compact();
    }

    public String generateRefreshToken(String userName){
        long expirationTime =REFRESH_TOKEN_VALIDITY_SECONDS*1000;
        Date expirationDate = new Date(System.currentTimeMillis()+expirationTime);

        return Jwts.builder()
                .setSubject(userName)
                .setHeaderParam("mod", "REFRESH")
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,ACCES_TOKEN_SECRET)
                .compact();
    }

    public String getAuth(String token){

            Jws<Claims> claims=Jwts.parser()
                    .setSigningKey(ACCES_TOKEN_SECRET)
                    .parseClaimsJws(token);

            String userName =claims.getBody().getSubject();
            String mode = (String) claims.getHeader().get("mod");
            if (mode == null || !mode.equals("ACCESS")) {
                throw new SecurityException("Invalid token type");
            }
            return userName;
    }

    public String getAuthRefresh(String token){
            Jws<Claims> claims=Jwts.parser()
                    .setSigningKey(ACCES_TOKEN_SECRET)
                    .parseClaimsJws(token);

            String userName =claims.getBody().getSubject();
            String mode = (String) claims.getHeader().get("mod");
            if (mode == null || !mode.equals("REFRESH")) {
                throw new SecurityException("Invalid token type");
            }
            return generateAccesToken(userName);
    }
}
