package com.example.gestion_rhbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtils {
    private SecretKey Key;
    private static final long EXPIRATION_TIME=876000;
    @Value("${Gestion_RH-backend.Secret}")
    private String secretString;
    public JWTUtils() {
        byte[] KeyBytes= Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(KeyBytes,"HmacSHA256");
    }
    public String generateToken(UserDetails userDetails){ //userDetails cad qu'on va cree un jeton a partir du details du user
        return Jwts.builder().subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }
    public String generateRefreshToken(HashMap<String,Object> claims,UserDetails userDetails){
        return Jwts.builder()
                //claims sont des informations supplimentaire q'on veux partager concernant le user
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }
    public <T> T extractClaims(String token, Function<Claims,T> claimsTFunction){
        /* puisque on sait pas le type qu'on veux recuperer a partir duu claims on declare la methode comme une methode generique selon le retoure et pour
        Function<Claims,T> claimsTFunction c est une methode fonctionnelle qui prend en param claims et retourne T et pour claimsFunction c est une fonctionne de la classe claims par exemple :getSubject/getExpiration ...
        */
        //cela fait verifier d'abord la signature du token e apres on fait recuperation de payload
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
    }
    public String extractUserName(String token){
        /*Claims::getSubject => claims est une classe de la biblio jjwt et getSubject c est une methode deja predefinie qui permet de recuperer le subject"nom du user a partir des claims
        Claims::getSubject c est comme si on fait Function<Claims,T> getSubject{}...
         */
        return extractClaims(token, Claims::getSubject);
    }
    public boolean isTokenExpired(String token){
        //fait un check sur la date d'expiration si elle egale la date actuelle
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        //verifier l'identite du user et puis verifier si le token est expirer ou pas encore
        final String userName=extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
