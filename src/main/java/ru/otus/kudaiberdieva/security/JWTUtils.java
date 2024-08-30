package ru.otus.kudaiberdieva.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Level;

@Service
public class JWTUtils {

   // private static final Logger logger = Logger.getLogger(JWTUtils.class.getName());
    @Value("${jwt-secret}")
    private String jwtSecret;

    @Value("${jwt-expiration-ms}")
    private int jwtExpMS;


    public String generateJwtToken(MyUserDetails myUserDetails){
        return Jwts.builder() //puts it into token structure
                .setSubject(myUserDetails.getUsername()) // just the email address
                .setIssuedAt(new Date()) // sets it with today's date
                .setExpiration(new Date(new Date().getTime() + jwtExpMS)) // adds expiration to today's date
                .signWith(SignatureAlgorithm.HS256, jwtSecret) //header and secret info
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (SecurityException e) {
         //   logger.info((Marker) Level.SEVERE, "Invalid JWT signature: {0}", e.getMessage());
        } catch (MalformedJwtException e) {
          //  logger.info((Marker) Level.SEVERE, "Invalid JWT token: {0}", e.getMessage());
        } catch (ExpiredJwtException e) {
          //  logger.info((Marker) Level.SEVERE, "JWT token is expired: {0}", e.getMessage());
        } catch (UnsupportedJwtException e) {
         //   logger.info((Marker) Level.SEVERE, "JWT token is unsupported: {0}", e.getMessage());
        } catch (IllegalArgumentException e) {
           // logger.info((Marker) Level.SEVERE, "JWT claims string is empty: {0}", e.getMessage());
        }
        return false;
    }
}