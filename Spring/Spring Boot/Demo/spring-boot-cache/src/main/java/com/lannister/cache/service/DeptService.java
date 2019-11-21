package com.lannister.cache.service;

import com.lannister.cache.bean.Department;
import com.lannister.cache.mapper.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

/**
 * Created by Lannister on 2019/11/2.
 */
@CacheConfig(cacheNames = "dept",cacheManager = "deptCacheManager")
@Service
public class DeptService {

  @Autowired
  private DepartmentMapper departmentMapper;

  @Qualifier("deptCacheManager")
  @Autowired
  RedisCacheManager deptCacheManager;

  private Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * 注解的方式使用缓存
   * 缓存的数据能存入redis
   * 第二次从缓存中查询就不能反序列化回来
   * 存的是dept的json数据；CacheManager默认使用RedisTemplate<Object,Employee>操作redis
   *
   * @param id
   * @return
   */
//  @Cacheable
//  public Department getDeptById(Integer id){
//    logger.info("查询部门" + id);
//    Department dept = departmentMapper.getDeptById(id);
//    return dept;
//  }

  /**
   * 编码的方式使用缓存
   * 使用缓存管理器得到缓存，进行API调用
   * @param id
   * @return
   */
  public Department getDeptById(Integer id){
    //获取某个缓存
    Cache deptCache = deptCacheManager.getCache("dept");
    Department dept = deptCache.get("dept:" + id, Department.class);
    if(dept == null){
      logger.info("查询部门" + id);
      dept = departmentMapper.getDeptById(id);
      deptCache.put("dept:"+id,dept);
    }
    return dept;
  }
}
