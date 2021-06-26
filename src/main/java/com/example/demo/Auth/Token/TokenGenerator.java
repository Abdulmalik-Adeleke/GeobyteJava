package com.example.demo.Auth.Token;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.example.demo.Auth.AuthModel.Role;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenGenerator {
    
    private static final String base64EncodedSecretKey = "MySecretKeyInAwsSecretsManager";

    public String generateToken(String email, Role role)
    {
        Date expiration = Date.from(LocalDate.now().plusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
        .setSubject(email)
        .claim("scope", role)
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
        .compact();
    }
    public boolean validateToken(String token) 
    {
        try {
            Jwts.parser()
            .setSigningKey(base64EncodedSecretKey)
            .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            //log
            System.out.println();
            System.out.println(e.getMessage());
            return false;
        }
        catch(UnsupportedJwtException e){
            System.out.println();
            System.out.println(e.getMessage());
            return false;
        }
        catch(MalformedJwtException e){
            System.out.println();
            System.out.println(e.getMessage());
            return false;
        }
        catch(SignatureException e){
            System.out.println();
            System.out.println(e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            System.out.println();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
        
    }

    public String getSubjectFromToken(String token) 
    {
        Claims claims =  Jwts.parser()
        .setSigningKey(base64EncodedSecretKey)
        .parseClaimsJws(token).getBody();
        return claims.getSubject(); 
    }

    
}
