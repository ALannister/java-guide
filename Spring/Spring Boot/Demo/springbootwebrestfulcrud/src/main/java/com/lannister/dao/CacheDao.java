package com.lannister.dao;


import org.springframework.stereotype.Repository;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by hp on 2019/10/9.
 */

@Repository
public class CacheDao {

  private static LinkedBlockingQueue<String> cache = null;
  static{
    cache = new LinkedBlockingQueue<>(50);
    cache.offer("abc");
    cache.offer("def");
    cache.offer("ghi");
    cache.offer("jkl");

  }
  public String remove(){
    return cache.poll();
  }

  public void add(String str){
    cache.offer(str);
  }
}
