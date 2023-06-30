package com.lzp.librarymanagament;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzp.librarymanagament.entity.BookUser;
import com.lzp.librarymanagament.entity.BookUserBorr;
import com.lzp.librarymanagament.entity.Message;
import com.lzp.librarymanagament.mapper.BookUserBorrMapper;
import com.lzp.librarymanagament.mapper.BookUserMapper;
import com.lzp.librarymanagament.mapper.MessageMapper;
import com.lzp.librarymanagament.service.BookUserService;
import com.lzp.librarymanagament.util.Code;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class TestUser {

    @Autowired
    BookUserMapper bookUserMapper;
    @Autowired
    BookUserBorrMapper bookUserBorrMapper;
    @Autowired
    MessageMapper messageMapper;

    @Test
    void insertUser() {
        BookUser user1 = new BookUser(1L, "15327237368", "635241", "2962861426@qq.com", "用户");
        bookUserMapper.insert(user1);
    }

    @Test
    void select() {
        List<BookUserBorr> bookUserBorrs = bookUserBorrMapper.selectList(new QueryWrapper<>());
        System.out.println(bookUserBorrs);
    }

    @Test
    void md5() {
        String s = Code.MD5Lock("123456");
        System.out.println(s);
    }

    @Test
    void test4() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        System.out.println(format);
    }

    @Test
    void test5() {
        List<Message> messages = messageMapper.selectList(new QueryWrapper<>());
        System.out.println(messages);
    }
}
