package com.antl.ics.dao;

import com.antl.ics.entity.Link;

import java.util.List;

public interface LinkDao {
    List<Link> getSingleScore();

    List<Link> getAll();

    String getSingleLinkState(String source, String target);

    String getBWInfoOfAllType();

    String getBWConOfAllLinks();
}
