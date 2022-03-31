package com.antl.ics.service.impl;

import com.antl.ics.dao.TestDao;
import com.antl.ics.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;
    @Override
    public List test() {
        return testDao.test();
    }
}
