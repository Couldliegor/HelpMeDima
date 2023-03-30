package com.example.telegrammbotter.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_tasks")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private long id;
    @Column(nullable = false, name = "message")
    private String message;
    @Column(nullable = false, name = "chat_id")
    private long chatId;
    @Column(nullable = false, name  = "notification_date_time")
    private LocalDateTime notificationDateTime;

    public NotificationTask(String message, long chatId, LocalDateTime notificationDateTime) {
        this.message = message;
        this.chatId = chatId;
        this.notificationDateTime = notificationDateTime;
    }

    public NotificationTask() {

    }

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
