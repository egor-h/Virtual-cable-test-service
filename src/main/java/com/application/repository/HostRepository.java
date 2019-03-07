package com.application.repository;

import com.application.model.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class HostRepository {
    private static final Logger logger = LoggerFactory.getLogger(HostRepository.class);

    private static final String BASE_QUERY = "SELECT h.hostid hostid, h.name host_name, h.description descr, \n" +
            "g.name grp_name, i.ip ip, i.port port FROM hstgrp g\n" +
            "  INNER JOIN hosts_groups hg\n" +
            "    ON hg.groupid = g.groupid\n" +
            "  INNER JOIN hosts h\n" +
            "    ON hg.hostid = h.hostid\n" +
            "  INNER JOIN interface i\n" +
            "    ON i.hostid = h.hostid\n";

    private static final String ALL_HOSTS_QUERY = BASE_QUERY + " WHERE g.name LIKE \"Switch%\"\n" +
            "GROUP BY(h.hostid)";
//    private static final String ALL_HOSTS_QUERY = "SELECT h.hostid hostid, h.name host_name, \n" +
//            "  g.name grp_name, i.ip ip, i.port port FROM hstgrp g\n" +
//            "  INNER JOIN hosts_groups hg \n" +
//            "    ON hg.groupid = g.groupid \n" +
//            "  INNER JOIN hosts h \n" +
//            "    ON hg.hostid = h.hostid\n" +
//            "  INNER JOIN interface i\n" +
//            "    ON i.hostid = h.hostid\n" +
//            "  WHERE g.name LIKE \"Switch%\"\n" +
//            "  GROUP BY(h.hostid)";

    private static final String SINGLE_HOST_BY_HOSTID_QUERY = BASE_QUERY + " WHERE h.hostid = ? GROUP BY(h.hostid)";

    private JdbcTemplate jdbc;

    @Autowired
    public HostRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private RowMapper<Host> hostRowMapper = (rs, idx) ->
            new Host(rs.getLong("hostid"),
                    rs.getString("host_name"),
                    rs.getString("grp_name"),
                    rs.getString("ip"),
                    rs.getInt("port"), true);

    public List<Host> getAllHosts() {
        logger.debug("Query for all hosts. {} ", ALL_HOSTS_QUERY);
        List<Host> result = jdbc.query(ALL_HOSTS_QUERY, hostRowMapper);
        logger.debug("result set: {}", result.size());
        return result;
    }


    public Host getHost(long id) {
        logger.debug("Query for single host. {}", SINGLE_HOST_BY_HOSTID_QUERY);
        Host host = jdbc.queryForObject(SINGLE_HOST_BY_HOSTID_QUERY, new Object[] {id}, hostRowMapper);
        logger.debug("found {}", host);
        return host;
    }
}
