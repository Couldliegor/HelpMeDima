package com.example.telegrammbotter.Repository;

import com.example.telegrammbotter.entity.NotificationTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask,Long> {

}
//с репозиториями работают сервисы прежде всего

//Конкретно они вроде и работают с БД
