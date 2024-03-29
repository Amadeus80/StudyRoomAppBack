package com.studyroom.studyroomapp.auth.service;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyroom.studyroomapp.auth.SimpleGrantedAuthorityMixin;
import com.studyroom.studyroomapp.userDetails.CustomUserDetail;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
@Component
public class JWTServiceImpl implements JWTService{

    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static final long EXPIRATION_DATE = 3600000L;

    public static final String TOKEN_PREFIXE = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    /* Crea el token a partir de la autenticación */
    @Override
    public String create(Authentication auth) throws IOException {
        // 1 forma de recoger el nombre del usuario autenticado
        String username = ((CustomUserDetail) auth.getPrincipal()).getEmail();
        System.out.println("Este es el username " + username);

        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
        
        String token = Jwts
            .builder()
            .setClaims(claims)
            // 2 forma de recoger el nombre del usuario autenticado
            .setSubject(username)
            .signWith(SECRET_KEY)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))

            .compact();
            return token;
    }

    /* Comprueba la validez del token */
    @Override
    public boolean validate(String token) {
        try{
            getClaims(token);
            return true;
        }
        catch(JwtException | IllegalArgumentException e){
            return false;
        }
    }

    /* Obtiene los claims, en este caso los claims */
    @Override
    public Claims getClaims(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(resolve(token))
            .getBody();
        return claims;
    }

    /* Obtiene el username del usuario que envia el token */
    @Override
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    /* Obtiene los roles a través de los claims */
    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaims(token).get("authorities");

        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
            .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
            
        return authorities;
    }

    @Override
    public String resolve(String token) {
        if(token !=  null && token.startsWith(TOKEN_PREFIXE)){
            return token.replace(TOKEN_PREFIXE, "");
        }
        return null;
    }
    
}
