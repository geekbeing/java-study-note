package com.lee.service;

import com.lee.dao.BookMapper;
import com.lee.pojo.Book;

import java.util.List;

public class BookServiceImpl  implements BookService{

    //service调用 Dao层：组合Dao
    private BookMapper bookMapper;
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public int addBook(Book book) {
        return bookMapper.addBook(book);
    }

    public int deleteBook(int id) {
        return bookMapper.deleteBook(id);
    }

    public int updateBook(Book book) {
        return bookMapper.updateBook(book);
    }

    public List<Book> queryBookByID(int id) {
        return bookMapper.queryBookByID(id);
    }

    public List<Book> queryAllBook() {
        return bookMapper.queryAllBook();
    }
}
