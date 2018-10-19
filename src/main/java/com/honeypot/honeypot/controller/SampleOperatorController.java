package com.honeypot.honeypot.controller;

import com.honeypot.honeypot.entity.Sample;
import com.honeypot.honeypot.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class SampleOperatorController {

    @Autowired
    private SampleService sampleService;

    @PostMapping("/getAllSample")
    public List<Sample> getAllSample(){
        Map<String,Object> modelMap = new HashMap<>();
        ArrayList<String> ipList = new ArrayList<>();
        ArrayList<Sample> sampleList = new ArrayList<>();
        sampleList = (ArrayList<Sample>) sampleService.getAllSample();
        modelMap.put("sampleList",sampleList);
        if (sampleList.size() != 0)
            modelMap.put("success",true);
        else
            modelMap.put("success",false);
        return sampleList;


    }
}
