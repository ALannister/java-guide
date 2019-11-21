package com.lannister.elastic;

import com.lannister.elastic.bean.Article;
import com.lannister.elastic.bean.Book;
import com.lannister.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * Created by Lannister on 2019/11/4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootElasticApplicationTests {

  @Autowired
  JestClient jestClient;
  @Autowired
  BookRepository bookRepository;

  @Test
  public void index() throws IOException {
    //1、给ES中索引（保存）一个文档
    Article article = new Article(1, "好消息", "zhangsan", "Hello World");

    //2、构建一个索引功能
    Index index = new Index.Builder(article).index("lannister").type("news").build();

    //3、执行
    jestClient.execute(index);
  }

  @Test
  public void search() throws IOException {
    //查询表达式
    String json = "{\n" +
        "\t\"query\":{\n" +
        "\t\t\"match\":{\n" +
        "\t\t\t\"content\":\"hello\"\n" +
        "\t\t}\n" +
        "\t}\n" +
        "}";

    //构建搜索功能
    Search search = new Search.Builder(json).addIndex("lannister").addType("news").build();

    //执行
    SearchResult result = jestClient.execute(search);
    System.out.println(result.getJsonString());
  }

  @Test
  public void test(){
    Book book = new Book(1,"十一字杀人","东野圭吾");
    bookRepository.index(book);
  }

  @Test
  public void find(){
    List<Book> list = bookRepository.findByBookNameLike("十");
    for(Book book : list){
      System.out.println(book);
    }
  }

}
