package com.lzp.librarymanagament;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lzp.librarymanagament.entity.Book;
import com.lzp.librarymanagament.entity.BookRecommend;
import com.lzp.librarymanagament.mapper.BookMapper;
import com.lzp.librarymanagament.mapper.BookRecommendMapper;
import com.lzp.librarymanagament.util.Constant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LibraryManagamentApplicationTests {

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookRecommendMapper bookRecommendMapper;
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    @Autowired
//    private RedisTemplate<String, ? extends Object> redisTemplate;

    @Test
    void testSplice() {

    }
    
    @Test
    void contextLoads() {
    }

    @Test
    void updateImg() {
        List<Book> books = bookMapper.selectList(new QueryWrapper<>());
        for (Book book : books) {
            int index = (int) (Math.random() * Constant.IMG_PATH.length);
            book.setImg("/common/imgs/" + Constant.IMG_PATH[index] + ".jpg");
            UpdateWrapper<Book> bookType = new UpdateWrapper<>(book);
            int update = bookMapper.updateById(book);
        }
    }

    @Test
    void updateType() {
        List<Book> books = bookMapper.selectList(new QueryWrapper<>());
        for (Book book : books) {
            int type = (int) (Math.random() * 12);
            if(type == 0) {
                type = 1;
            } else if(type == 12) {
                type = 11;
            }
            book.setBookType(type);
            UpdateWrapper<Book> bookType = new UpdateWrapper<>(book);
            int update = bookMapper.updateById(book);
        }
    }

    @Test
    void testSql() {
        List<BookRecommend> books = bookRecommendMapper.selectList(new QueryWrapper<>());
        for (BookRecommend book : books) {
            System.out.println(book);
        }
    }

    @Test
    void testMysql() {
        bookMapper.insert(new Book(1L, "书籍自传", 1, "书籍", 250L, 10,
                1L, "书籍自传", "/common/imgs/e.jpg", "x2-1-1109"));
    }

    @Test
    void pitchAdd() {
        for (int i = 99; i <= 200; i++) {
            bookMapper.insert(
                    new Book(1L,"书籍" + i + "号",
                            1, "作者：作者" + i + "号", 250L, 10 + i, 1L + i,
                            "作者"+ i + "的自传。face都不要了!", "/common/imgs/1.jpg", "x2-" + i + "-" + i
            ));
        }
    }

    @Test
    void getAllBooks() {
        List<Book> books = bookMapper.selectList(new QueryWrapper<>());
        for (Book book : books) {
            System.out.println(book.getId() + book.getBookName());
        }
    }
}
