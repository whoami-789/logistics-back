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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;



import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot /*implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer */ {
//
//    private final TelegramClient telegramClient;
//    private final UserService userService;
//    private final UserRepository userRepository;
//
//    public TelegramBot(UserService userService, UserRepository userRepository) {
//        this.userService = userService;
//        this.userRepository = userRepository;
//        telegramClient = new OkHttpTelegramClient(getBotToken());
//    }
//
//    @Override
//    public String getBotToken() {
//        return "7473945899:AAGQycFMs_jY5Rw7LKNVIlLDMCIg9B7ODJQ";
//    }
//
//    @Override
//    public LongPollingUpdateConsumer getUpdatesConsumer() {
//        return this;
//    }
//
//    @Override
//    public void consume(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String messageText = update.getMessage().getText();
//            Long chatId = update.getMessage().getChatId();
//            String account = update.getMessage().getUserShared().getUsername();
//
//            if (messageText.startsWith("/start ")) {
//                String token = messageText.substring("/start ".length());
//
//                // Найти пользователя по токену
//                User user = userService.findByRegistrationToken(token);
//                if (user != null) {
//                    // Сохранить chat_id в базе данных
//                    user.setChatId(chatId);
//                    user.setTelegram(account);
//                    userRepository.save(user);
//
//                    // Отправить подтверждающее сообщение
//                    SendMessage message = SendMessage.builder()
//                            .chatId(chatId.toString())
//                            .text("Ваш аккаунт успешно привязан к боту! В него будут приходить новости и ваши новые заказы☺️\nМожете возвращаться на сайт.")
//                            .build();
//                    try {
//                        telegramClient.execute(message);
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    SendMessage message = SendMessage.builder()
//                            .chatId(chatId.toString())
//                            .text("Неверный токен!")
//                            .build();
//                    try {
//                        telegramClient.execute(message);
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } else if (update.hasCallbackQuery()) {
//            handleCallbackQuery(update);
//        }
//    }
//
//    public void sendMessageWithInlineButtons(Long chatId, String text, Long orderId) {
//        // Создаем кнопки
//        InlineKeyboardButton acceptButton = InlineKeyboardButton.builder()
//                .text("Принять✅")
//                .callbackData("accept_" + orderId)
//                .build();
//
//        InlineKeyboardButton rejectButton = InlineKeyboardButton.builder()
//                .text("Отказаться❌")
//                .callbackData("reject_" + orderId)
//                .build();
//
//        // Создаем строки для кнопок
//        InlineKeyboardRow row = new InlineKeyboardRow();
//        row.add(acceptButton);
//        row.add(rejectButton);
//
//        // Создаем и заполняем InlineKeyboardMarkup
//        List<InlineKeyboardRow> rows = new ArrayList<>();
//        rows.add(row); // Добавляем строку с кнопками в список строк
//
//        // Теперь создаем InlineKeyboardMarkup, используя конструктор
//        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(rows);
//
//        // Создаем и отправляем сообщение
//        SendMessage message = SendMessage.builder()
//                .chatId(chatId.toString())
//                .text(text)
//                .replyMarkup(markup)
//                .build();
//
//        try {
//            telegramClient.execute(message);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void handleCallbackQuery(Update update) {
//        String callbackData = update.getCallbackQuery().getData();
//        Long chatId = update.getCallbackQuery().getMessage().getChatId();
//        String responseMessage;
//
//        if (callbackData.startsWith("accept_")) {
//            Long orderId = Long.parseLong(callbackData.split("_")[1]);
//            responseMessage = "Отлично, заказ ваш!";
//            // Здесь можно добавить логику для обработки принятия заказа
//        } else if (callbackData.startsWith("reject_")) {
//            Long orderId = Long.parseLong(callbackData.split("_")[1]);
//            responseMessage = "Поищем еще 🌏";
//            // Здесь можно добавить логику для обработки отказа от заказа
//        } else {
//            responseMessage = "Неизвестная команда.";
//        }
//
//        // Отправляем сообщение пользователю
//        SendMessage message = SendMessage.builder()
//                .chatId(chatId.toString())
//                .text(responseMessage)
//                .build();
//
//        try {
//            telegramClient.execute(message);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @AfterBotRegistration
//    public void afterRegistration(BotSession botSession) {
//        System.out.println("Registered bot running state is: " + botSession.isRunning());
//    }
}
