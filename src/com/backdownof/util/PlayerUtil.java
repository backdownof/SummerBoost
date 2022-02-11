package com.backdownof.util;

import com.backdownof.dao.PlayerDao;
import com.backdownof.entity.Player;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public final class PlayerUtil {

    public PlayerUtil() {}

    public static String createPlayer(Message msg) {
        if (PlayerDao.getInstance().getByUsername(msg.getFrom().getUserName()).isEmpty()) {
            Player player = new Player(msg.getFrom().getUserName(), msg.getFrom().getId(), msg.getChatId());
            PlayerDao.getInstance().save(player);
//            System.out.println(msg.getFrom().ge);

            return "Player created";
        } else {
//            List<Player> players = PlayerDao.getInstance().findAll();
            PlayerUtil.playersToString(PlayerDao.getInstance().findAll());

            return "Player exists";
        }
    }

    public static String playersToString(List<Player> list) {
        StringBuilder sb = new StringBuilder();
        for (Player player : list) {
            sb.append(player);
            sb.append("\n");
        }

        return sb.toString();
    }
}
