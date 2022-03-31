package com.antl.ics.entity;

import java.util.ArrayList;
import java.util.List;

public class TopoEntity {
    /**
     * nodes
     */
    private List<Node> nodes = new ArrayList<>();
    /**
     * links
     */
    private List<Link> links = new ArrayList<>();

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
