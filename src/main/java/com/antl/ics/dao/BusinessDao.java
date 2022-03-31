package com.antl.ics.dao;

import com.antl.ics.entity.Business;
import com.antl.ics.entity.PieChart;

import java.util.List;

public interface BusinessDao {
    List<Business> getBusInfo(String algorithm);

    String getBusInfoByType(String algorithm);

    Object[] getBusAvgDelay(String algorithm);

    Object[] getBusAvgArrivate(String algorithm);

    Object[] getBusNum(String algorithm);

    List<Business> getBusInfoBySrcId(String id, String algorithm);

    List<PieChart> getBusInfoByParam(String source, String target, String type, String algorithm);
}
