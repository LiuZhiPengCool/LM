package com.lzp.librarymanagament.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.librarymanagament.dto.BBDTO;
import com.lzp.librarymanagament.dto.BUDTO;
import com.lzp.librarymanagament.entity.Book;
import com.lzp.librarymanagament.entity.BookUser;
import com.lzp.librarymanagament.entity.BookUserBorr;
import com.lzp.librarymanagament.mapper.BookMapper;
import com.lzp.librarymanagament.mapper.BookUserBorrMapper;
import com.lzp.librarymanagament.mapper.BookUserMapper;
import com.lzp.librarymanagament.service.BookUserBorrService;
import com.lzp.librarymanagament.util.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.lzp.librarymanagament.util.Constant.BOOK_LIMIT;

@Service
public class BookUserBorrImpl extends ServiceImpl<BookUserBorrMapper, BookUserBorr> implements BookUserBorrService {

    @Autowired
    BookUserMapper bookUserMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    BookUserBorrMapper bookUserBorrMapper;

    @Override
    public List<Book> getUserBooks() {

        String phone = Login.userDTO.getPhone();
        // 查询用户id
        QueryWrapper<BookUser> qw = new QueryWrapper<BookUser>().eq("phone", phone);
        BookUser bookUser = bookUserMapper.selectOne(qw);
        Long id = bookUser.getId();

        // 查询书籍
        QueryWrapper<BookUserBorr> qw1 = new QueryWrapper<BookUserBorr>().eq("user_id", id);
        List<BookUserBorr> bookUserBorrs = bookUserBorrMapper.selectList(qw1);

        // 查询
        List<Book> res = new ArrayList<>();
        for (BookUserBorr bookUserBorr : bookUserBorrs) {
            QueryWrapper<Book> qw2 = new QueryWrapper<Book>().eq("id",bookUserBorr.getBookId());
            Book book = bookMapper.selectOne(qw2);
            res.add(book);
        }
        return res;
    }

    @Override
    public List<BUDTO> getAllUserBooks() {
        // 查询用户id
        List<BookUser> bookUsers = bookUserMapper.selectList(new QueryWrapper<>());
        List<BUDTO> res = new ArrayList<>();
        for (BookUser bookUser : bookUsers) {
            Long id = bookUser.getId();
            String phone = bookUser.getPhone();

            // 查询书籍
            QueryWrapper<BookUserBorr> qw1 = new QueryWrapper<BookUserBorr>().eq("user_id", id);
            List<BookUserBorr> bookUserBorrs = bookUserBorrMapper.selectList(qw1);

            for (BookUserBorr bookUserBorr : bookUserBorrs) {
                QueryWrapper<Book> qw2 = new QueryWrapper<Book>().eq("id",bookUserBorr.getBookId());
                Book book = bookMapper.selectOne(qw2);
                res.add(new BUDTO(phone, book.getBookName(),book.getAuthor(),book.getBookType(),book.getStore(),
                        book.getPrice(),book.getBorrowNumber(),book.getPlace()));
            }
        }
        return res;
    }

    @Override
    public List<BBDTO> getAllUserBackBooks(Long page) {
        // 查询用户id
        List<BookUser> bookUsers = bookUserMapper.selectList(new QueryWrapper<>());
        List<BBDTO> res = new ArrayList<>();
        for (BookUser bookUser : bookUsers) {
            Long id = bookUser.getId();

            // 手机号
            String phone = bookUser.getPhone();

            // 查询书籍
            QueryWrapper<BookUserBorr> qw1 = new QueryWrapper<BookUserBorr>().eq("user_id", id);
            List<BookUserBorr> bookUserBorrs = bookUserBorrMapper.selectList(qw1);

            for (BookUserBorr bookUserBorr : bookUserBorrs) {

                // 借阅归还时间
                String brt = bookUserBorr.getBorrowTime();
                String bct = bookUserBorr.getBackTime();

                QueryWrapper<Book> qw2 = new QueryWrapper<Book>().eq("id",bookUserBorr.getBookId());
                Book book = bookMapper.selectOne(qw2);
                res.add(new BBDTO(phone, book.getBookName(), brt, bct));
            }
        }

        Deque<BBDTO> result = new ArrayDeque<>();

        for(int i = (int) ((page - 1) * BOOK_LIMIT); i <= page * BOOK_LIMIT - 1 && i < res.size(); i++) {
            result.addLast(res.get(i));
        }

        return new ArrayList<>(result);
    }

    @Override
    public List<BBDTO> getOneUserBackBooks(String phone) {
        // 查询用户id
        BookUser bu = bookUserMapper.selectOne(new QueryWrapper<BookUser>().eq("phone", phone));
        List<BBDTO> res = new ArrayList<>();

        Long id = bu.getId();
        // 手机号
        String phone1 = bu.getPhone();
        // 查询书籍
        QueryWrapper<BookUserBorr> qw1 = new QueryWrapper<BookUserBorr>().eq("user_id", id);
        List<BookUserBorr> bookUserBorrs = bookUserBorrMapper.selectList(qw1);

        for (BookUserBorr bookUserBorr : bookUserBorrs) {
            // 借阅归还时间
            String brt = bookUserBorr.getBorrowTime();
            String bct = bookUserBorr.getBackTime();

            QueryWrapper<Book> qw2 = new QueryWrapper<Book>().eq("id",bookUserBorr.getBookId());
            Book book = bookMapper.selectOne(qw2);
            res.add(new BBDTO(phone, book.getBookName(), brt, bct));
        }
        return res;
    }

    @Override
    public void back(BBDTO ut) {
        BookUser user = bookUserMapper.selectOne(new QueryWrapper<BookUser>().eq("phone", ut.getPhone()));
        Book book = bookMapper.selectOne(new QueryWrapper<Book>().eq("book_name", ut.getBook_name()));
        bookUserBorrMapper.delete(new QueryWrapper<BookUserBorr>().eq("user_id",user.getId()).eq("book_id",book.getId()));
    }
}
