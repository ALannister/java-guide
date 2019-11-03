package com.lannister.starter;

/**
 * Created by Lannister on 2019/10/28.
 */
public class HelloService {

  HelloProperties helloProperties;

  public HelloProperties getHelloProperties() {
    return helloProperties;
  }

  public void setHelloProperties(HelloProperties helloProperties) {
    this.helloProperties = helloProperties;
  }

  public String sayHelloLannister(String name){
    return helloProperties.getPrefix() + "-" + name + "-" + helloProperties.getSuffix();
  }
}
