package com.lzp.librarymanagament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticalDTO {
    private String name;
    private String header;
    private String time;
    private String text;
    private String push;
}
