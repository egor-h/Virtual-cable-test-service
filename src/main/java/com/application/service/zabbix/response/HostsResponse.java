package com.application.service.zabbix.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HostsResponse {

    private List<Host> result;

    public List<Host> getResult() {
        return result;
    }

    @JsonProperty("result")
    private void setResult(List<Map<String, Object>> result) {
        this.result = result.stream().map(hostMap -> {
            var host = new Host();
            if (hostMap.containsKey("hostid")) {
                host.setHostId((String) hostMap.get("hostid"));
            }
            if (hostMap.containsKey("host")) {
                host.setHost((String) hostMap.get("host"));
            }
            if (hostMap.containsKey("name")) {
                host.setName((String) hostMap.get("name"));
            }
            if (hostMap.containsKey("interfaces")) {
                host.setInterfaces(((List<Map>) hostMap.get("interfaces")).stream()
                        .map((Map itfc) -> new Interface((String) itfc.get("interfaceid"), (String) itfc.get("ip")))
                        .collect(Collectors.toList()));
            }
            return host;
        }).collect(Collectors.toList());
    }

    public static class Host {
        private String hostId;
        private String host;
        private String name;
        private List<Interface> interfaces;

        public Host(String hostId, String host, String name, List<Interface> interfaces) {
            this.hostId = hostId;
            this.host = host;
            this.name = name;
            this.interfaces = interfaces;
        }

        public Host() {
        }

        @Override
        public String toString() {
            return "Host{" +
                    "hostId='" + hostId + '\'' +
                    ", host='" + host + '\'' +
                    ", name='" + name + '\'' +
                    ", interfaces=" + interfaces +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHostId() {
            return hostId;
        }

        public void setHostId(String hostId) {
            this.hostId = hostId;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public List<Interface> getInterfaces() {
            return interfaces;
        }

        public void setInterfaces(List<Interface> interfaces) {
            this.interfaces = interfaces;
        }
    }

    public static class Interface {
        private String interfaceid;
        private String ip;

        public Interface(String interfaceid, String ip) {
            this.interfaceid = interfaceid;
            this.ip = ip;
        }

        public Interface() {
        }

        @Override
        public String toString() {
            return "Interface{" +
                    "interfaceid='" + interfaceid + '\'' +
                    ", ip='" + ip + '\'' +
                    '}';
        }

        public String getInterfaceid() {
            return interfaceid;
        }

        public void setInterfaceid(String interfaceid) {
            this.interfaceid = interfaceid;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
    }
}
