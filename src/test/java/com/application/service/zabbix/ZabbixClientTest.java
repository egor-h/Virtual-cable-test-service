package com.application.service.zabbix;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ZabbixClientTest {
    public static final String ZABBIX_URL = "someAddress";

    @Mock
    RestTemplate restTemplate;

    ZabbixClient zabbixClient;

    @Test
    public void request_givenCorrectRequest_IncrementCounter() {
        Object requestObject = new Object();
        Class<Object> responseClass = Object.class;
        final int atomicIntegerInitialValue = 1;
        AtomicInteger atomicInteger = new AtomicInteger(atomicIntegerInitialValue);
        zabbixClient = new ZabbixClient(atomicInteger, restTemplate, ZABBIX_URL);
        Mockito.when(restTemplate.postForEntity(Mockito.eq(ZABBIX_URL), Mockito.any(), Mockito.eq(responseClass)))
                .thenReturn(new ResponseEntity<>(requestObject, HttpStatus.OK));

        zabbixClient.request("some", requestObject, responseClass, null);

        assertEquals(atomicIntegerInitialValue+1, atomicInteger.get());
    }

    @Test
    public void request_givenCorrectRequest_postMethodCalled() {
        Object requestObject = new Object();
        Class<Object> responseClass = Object.class;
        zabbixClient = new ZabbixClient(new AtomicInteger(1), restTemplate, ZABBIX_URL);
        Mockito.when(restTemplate.postForEntity(Mockito.eq(ZABBIX_URL), Mockito.any(), Mockito.eq(responseClass)))
                .thenReturn(new ResponseEntity<>(requestObject, HttpStatus.OK));

        zabbixClient.request("some", requestObject, responseClass, null);

        Mockito.verify(restTemplate, Mockito.only())
                .postForEntity(Mockito.eq(ZABBIX_URL), Mockito.any(), Mockito.eq(responseClass));
    }

}