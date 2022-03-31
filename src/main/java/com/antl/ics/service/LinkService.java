package com.antl.ics.service;

import com.antl.ics.entity.Link;

import java.util.List;

public interface LinkService {
    List<Link> getAll();

    List<Link> getSingleScore();

    String getSingleLinkState(String source, String target);

    String getBWInfoOfAllType();

    String getBWConOfAllLinks();
}
