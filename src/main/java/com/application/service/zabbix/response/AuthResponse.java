package com.application.service.zabbix.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {
    private String result;

    public AuthResponse(String result) {
        this.result = result;
    }

    public AuthResponse() {
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "result='" + result + '\'' +
                '}';
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
