package com.application.service.zabbix.request;

public class BaseRequest<T> {
    private String jsonrpc;
    private String method;
    private T params;
    private long id;
    private String auth;

    public BaseRequest(String method, T params, long id, String auth) {
        this.jsonrpc = "2.0";
        this.method = method;
        this.params = params;
        this.id = id;
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "jsonrpc='" + jsonrpc + '\'' +
                ", method='" + method + '\'' +
                ", params=" + params +
                ", id=" + id +
                ", auth='" + auth + '\'' +
                '}';
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
