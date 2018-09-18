package com.honeypot.honeypot.service;

import com.alibaba.fastjson.JSONObject;
import com.honeypot.honeypot.entity.SystemSecurityConf;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SystemSecurityConfService {
    @Transactional
    JSONObject getSystemSecurityConf();

    @Transactional
    SystemSecurityConf getAConfByName(String confName);

    @Transactional
    JSONObject updateSystemSecurityConf(JSONObject updateConf);
}
