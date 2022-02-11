package com.backdownof.entity;

import java.util.Objects;

public class Player {
    private int id;
    private String nickname;

    public Player(String nickname) {
        this.nickname = nickname;
    }

    public Player(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder playerSB = new StringBuilder();
        playerSB.append("Player id = ");
        playerSB.append(id);
        playerSB.append("\t\t - nickname: ");
        playerSB.append(nickname);
        return playerSB.toString();
    }
}
