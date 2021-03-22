package com.application.service.zabbix;

import com.application.service.zabbix.response.GroupsResponse;
import com.application.service.zabbix.response.HostsResponse;

public interface ZabbixService {
    void auth();
    GroupsResponse groups();
    HostsResponse hosts();
    HostsResponse hosts(String groupId);
}