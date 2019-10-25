package com.lannister.dao;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 2019/10/9.
 */
@Repository
public class UserDao {

  private static Map<String,String> map;

  static{
    map = new HashMap<>();
    map.put("lxf","lxf123");
    map.put("zxh","zxh123");
    map.put("lnw","lnw123");
  }

  public boolean containsUser(String username,String password){
    return map.containsKey(username) && !StringUtils.isEmpty(password) && password.equals(map.get(username));
  }
}
