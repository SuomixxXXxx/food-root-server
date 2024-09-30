package org.chiches.foodrootservir.dto;

import jakarta.validation.constraints.NotBlank;

public class TokenDto {
    // не нужно тк такое только на выходе
    private String token;
    private String refreshToken;

    public TokenDto(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
