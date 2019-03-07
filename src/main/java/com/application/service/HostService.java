package com.application.service;

import com.application.model.Host;

import java.util.List;

public interface HostService {
    List<Host> allHosts();
    Host host(long id);
}
