package com.application.model;


public class Host {
    private long hostId;
    private String name;
    private String group;
    private String ip;
    private int port;
    private boolean enabled;

    public Host() {
    }

    public Host(long hostId, String name, String group, String ip, int port, boolean enabled) {
        this.hostId = hostId;
        this.name = name;
        this.group = group;
        this.ip = ip;
        this.port = port;
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Host{" +
                "hostId=" + hostId +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", enabled=" + enabled +
                '}';
    }

    public long getHostId() {
        return hostId;
    }

    public void setHostId(long hostId) {
        this.hostId = hostId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
