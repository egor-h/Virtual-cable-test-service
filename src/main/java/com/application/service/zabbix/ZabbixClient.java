package com.application.service.zabbix;

import com.application.service.zabbix.request.BaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ZabbixClient {
    private static final Logger logger = LoggerFactory.getLogger(ZabbixClient.class);

    private AtomicInteger requestId;
    private RestTemplate rest;

    private String url;

    public ZabbixClient(AtomicInteger requestId, RestTemplate rest, String url) {
        this.requestId = requestId;
        this.rest = rest;
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public <T, R> R request(String method, T body, Class<R> responseEntity, String auth) {
        BaseRequest<T> request = new BaseRequest<>(method, body, requestId.getAndIncrement(), auth);
        logger.trace("Request to {}", url);
        var response = rest.postForEntity(url, request, responseEntity);
        logger.info("Response {}", response);
        return response.getBody();
    }
}
