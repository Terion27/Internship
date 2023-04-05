package ylab.lesson5.eventsourcing.db;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
public class MessageProcessing {
    private final DbClient dbClient;
    private final RabbitClient rabbitClient;

    public MessageProcessing(DbClient dbClient, RabbitClient rabbitClient) {
        this.dbClient = dbClient;
        this.rabbitClient = rabbitClient;
    }

    public void processMessage() {
        String message = rabbitClient.getMessage();
        if (!message.isEmpty()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> map = mapper.readValue(message, Map.class);
                String operation = map.get("operation");
                if (operation.equals("savePerson")) {
                    dbClient.savePerson(map);
                } else if (operation.equals("deletePerson")) {
                    dbClient.deletePerson(map);
                } else {
                    System.out.println("Неизвестная операция");
                }
            } catch (Exception e) {
                System.out.println("Ошибка, переданные данные некорректны");
            }
        }
    }
}
