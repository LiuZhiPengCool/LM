package com.lzp.librarymanagament.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.librarymanagament.dto.BookDTO;
import com.lzp.librarymanagament.entity.Book;
import com.lzp.librarymanagament.entity.BookRecommend;
import com.lzp.librarymanagament.mapper.BookMapper;
import com.lzp.librarymanagament.service.BookRecommendService;
import com.lzp.librarymanagament.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import static com.lzp.librarymanagament.util.Constant.*;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    BookRecommendService bookRecommendService;
    @Autowired
    BookService bookService;
    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Book> getAllBooks() {
        return list();
    }

    @Override
    public List<Book> getABooks(Long page, Integer limit) {
        List<Book> allBooks = getAllBooks();
        List<Book> reverse = new ArrayList<>();

        for(int i = allBooks.size() - 1; i >= 0; i--) {
            reverse.add(allBooks.get(i));
        }

        // 1(1~5,0~4) 2(6~10,5~9)
        Deque<Book> res = new ArrayDeque<>();

        for(int i = (int) ((page - 1) * BOOK_ALL_LIMIT); i <= page * BOOK_ALL_LIMIT - 1 && i < reverse.size(); i++) {
            res.addLast(reverse.get(i));
        }
        return new ArrayList<>(res);
    }

    @Override
    public List<Book> getBooks(Long page, Integer limit) {
        List<Book> allBooks = getAllBooks();
        List<Book> reverse = new ArrayList<>();

        for(int i = allBooks.size() - 1; i >= 0; i--) {
            reverse.add(allBooks.get(i));
        }

        // 1(1~5,0~4) 2(6~10,5~9)
        Deque<Book> res = new ArrayDeque<>();

        for(int i = (int) ((page - 1) * BOOK_LIMIT); i <= page * BOOK_LIMIT - 1 && i < reverse.size(); i++) {
            res.addLast(reverse.get(i));
        }
        return new ArrayList<>(res);
    }

    @Override
    public List<Book> getBookRecommend(Integer bookRecommendLimit) {
        List<Book> allBooks = getAllBooks();
        List<Book> res = new ArrayList<>();
        for (int i = 0; i < BOOK_RECOMMEND_LIMIT; i++) {
            res.add(allBooks.get(i));
        }

        return res;
    }

    @Override
    public List<Book> getBSBooks() {
        List<Book> books = list();
        Object[] objects = books.stream().sorted(new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return (int) (o2.getBorrowNumber() - o1.getBorrowNumber());
            }
        }).toArray();
        List<Book> res = new ArrayList<>();
        for (int i = 0; i < BOOK_BS_LIMIT_BACK; i++) {
            res.add((Book) objects[i]);
        }
        return res;
    }

    @Override
    public void deleteBKById(Integer id) {
        bookRecommendService.remove(new QueryWrapper<BookRecommend>().eq("book_id", id));
        bookService.remove(new QueryWrapper<Book>().eq("id", id));
//        System.out.println(id);
//        Object[] objects = bookService.list().stream().limit(20).toArray();
//        System.out.println(Arrays.toString(objects));
    }

    @Override
    public void deleteByIds(List<Integer> pids) {
        for (Integer id : pids) {
            bookRecommendService.remove(new QueryWrapper<BookRecommend>().eq("book_id", id));
            bookService.remove(new QueryWrapper<Book>().eq("id", id));
        }
    }

    @Override
    public void updateBook(BookDTO bk) {
        Book book = new Book();
        UpdateWrapper<Book> wrapper = new UpdateWrapper<>();
        wrapper.eq("book_name", bk.getBook_name());
        wrapper.set("book_name", bk.getBook_name());
        wrapper.set("author", bk.getBook_author());
        wrapper.set("book_type", bk.getBook_type());
        wrapper.set("price", bk.getBook_pri());
        wrapper.set("store", bk.getBook_store());
        wrapper.set("borrow_number", bk.getBook_bnum());
        wrapper.set("place", bk.getBook_pla());
        bookService.update(book, wrapper);
    }

    @Override
    public void saveBook(BookDTO bk) {
        Book b = new Book();
        b.setBookName(bk.getBook_name());
        b.setAuthor(bk.getBook_author());
        b.setStore(bk.getBook_store());
        b.setPlace(bk.getBook_pla());
        Integer bookType = bk.getBook_type();
        b.setBookType(bookType);
        Integer bookBnum = bk.getBook_bnum();
        b.setBorrowNumber(Long.valueOf(bookBnum));
        Integer bookPri = bk.getBook_pri();
        b.setPrice(Long.valueOf(bookPri));
        b.setIntroduce("默认简介");
        b.setImg("/common/imgs/p3.jpg");
        bookService.save(b);
    }

    @Override
    public List<Book> getBooksGroupByType(Long page, Long type, Integer limit) {
        QueryWrapper<Book> bookType = new QueryWrapper<Book>().eq("book_type", type);
        List<Book> allBooks = list(bookType);
        List<Book> reverse = new ArrayList<>();

        for(int i = allBooks.size() - 1; i >= 0; i--) {
            reverse.add(allBooks.get(i));
        }

        // 1(1~5,0~4) 2(6~10,5~9)
        Deque<Book> res = new ArrayDeque<>();

        for(int i = (int) ((page - 1) * limit); i <= page * limit - 1 && i < reverse.size(); i++) {
            res.addLast(reverse.get(i));
        }
        return new ArrayList<>(res);
    }

    @Override
    public void getSearchBooks(String sc) {
        QueryWrapper<Book> q1 = new QueryWrapper<Book>().like("book_name", sc);
        QueryWrapper<Book> q2 = new QueryWrapper<Book>().like("author", sc);
        List<Book> bs1 = bookMapper.selectList(q1);
        List<Book> bs2 = bookMapper.selectList(q2);

        books.addAll(bs1);
        books.addAll(bs2);
    }

    @Override
    public List<Book> handleSearchBooks(Long page) {
        // 1(1~5,0~4) 2(6~10,5~9)
        Deque<Book> res = new ArrayDeque<>();

        for(int i = (int) ((page - 1) * BOOK_ALL_LIMIT); i <= page * BOOK_ALL_LIMIT - 1 && i < books.size(); i++) {
            res.addLast(books.get(i));
        }
        return new ArrayList<>(res);
    }

    @Override
    public List<Book> search(BookDTO ut) {
        List<Book> books = new ArrayList<>();
        QueryWrapper<Book> q1;
        QueryWrapper<Book> q2;
        QueryWrapper<Book> q3;
        if(!ut.getBook_name().equals("")) {
            q1 = new QueryWrapper<Book>().like("book_name", ut.getBook_name());
            List<Book> bs1 = bookMapper.selectList(q1);
            books.addAll(bs1);
        }

        if(!ut.getBook_author().equals("")) {
            q2 = new QueryWrapper<Book>().like("author", ut.getBook_author());
            List<Book> bs2 = bookMapper.selectList(q2);
            books.addAll(bs2);
        }

        if(ut.getBook_name().equals("") && ut.getBook_author().equals("")) {
            books.addAll(list());
        }

        q3 = new QueryWrapper<Book>().like("author", ut.getBook_type());
        List<Book> bs3 = bookMapper.selectList(q3);
        books.addAll(bs3);
        return books;
    }

    @Override
    public List<Book> getSortBorrBooks(Long page) {
        List<Book> allBooks = getAllBooks();
        allBooks.sort(new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return (int) (o1.getBorrowNumber() - o2.getBorrowNumber());
            }
        });
        List<Book> reverse = new ArrayList<>();

        for(int i = allBooks.size() - 1; i >= 0; i--) {
            reverse.add(allBooks.get(i));
        }

        // 1(1~5,0~4) 2(6~10,5~9)
        Deque<Book> res = new ArrayDeque<>();

        for(int i = (int) ((page - 1) * BOOK_ALL_LIMIT); i <= page * BOOK_ALL_LIMIT - 1 && i < reverse.size(); i++) {
            res.addLast(reverse.get(i));
        }
        return new ArrayList<>(res);
    }
}
