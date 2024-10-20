package com.example.gestion_rhbackend.security;

import com.example.gestion_rhbackend.enums.RoleEnum;
import com.example.gestion_rhbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private JWTAuthFilter jwtAuthFilter;
    @Autowired
    private UserRepository userRepository;
    //configurer les authorite pour les roles et faire le controle d accee
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request->request
                        .requestMatchers("/auth/**","/public/**").permitAll()
                        .requestMatchers("/rh/**").hasAnyAuthority(RoleEnum.RH.name())
                        .requestMatchers("/conges/**").permitAll()
                        .requestMatchers("/employe/**").hasAnyAuthority(RoleEnum.EMPLOYE.name())
                        .requestMatchers("/user/**").hasAnyAuthority(RoleEnum.RH.name(),RoleEnum.EMPLOYE.name())
                        .anyRequest().authenticated())
                //puis cree une session avec STATELESS et non pas STATEFULL (on travail avec jwt)
                .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // et apres on cree le filtre sur les requetes pour verifier authentification
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        /*
        cela permet de faire verifier a partir de daoAuthProvider qui recuper les infos du user a partir d une base de donnees avec
        les donnees de userDetailsService
        */
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    //Bcrypt est un algo de hashage tres robuste car il utilise un grand nombre de tour pour hasher un mot de passe
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
