package com.lzp.librarymanagament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageDTO {

    private String client;
    private String server;
    private List<String> messages;
    boolean read;
}
