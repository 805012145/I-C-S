package com.antl.ics.dao.impl;

import com.antl.ics.dao.NodeDao;
import com.antl.ics.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NodeDaoImpl implements NodeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Node> getAll() {
        String controllerSql = "SELECT * FROM nodes where node_type = 'controller'";
        String switchSql = "SELECT * FROM nodes where node_type = 'switch'";
        String hostSql = "SELECT nodes.id as id, name, symbolSize, position_x, position_y, value, category, field, linked_switch_id as switch_id, host_ip as ip FROM nodes left join host_switch on nodes.id = host_switch.host_id where node_type = 'host'";
        List<Node> nodes = new ArrayList<>();
        nodes.addAll(jdbcTemplate.query(controllerSql, (RowMapper<Controller>) new BeanPropertyRowMapper<Controller>(Controller.class)));
        nodes.addAll(jdbcTemplate.query(switchSql, (RowMapper<Switch>) new BeanPropertyRowMapper<Switch>(Switch.class)));
        nodes.addAll(jdbcTemplate.query(hostSql, (RowMapper<Host>) new BeanPropertyRowMapper<Host>(Host.class)));
        return nodes;
    }
}
