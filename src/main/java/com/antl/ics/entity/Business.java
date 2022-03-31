package com.antl.ics.entity;

import cn.hutool.core.date.DateTime;

import java.sql.Date;
import java.sql.Timestamp;

public class Business {
    private String src;

    private String dst;

    private String src_port;

    private String dst_port;

    private String bus_type;

    private String send_time;

    private String send_num;

    private String recv_time;

    private String recv_num;

    private String delay;

    private String route;

    private String linkType;

    private String bandwidth;

    private String active_nodes;

    public Business() {
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

    public String getSrc_port() {
        return src_port;
    }

    public void setSrc_port(String src_port) {
        this.src_port = src_port;
    }

    public String getDst_port() {
        return dst_port;
    }

    public void setDst_port(String dst_port) {
        this.dst_port = dst_port;
    }

    public String getBus_type() {
        return bus_type;
    }

    public void setBus_type(String bus_type) {
        this.bus_type = bus_type;
    }


    public String getRecv_num() {
        return recv_num;
    }

    public void setRecv_num(String recv_num) {
        this.recv_num = recv_num;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getActive_nodes() {
        return active_nodes;
    }

    public void setActive_nodes(String active_nodes) {
        this.active_nodes = active_nodes;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getSend_num() {
        return send_num;
    }

    public void setSend_num(String send_num) {
        this.send_num = send_num;
    }

    public String getRecv_time() {
        return recv_time;
    }

    public void setRecv_time(String recv_time) {
        this.recv_time = recv_time;
    }
}
