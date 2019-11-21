package com.lannister.user;

import com.lannister.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Lannister on 2019/11/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerUserApplicationTests {

  @Autowired
  UserService userService;

  @Test
  public void contextLoads(){
    userService.hello();
  }
}
