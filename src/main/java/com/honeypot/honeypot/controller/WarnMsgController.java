package com.honeypot.honeypot.controller;

import com.honeypot.honeypot.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class WarnMsgController {
    @Autowired
    private WarningService warningService;

    /**
     * 没问题
     * @return
     */
    @RequestMapping(value = "/getSumOfWarningSumForMonth",method = RequestMethod.GET)
    public Map<String,Object> getSumOfWarningSumForMonth(){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap = warningService.getSumOfWarningSumForMonth();
        return modelMap;
    }
    //以下是还没有测试的


//    @GetMapping(value = "")
//    public void getOrignialLogsForMac(){
//        Map<String, String> items = getItems();
//        String json = logQueryService.getOriginalLogsForMac(macAddress, "original", items, page, pageSize);
//
//    }

    /**
     * 没问题
     * @return
     */
    @GetMapping(value = "/getWarningSum")
    public List<Integer> getWarningSum(){
        Map<String, Integer> sums = new HashMap<String, Integer>();
        Integer sumOfSensitive = warningService.getSumOfWarningSumForYear(1);
        Integer sumOfSeq = warningService.getSumOfWarningSumForYear(0);
        Integer sumOfVir = warningService.getSumOfWarningSumForYear(2);
        List<Integer> list = new ArrayList<Integer>();
        list.add(sumOfSeq);
        list.add(sumOfSensitive);
        list.add(sumOfVir);
        sums.put("sensitive", sumOfSensitive);
        sums.put("BA", sumOfSeq);
        sums.put("virtual", sumOfVir);
       return list;
    }

    /**
     * 获取警告数字，正确待定
     */
    @GetMapping(value = "/getWarningNum")
    public List<Object> getWarningNum(){
        //new
        List<List<Object>> warningListForB = warningService.getNumOfWarningFor7DaysNew(0);
        List<List<Object>> warningListForS = warningService.getNumOfWarningFor7DaysNew(1);
        List<List<Object>> warningListForV = warningService.getNumOfWarningFor7DaysNew(2);
        List<Object> res = new ArrayList<Object>();
        res.add(warningListForB);
        res.add(warningListForS);
        res.add(warningListForV);
        return res;
    }

    @GetMapping("/getAllWarningSum")
    public List<Map<String,Object>> getAllWarningSum(){
        return warningService.getAllWarningSum();
    }


}
