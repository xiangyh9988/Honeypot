package com.honeypot.honeypot.service.impl;

import com.honeypot.honeypot.dao.WarnMsgDao;
import com.honeypot.honeypot.entity.AlarmInfo;
import com.honeypot.honeypot.entity.AlarmInfoResult;
import com.honeypot.honeypot.service.WarnMsgService;
import com.honeypot.honeypot.util.AlarmInfoUtil;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarnMsgServiceImpl implements WarnMsgService {
    @Autowired
    private WarnMsgDao warnMsgDao;

    @Override
    public Map<String,Object> getWarnMsgNum() {
        Map<String,Object> modelMap = new HashMap<>();
        List<AlarmInfo> alarmInfos = new ArrayList<>();
        alarmInfos = warnMsgDao.getWarnMsgNum();
        DateFormat dateFormat = DateFormat.getTimeInstance();
        int warnClock14 = 0;
        int warnClock15 = 0;
        int warnClock16 = 0;
        int warnClock17 = 0;

        for(AlarmInfo alarmInfo : alarmInfos){
//            if (dateFormat.format(alarmInfo.getTime()).substring(0,2).equals("16"))
//            System.out.println();
            switch (dateFormat.format(alarmInfo.getTime()).substring(0,2)){
                case "14":
                    warnClock14++;
                    break;
                case "15":
                    warnClock15++;
                    break;
                case "16":
                    warnClock16++;
                    break;
                case "17":
                    warnClock17++;
                    break;
            }
        }
        List<String> timeList = new ArrayList<>();
        List<Integer> sumList = new ArrayList<>();
        sumList.add(warnClock14);
        sumList.add(warnClock15);
        sumList.add(warnClock16);
        sumList.add(warnClock17);
        timeList.add("14");
        timeList.add("15");
        timeList.add("16");
        timeList.add("17");
        modelMap.put("timeList",timeList);
        modelMap.put("sumList",sumList);
        return modelMap;
    }

    @Override
    public List<AlarmInfoResult> getNewWarnMsg() {
        List<AlarmInfo> alarmInfoList = warnMsgDao.getNewWarnMsg();
        List<AlarmInfoResult> alarmInfoResults = new ArrayList<>();
        DateFormat dateFormat = DateFormat.getDateInstance();
        for (AlarmInfo alarmInfo : alarmInfoList){
            AlarmInfoResult alarmInfoResult = new AlarmInfoResult();
            alarmInfoResult.setWarnType("虚拟机警告");
            alarmInfoResult.setWarn(AlarmInfoUtil.typeMap(alarmInfo));
            alarmInfoResult.setTime(dateFormat.format(alarmInfo.getTime()));
            alarmInfoResults.add(alarmInfoResult);
        }
        return alarmInfoResults;
    }

    @Override
    public List<AlarmInfoResult> getMoreWarnMsg() {
        List<AlarmInfo> alarmInfoList = warnMsgDao.getMoreWarnMsg();
        List<AlarmInfoResult> alarmInfoResultList = new ArrayList<>();
        DateFormat dateFormat = DateFormat.getDateInstance();
        int i = 1;
        for (AlarmInfo alarmInfo : alarmInfoList){
            AlarmInfoResult alarmInfoResult = new AlarmInfoResult();
            alarmInfoResult.setIndex(i++);
            alarmInfoResult.setWarnType("虚拟机警告");
            alarmInfoResult.setWarn(AlarmInfoUtil.typeMap(alarmInfo));
            alarmInfoResult.setTime(dateFormat.format(alarmInfo.getTime()));
            alarmInfoResultList.add(alarmInfoResult);
        }
        return alarmInfoResultList;
    }
}
