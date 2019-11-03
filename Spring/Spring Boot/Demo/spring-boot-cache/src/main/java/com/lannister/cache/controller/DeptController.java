package com.lannister.cache.controller;

import com.lannister.cache.bean.Department;
import com.lannister.cache.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lannister on 2019/11/2.
 */
@RestController
public class DeptController {

  @Autowired
  DeptService deptService;

  @GetMapping("/dept/{id}")
  public Department getDept(@PathVariable("id") Integer id){
    return deptService.getDeptById(id);
  }
}
