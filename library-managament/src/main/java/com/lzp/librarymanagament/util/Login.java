package com.lzp.librarymanagament.util;

import com.lzp.librarymanagament.dto.ArticalDTO;
import com.lzp.librarymanagament.dto.SessionDTO;
import com.lzp.librarymanagament.dto.UserDTO;
import lombok.Data;

@Data
public class Login {

    public static ArticalDTO articalDTO;
    public static UserDTO userDTO;
//    public static SessionDTO sessionDTO;
    public static String msg = "OK";
}
