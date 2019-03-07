package com.application;

import com.application.util.SnmpUtil;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.smi.Address;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;


@SpringBootApplication
@EnableConfigurationProperties
public class Application {

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx =  SpringApplication.run(Application.class, args);
//		new SnmpUtil().fetchVCT(null);
	}

}

