package com.lzp.librarymanagament.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.ws.soap.Addressing;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUserBorr {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private String borrowTime;
    private String backTime;

}
