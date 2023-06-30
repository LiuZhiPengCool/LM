package com.lzp.librarymanagament.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.librarymanagament.dto.BBDTO;
import com.lzp.librarymanagament.dto.User2DTO;
import com.lzp.librarymanagament.dto.UserDTO;
import com.lzp.librarymanagament.dto.UserSignDTO;
import com.lzp.librarymanagament.entity.Book;
import com.lzp.librarymanagament.entity.BookUser;
import com.lzp.librarymanagament.mapper.BookUserMapper;
import com.lzp.librarymanagament.service.BookUserService;
import com.lzp.librarymanagament.util.Code;
import com.lzp.librarymanagament.util.Login;
import com.lzp.librarymanagament.util.Sign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class BookUserServiceImpl extends ServiceImpl<BookUserMapper, BookUser> implements BookUserService {

    @Autowired
    BookUserService bookUserService;

    @Autowired
    BookUserMapper bookUserMapper;
    @Override
    public BookUser queryByUt(UserDTO ut) {
        System.out.println(ut);
        System.out.println(Code.CODE);
        QueryWrapper<BookUser> qw = new QueryWrapper<BookUser>().eq("phone", ut.getPhone());
        BookUser bookUser = bookUserMapper.selectOne(qw);
        Login.msg = "OK";

        if(bookUser == null) {
            Login.userDTO = null;
            Login.msg = "不存在用户";
            return null;
        } else {
            if(!bookUser.getPassword().equals(Code.MD5Lock(ut.getPassword()))) {
                Login.userDTO = null;
                Login.msg = "密码错误";
                return null;
            }
        }

        return bookUser;
    }

    @Override
    public void insertOne(UserSignDTO ut) {
        QueryWrapper<BookUser> qw = new QueryWrapper<BookUser>().eq("phone", ut.getPhone());
        BookUser bookUser = bookUserMapper.selectOne(qw);
        Sign.msg = "注册成功";
        if(bookUser == null) {
            BookUser bu = new BookUser(1L, ut.getPhone(), Code.MD5Lock(ut.getPassword()), ut.getEmail(), "用户");
            bookUserMapper.insert(bu);
        } else {
            Sign.msg = "账号已存在";
        }
    }

    @Override
    public String authCheck() {

        if(Login.userDTO == null) {
            return "未登录";
        }
        String phone = Login.userDTO.getPhone();
        BookUser bu = bookUserMapper.selectOne(new QueryWrapper<BookUser>().eq("phone", phone));
        return bu.getAuth();
    }

    @Override
    public void updateUser(String phone, Integer count) {
        String auth = "";
        if(count == 0)
            auth = "管理员";
        else
            auth = "用户";
        BookUser user = bookUserMapper.selectOne(new QueryWrapper<BookUser>().eq("phone", phone));
        UpdateWrapper<BookUser> uw = new UpdateWrapper<BookUser>().eq("id", user.getId());
        uw.set("auth", auth);
        bookUserService.update(user, uw);
    }

    @Override
    public void resetPwd(User2DTO ut) {
        String phone = ut.getPhone();
        BookUser user = bookUserMapper.selectOne(new QueryWrapper<BookUser>().eq("phone", ut.getPhone()));
        UpdateWrapper<BookUser> uw = new UpdateWrapper<BookUser>().eq("phone", ut.getPhone());
        user.setPassword(Code.MD5Lock("123456"));
        bookUserMapper.update(user, uw);
    }

    public void deluser(User2DTO ut) {
        bookUserMapper.delete(new QueryWrapper<BookUser>().eq("phone", ut.getPhone()));
    }

    @Override
    public void resetemail(User2DTO ut) {
        String phone = ut.getPhone();
        BookUser user = bookUserMapper.selectOne(new QueryWrapper<BookUser>().eq("phone", ut.getPhone()));
        UpdateWrapper<BookUser> uw = new UpdateWrapper<BookUser>().eq("phone", ut.getPhone());
        user.setEmail(ut.getEmail());
        bookUserMapper.update(user, uw);
    }

}
