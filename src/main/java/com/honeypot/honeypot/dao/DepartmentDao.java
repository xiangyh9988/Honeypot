package com.honeypot.honeypot.dao;

import com.honeypot.honeypot.entity.Department;

import java.util.List;

public interface DepartmentDao {
    // 获取全部部门信息
    public List<Department> getAllDepts();
    // 根据部门名称获取单个部门信息
    public Department getDeptByDepName(String depName);
    // 根据部门id获取单个部门信息
    public Department getDeptById(int id);
    // 增加部门
    public boolean addDept(Department newDepartment);
    // 删除部门
    public boolean delDept(int id);
    // 更新部门
    public boolean updateDept(Department updateDepartment);
}
