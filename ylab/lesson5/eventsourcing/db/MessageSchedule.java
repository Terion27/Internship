package ylab.lesson5.eventsourcing.db;

import org.springframework.stereotype.Component;

@Component
public class MessageSchedule {
    private final MessageProcessing messageProcessing;

    public MessageSchedule(MessageProcessing messageProcessing) {
        this.messageProcessing = messageProcessing;
    }

    public void start() {
        while (!Thread.currentThread().isInterrupted()) {
            messageProcessing.processMessage();
        }
    }

}
