package com.finalproject.assetmanagement.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    @Value("${asset.jwt.secret}")
    private String jwtSecret;
    @Value("${asset.jwt.expiration}")
    private Long jwtExpiration;

    public String getEmailByToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody().getSubject();
    }
    public String generateToken(String email){
        return  Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes(StandardCharsets.UTF_8))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .compact();
    }

    public Boolean validateJwtToken(String token){

            try {
                 Jwts.parser().setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
                 return true;
            }catch (MalformedJwtException e){
                log.error("Invalid Jwt Token", e.getMessage());
            }catch (ExpiredJwtException e){
                log.error("Jwt Token is Expired", e.getMessage());
            }catch (UnsupportedJwtException e) {
                log.error("Unsupported Jwt token", e.getMessage());
            } catch (IllegalArgumentException e){
                    log.error("Jwt claims is empty", e.getMessage());
            }
                return false;
        }
    }
