package ylab.lesson5.messagefilter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MessageFilterApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();

        DbClient dbClient = applicationContext.getBean(DbClient.class);
        dbClient.initTable();

        InputRabbitClient inputRabbitClient = applicationContext.getBean(InputRabbitClient.class);
        inputRabbitClient.inputMessage();
    }
}
