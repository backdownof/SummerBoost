package com.backdownof.dto;

import java.util.Objects;

public class PlayerDto {
    private final int id;
    private final String nickname;
    private final String description;

    public PlayerDto(int id, String nickname, String description) {
        this.id = id;
        this.description = description;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDto playerDto = (PlayerDto) o;
        return id == playerDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
