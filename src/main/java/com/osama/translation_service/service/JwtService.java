package com.osama.translation_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String JWT_SECRET = "57a651fd41c07661c3c8914fe0baafccdcb26a4a7a831ec9b0497a10db3168bf";
    private static final int twentyFourHours = 1000 * 60 * 24;

    public String generateJwt(UserDetails userDetails) {
        return generateJwt(new HashMap<>(), userDetails);
    }

    public String generateJwt(Map<String, Object> claims,
                              UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + twentyFourHours))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isJwtValid(String jwt, UserDetails userDetails) {
        final String userName = getUsername(jwt);
        return (userName.equals(userDetails.getUsername()) && !isJwtExpired(jwt));
    }

    public String getUsername(String jwt) {
        return getClaim(jwt, Claims::getSubject);
    }

    private Claims getClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private <T> T getClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isJwtExpired(String jwt) {
        return getClaim(jwt, Claims::getExpiration)
                .before(new Date());
    }

}
