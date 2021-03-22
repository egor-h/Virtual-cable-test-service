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

    @Autowired
    public ZabbixServiceImpl(ApplicationConfigurationProperties config) {
        this.config = config;
        this.rest = new RestTemplate();
    }

    @PostConstruct
    void init() {
        auth();
    }

    public <T, R> R request(String method, T body, Class<R> responseEntity) {
        BaseRequest<T> request = new BaseRequest<>(method, body, requestId.getAndIncrement(), auth);
        logger.trace("Request to {}", config.getZabbix().getUrl()+ZABBIX_API);
        var response = rest.postForEntity(config.getZabbix().getUrl()+ZABBIX_API, request, responseEntity);
        logger.info("Response {}", response);
        return response.getBody();
    }

    public void auth() {
        var authRequest = new AuthRequest(config.getZabbix().getUsername(), config.getZabbix().getPassword());
        var response = request(AuthRequest.METHOD_NAME, authRequest, AuthResponse.class);
        auth = response.getResult();
    }

    public GroupsResponse groups() {
        var groupRequest = new GroupsRequest("extend");
        var response = request(GroupsRequest.METHOD_NAME, groupRequest, GroupsResponse.class);
        return response;
    }

    public HostsResponse hosts() {
        var hostsRequest = new HostsRequest(List.of("hostid", "host", "name"), List.of("interfaceid", "ip"));
        var response = request(HostsRequest.METHOD_NAME, hostsRequest, HostsResponse.class);
        return response;
    }

    public HostsResponse hosts(String groupId) {
        var hostsRequest = new HostsByGroupRequest(
                List.of("hostid", "host", "name"), List.of("interfaceid", "ip"), List.of(groupId));
        var response = request(HostsByGroupRequest.METHOD_NAME, hostsRequest, HostsResponse.class);
        return response;
    }
}
