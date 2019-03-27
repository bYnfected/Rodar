package com.example.android.rodar.models;

public class UsuarioLogin {

    private String username;
    private String password;
    private String grant_type = "password";

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
