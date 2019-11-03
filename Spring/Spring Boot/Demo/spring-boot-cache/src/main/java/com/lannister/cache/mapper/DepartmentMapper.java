package com.lannister.cache.mapper;

import com.lannister.cache.bean.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Lannister on 2019/10/30.
 */
@Mapper
public interface DepartmentMapper {

  @Select("SELECT * FROM department WHERE id=#{id}")
  public Department getDeptById(Integer id);
}
