package com.honeypot.honeypot.dao;

import com.honeypot.honeypot.entity.Pot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PotDao {
    List<Pot> getPotByType(@Param("type") String type);

    List<Pot> getPotByIp(@Param("ip") String ip);
}
