package com.honeypot.honeypot.service;

import com.honeypot.honeypot.entity.Pot;

import java.util.List;
import java.util.Map;

public interface PotService {
    /**
     * 应该根据类型获取蜜罐信息，暂时写死
     * @return
     */
    List<Pot> getPotByType(String type);

    List<Pot> getPotByIp(String ip);
}
