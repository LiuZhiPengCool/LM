package com.lzp.librarymanagament.controller.book;

import com.lzp.librarymanagament.dto.*;
import com.lzp.librarymanagament.entity.Book;
import com.lzp.librarymanagament.entity.BookRecommend;
import com.lzp.librarymanagament.service.BookRecommendService;
import com.lzp.librarymanagament.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.lzp.librarymanagament.util.Constant.*;


@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookRecommendService bookRecommendService;



    // 新增图书 更改图书
    @RequestMapping("/books/bsave")
//    @ResponseBody
    public Result saveBook(@RequestBody BookDTO bk) {
        System.out.println(bk);
        bookService.saveBook(bk);
        return Result.ok();
    }

    // 删除图书
    @GetMapping("/books/bd/{id}")
    @ResponseBody
    public Result getDelId(@PathVariable("id") Integer id) {
        bookService.deleteBKById(id);
        return Result.ok();
    }

    // 图书批量删除
    @RequestMapping("/books/pitd")
//    @ResponseBody
    public Result getPitchDel(@RequestBody Map<String, ?> map) {
        System.out.println(map.get("pids"));
        List<Integer> pids = (List<Integer>) map.get("pids");
        bookService.deleteByIds(pids);
        return Result.ok();
    }

    // 图书分类查询
    @GetMapping("/typebooks/{page}/{type}")
    @ResponseBody
    public Result getABooks(@PathVariable("page") Long page,
                            @PathVariable("type") Long type) {
        List<Book> books = bookService.getBooksGroupByType(page, type, BOOK_ALL_LIMIT);
        return Result.ok(books);
    }

    // 封页图书推荐查询
    @GetMapping("/books/allrecommend")
    @ResponseBody
    public Result getAllBookRecommend() {
        List<BookRecommend> bookRecommend = bookRecommendService.getBookAllRecommend(BOOK_ALL_RECOMMEND_LIMIT);
        return Result.ok(bookRecommend);
    }

    // 封页图书新增
    @RequestMapping("/books/brsave")
//    @ResponseBody
    public Result saveBook(@RequestBody BrDTO bk) {
        bookRecommendService.saveBr(bk);
        return Result.ok();
    }

    // 封页图书删除
    @RequestMapping("/brdel")
    public Result brDel(@RequestBody BDDTO bd) {
        bookRecommendService.delBr(bd.getBook_name());
        return Result.ok();
    }

    // 封页借阅排行
    @GetMapping("/books/bs")
    @ResponseBody
    public Result getBSBooks() {
        List<Book> bs = bookService.getBSBooks();
        return Result.ok(bs);
    }

    // 搜索
    @GetMapping("/search/{searchContent}")
    @ResponseBody
    public Result selectSearchBook(@PathVariable("searchContent") String sc) {
        books = new ArrayList<>();
        bookService.getSearchBooks(sc);
        return Result.ok();
    }


    @RequestMapping("/books/bup")
//    @ResponseBody
    public Result getEdit(@RequestBody BookDTO bk) {
        System.out.println(bk);
        bookService.updateBook(bk);
        return Result.ok();
    }

    @GetMapping("/books/br")
    @ResponseBody
    public Result getBrBook() {
        List<BookRecommend> br = bookRecommendService.getBookRecommend(BOOK_RECOMMEND_LIMIT);
        return Result.ok(br);
    }

    // 后台书籍列表
    @GetMapping("/books/back/{page}")
    @ResponseBody
    public Result getBackBooks(@PathVariable("page") Long page) {
        List<Book> books = bookService.getBooks(page, BOOK_LIMIT_BACK);
        return Result.ok(books);
    } 

    @GetMapping("/books/{page}")
    @ResponseBody
    public Result getBooks(@PathVariable("page") Long page) {
        List<Book> books = bookService.getBooks(page, BOOK_LIMIT);
        return Result.ok(books);
    }

    // 全部图书列表
    @GetMapping("/allbooks/{page}")
    @ResponseBody
    public Result getallBooks(@PathVariable("page") Long page) {
        List<Book> books = bookService.getABooks(page, BOOK_ALL_LIMIT);
        return Result.ok(books);
    }

    // 借阅排行
    @GetMapping("/sortborrbooks/{page}")
    @ResponseBody
    public Result getsortborrBooks(@PathVariable("page") Long page) {
        List<Book> books = bookService.getSortBorrBooks(page);
        return Result.ok(books);
    }

    // 推荐书籍
    @GetMapping("/books/recommend")
    @ResponseBody
    public Result getBookRecommend() {
        List<BookRecommend> bookRecommend = bookRecommendService.getBookRecommend(BOOK_RECOMMEND_LIMIT);
        return Result.ok(bookRecommend);
    }

    @GetMapping("/search/allbooks/{page}")
    @ResponseBody
    public Result getSearchBooks(@PathVariable("page") Long page) {
        List<Book> res = bookService.handleSearchBooks(page);
        return Result.ok(res);
    }

    // 后台书籍展示页面搜索
    @PostMapping("/search")
    @ResponseBody
    public Result search(@RequestBody BookDTO ut) {
        List<Book> bl = bookService.search(ut);
        return Result.ok(bl);
    }
}
