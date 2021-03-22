package com.application.service.zabbix.request;

import java.util.List;

public class GroupsRequest {
    public static final String METHOD_NAME = "hostgroup.get";

    private String output;

    public GroupsRequest() {
    }

    public GroupsRequest(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "GroupsRequest{" +
                "output=" + output +
                '}';
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
