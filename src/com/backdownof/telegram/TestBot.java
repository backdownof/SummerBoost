package com.backdownof.telegram;

import com.backdownof.dao.PlayerDao;
import com.backdownof.util.DBQueryUtil;
import com.backdownof.util.PlayerUtil;
import com.backdownof.util.PropertiesUtil;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Optional;

public class TestBot extends TelegramLongPollingBot {
    private static final String BOT_NAME_KEY = "telegram.bot.name";
    private static final String BOT_TOKEN_KEY = "telegram.bot.token";

    @Override
    public String getBotUsername() {
        return PropertiesUtil.get(BOT_NAME_KEY);
    }

    @Override
    public String getBotToken() {
        return PropertiesUtil.get(BOT_TOKEN_KEY);
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
            Message message = update.getMessage();

            System.out.println(message);

            if (message.hasText() && message.isCommand() && message.getText().equals("/players")) {
                String response = PlayerUtil.createPlayer(message);
                StringBuilder sb = new StringBuilder();
                sb.append(response);
                sb.append(PlayerUtil.playersToString(PlayerDao.getInstance().findAll()));
                System.out.println(message.getChatId());

                execute(SendMessage.builder()
                        .chatId(message.getChatId().toString())
                        .text(response)
                        .text(sb.toString())
                        .build());

            }

            if (message.hasText()) {
                execute(SendMessage
                        .builder()
                        .chatId(message.getChatId().toString())
                        .text("You sent: " + message.getText())
                        .build());
            }
        }
    }


    @SneakyThrows
    private void handleMessage(Message message) {
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            
        }

//        if (message.hasText() && message.isCommand() && message.getText().equals("/players")) {
//            String response = PlayerUtil.createPlayer(message);
//            StringBuilder sb = new StringBuilder();
//            sb.append(response);
//            sb.append(PlayerUtil.playersToString(PlayerDao.getInstance().findAll()));
//            System.out.println(message.getChatId());
//
//            execute(SendMessage.builder()
//                    .chatId(message.getChatId().toString())
//                    .text(response)
//                    .text(sb.toString())
//                    .build());
//
//        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        TestBot bot = new TestBot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }
}
