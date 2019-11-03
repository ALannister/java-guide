package com.lannister.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Lannister on 2019/10/28.
 */
@ConfigurationProperties(prefix = "lannister.hello")
public class HelloProperties {

  private String prefix;
  private String suffix;

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }
}
