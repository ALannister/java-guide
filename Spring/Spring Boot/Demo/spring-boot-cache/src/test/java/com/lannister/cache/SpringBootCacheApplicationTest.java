package com.lannister.cache;

import com.lannister.cache.bean.Employee;
import com.lannister.cache.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Lannister on 2019/10/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootCacheApplicationTest {

  @Autowired
  private EmployeeMapper employeeMapper;
  @Autowired
  private StringRedisTemplate stringRedisTemplate;//操作k-v都是字符串
  @Autowired
  private RedisTemplate redisTemplate;//k-v都是对象的
  @Autowired
  private RedisTemplate<Object,Employee> empRedisTemplate;

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Test
  public void testMysql01(){
    Employee emp = employeeMapper.getEmpById(1);
    logger.info(emp.toString());
  }

  /**
   * Redis常见的五大数据集合：
   * String(字符串)、List（列表）、Set(集合)、Hash(散列)、ZSet(有序集合)
   * stringRedisTemplate.opsForValue()[String（字符串）]
   * stringRedisTemplate.opsForList()[List（列表）]
   * stringRedisTemplate.opsForSet()[Set（集合）]
   * stringRedisTemplate.opsForHash()[Hash（散列）]
   * stringRedisTemplate.opsForZSet()[ZSet（有序集合）]
   */
  @Test
  public void testRedis01(){
    stringRedisTemplate.opsForValue().append("msg","xiaolin");
    String msg = stringRedisTemplate.opsForValue().get("msg");
    logger.info(msg);
    stringRedisTemplate.opsForList().leftPush("mylist","零");
    stringRedisTemplate.opsForList().leftPush("mylist","起");
  }

  //测试保存对象
  @Test
  public void testRedis02(){
    Employee emp = employeeMapper.getEmpById(1);
    //默认如果保存对象，使用jdk序列化机制，序列化后的数据保存到redis中
    //redisTemplate.opsForValue().set("emp-01",emp);
    //1、将数据以json的方式保存
      //（1）自己将对象转为json
      //（2）redisTemplate默认的序列化规则,改变默认的序列化规则
    empRedisTemplate.opsForValue().set("emp-01",emp);

  }
}
