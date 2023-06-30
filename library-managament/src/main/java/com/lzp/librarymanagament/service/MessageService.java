package com.lzp.librarymanagament.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzp.librarymanagament.dto.MessageDTO;
import com.lzp.librarymanagament.dto.MessageOneDTO;
import com.lzp.librarymanagament.entity.Message;

import java.util.List;

public interface MessageService extends IService<Message> {
    List<MessageDTO> allUserMessage();

    void updateRead(String client);

    String saveOneResponse(MessageOneDTO messageDTO);
}
