package com.lannister.springboot.repository;

import com.lannister.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lannister on 2019/10/22.
 */
//继承JpaRepository来完成对数据库的操作
public interface UserRepository extends JpaRepository<User,Integer> {

}
