package com.example.telegrammbotter.service;

import com.example.telegrammbotter.Repository.NotificationTaskRepository;
import com.example.telegrammbotter.entity.NotificationTask;
import org.springframework.stereotype.Service;

@Service
public class NotificationTaskService {
    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public void save(NotificationTask  notificationTask) {
        notificationTaskRepository.save(notificationTask);
    }
}
