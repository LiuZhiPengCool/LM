package com.lzp.librarymanagament;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lzp.librarymanagament.mapper")
public class LibraryManagamentApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagamentApplication.class, args);
    }

}
