package com.legado_barberia.security;
import org.springframework.context.annotation.*; import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*; import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
  private final JwtAuthFilter jwtFilter; public SecurityConfig(JwtAuthFilter f){this.jwtFilter=f;}
  @Bean PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
  @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf->csrf.disable())
        .authorizeHttpRequests(reg->reg.requestMatchers("/auth/**","/h2-console/**","/static/**").permitAll()
                                      .anyRequest().authenticated())
        .headers(h->h.frameOptions(f->f.disable()));
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
  @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
    return cfg.getAuthenticationManager();
  }
}
