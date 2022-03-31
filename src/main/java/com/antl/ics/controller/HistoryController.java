package com.antl.ics.controller;

import com.antl.ics.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    /**
     * 历史信息展示
     * @return
     */
    @RequestMapping(value = "/history/historydata", method = RequestMethod.GET)
    @ResponseBody
    public String historyData() {
        return historyService.getAllHistoryParam();
    }

    /**
     * 历史信息展示
     * @return
     */
    @RequestMapping(value = "/history/important", method = RequestMethod.GET)
    @ResponseBody
    public String importantBusinessNum() {
        return historyService.getImportantBusinessNum();
    }

}
