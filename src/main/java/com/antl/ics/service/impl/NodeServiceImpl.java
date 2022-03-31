package com.antl.ics.service.impl;

import com.antl.ics.dao.NodeDao;
import com.antl.ics.entity.Node;
import com.antl.ics.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeDao nodeDao;

    @Override
    public List<Node> getAll() {
        return nodeDao.getAll();
    }
}
