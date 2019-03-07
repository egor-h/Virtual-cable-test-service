package com.application.service;

import com.application.model.Host;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


@Service
@Profile("dev") @Primary
public class MockHostServiceImpl implements HostService {

    private static final List<Host> HOSTS = new LinkedList<>();

    {
        HOSTS.add(new Host(0, "First host", "Switch", "192.168.99.99", 126, true));
        HOSTS.add(new Host(1, "Second host", "Switch", "192.168.0.122", 126, true));
        HOSTS.add(new Host(2, "Third host", "Switch", "192.168.99.101", 126, true));
    }

    @Override
    public List<Host> allHosts() {
        return HOSTS;
    }

    @Override
    public Host host(long id) {
        return HOSTS.get((int) id);
    }
}
