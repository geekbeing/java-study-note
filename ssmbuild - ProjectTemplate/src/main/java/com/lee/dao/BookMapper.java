package com.lee.dao;

import com.lee.pojo.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {
    //增加一本书
    public int addBook(Book book);

    //删除一本书
    public int deleteBook(@Param("bookId") int id);

    //修改一本书
    public int updateBook(Book book);

    //查询一本书
    public List<Book> queryBookByID(@Param("bookId") int id);

    //查询全部本书
    public List<Book> queryAllBook();


}
