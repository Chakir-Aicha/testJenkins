package com.example.gestion_rhbackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
    /*cette classe est appeller a chaque fois qu'on fait une requete HTTP c est a dire que lorsqu'on envoie une requette HTTP le token sera aussi envoyer est donc on va verifier la validite du token
    et l identite de son proprietaire
    //ce filtre permet d authentifier user a partir du token envoyer en header
    -OncePerRequestFilter c est a dire que le filtre sera applique une seule fois par rquete
    */
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserDetailsService userDetailsService; //injecter une instance de userDetailsService qui contient une methode pour recuperer le UserDetails d'un user a partir de son nom qui est le email

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); //requperer le header
        final String jwtToken;
        final String userEmail;
        //si jwt est vide on fait passer a un autre filtre puisque pas necessirement que tous les requette necessite une authetification
        //ici si la requette n a pas de bearer pour envoyer le token on va juste laisser passer la requete
        if (authHeader == null || authHeader.isBlank()){
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken = authHeader.substring(7);// "Bearer " c est a partir du caractere num 7
        userEmail=jwtUtils.extractUserName(jwtToken); // recuperer le mail du user a partir du token
        //ici on dois verifier d abord que le user n est pas encore authentifier si oui on va l'authentifier on creant un securityContext a partir de ses informations
        //si le user est deja authentifier on laisse passer la requette
        if (userEmail!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails= userDetailsService.loadUserByUsername(userEmail);
            if(jwtUtils.isTokenValid(jwtToken,userDetails)){
                SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request,response);
    }
}
