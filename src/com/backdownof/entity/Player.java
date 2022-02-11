package com.backdownof.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Player {
    private int id;
    private String nickname;
    private long telegramUserId;
    private long chatId;

    public Player(String nickname, long telegramUserId, long chatId) {
        this.nickname = nickname;
        this.telegramUserId = telegramUserId;
        this.chatId = chatId;
    }
}
