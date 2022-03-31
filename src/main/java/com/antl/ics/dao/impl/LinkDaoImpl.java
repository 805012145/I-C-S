package com.antl.ics.dao.impl;

import com.antl.ics.dao.LinkDao;
import com.antl.ics.entity.Channel;
import com.antl.ics.entity.Link;
import com.antl.ics.entity.TableEntity;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LinkDaoImpl implements LinkDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 得到单链路拓扑中的链路评分（平均）
     * @return
     */
    @Override
    public List<Link> getSingleScore() {
        String sql = "SELECT src, dst, avg(link_score) as link_score from link_info left join links on link_info.id = links.id group by src, dst";
        List<Link> links = jdbcTemplate.query(sql, (RowMapper<Link>) new BeanPropertyRowMapper<Link>(Link.class));
        return links;
    }

    /**
     * 获取所有链路信息
     * @return
     */
    @Override
    public List<Link> getAll() {
        String sql = "SELECT links.id, src, dst, src_port, dst_port, link_type, remain_bandwidth, max_bandwidth, link_score, link_delay, link_drop FROM links left join link_info on links.id = link_info.id";
        List<Link> links = jdbcTemplate.query(sql, (RowMapper<Link>) new BeanPropertyRowMapper<Link>(Link.class));
        return links;
    }

    /**
     * 从数据库中提取指定的链路信息并采用Table封装
     * @param source
     * @param target
     * @return
     */
    @Override
    public String getSingleLinkState(String source, String target) {
        Object[] header = {"source", "amount", "type"};
        String sql = "select link_score, remain_bandwidth, link_type from link_info left join links on link_info.id = links.id where src=? and dst=?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, source, target);
        Object[] linkList = new Object[5];
        TableEntity tableEntity = new TableEntity();
        TableEntity.Data data = new TableEntity.Data();
        linkList[0] = header;
        for (int i = 0; i < 5; i++) {
            if (linkList[i] == null) {
                Object[] linkInfo = {0.0, 0.0, String.valueOf(i)};
                linkList[i] = linkInfo;
            }
        }
        for (Map item : list) {
            linkList[(int) item.get("type")] = new Object[]{item.get("link_score"), item.get("remain_bandwidth"), item.get("type")};
        }
        data.setSource(linkList);
        tableEntity.setData(data);
        return new Gson().toJson(tableEntity);
    }

    @Override
    public String getBWInfoOfAllType() {
        Map<String, List<Channel>> channelMap = new HashMap<>();
        List<Channel> channelList = new ArrayList<>();
        String sql = "SELECT link_type, sum(max_bandwidth-remain_bandwidth) as used_BW, sum(remain_bandwidth) as remain FROM link_info left join links on link_info.id = links.id group by link_type";
        List<Channel.Data> channelDataList = jdbcTemplate.query(sql, (RowMapper<Channel.Data>) new BeanPropertyRowMapper<Channel.Data>(Channel.Data.class));
        for (Channel.Data data : channelDataList) {
            Channel channel = new Channel(data);
            channelList.add(channel);
        }
        channelMap.put("channel", channelList);
        return new Gson().toJson(channelMap);
    }

    /**
     * 提取链路带宽信息计算链路拥塞
     * @return
     */
    @Override
    public String getBWConOfAllLinks() {
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
            productMap.get(pair)[2] = 1;
            productMap.get(pair)[5] = 2;
            productMap.get(pair)[8] = 3;
            productMap.get(pair)[11] = 4;
            switch (String.valueOf(item.get("link_type"))) {
                case "1":
                    productMap.get(pair)[0] = item.get("link_score");
                    productMap.get(pair)[1] = item.get("amount");

                    break;
                case "2":
                    productMap.get(pair)[3] = item.get("link_score");
                    productMap.get(pair)[4] = item.get("amount");

                    break;
                case "3":
                    productMap.get(pair)[6] = item.get("link_score");
                    productMap.get(pair)[7] = item.get("amount");

                    break;
                case "4":
                    productMap.get(pair)[9] = item.get("link_score");
                    productMap.get(pair)[10] = item.get("amount");

                    break;
                default:
                    break;
            }
        }
        bwInfo.addAll(productMap.values());
        data.setSource(bwInfo);
        tableEntity.setData(data);
        return new Gson().toJson(tableEntity);
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
