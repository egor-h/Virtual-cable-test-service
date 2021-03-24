package com.application.service.zabbix.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupsResponse {
    List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }

    @JsonProperty("result")
    private void setResult(List<Map<String, Object>> result) {
        groups = result.stream()
                .map(groupMap -> new Group((String) groupMap.get("groupid"), (String) groupMap.get("name"), (String) groupMap.get("internal")))
                .collect(Collectors.toList());
    }

    public class Group {
        private String groupId;
        private String name;
        private String internal;

        public Group(String groupId, String name, String internal) {
            this.groupId = groupId;
            this.name = name;
            this.internal = internal;
        }

        public Group() {
        }

        @Override
        public String toString() {
            return "Groups{" +
                    "groupId='" + groupId + '\'' +
                    ", name='" + name + '\'' +
                    ", internal='" + internal + '\'' +
                    '}';
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInternal() {
            return internal;
        }

        public void setInternal(String internal) {
            this.internal = internal;
        }
    }
}
