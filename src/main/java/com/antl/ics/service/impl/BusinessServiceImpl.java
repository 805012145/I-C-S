package com.antl.ics.service.impl;

import com.antl.ics.dao.BusinessDao;
import com.antl.ics.entity.Business;
import com.antl.ics.entity.PieChart;
import com.antl.ics.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessDao businessDao;

    @Override
    public List<Business> getBusInfo(String algorithm) {
        return businessDao.getBusInfo(algorithm);
    }

    @Override
    public String getBusInfoByType(String algorithm) {
        return businessDao.getBusInfoByType(algorithm);
    }

    @Override
    public Object[] getBusAvgDelay(String algorithm) {
        return businessDao.getBusAvgDelay(algorithm);
    }

    @Override
    public Object[] getBusAvgArrivate(String algorithm) {
        return businessDao.getBusAvgArrivate(algorithm);
    }

    @Override
    public Object[] getBusNum(String algorithm) {
        return businessDao.getBusNum(algorithm);
    }

    @Override
    public List<Business> getBusInfoBySrcId(String id, String algorithm) {
        return businessDao.getBusInfoBySrcId(id, algorithm);
    }

    @Override
    public List<PieChart> getBusInfoByParam(String source, String target, String type, String algorithm) {
        return businessDao.getBusInfoByParam(source, target, type, algorithm);
    }
}
