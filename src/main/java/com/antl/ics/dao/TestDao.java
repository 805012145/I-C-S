package com.antl.ics.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface TestDao {
    List test();
}
