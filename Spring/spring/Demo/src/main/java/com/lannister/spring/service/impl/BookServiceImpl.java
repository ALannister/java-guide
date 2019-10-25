package com.lannister.spring.service.impl;

import com.lannister.spring.dao.BookDao;
import com.lannister.spring.service.BookService;

/**
 * Created by hp on 2019/9/26.
 */
public class BookServiceImpl implements BookService{

  // 方式1：之前，接口=实现类
  //	private BookDao bookDao = new BookDaoImpl();
  // 方式2：接口 + setter
  private BookDao bookDao;
  public void setBookDao(BookDao bookDao) {
    this.bookDao = bookDao;
  }

  @Override
  public void addBook() {
    this.bookDao.addBook();
  }
}
