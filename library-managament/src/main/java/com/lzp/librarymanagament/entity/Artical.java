package com.lzp.librarymanagament.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artical {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer userId;
    private String header;
    private String text;
    private String time;
    private String push;
}
