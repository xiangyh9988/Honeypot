package com.honeypot.honeypot.dao;

import com.honeypot.honeypot.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentDao {
    /**
     * 获取所有department信息
     * @return
     */
    List<Department> getAllTypes();
}
