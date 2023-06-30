package com.lzp.librarymanagament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BUDTO {
    private String phone;
    private String book_name;
    private String book_author;
    private Integer book_type;
    private Integer book_store;
    private Long book_pri;
    private Long book_bnum;
    private String book_pla;

}
