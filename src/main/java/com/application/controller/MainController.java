package com.application.controller;

import com.application.model.Host;
import com.application.model.IfTableRow;
import com.application.service.HostService;
import com.application.service.VirtualCableTestService;
import com.application.service.zabbix.ZabbixService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public MainController(VirtualCableTestService vct, ZabbixService zabbix) {
        this.vct = vct;
        this.zabbix = zabbix;
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

//        Host host = hostService.host(hostid);
        vct.startVCT(ip, port);
        model.addAttribute("vctResult", vct.getVCT(ip, port));

        return "vctResult";
    }

    @RequestMapping("/ports")
    public String ports(Model model, @RequestParam("ip") String ip) {
        Collection<IfTableRow> ifTable = vct.getIfTable(ip);
        model.addAttribute("ip", ip);
        model.addAttribute("iftable", ifTable);
        return "ports";
    }

}