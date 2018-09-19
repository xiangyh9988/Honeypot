package com.honeypot.honeypot.service;


import com.honeypot.honeypot.entity.SystemSecurityConf;
import org.json.JSONObject;
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
