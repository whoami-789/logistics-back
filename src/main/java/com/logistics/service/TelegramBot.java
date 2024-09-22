package com.logistics.service;
import com.logistics.model.User;
import com.logistics.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class TelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final UserService userService;
    private final UserRepository userRepository;

    public TelegramBot(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        telegramClient = new OkHttpTelegramClient(getBotToken());
    }

    @Override
    public String getBotToken() {
        return "7473945899:AAGQycFMs_jY5Rw7LKNVIlLDMCIg9B7ODJQ";
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (messageText.startsWith("/start ")) {
                String token = messageText.substring("/start ".length());

                // Найти пользователя по токену
                User user = userService.findByRegistrationToken(token);
                if (user != null) {
                    // Сохранить chat_id в базе данных
                    user.setChatId(chatId);
                    userRepository.save(user);

                    // Отправить подтверждающее сообщение
                    SendMessage message = SendMessage.builder()
                            .chatId(chatId.toString())
                            .text("Ваш аккаунт успешно привязан к боту! В него будут приходить новости и ваши новые заказы☺️ \n Можете возвращаться на сайт.")
                            .build();
                    try {
                        telegramClient.execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    SendMessage message = SendMessage.builder()
                            .chatId(chatId.toString())
                            .text("Неверный токен!")
                            .build();
                    try {
                        telegramClient.execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        System.out.println("Registered bot running state is: " + botSession.isRunning());
    }
}
