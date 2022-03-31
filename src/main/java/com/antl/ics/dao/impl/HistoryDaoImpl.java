package com.antl.ics.dao.impl;

import com.antl.ics.dao.HistoryDao;
import com.antl.ics.entity.TableEntity;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HistoryDaoImpl implements HistoryDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getAllHistoryParam() {
        String sql = "SELECT  controller_sum,switch_sum,host_sum ,\n" +
                "  business_sum,\n" +
                "        throughout,\n" +
                "        link_sum,\n" +
                "  controller_sum - (\n" +
                "  select controller_sum FROM history_para order by time desc limit 1,1\n" +
                "        ) +\n" +
                "  switch_sum-(\n" +
                "  select switch_sum FROM history_para  order by time desc limit 1,1\n" +
                "        ) +\n" +
                "        host_sum-(\n" +
                "  select host_sum FROM history_para  order by time desc limit 1,1\n" +
                "        ) as nodesdiff,\n" +
                "        business_sum-(\n" +
                "  select business_sum FROM history_para  order by time desc limit 1,1\n" +
                "        ) as businessdiff,\n" +
                "        throughout-(\n" +
                "  select throughout FROM history_para  order by time desc limit 1,1\n" +
                "        ) as throughoutdiff, \n" +
                "        link_sum-(\n" +
                "  select link_sum FROM history_para  order by time desc limit 1,1\n" +
                "        ) as linkdiff\n" +
                "from  history_para  order by time desc limit 0,1";
        List<Map<String, Object>> historyDataList = jdbcTemplate.queryForList(sql);
        TableEntity tableEntity = new TableEntity();
        TableEntity.Data data = new TableEntity.Data();
        List<Object[]> historyInfoList = new ArrayList<>();
        Object[] header = {"controller_sum","switch_sum", "host_sum", "businsess_sum", "throughout", "link_sum", "nodesdiff", "businsessdiff", "throughoutdiff", "linkdiff"};
        historyInfoList.add(header);
        for (Map historyData : historyDataList) {
            Object[] listData = (Object[]) historyData.values().toArray();
            historyInfoList.add(listData);
        }
        data.setSource(historyInfoList);
        tableEntity.setData(data);
        return new Gson().toJson(tableEntity);
    }

    @Override
    public String getImportantBusinessNum() {
        String sql = "SELECT count(*) FROM business_routenet  WHERE  business_routenet.bus_type<4\n" +
                " AND\n" +
                " business_routenet.send_time between date_add((select time from  history_para  order by time desc limit 0,1),  interval -5 second)" +
                " and (select time from  history_para  order by time desc limit 0,1)";
        int num = jdbcTemplate.queryForObject(sql, Integer.class);
        Map<String, Integer> numMap = new HashMap<>();
        numMap.put("ImportantBusinessNum", num);
        return new Gson().toJson(numMap);
    }
}
