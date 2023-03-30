package com.example.telegrammbotter.Repository;

import com.example.telegrammbotter.entity.NotificationTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask,Long> {
    List<NotificationTask> findAllByNotificationDateTime(LocalDateTime localDateTime);

    List<NotificationTask> findAllByNotificationDateTimeAndChatId(LocalDateTime localDateTime, long ChatId);

    //бывает что нужно написать гигантский сложный вопрос, тогда приходит на помощь Query
    //    @Query("SELECT nt FROM NotificationTask nt WHERE nt.user.name like %:nameLike%") //тут мы пишем названия классов и полей
    @Query(value = "SELECT nt.* FROM notification_tasks nt INNER JOIN user u ON nt.user.id = u.id WHERE u.name like %:nameLike%", nativeQuery = true ) //нативный, не уррощенный вариант
    List<NotificationTask> findAllByNameLike(@Param("nameLike") String name); //аннотация для параметризации в запросе Query

    @Modifying //без этой аннотации запрос работать не будет, тк спринг думает что это SELECT, другие любые манипуляции с БД мы должны помечать такой анотацией
    @Query("DELETE FROM NotificationTask WHERE message like %:nameLike%")
    void removeAllLike(@Param("nameLike") String name);
}
//с репозиториями работают сервисы прежде всего

//Конкретно они вроде и работают с БД
