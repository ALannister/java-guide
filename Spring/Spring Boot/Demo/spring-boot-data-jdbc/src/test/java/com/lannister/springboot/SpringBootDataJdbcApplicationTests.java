package com.lannister.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Lannister on 2019/10/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDataJdbcApplicationTests {

  @Autowired
  DataSource dataSource;

  @Test
  public void contextLoads() throws SQLException {
    System.out.println(dataSource.getClass());
    Connection connection = dataSource.getConnection();
    System.out.println(connection);
    connection.close();
  }
}
