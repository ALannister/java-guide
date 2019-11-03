package com.lannister.cache.controller;

import com.lannister.cache.bean.Employee;
import com.lannister.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lannister on 2019/10/30.
 */
@RestController
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @GetMapping("/emp/{id}")
  public Employee getEmployee(@PathVariable("id") Integer id){
    Employee emp = employeeService.getEmp(id);
    return emp;
  }

  @GetMapping("/emp")
  public Employee update(Employee employee){
    Employee emp = employeeService.updateEmp(employee);
    return emp;
  }

  @GetMapping("/delemp/{id}")
  public String deleteEmp(@PathVariable("id") Integer id){
    employeeService.deleteEmp(id);
    return "success";
  }

  @GetMapping("/emp/lastname/{lastName}")
  public Employee getEmpByLastName(@PathVariable("lastName") String lastName){
    return employeeService.getEmpByLastName(lastName);
  }

}
