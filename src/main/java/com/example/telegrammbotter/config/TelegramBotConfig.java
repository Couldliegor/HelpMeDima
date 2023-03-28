package com.example.telegrammbotter.config;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
public class TelegramBotConfig {
    @Bean // спринг поместит этого бота в свой контекст!, после мы сможем его инджектить
    public TelegramBot telegramBot(@Value("${telegramm.bot.token}") String token) {
        return new TelegramBot(token);
    }
}
