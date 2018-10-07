package com.honeypot.honeypot.controller;

import com.honeypot.honeypot.entity.*;
import com.honeypot.honeypot.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RestController
@CrossOrigin
public class HostHoneypotController {
    @Autowired
    private PotService potService;
    @Autowired
    private HostManagementService hostManagementService;
    /**
     * 主机蜜罐-主机蜜罐管理信息页面
     * @return
     */
    @GetMapping("/getListHostPot")
    @ResponseBody
    public List<Pot> listHostPot() {
        List<Pot> potList = potService.getHostPotByType("");   //暂时把type写死
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("potList",potList);
        return potService.getHostPotByType("");
    }
}
