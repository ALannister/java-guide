package com.lannister.repository;

import com.lannister.elastic.bean.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by Lannister on 2019/11/5.
 */
public interface BookRepository extends ElasticsearchRepository<Book,Integer> {

  public List<Book> findByBookNameLike(String name);
}
