package com.lannister.springboot.bean;

/**
 * Created by Lannister on 2019/10/21.
 */
public class Department {

  private Integer id;
  private String departmentName;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }
}
