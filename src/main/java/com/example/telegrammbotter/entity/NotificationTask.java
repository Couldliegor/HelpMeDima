package com.example.telegrammbotter.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_tasks")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "message")
    private String message;
    @Column(name = "chat_id")
    private long chatId;
    @Column(name  = "notification_time")
    private LocalDateTime notificationDateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public LocalDateTime getNotificationDateTime() {
        return notificationDateTime;
    }

    public void setNotificationDateTime(LocalDateTime notificationDateTime) {
        this.notificationDateTime = notificationDateTime;
    }
}
