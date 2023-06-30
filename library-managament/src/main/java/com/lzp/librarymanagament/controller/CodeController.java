package com.lzp.librarymanagament.controller;

import com.lzp.librarymanagament.dto.Result;
import com.lzp.librarymanagament.util.Code;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CodeController {
    @RequestMapping("/code")
    @ResponseBody
    public Result Code() {
        int c = Code.createCode();
        Code.CODE = String.valueOf(c);
        System.out.println(Code.CODE);
        return Result.ok(Code.CODE);
    }
}
