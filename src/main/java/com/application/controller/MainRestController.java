package com.application.controller;

import com.application.model.Host;
import com.application.model.IfTableRow;
import com.application.service.HostService;
import com.application.service.VirtualCableTestService;
import com.application.util.SnmpUtil;
import io.swagger.annotations.Api;
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

    private HostService hostService;
    private VirtualCableTestService vct;

    @Autowired
    public MainRestController(HostService hostService, VirtualCableTestService vct) {
        this.hostService = hostService;
        this.vct = vct;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    @ApiOperation("allHosts")
    @GetMapping("/host")
    public List<Host> allHosts() {
        return hostService.allHosts();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Host not found")
    })
    @GetMapping("/host/{id}")
    public Host host(@PathVariable("id") long id) {
        return hostService.host(id);
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Host not found"),
            @ApiResponse(code = 500, message = "Error on SNMP GET"),
    })
    @GetMapping("/ports")
    public Collection<IfTableRow> ports(@RequestParam(value = "ip", required = false) String ip,
                                  @RequestParam(value = "hostid", required = false) long hostid) {

        Host host = null;

        if (ip == null && hostid == 0) {
            throw new RuntimeException("Required ip or hostid");
        }

        if (hostid != 0) {
            host = hostService.host(hostid);
        }

        Collection<IfTableRow> ifTable = vct.getIfTable(host);

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
