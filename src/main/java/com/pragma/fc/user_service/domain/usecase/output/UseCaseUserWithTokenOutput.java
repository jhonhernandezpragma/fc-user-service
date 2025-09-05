package com.pragma.fc.user_service.domain.usecase.output;

import com.pragma.fc.user_service.domain.model.User;

public class UseCaseUserWithTokenOutput {
    private User user;
    private String accessToken;
    private String refreshToken;

    public UseCaseUserWithTokenOutput() {
    }

    public UseCaseUserWithTokenOutput(User user, String accessToken, String refreshToken) {
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
