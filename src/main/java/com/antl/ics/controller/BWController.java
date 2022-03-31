package com.antl.ics.controller;

import com.antl.ics.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BWController {

    @Autowired
    private LinkService linkService;

    /**
     * 各信道总带宽占用
     * @return
     */
    @RequestMapping(value = "/bandwidth/usage", method = RequestMethod.GET)
    @ResponseBody
    public String getBWUsage() {
        return linkService.getBWInfoOfAllType();
    }

    //todo JSON字符串可能仍需重新设计
    /**
     * 带宽拥塞
     * @return
     */
    @RequestMapping(value = "/bandwidth/congestion", method = RequestMethod.GET)
    @ResponseBody
    public String getBWCongestion() {
        return linkService.getBWConOfAllLinks();
    }

}
