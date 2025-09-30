package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthToken {

    @JsonProperty("token")
    private String token;

    public AuthToken() {}

    public AuthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}