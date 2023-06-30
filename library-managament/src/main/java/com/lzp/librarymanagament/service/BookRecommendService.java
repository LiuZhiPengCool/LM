package com.lzp.librarymanagament.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzp.librarymanagament.dto.BrDTO;
import com.lzp.librarymanagament.entity.Book;
import com.lzp.librarymanagament.entity.BookRecommend;

import java.util.List;

public interface BookRecommendService extends IService<BookRecommend> {
    List<BookRecommend> getBookRecommend(Integer bookRecommendLimit);

    void saveBr(BrDTO bk);

    List<BookRecommend> getBookAllRecommend(Integer bookAllRecommendLimit);

    void delBr(String bn);
}
