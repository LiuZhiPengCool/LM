package com.lzp.librarymanagament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BBDTO {
    private String phone;
    private String book_name;
    private String borrow_time;
    private String back_time;
}
