package com.antl.ics.entity;

public class Link {
    private String src;

    private String src_port;

    private String dst;

    private String dst_port;

    private String link_type;

    private String remain_bandwidth;

    private String max_bandwidth;

    private String link_score;

    private String link_delay;

    private String link_drop;

    private String id;

    public Link() {
    }

    public Link(String src, String src_port, String dst, String dst_port, String link_type, String remain_bandwidth, String max_bandwidth, String link_score, String link_delay, String link_drop, String id) {
        this.src = src;
        this.src_port = src_port;
        this.dst = dst;
        this.dst_port = dst_port;
        this.link_type = link_type;
        this.remain_bandwidth = remain_bandwidth;
        this.max_bandwidth = max_bandwidth;
        this.link_score = link_score;
        this.link_delay = link_delay;
        this.link_drop = link_drop;
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrc_port() {
        return src_port;
    }

    public void setSrc_port(String src_port) {
        this.src_port = src_port;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getDst_port() {
        return dst_port;
    }

    public void setDst_port(String dst_port) {
        this.dst_port = dst_port;
    }

    public String getLink_type() {
        return link_type;
    }

    public void setLink_type(String link_type) {
        this.link_type = link_type;
    }

    public String getRemain_bandwidth() {
        return remain_bandwidth;
    }

    public void setRemain_bandwidth(String remain_bandwidth) {
        this.remain_bandwidth = remain_bandwidth;
    }

    public String getMax_bandwidth() {
        return max_bandwidth;
    }

    public void setMax_bandwidth(String max_bandwidth) {
        this.max_bandwidth = max_bandwidth;
    }

    public String getLink_score() {
        return link_score;
    }

    public void setLink_score(String link_score) {
        this.link_score = link_score;
    }

    public String getLink_delay() {
        return link_delay;
    }

    public void setLink_delay(String link_delay) {
        this.link_delay = link_delay;
    }

    public String getLink_drop() {
        return link_drop;
    }

    public void setLink_drop(String link_drop) {
        this.link_drop = link_drop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
