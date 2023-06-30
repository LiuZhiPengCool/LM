package com.lzp.librarymanagament.service.impl;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.librarymanagament.dto.BrDTO;
import com.lzp.librarymanagament.entity.Book;
import com.lzp.librarymanagament.entity.BookRecommend;
import com.lzp.librarymanagament.mapper.BookMapper;
import com.lzp.librarymanagament.mapper.BookRecommendMapper;
import com.lzp.librarymanagament.service.BookRecommendService;
import com.lzp.librarymanagament.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.lzp.librarymanagament.util.Constant.BOOK_RECOMMEND_LIMIT;

@Service
public class BookRecommendServiceImpl  extends ServiceImpl<BookRecommendMapper, BookRecommend> implements BookRecommendService {

    @Autowired
    BookService bookService;
    @Autowired
    BookRecommendService bookRecommendService;
    @Autowired
    BookRecommendMapper bookRecommendMapper;
    @Autowired
    BookMapper bookMapper;

    @Override
    public List<BookRecommend> getBookRecommend(Integer bookRecommendLimit) {
        List<BookRecommend> list = list();
        List<BookRecommend> res = new ArrayList<>();

        for (int i = 0; i < BOOK_RECOMMEND_LIMIT; i++) {
            res.add(list.get(i));
        }
        return res;
    }

    @Override
    public void saveBr(BrDTO bk) {
        String bname = bk.getBook_name();
        List<Book> bookNames = bookMapper.selectList(new QueryWrapper<Book>().eq("book_name", bname));
        int bId= Integer.parseInt(String.valueOf(bookNames.get(0).getId()));
        bookRecommendMapper.insert(new BookRecommend(1, bk.getBook_name(), bId, "/common/imgs/bir3.jpg"));
    }

    @Override
    public List<BookRecommend> getBookAllRecommend(Integer bookAllRecommendLimit) {
        List<BookRecommend> list = list();
        List<BookRecommend> res = new ArrayList<>();

        for (int i = 0; i < bookAllRecommendLimit && i < list.size(); i++) {
            res.add(list.get(i));
        }
        return res;
    }

    @Override
    public void delBr(String bn) {
        Book bookName = bookMapper.selectOne(new QueryWrapper<Book>().eq("book_name", bn));
        Long id = bookName.getId();
        bookRecommendService.remove(new QueryWrapper<BookRecommend>().eq("book_id", id));
    }
}
