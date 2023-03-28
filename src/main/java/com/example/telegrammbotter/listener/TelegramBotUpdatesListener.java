package com.example.telegrammbotter.listener;

import com.example.telegrammbotter.Service.NotificationTaskService;
import com.example.telegrammbotter.entity.NotificationTask;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component // или  Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final TelegramBot telegramBot;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final NotificationTaskService notificationTaskService;
    private final Pattern pattern = Pattern.compile("" +
                                                    "3(\\d{1,2}\\.\\d{1,2}\\.\\d{4} \\d{1,2}:\\d{2}) " +
                                                    "([А - я\\d\\s.,!?:]+ )" +
                                                    "");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationTaskService notificationTaskService) {
        this.telegramBot = telegramBot;
        this.notificationTaskService = notificationTaskService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this); // регистрируем Listener
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Hadles update: {}", update); //логгеры можно конфигурировать
                Message message = update.message();
                Long chatId = message.from().id();
                String text = message.text();
                if ("/start".equals(text)) {
                    sendMessage(chatId, """
                            Привет! Я могу тебе запланировать задачу. Отправь ее в формате: 12.03.2023 21:00 Сделать домашку
                            """);
                } else if (text != null) {
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.find()) {
                        LocalDateTime dataTime = parse(matcher.group(1));
                        if (Objects.isNull(dataTime)) {
                            sendMessage(chatId, "Некорректный формат даты");
                        } else {
                            String txt = matcher.group(2);
                            NotificationTask notificationTask = new NotificationTask();
                            notificationTask.setId(chatId);
                            notificationTask.setMessage(txt);
                            notificationTask.setNotificationDateTime(dataTime);
                            notificationTaskService.save(notificationTask);
                        }
                    } else {
                        sendMessage(chatId, "Некорректный формат сообщения");
                    }
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse sendResponse = telegramBot.execute(sendMessage); // возвращает ответ
        if (!sendResponse.isOk()) { // если код не 200, то мы логиурем
            logger.error("Error during sending meaasge {}", sendResponse.description());
        }
    }

    private LocalDateTime parse(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

}
