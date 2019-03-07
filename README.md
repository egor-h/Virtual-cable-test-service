Simple web service for Virtual cable test feature on SNR switches.
The service connects to zabbix database and finds all hosts from group named 'Switch.*'

To start service run systemd service:
```shell
	systemctl start vct.service
```

# Configuration:
Default configuration looks like this
```yml
app:
	dbUrl: "jdbc:mysql://localhost:3306/zabbix"
	dbUser: zabbix
	dbPassword: zabbix
```