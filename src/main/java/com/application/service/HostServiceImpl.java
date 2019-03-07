package com.application.service;

import com.application.model.Host;
import com.application.repository.HostRepository;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HostServiceImpl implements HostService {

    private HostRepository hosts;

    @Autowired
    public HostServiceImpl(HostRepository hosts) {
        this.hosts = hosts;
    }

    public List<Host> allHosts() {
        return hosts.getAllHosts();
    }

    public Host host(long id) {
        return hosts.getHost(id);
    }
}
