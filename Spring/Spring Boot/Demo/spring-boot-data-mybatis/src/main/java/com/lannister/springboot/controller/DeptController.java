package com.lannister.springboot.controller;

import com.lannister.springboot.bean.Department;
import com.lannister.springboot.bean.Employee;
import com.lannister.springboot.mapper.DepartmentMapper;
import com.lannister.springboot.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lannister on 2019/10/22.
 */
@RestController
public class DeptController {

  @Autowired
  DepartmentMapper departmentMapper;

  @Autowired
  EmployeeMapper employeeMapper;

  @GetMapping("/dept/{id}")
  public Department getDepartment(@PathVariable("id") Integer id){
    return departmentMapper.getDeptById(id);
  }

  @GetMapping("/dept")
  public Department insertDept(Department department){
    departmentMapper.insertDept(department);
    return department;
  }

  @GetMapping("/emp/{id}")
  public Employee getEmp(@PathVariable("id") Integer id){
    return employeeMapper.getEmpById(id);
  }
}
