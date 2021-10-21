package com.felipe.garrido.clientCRUD.payload.request;


public class LoginRequest {

    private String rut;

    private String password;

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequest(String rut, String password) {
        this.rut = rut;
        this.password = password;
    }
}
