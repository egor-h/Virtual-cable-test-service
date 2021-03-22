package com.application.service.zabbix.request;

import java.util.List;

public class HostsRequest {
    public static final String METHOD_NAME = "host.get";

    private List<String> output;
    private List<String> selectInterfaces;

    public HostsRequest(List<String> output, List<String> selectInterfaces) {
        this.output = output;
        this.selectInterfaces = selectInterfaces;
    }

    public HostsRequest() {
    }

    @Override
    public String toString() {
        return "HostsRequest{" +
                "output=" + output +
                ", selectInterfaces=" + selectInterfaces +
                '}';
    }

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(List<String> output) {
        this.output = output;
    }

    public List<String> getSelectInterfaces() {
        return selectInterfaces;
    }

    public void setSelectInterfaces(List<String> selectInterfaces) {
        this.selectInterfaces = selectInterfaces;
    }
}
