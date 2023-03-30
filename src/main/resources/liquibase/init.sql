CREATE TABLE notification_tasks
(
    id                     BIGSERIAL  PRIMARY KEY ,
    chat_id                BIGINT       NOT NULL,
    message                VARCHAR(255) NOT NULL,
    notification_date_time TIMESTAMP(6) NOT NULL
);