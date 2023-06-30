package com.lzp.librarymanagament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private String book_name;
    private String book_author;
    private Integer book_type;
    private Integer book_store;
    private Integer book_pri;
    private Integer book_bnum;
    private String book_pla;
}
