package com.honeypot.honeypot.dao;

import com.honeypot.honeypot.entity.WarningCriterial;
import com.honeypot.honeypot.entity.WarningSum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface WarningDao {

    List<WarningSum> getWarningSum(@Param("time") String time,@Param("flag") Integer flag);

    Integer getNumForOneDay(@Param("warn_time") String time, @Param("flag") Integer flag);

    List<WarningCriterial> getLastWarning();

    /**
     * 获取所有警告信息
     * @return
     */
    List<WarningSum> getAllWarningSum();
}
