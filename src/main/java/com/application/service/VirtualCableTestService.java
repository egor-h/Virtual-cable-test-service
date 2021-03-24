package com.application.service;

import com.application.model.IfTableRow;
import com.application.util.SnmpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Collection;

@Service
public class VirtualCableTestService {
    private static final Logger logger = LoggerFactory.getLogger(VirtualCableTestService.class);
    private Snmp snmp;

    @PostConstruct
    public void init() throws Exception {
        TransportMapping transportMapping = new DefaultUdpTransportMapping();
        this.snmp = new Snmp(transportMapping);
        logger.debug("Created instance {}" + Snmp.class.getCanonicalName());
        transportMapping.listen();
    }

    @PreDestroy
    public void destroy() {
        try {
            this.snmp.close();
        } catch (IOException e) {
            logger.error("Error on closing snmp {}", e);
        }
    }

    public Collection<IfTableRow> getIfTable(String ip) throws RuntimeException {
        logger.debug("Get if table from {}", ip);
        SnmpUtil snmpUtil = new SnmpUtil(snmp);
        Collection<IfTableRow> ifTable = null;

        try {
            ifTable = snmpUtil.getIfTable(ip);
        } catch (Exception e) {
            logger.error("Error while retrieving ifTable {}", e);
            throw new RuntimeException(e);
        }

        logger.debug("Retrieved ifTable of {} length", ifTable.size());

        return ifTable;
    }

    public void startVCT(String ip, int physicalPort) throws RuntimeException {
        SnmpUtil snmpUtil = new SnmpUtil(snmp);
        try {
            snmpUtil.startVCT(ip, physicalPort);
        } catch (Exception e) {
            logger.error("Error while sending VCT {}", e);
            throw new RuntimeException(e);
        }
    }

    public String getVCT(String ip, int physicalPort) throws RuntimeException {
        SnmpUtil snmpUtil = new SnmpUtil(snmp);
        try {
            return snmpUtil.getVCT(ip, physicalPort);
        } catch (Exception e) {
            logger.error("Error while sending VCT {}", e);
            throw new RuntimeException(e);
        }
    }
}
