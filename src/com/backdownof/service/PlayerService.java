package com.backdownof.service;

import com.backdownof.dao.PlayerDao;
import com.backdownof.dto.PlayerDto;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerService {
    private static final PlayerService INSTANCE = new PlayerService();
    private final PlayerDao playerDao = PlayerDao.getInstance();

    private PlayerService() {}

    public static PlayerService getInstance() {
        return INSTANCE;
    }

    public List<PlayerDto> findAll() {
        return playerDao.findAll().stream()
                .map(player -> new PlayerDto(
                        player.getId(),
                        player.getNickname(),
                        "player's nickname: %s".formatted(player.getNickname())
                )).collect(Collectors.toList());
    }
}
