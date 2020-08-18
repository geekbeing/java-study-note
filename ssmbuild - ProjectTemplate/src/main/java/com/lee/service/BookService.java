package com.lee.service;

import com.lee.pojo.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookService {

    //增加一本书
    public int addBook(Book book);

    //删除一本书
    public int deleteBook(int id);

    //修改一本书
    public int updateBook(Book book);

    //查询一本书
    public List<Book> queryBookByID(int id);

    //查询全部本书
    public List<Book> queryAllBook();
}
