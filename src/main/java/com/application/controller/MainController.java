package com.application.controller;

import com.application.model.IfTableRow;
import com.application.service.VirtualCableTestService;
import com.application.service.zabbix.ZabbixService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Optional;

@Controller
public class MainController {
    private VirtualCableTestService vct;
    private ZabbixService zabbix;
    private MeterRegistry meterRegistry;

    public MainController(VirtualCableTestService vct, ZabbixService zabbix, MeterRegistry meterRegistry) {
        this.vct = vct;
        this.zabbix = zabbix;
        this.meterRegistry = meterRegistry;
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("groups", zabbix.groups().getGroups());
        return "allGroups";
    }

    @RequestMapping("/hosts")
    public String hosts(Model model, @RequestParam(name = "grp", required = false) Optional<String> groupId) {
        model.addAttribute("hosts", groupId.map(zabbix::hosts).orElseGet(zabbix::hosts).getResult());
        return "allHosts";
    }

    @RequestMapping("/vct")
    public String vct(Model model, @RequestParam(name = "ip", required = true) String ip,
                      @RequestParam(name = "port") int port) {

        var timer = meterRegistry.timer("getVct");
        vct.startVCT(ip, port);
        model.addAttribute("vctResult", timer.record(() -> vct.getVCT(ip, port)));

        return "vctResult";
    }

    @RequestMapping("/ports")
    public String ports(Model model, @RequestParam("ip") String ip) {
        var timer = meterRegistry.timer("getIfTableTimer");
        Collection<IfTableRow> ifTable = timer.record(() -> vct.getIfTable(ip));
        model.addAttribute("ip", ip);
        model.addAttribute("iftable", ifTable);
        return "ports";
    }

}