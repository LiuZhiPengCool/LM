package com.lzp.librarymanagament.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.librarymanagament.dto.MessageDTO;
import com.lzp.librarymanagament.dto.MessageOneDTO;
import com.lzp.librarymanagament.entity.Message;
import com.lzp.librarymanagament.mapper.MessageMapper;
import com.lzp.librarymanagament.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public List<MessageDTO> allUserMessage() {

        Map<String, List<String>> map = new HashMap<String, List<String>>();
        Map<String, Boolean> mRead = new HashMap<String, Boolean>();
        List<Message> messages = list();

        List<MessageDTO> res = new ArrayList<>();

        for (Message message : messages) {
            List<String> texts = new ArrayList<>();
            if(!map.containsKey(message.getClient())) {
                texts.add(message.getMessageText());
                map.put(message.getClient(),texts);
            } else {
                List<String> ts = map.get(message.getClient());
                ts.add(message.getMessageText());
                map.put(message.getClient(), ts);
            }

            if(message.getIsRead() == 0) {
                mRead.put(message.getClient(), false);
            } else {
                mRead.put(message.getClient(), true);
            }
        }

        Set<Map.Entry<String, List<String>>> entries = map.entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            String client = entry.getKey();
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setClient(client);
            messageDTO.setMessages(entry.getValue());
            messageDTO.setRead(mRead.get(client));
            res.add(messageDTO);
        }

        return res;
    }

    @Override
    public void updateRead(String client) {
        List<Message> clients = messageMapper.selectList(new QueryWrapper<Message>().eq("client", client));
        for (Message message : clients) {
            message.setIsRead(1);
            messageMapper.update(message, new QueryWrapper<Message>().eq("id",message.getId()));
        }
    }

    @Override
    public String saveOneResponse(MessageOneDTO messageDTO) {
        Message message = new Message();
        message.setId(1L);
        message.setClient(messageDTO.getClient());
        message.setServer(messageDTO.getServer());
        message.setMessageText(messageDTO.getMessage());
        message.setIsRead(0);
        messageMapper.insert(message);
        return "OK";
    }
}
