package com.camelsoft.scano.client.Response;

import com.camelsoft.scano.client.models.Auth.users;

public class CustomSignupResponse {
    private users user;
    private String token;
    private String refreshtoken;

    public CustomSignupResponse(users user, String token, String refreshtoken) {
        this.user = user;
        this.token = token;
        this.refreshtoken = refreshtoken;
    }

    public CustomSignupResponse() {
    }

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public void setRefreshtoken(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }
}
