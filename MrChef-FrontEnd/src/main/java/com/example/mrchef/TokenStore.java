package com.example.mrchef;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope  // Esta anotación hace que el componente persista durante toda la sesión del usuario
public class TokenStore {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}