package com.lzp.librarymanagament.controller.message;

import com.lzp.librarymanagament.dto.MessageDTO;
import com.lzp.librarymanagament.dto.MessageOneDTO;
import com.lzp.librarymanagament.dto.Result;
import com.lzp.librarymanagament.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/alluser/message")
    @ResponseBody
    public Result allUserMessage() {
        List<MessageDTO> messageDTOS = messageService.allUserMessage();
        return Result.ok(messageDTOS);
    }

    @GetMapping("/read/{client}")
    @ResponseBody
    public Result allUserMessage(@PathVariable("client") String client) {
        messageService.updateRead(client);
        return Result.ok();
    }

    // 消息栏回复
    @RequestMapping("/responsemessage")
    @ResponseBody
    public Result responseMessage(@RequestBody MessageOneDTO messageDTO) {
        System.out.println(messageDTO);
        String res = messageService.saveOneResponse(messageDTO);
        return Result.ok(res);
    }
}
