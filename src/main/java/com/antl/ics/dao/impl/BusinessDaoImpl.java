package com.antl.ics.dao.impl;

import com.antl.ics.dao.BusinessDao;
import com.antl.ics.entity.Business;
import com.antl.ics.entity.PieChart;
import com.antl.ics.entity.TableEntity;
import com.antl.ics.util.StringUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class BusinessDaoImpl implements BusinessDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Business> getBusInfo(String algorithm) {
        String sql = "SELECT *, port as src_port, port as dst_port, TIMESTAMPDIFF(second, send_time, recv_time) as delay FROM business where algorithm_type = ?";
        List<Business> businessList = jdbcTemplate.query(sql, new Object[] {algorithm}, new BeanPropertyRowMapper<>(Business.class));
        return businessList;
    }

    @Override
    public String getBusInfoByType(String algorithm) {
        String sql = "SELECT \n" +
                "    COUNT(*) AS num,\n" +
                "    bus_type AS flowtype,\n" +
                "    AVG(TIMESTAMPDIFF(second, send_time, recv_time))  AS avgDelay,\n" +
                "    AVG(isArrive) AS avgArrive\n" +
                "FROM\n" +
                "    (SELECT \n" +
                "        *,\n" +
                "            IF((recv_num / send_num >= 0.8\n" +
                "                AND bus_type = 1), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.8\n" +
                "                AND bus_type = 2), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.8\n" +
                "                AND bus_type = 3), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.5\n" +
                "                AND bus_type = 4), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.5\n" +
                "                AND bus_type = 5), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.5\n" +
                "                AND bus_type = 6), 1, 0) AS isArrive\n" +
                "    FROM\n" +
                "        business where algorithm_type = ? and business.send_time BETWEEN DATE_ADD((SELECT \n" +
                "                time\n" +
                "            FROM\n" +
                "                history_para\n" +
                "            ORDER BY time DESC\n" +
                "            LIMIT 0 , 1),\n" +
                "        INTERVAL - 5 SECOND) AND (SELECT \n" +
                "            time\n" +
                "        FROM\n" +
                "            history_para\n" +
                "        ORDER BY time DESC\n" +
                "        LIMIT 0 , 1)) AS temp\n" +
                "GROUP BY bus_type";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, algorithm);
        Object[] header = {"flowtype", "sum", "avgDelay", "avgArrivate"};
        TableEntity tableEntity = new TableEntity();
        TableEntity.Data data = new TableEntity.Data();
        Object[] busList = new Object[7];
        busList[0] = header;
        for (int i = 1; i < 7; i++) {
            if (busList[i] == null) {
                Object[] busItem = {i, 0.0, 0.0, 0.0};
                busList[i] = busItem;
            }
        }
        for (Map item : maps) {
            busList[(int) item.get("flowtype")] = new Object[]{item.get("flowtype"), item.get("num"), item.get("avgDelay"), item.get("avgArrive")};
        }
        data.setSource(busList);
        tableEntity.setData(data);
        return new Gson().toJson(busList);
    }

    @Override
    public Object[] getBusAvgDelay(String algorithm) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String sql = "SELECT \n" +
                "    bus_type AS flowtype,\n" +
                "    AVG(TIMESTAMPDIFF(second, send_time, recv_time)) AS avgDelay\n" +
                "FROM\n" +
                "    (SELECT \n" +
                "        *,\n" +
                "            IF((recv_num / send_num >= 0.8\n" +
                "                AND bus_type = 1), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.8\n" +
                "                AND bus_type = 2), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.8\n" +
                "                AND bus_type = 3), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.5\n" +
                "                AND bus_type = 4), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.5\n" +
                "                AND bus_type = 5), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.5\n" +
                "                AND bus_type = 6), 1, 0) AS isArrive\n" +
                "    FROM\n" +
                "        business where algorithm_type = ? and business.send_time BETWEEN DATE_ADD((SELECT \n" +
                "                time\n" +
                "            FROM\n" +
                "                history_para\n" +
                "            ORDER BY time DESC\n" +
                "            LIMIT 0 , 1),\n" +
                "        INTERVAL - 5 SECOND) AND (SELECT \n" +
                "            time\n" +
                "        FROM\n" +
                "            history_para\n" +
                "        ORDER BY time DESC\n" +
                "        LIMIT 0 , 1)) AS temp\n" +
                "GROUP BY bus_type";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        Object[] delayList = new Object[7];
        delayList[0] = formatter.format(new Date());
        for (int i = 1; i < 7; i++) {
            delayList[i] = 0.0;
        }
        for (Map item : maps){
            delayList[(int) item.get("flowtype")] = item.get("avgDelay");
        }
        return delayList;
    }

    @Override
    public Object[] getBusAvgArrivate(String algorithm) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String sql = "SELECT \n" +
                "    bus_type AS flowtype,\n" +
                "    AVG(isArrive) as avgArrive\n" +
                "FROM\n" +
                "    (SELECT \n" +
                "        *,\n" +
                "            IF((recv_num / send_num >= 0.8\n" +
                "                AND bus_type = 1), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.8\n" +
                "                AND bus_type = 2), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.8\n" +
                "                AND bus_type = 3), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.5\n" +
                "                AND bus_type = 4), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.5\n" +
                "                AND bus_type = 5), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.5\n" +
                "                AND bus_type = 6), 1, 0) AS isArrive\n" +
                "    FROM\n" +
                "        business where algorithm_type = ? and business.send_time BETWEEN DATE_ADD((SELECT \n" +
                "                time\n" +
                "            FROM\n" +
                "                history_para\n" +
                "            ORDER BY time DESC\n" +
                "            LIMIT 0 , 1),\n" +
                "        INTERVAL - 5 SECOND) AND (SELECT \n" +
                "            time\n" +
                "        FROM\n" +
                "            history_para\n" +
                "        ORDER BY time DESC\n" +
                "        LIMIT 0 , 1)) AS temp\n" +
                "GROUP BY bus_type";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        Object[] avgArrivateList = new Object[7];
        avgArrivateList[0] = formatter.format(new Date());
        for (int i = 1; i < 7; i++) {
            avgArrivateList[i] = 0.0;
        }
        for (Map item : maps){
            avgArrivateList[(int) item.get("flowtype")] = item.get("avgArrive");
        }
        return avgArrivateList;
    }

    @Override
    public Object[] getBusNum(String algorithm) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String sql = "SELECT \n" +
                "    COUNT(*) AS num,\n" +
                "    bus_type AS flowtype\n" +
                "FROM\n" +
                "    (SELECT \n" +
                "        *,\n" +
                "            IF((recv_num / send_num >= 0.8\n" +
                "                AND bus_type = 1), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.8\n" +
                "                AND bus_type = 2), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.8\n" +
                "                AND bus_type = 3), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.5\n" +
                "                AND bus_type = 4), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.5\n" +
                "                AND bus_type = 5), 1, 0)\n" +
                "                OR IF((recv_num / send_num >= 0.5\n" +
                "                AND bus_type = 6), 1, 0) AS isArrive\n" +
                "    FROM\n" +
                "        business where algorithm_type = ? and business_routenet.send_time BETWEEN DATE_ADD((SELECT \n" +
                "                time\n" +
                "            FROM\n" +
                "                history_para\n" +
                "            ORDER BY time DESC\n" +
                "            LIMIT 0 , 1),\n" +
                "        INTERVAL - 5 SECOND) AND (SELECT \n" +
                "            time\n" +
                "        FROM\n" +
                "            history_para\n" +
                "        ORDER BY time DESC\n" +
                "        LIMIT 0 , 1)) AS temp\n" +
                "GROUP BY bus_type";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        Object[] numList = new Object[7];
        numList[0] = formatter.format(new Date());
        for (int i = 1; i < 7; i++) {
            numList[i] = 0;
        }
        for (Map item : maps){
            numList[(int) item.get("flowtype")] = item.get("num");
        }
        return numList;
    }

    @Override
    public List<Business> getBusInfoBySrcId(String id, String algorithm) {
        String sql = "SELECT *, port as src_port, port as dst_port, TIMESTAMPDIFF(second, send_time, recv_time) as delay FROM business_routenet where route like '%" +id+"%'";
        List<Business> businessList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Business.class));
        return businessList;
    }

    @Override
    public List<PieChart> getBusInfoByParam(String source, String target, String type, String algorithm) {
        String sql = "SELECT *, port as src_port, port as dst_port, TIMESTAMPDIFF(second, send_time, recv_time) as delay FROM business_routenet where route like '%" + source + "," + target+"%' and linkType like '%" + type + "%' and business.send_time BETWEEN DATE_ADD(now(), interval- 5 SECOND) and now()";
        List<Business> businessList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Business.class));
        System.out.println(businessList);
        List<PieChart> pieCharts = new ArrayList<>();
        int[] total = new int[6];
        for (Business business : businessList) {
            String[] routes = StringUtil.StringToArray(business.getRoute());
            String[] link_types = StringUtil.StringToArray(business.getLinkType());
            String[] bandwidth = StringUtil.StringToArray(business.getBandwidth());
            for (int j = 0; j < routes.length - 1; j++) {
                if (routes[j].equals(String.valueOf(source)) && routes[j+1].equals(String.valueOf(target)) && link_types[j].equals(String.valueOf(type))) {
                    String band = bandwidth[j];
                    total[Integer.parseInt(business.getBus_type())-1] +=Integer.parseInt(band);
                    break;
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            PieChart pieChart = new PieChart(total[i], i+1);
            pieCharts.add(pieChart);
        }
        return pieCharts;
    }
}
