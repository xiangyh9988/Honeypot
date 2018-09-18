package com.honeypot.honeypot.controller;

import com.honeypot.honeypot.dao.PotDao;
import com.honeypot.honeypot.entity.*;
import com.honeypot.honeypot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HostManageController {
    @Autowired
    private HostManagementService hostManagementService;
    @Autowired
    private ServerService serverService;

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CityService cityService;

    @Autowired
    private PotService potService;

    @GetMapping("/getPotByIp")
    public List<Pot> getPotByIp(@RequestParam(value = "ip")String ip){
        return potService.getPotByIp(ip+"%");
    }



}
