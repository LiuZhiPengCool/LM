package com.lzp.librarymanagament.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    // 书号
    @TableId(type = IdType.AUTO)
    private Long id;

    // 书名
    private String bookName;

    // 书类型
    private Integer bookType;

    // 作者
    private String author;

    // 书价格
    private Long price;

    // 书的库存
    private Integer store;

    // 借阅次数
    private Long borrowNumber;

    // 简介
    private String introduce;

    // 书的封面
    private String img;

    // 书的位置
    private String place;
}
