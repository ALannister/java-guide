package com.lannister.springboot.mapper;

import com.lannister.springboot.bean.Employee;

/**
 * Created by Lannister on 2019/10/22.
 */
public interface EmployeeMapper {

  public Employee getEmpById(Integer Id);

  public void insertEmp(Employee employee);
}
