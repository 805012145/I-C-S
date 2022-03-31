package com.antl.ics.service.impl;

import com.antl.ics.dao.HistoryDao;
import com.antl.ics.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryDao historyDao;

    @Override
    public String getAllHistoryParam() {
        return historyDao.getAllHistoryParam();
    }

    @Override
    public String getImportantBusinessNum() {
        return historyDao.getImportantBusinessNum();
    }
}
