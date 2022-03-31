package com.antl.ics;

import com.antl.ics.entity.*;
import com.antl.ics.util.StringUtil;
import com.google.gson.Gson;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class ICSApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

    @Test
    void query() throws SQLException {
        Object[] header = {"source", "amount", "type"};
        String sql = "select link_score, remain_bandwidth, type from link_info left join links on link_info.id = links.id where src=? and dst=?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, 1, 2);
        Object[] linkList = new Object[5];
        linkList[0] = header;
        for (int i = 0; i < 5; i++) {
            if (linkList[i] == null) {
                Object[] linkInfo = {0.0, 0.0, i};
                linkList[i] = linkInfo;
            }
        }
        for (Map item : list) {
            linkList[(int) item.get("type")] = new Object[]{item.get("link_score"), item.get("remain_bandwidth"), item.get("type")};
        }
        System.out.println(linkList);
    }
    @Test
    void test() {
        Map<SrcDstPair, Object[]> productMap = new HashMap<>();
        TableEntity tableEntity = new TableEntity();
        List<Object[]> bwInfo = new ArrayList<>();
        TableEntity.Data data = new TableEntity.Data();
        Object[] header = {"score1", "amount1", "type1", "score2", "amount2", "type2", "score3", "amount3", "type3",
                "score4", "amount4", "type4", "product"};
        bwInfo.add(header);
        String sql = "SELECT src, dst, link_score, max_bandwidth-remain_bandwidth as amount, link_type FROM link_info left join links on link_info.id = links.id";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map item : maps) {
            SrcDstPair pair = new SrcDstPair(String.valueOf(item.get("src")), String.valueOf(item.get("dst")));
            if (!productMap.containsKey(pair)) {
                Object[] linkInfo = new Object[13];
                linkInfo[12] = pair.src + ":" +pair.dst;
                productMap.put(pair, linkInfo);
            }
            switch (String.valueOf(item.get("link_type"))) {
                case "1":
                    productMap.get(pair)[0] = item.get("link_score");
                    productMap.get(pair)[1] = item.get("amount");
                    productMap.get(pair)[2] = item.get("link_type");
                    break;
                case "2":
                    productMap.get(pair)[3] = item.get("link_score");
                    productMap.get(pair)[4] = item.get("amount");
                    productMap.get(pair)[5] = item.get("link_type");
                    break;
                case "3":
                    productMap.get(pair)[6] = item.get("link_score");
                    productMap.get(pair)[7] = item.get("amount");
                    productMap.get(pair)[8] = item.get("link_type");
                    break;
                case "4":
                    productMap.get(pair)[9] = item.get("link_score");
                    productMap.get(pair)[10] = item.get("amount");
                    productMap.get(pair)[11] = item.get("link_type");
                    break;
                default:
                    break;
            }
        }
        bwInfo.addAll(productMap.values());
        data.setSource(bwInfo);
        tableEntity.setData(data);
        System.out.println(new Gson().toJson(tableEntity));
    }
    @Test
    void test2() throws ParseException {
        String sql = "SELECT contorller_sum+switch_sum+host_sum as node_sum,\n" +
                "  businsess_sum,\n" +
                "        throughout,\n" +
                "        link_sum,\n" +
                "  contorller_sum - (\n" +
                "  select contorller_sum FROM history_para  order by time desc limit 1,1\n" +
                "        ) +\n" +
                "  switch_sum-(\n" +
                "  select switch_sum FROM history_para  order by time desc limit 1,1\n" +
                "        ) +\n" +
                "        host_sum-(\n" +
                "  select host_sum FROM history_para  order by time desc limit 1,1\n" +
                "        ) as nodesdiff,\n" +
                "        businsess_sum-(\n" +
                "  select businsess_sum FROM history_para  order by time desc limit 1,1\n" +
                "        ) as businsessdiff,\n" +
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
        Object[] header = {"node_sum", "businsess_sum", "throughout", "link_sum", "nodesdiff", "businsessdiff", "throughoutdiff", "linkdiff"};
        historyInfoList.add(header);
        for (Map historyData : historyDataList) {
            Object[] listData = (Object[]) historyData.values().toArray();
            historyInfoList.add(listData);
        }
        System.out.println( new Gson().toJson(historyInfoList));
    }
    class SrcDstPair {

        public String src;
        public String dst;

        public SrcDstPair() {
        }

        public SrcDstPair(String src, String dst) {
            this.src = src;
            this.dst = dst;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getDst() {
            return dst;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SrcDstPair pair = (SrcDstPair) o;
            return Objects.equals(src, pair.src) && Objects.equals(dst, pair.dst);
        }

        @Override
        public String toString() {
            return "SrcDstPair{" +
                    "src='" + src + '\'' +
                    ", dst='" + dst + '\'' +
                    '}';
        }

        @Override
        public int hashCode() {
            return Objects.hash(src, dst);
        }
    }

}
