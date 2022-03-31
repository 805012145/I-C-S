package com.antl.ics.controller;
import com.antl.ics.entity.Business;
import com.antl.ics.entity.Node;
import com.antl.ics.entity.PieChart;
import com.antl.ics.service.BusinessService;
import com.antl.ics.service.NodeService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BusinessController {

    @Autowired
    private BusinessService businessService;
    @Autowired
    private NodeService nodeService;

    public String algorithm = "routeNet";

    /**
     * 业务流量信息表格化展示
     * @return
     */
    @RequestMapping(value = "/business/businessdata", method = RequestMethod.GET)
    @ResponseBody
    public String getBusInfo() throws ParseException {
        List<Business> businesses = businessService.getBusInfo(algorithm);
        return new Gson().toJson(businesses);
    }

    /**
     *各类型的业务信息
     */
    @RequestMapping(value = "/business/info", method = RequestMethod.GET)
    @ResponseBody
    public String getBusInfoByType() throws ParseException {
        return businessService.getBusInfoByType(algorithm);
    }

    /**
     * 节点信息展示
     * @return
     */
    @RequestMapping(value = "/business/nodedata", method = RequestMethod.GET)
    @ResponseBody
    public String getNodeData() {
        List<Node> nodes = nodeService.getAll();
        Map<String, List<Node>> map = new HashMap<>();
        map.put("nodes", nodes);
        return new Gson().toJson(map);
    }

    /**
     * 返回平均延时
     * @return
     * @throws ParseException
     */
    @RequestMapping("/business/avgDelay")
    @ResponseBody
    public String getDelay() throws ParseException {
        Object[] delayList = businessService.getBusAvgDelay(algorithm);
        return new Gson().toJson(delayList);
    }

    /**
     * 返回平均到达率
     * @return
     * @throws ParseException
     */
    @RequestMapping("/business/avgArrivate")
    @ResponseBody
    public String getLoss() throws ParseException {
        Object[] arrivate = businessService.getBusAvgArrivate(algorithm);
        return new Gson().toJson(arrivate);
    }

    /**
     * 返回数目
     * @return
     * @throws ParseException
     */
    @RequestMapping("/business/num")
    @ResponseBody
    public String getNum() throws ParseException {
        Object[] num = businessService.getBusNum(algorithm);
        return new Gson().toJson(num);
    }

    /**
     * 前端返回id，查询business表中所有由id发出的业务信息
     * @param id
     * @return
     */
//    @RequestMapping(value = "/topo/business/{id}", method = RequestMethod.GET)
    @PostMapping("topo/postNodeNum")
    @ResponseBody
    public String getBusById(String id) throws ParseException {
        System.out.println(id);
        List<Business> businesses = businessService.getBusInfoBySrcId(id, algorithm);
        Map<String, List<Business>> map = new HashMap<>();
        map.put("routingtable", businesses);
        System.out.println(map);
        return new Gson().toJson(map);
    }

    /**
     * 前端返回src,dst,src_port,dst_port,link_type，查询business表中满足所有条件的最近时刻的名称，类型与占用带宽情况
     *
     * @return
     */
    @PostMapping("topo/postLinkState")
    @ResponseBody
    public String getBusByLink(String source, String target, String type) throws ParseException {
        System.out.println(source);
        List<PieChart> pieCharts = businessService.getBusInfoByParam(source, target, type, algorithm);
        Map<String, List<PieChart>> map = new HashMap<>();
        map.put("routingtable", pieCharts);
        return new Gson().toJson(map);
    }

}
