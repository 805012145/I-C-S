package com.antl.ics.controller;

import com.antl.ics.entity.Link;
import com.antl.ics.entity.Node;
import com.antl.ics.entity.TopoEntity;
import com.antl.ics.service.LinkService;
import com.antl.ics.service.NodeService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;

@Controller
public class TopoController {

    private NodeService nodeService;
    private LinkService linkService;

    @Autowired
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Autowired
    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * 拓扑数据展示
     * @return
     */

    @RequestMapping(value = "/topodata", method = RequestMethod.GET)
    @ResponseBody
    public String topoData() {
        TopoEntity topoEntity = new TopoEntity();
        try {
            List<Link> links = linkService.getAll();
            List<Node> nodes = nodeService.getAll();
            topoEntity.setNodes(nodes);
            topoEntity.setLinks(links);
        }catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(new Gson().toJson(topoEntity));
        return new Gson().toJson(topoEntity);
    }

    @RequestMapping(value = "/topo/single", method = RequestMethod.GET)
    @ResponseBody
    public String getSingleTopo() {
        TopoEntity topoEntity = new TopoEntity();
        try {
            List<Link> links = linkService.getSingleScore();
            List<Node> nodes = nodeService.getAll();
            topoEntity.setNodes(nodes);
            topoEntity.setLinks(links);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(topoEntity);
    }
    @PostMapping("topo/singleLinkState")
    @ResponseBody
    public String getSingleLinkState(String source, String target) throws ParseException {
        if (source.equals("5757") || source.equals("6767") || source.equals("57576767")) {
            return null;
        }
        if (target.equals("5757") || target.equals("6767") || target.equals("57576767")) {
            return null;
        }
        System.out.println(source +" "+target);
        System.out.println(linkService.getSingleLinkState(source, target));
        return linkService.getSingleLinkState(source, target);
    }
}