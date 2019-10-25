package com.lannister.spring.dao.impl;

import com.lannister.spring.dao.BookDao;

/**
 * Created by hp on 2019/9/26.
 */
public class BookDaoImpl implements BookDao {
  @Override
  public void addBook() {
    System.out.println("[di] add a book");
  }
}
