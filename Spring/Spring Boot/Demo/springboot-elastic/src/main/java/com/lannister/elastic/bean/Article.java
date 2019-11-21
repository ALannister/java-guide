package com.lannister.elastic.bean;

import io.searchbox.annotations.JestId;

/**
 * Created by Lannister on 2019/11/4.
 */
public class Article {

  @JestId
  private Integer id;
  private String author;
  private String title;
  private String content;

  public Article(Integer id, String author, String title, String content) {
    this.id = id;
    this.author = author;
    this.title = title;
    this.content = content;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
