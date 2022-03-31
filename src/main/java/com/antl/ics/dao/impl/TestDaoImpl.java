package com.antl.ics.dao.impl;

import com.antl.ics.dao.TestDao;
import com.antl.ics.util.JDBCUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class TestDaoImpl implements TestDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List test() {
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList("select * from business",new Object[]{});
        System.out.println(list.size());
        return list;
    }

}
