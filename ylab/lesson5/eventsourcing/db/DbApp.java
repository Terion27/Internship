package ylab.lesson5.eventsourcing.db;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbApp {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        // тут пишем создание и запуск приложения работы с БД

        MessageSchedule messageSchedule = applicationContext.getBean(MessageSchedule.class);
        messageSchedule.start();
    }

}
