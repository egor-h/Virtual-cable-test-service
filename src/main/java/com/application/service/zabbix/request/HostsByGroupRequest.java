package com.application.service.zabbix.request;

import java.util.List;

public class HostsByGroupRequest extends HostsRequest {
    public static final String METHOD_NAME = "host.get";

    private List<String> groupids;

    public HostsByGroupRequest(List<String> output, List<String> selectInterfaces, List<String> groupIds) {
        super(output, selectInterfaces);
        this.groupids = groupIds;
    }

    public HostsByGroupRequest() {
    }

    @Override
    public String toString() {
        return "HostsByGroupRequest{" +
                "groupIds=" + groupids +
                '}';
    }

    public List<String> getGroupids() {
        return groupids;
    }

    public void setGroupids(List<String> groupids) {
        this.groupids = groupids;
    }
}
