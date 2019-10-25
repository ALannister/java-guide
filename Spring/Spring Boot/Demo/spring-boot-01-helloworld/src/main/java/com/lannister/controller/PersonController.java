package com.lannister.controller;

import com.lannister.bean.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hp on 2019/9/18.
 */

@RestController
public class PersonController {

  @Autowired
  private Person person;

  @RequestMapping("/person")
  public String person(){
    return person.toString();
  }
}
