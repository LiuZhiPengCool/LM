package com.lzp.librarymanagament.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzp.librarymanagament.dto.BookDTO;
import com.lzp.librarymanagament.entity.Book;
import lombok.Lombok;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService extends IService<Book> {

    List<Book> getAllBooks();

    public List<Book> getABooks(Long page, Integer limit);

    List<Book> getBooks(Long page, Integer limit);

    List<Book> getBookRecommend(Integer bookRecommendLimit);

    List<Book> getBSBooks();

    void deleteBKById(Integer id);

    void deleteByIds(List<Integer> pids);

    void updateBook(BookDTO bk);

    void saveBook(BookDTO bk);

    List<Book> getBooksGroupByType(Long page, Long type, Integer bookAllLimit);

    void getSearchBooks(String sc);

    List<Book> handleSearchBooks(Long page);

    List<Book> search(BookDTO ut);

    List<Book> getSortBorrBooks(Long page);
}
