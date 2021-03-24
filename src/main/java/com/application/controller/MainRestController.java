package com.application.controller;

import com.application.model.IfTableRow;
import com.application.service.VirtualCableTestService;
import com.application.service.zabbix.ZabbixService;
import com.application.service.zabbix.response.GroupsResponse;
import com.application.service.zabbix.response.HostsResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MainRestController {

    private ZabbixService zabbixService;
    private VirtualCableTestService vct;

    @Autowired
    public MainRestController(ZabbixService zabbixService, VirtualCableTestService vct) {
        this.zabbixService = zabbixService;
        this.vct = vct;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    @ApiOperation("allHosts")
    @GetMapping("/host")
    public List<HostsResponse.Host> allHosts() {
        return zabbixService.hosts().getResult();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    @ApiOperation("allGroups")
    @GetMapping("/group")
    public List<GroupsResponse.Group> allGroups() {
        return zabbixService.groups().getGroups();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Group not found")
    })
    @GetMapping("/group/{id}/host")
    public List<HostsResponse.Host> hostsFromGroup(@PathVariable("id") long id) {
        return zabbixService.hosts(String.valueOf(id)).getResult();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Host not found"),
            @ApiResponse(code = 500, message = "Error on SNMP GET"),
    })
    @GetMapping("/ports")
    public Collection<IfTableRow> ports(@RequestParam(value = "ip") String ip) {
        Collection<IfTableRow> ifTable = vct.getIfTable(ip);

        if (ifTable == null) {
            ifTable = new LinkedList();
        }
        return ifTable;
    }


    @ApiOperation(value = "vct", notes = "Perform cable test")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Test succesful"),
            @ApiResponse(code = 404, message = "Host not found"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping("/vct/{id}")
    public String vctHost(@PathVariable("id") long id) {
        return "";
    }


}
