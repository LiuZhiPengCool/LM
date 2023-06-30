package com.lzp.librarymanagament.controller.user;

import com.lzp.librarymanagament.dto.*;
import com.lzp.librarymanagament.entity.Book;
import com.lzp.librarymanagament.entity.BookUser;
import com.lzp.librarymanagament.service.BookUserBorrService;
import com.lzp.librarymanagament.service.BookUserService;
import com.lzp.librarymanagament.util.Login;
import com.lzp.librarymanagament.util.Sign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    BookUserService bookUserService;

    @Autowired
    BookUserBorrService bookUserBorrService;

    // 根据信息查询用户，判断是否密码正确
    @PostMapping("/login")
    @ResponseBody
    public Result queryUuser(@RequestBody UserDTO ut, HttpServletResponse response) {
        Login.userDTO = ut;
        System.out.println(Login.userDTO);
        BookUser bookUser = bookUserService.queryByUt(ut);
        if(bookUser == null) {
            return Result.fail("用户不存在 或者 密码错误");
        }
        Cookie cookie = new Cookie("user", bookUser.getPhone());
        response.addCookie(cookie);
        System.out.println("cookie1 = " + cookie);
        return Result.ok(bookUser);
    }

    // 登录权限校验 是否存在用户
/*    @GetMapping("/check")
    @ResponseBody
    public Result loginCheck() {
        System.out.println(Login.msg);
        return Result.ok(Login.msg);
    }*/

    // 显示用户信息
    @GetMapping("/info")
    @ResponseBody
    public Result getUserInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("user")) {
                System.out.println("cookie2 = " + cookie);
                System.out.println(cookie.getName());
            }
        }
        return Result.ok();
    }

    // 注册插入
    @PostMapping("/sign")
    @ResponseBody
    public Result sign(@RequestBody UserSignDTO ut) {
//        System.out.println(ut);
        bookUserService.insertOne(ut);
        return Result.ok();
    }

    // 注册权限校验
    @GetMapping("/sign/check")
    @ResponseBody
    public Result signCheck() {
//        System.out.println(Sign.msg);
        return Result.ok(Sign.msg);
    }

    // 权限校验 未登录？用户？管理员
    @GetMapping("/auth")
    @ResponseBody
    public Result authCheck() {
        String auth = bookUserService.authCheck();
        return Result.ok(auth);
    }

    // 重置邮箱
    @PostMapping("/resetemail")
    @ResponseBody
    public Result resetemail(@RequestBody User2DTO ut) {
        bookUserService.resetemail(ut);
        return Result.ok();
    }

    // 重置密码
    @PostMapping("/resetpwd")
    @ResponseBody
    public Result resetpwd(@RequestBody User2DTO ut) {
        bookUserService.resetPwd(ut);
        return Result.ok();
    }

    // 删除用户
    @PostMapping("/deluser")
    @ResponseBody
    public Result deluser(@RequestBody User2DTO ut) {
        bookUserService.deluser(ut);
        return Result.ok();
    }

    // 用户借阅图书信息
    @GetMapping("/back/{page}")
    @ResponseBody
    public Result allUserBackBook(@PathVariable("page") Long page) {
        List<BBDTO> books = bookUserBorrService.getAllUserBackBooks(page);
        return Result.ok(books);
    }

    // 获取当前用户所有借阅书籍
    @GetMapping("/back/oneborrowbook")
    @ResponseBody
    public Result allUserBackBook() {
        List<BBDTO> books = bookUserBorrService.getOneUserBackBooks(Login.userDTO.getPhone());
        return Result.ok(books);
    }

    @GetMapping("/books")
    @ResponseBody
    public Result userBook() {
        List<Book> books = bookUserBorrService.getUserBooks();
        return Result.ok(books);
    }

    @GetMapping("/all")
    @ResponseBody
    public Result allUserBook() {
        List<BUDTO> books = bookUserBorrService.getAllUserBooks();
        return Result.ok(books);
    }

    // 获取所有用户
    @GetMapping("/alluser")
    @ResponseBody
    public Result alluser() {
        return Result.ok(bookUserService.list());
    }

    // 更改用户权限
    @GetMapping("/updateuser/{phone}/{count}")
    @ResponseBody
    public Result updateUser(
            @PathVariable("phone") String phone,
            @PathVariable("count") Integer count) {
        bookUserService.updateUser(phone, count);
        return Result.ok();
    }

    // 确认还书
    @PostMapping("/backbook")
    @ResponseBody
    public Result back(@RequestBody BBDTO ut) {
        bookUserBorrService.back(ut);
        return Result.ok();
    }
}
