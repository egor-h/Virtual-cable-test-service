package com.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "app")
public class ApplicationConfigurationProperties {

//    @NestedConfigurationProperty
    private ZabbixConfiguration zabbix;

    public static class ZabbixConfiguration {
        private String username;
        private String password;
        private String url;

        public ZabbixConfiguration() {
        }

        @Override
        public String toString() {
            return "Zabbix{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    private String dbUrl = "jdbc:mysql://localhost:3306/zabbix";
    private String dbUser = "zabbix";
    private String dbPassword = "zabbix";

    public ApplicationConfigurationProperties() {
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public ZabbixConfiguration getZabbix() {
        return zabbix;
    }

    public void setZabbix(ZabbixConfiguration zabbix) {
        this.zabbix = zabbix;
    }
}
