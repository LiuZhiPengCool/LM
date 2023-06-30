package com.lzp.librarymanagament.util;

import com.lzp.librarymanagament.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    // 推荐位书籍限制
    public static final Integer BOOK_BS_LIMIT_BACK = 10;
    // 后台每页显示数量
    public static final Integer BOOK_LIMIT_BACK = 30;
    // 每页显示数量
    public static final Integer BOOK_LIMIT = 6;
    public static final Integer BOOK_ALL_LIMIT = 12;
    public static final Integer BOOK_RECOMMEND_LIMIT = 4;
    public static final Integer BOOK_ALL_RECOMMEND_LIMIT = 8;
    public static final String BOOK_LIST = "books";

    public static String[] IMG_PATH = {"p1", "p2", "p3", "p4", "1", "2", "3", "4", "5", "bir4"};

    public static List<Book> books = new ArrayList<>();
}
