package com.lzp.librarymanagament.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookRecommend {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String bookName;
    private Integer bookId;
    private String bookRImg;
}
