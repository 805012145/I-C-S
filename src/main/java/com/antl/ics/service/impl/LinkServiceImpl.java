package com.antl.ics.service.impl;

import com.antl.ics.dao.LinkDao;
import com.antl.ics.entity.Link;
import com.antl.ics.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkDao linkDao;

    @Override
    public List<Link> getAll() {
        return linkDao.getAll();
    }

    @Override
    public List<Link> getSingleScore() {
        return linkDao.getSingleScore();
    }

    @Override
    public String getSingleLinkState(String source, String target) {
        return linkDao.getSingleLinkState(source, target);
    }

    @Override
    public String getBWInfoOfAllType() {
        return linkDao.getBWInfoOfAllType();
    }

    @Override
    public String getBWConOfAllLinks() {
        return linkDao.getBWConOfAllLinks();
    }
}
