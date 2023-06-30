package com.lzp.librarymanagament.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzp.librarymanagament.dto.User2DTO;
import com.lzp.librarymanagament.dto.UserDTO;
import com.lzp.librarymanagament.dto.UserSignDTO;
import com.lzp.librarymanagament.entity.BookUser;

import javax.servlet.http.HttpServletRequest;

public interface BookUserService extends IService<BookUser> {
    BookUser queryByUt(UserDTO ut);

    void insertOne(UserSignDTO ut);

    String authCheck();

    void updateUser(String phone, Integer count);

    void resetPwd(User2DTO ut);

    void deluser(User2DTO ut);

    void resetemail(User2DTO ut);
}
