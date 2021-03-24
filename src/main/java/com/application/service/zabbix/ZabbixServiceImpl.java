package com.application.service.zabbix;

import com.application.config.ApplicationConfigurationProperties;
import com.application.service.zabbix.request.*;
import com.application.service.zabbix.response.AuthResponse;
import com.application.service.zabbix.response.GroupsResponse;
import com.application.service.zabbix.response.HostsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ZabbixServiceImpl implements ZabbixService {
    private static final Logger logger = LoggerFactory.getLogger(ZabbixServiceImpl.class);
    private static final String ZABBIX_API = "/api_jsonrpc.php";

    private ApplicationConfigurationProperties config;
    private RestTemplate rest;

    private AtomicInteger requestId = new AtomicInteger(1);
    private String auth;
    private ZabbixClient zabbixClient;

    @Autowired
    public ZabbixServiceImpl(ApplicationConfigurationProperties config, String auth, ZabbixClient zabbixClient) {
        this.config = config;
        this.auth = auth;
        this.zabbixClient = zabbixClient;
    }

    @PostConstruct
    void init() {
        zabbixClient.setUrl(config.getZabbix().getUrl()+ZABBIX_API);
        auth();
    }

    public void auth() {
        var authRequest = new AuthRequest(config.getZabbix().getUsername(), config.getZabbix().getPassword());
        var response = zabbixClient.request(AuthRequest.METHOD_NAME, authRequest, AuthResponse.class, auth);
        auth = response.getResult();
    }

    public GroupsResponse groups() {
        var groupRequest = new GroupsRequest("extend");
        var response = zabbixClient.request(GroupsRequest.METHOD_NAME, groupRequest, GroupsResponse.class, auth);
        return response;
    }

    public HostsResponse hosts() {
        var hostsRequest = new HostsRequest(List.of("hostid", "host", "name"), List.of("interfaceid", "ip"));
        var response = zabbixClient.request(HostsRequest.METHOD_NAME, hostsRequest, HostsResponse.class, auth);
        return response;
    }

    public HostsResponse hosts(String groupId) {
        var hostsRequest = new HostsByGroupRequest(
                List.of("hostid", "host", "name"), List.of("interfaceid", "ip"), List.of(groupId));
        var response = zabbixClient.request(HostsByGroupRequest.METHOD_NAME, hostsRequest, HostsResponse.class, auth);
        return response;
    }
}
