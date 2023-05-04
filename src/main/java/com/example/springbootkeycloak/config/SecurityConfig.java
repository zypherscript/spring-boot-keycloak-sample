package com.example.springbootkeycloak.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@Slf4j
@EnableMethodSecurity
class SecurityConfig {

  private String[] allowedOrigins;

  @Autowired
  public void setAllowedOrigins(@Value("${cors.allowed-origins}") String[] allowedOrigins) {
    this.allowedOrigins = allowedOrigins;
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    log.info("CORS Allowed Origins: " + Arrays.toString(this.allowedOrigins));
    var configuration = new CorsConfiguration();
    configuration.setAllowedHeaders(
        Arrays.asList("Access-Control-Allow-Headers", "Origin", "Content-Type", "Accept",
            "Authorization"));
    configuration.setAllowedMethods(
        Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
    configuration.setAllowedOrigins(Arrays.asList(this.allowedOrigins));
    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(auth -> auth
            .anyRequest()
            .authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt()
            .jwtAuthenticationConverter(grantedAuthoritiesExtractor()))
        .cors(Customizer.withDefaults())
        .build();
  }

  private Converter<Jwt, AbstractAuthenticationToken> grantedAuthoritiesExtractor() {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new GrantedAuthoritiesExtractor());
    return jwtAuthenticationConverter;
  }

  @SuppressWarnings("unchecked")
  static class GrantedAuthoritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {

    public Collection<GrantedAuthority> convert(Jwt jwt) {
      var realmAccessMap = (Map<String, Collection<?>>) jwt.getClaims()
          .getOrDefault("realm_access", Collections.emptyMap());
      return realmAccessMap.getOrDefault("roles", Collections.emptyList())
          .stream()
          .map(Object::toString)
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());
    }
  }
}
