package com.example.telegrammbotter.timer;

import com.example.telegrammbotter.Repository.NotificationTaskRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationTaskTimer {
    private final NotificationTaskRepository notificationTaskRepository;
    private final TelegramBot telegramBot;

    public NotificationTaskTimer(NotificationTaskRepository notificationTaskRepository, TelegramBot telegramBot) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.telegramBot = telegramBot;
    }
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES) //fixedDelay == раз в минуту // раз в минуту будет вызываться
    //delay - минута после последнего
    //rate - не ждет окончания
    public void task() {
        notificationTaskRepository.findAllByNotificationDateTime(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES) // просто обрезаем секунды до минут
        ).forEach(notificationTask -> {
            telegramBot.execute(
                    new SendMessage(notificationTask.getChatId(),"Вы просили напомнить вам о задаче" + notificationTask.getMessage()));
                         new SendMessage(notificationTask.getChatId(), notificationTask.getMessage());
        notificationTaskRepository.delete(notificationTask);
        });
    }
}
