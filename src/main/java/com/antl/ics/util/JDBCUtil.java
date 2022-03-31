package com.antl.ics.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JDBCUtil {

    @Autowired
    public JdbcTemplate jdbcTemplate;

}
