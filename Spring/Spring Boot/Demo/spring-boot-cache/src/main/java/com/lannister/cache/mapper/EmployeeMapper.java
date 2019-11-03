package com.lannister.cache.mapper;

import com.lannister.cache.bean.Employee;
import org.apache.ibatis.annotations.*;

/**
 * Created by Lannister on 2019/10/30.
 */
@Mapper
public interface EmployeeMapper {

  @Select("SELECT * FROM employee WHERE id=#{id}")
  public Employee getEmpById(Integer id);

  @Update("UPDATE employee SET lastName=#{lastName},email=#{email},gender=#{gender},d_id=#{dId} WHERE id=#{id}")
  public void updateEmp(Employee employee);

  @Delete("DELETE FROM employee WHERE id=#{id}")
  public void deleteEmp(Integer id);

  @Insert("INSERT INTO employee(lastName,email,gender,d_id) VALUES(#{lastName},#{email},#{gender},#{dId})")
  public void insertEmployee(Employee employee);

  @Select("SELECT * FROM employee WHERE lastName=#{lastName}")
  public Employee getEmpByLastName(String lastName);
}
