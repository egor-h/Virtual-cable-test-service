package com.application.service.zabbix.request;

public class AuthRequest {
    public static final String METHOD_NAME = "user.login";
    private String user;
    private String password;

    public AuthRequest(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public AuthRequest() {
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
