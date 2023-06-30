package com.lzp.librarymanagament.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzp.librarymanagament.dto.BBDTO;
import com.lzp.librarymanagament.dto.BUDTO;
import com.lzp.librarymanagament.entity.BookUserBorr;
import com.lzp.librarymanagament.entity.Book;

import java.util.List;

public interface BookUserBorrService extends IService<BookUserBorr> {


    List<Book> getUserBooks();

    List<BUDTO> getAllUserBooks();

    List<BBDTO> getAllUserBackBooks(Long page);

    List<BBDTO> getOneUserBackBooks(String phone);

    void back(BBDTO ut);
}
