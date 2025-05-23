package com.example.mrchef;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private TokenStore tokenStore;
    
    @Value("${backend.url:http://localhost:8080}")
    private String backendUrl;

    public CustomAuthenticationProvider(TokenStore tokenStore) {
        super();
        this.tokenStore = tokenStore;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        System.out.println("Llegué Custom Authentication Provider: ");
        System.out.println("Authentication: " + authentication);
        final String name = authentication.getName();
        System.out.println("Name: " + name);
        final String password = authentication.getCredentials().toString();
        System.out.println("Password: " + password);

        System.out.println("Custom Authentication Provider: " + name);

        // Usar tipo específico para evitar advertencias
        final MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("user", name);
        requestBody.add("encryptedPass", password);

        System.out.println("Request Body: " + requestBody);

        final var restTemplate = new RestTemplate();
        final var responseEntity = restTemplate.postForEntity(backendUrl + "/login", requestBody, String.class);

        System.out.println("Response Entity: " + responseEntity);

        tokenStore.setToken(responseEntity.getBody());

        System.out.println("Token Store: " + tokenStore.getToken());

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new BadCredentialsException("Invalid username or password");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        Authentication authenticatedToken = new UsernamePasswordAuthenticationToken(name, password,
                authorities);

        return authenticatedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}