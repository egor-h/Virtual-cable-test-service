package com.application.controller;

import com.application.model.Host;
import com.application.model.IfTableRow;
import com.application.service.HostService;
import com.application.service.VirtualCableTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class MainController {

    private HostService hostService;
    private VirtualCableTestService vct;

    @Autowired
    public MainController(HostService hostService, VirtualCableTestService vct) {
        this.hostService = hostService;
        this.vct = vct;
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("hosts", hostService.allHosts());
        return "allHosts";
    }

    @RequestMapping("/vct")
    public String vct(Model model, @RequestParam(name = "ip", required = false) String ip,
                      @RequestParam(name = "hostid", required = false) long hostid,
                      @RequestParam(name = "port") int port) {

        if (ip == null && hostid == 0) {
            throw new RuntimeException("No host selected");
        }

        Host host = hostService.host(hostid);
        vct.startVCT(host, port);
        model.addAttribute("vctResult", vct.getVCT(host, port));

        return "vctResult";
    }

    @RequestMapping("/ports")
    public String ports(Model model, @RequestParam("hostid") long hostid) {
        Host host = hostService.host(hostid);
        Collection<IfTableRow> ifTable = vct.getIfTable(host);

        model.addAttribute("host", host);
        model.addAttribute("iftable", ifTable);

        return "ports";
    }

}