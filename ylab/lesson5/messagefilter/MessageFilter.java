package ylab.lesson5.messagefilter;

import org.springframework.stereotype.Component;

@Component
public class MessageFilter {
    private final OutputRabbitClientImpl outputRabbitClient;
    private final DbClient dbClient;

    public MessageFilter(OutputRabbitClientImpl outputRabbitClient, DbClient dbClient) {
        this.outputRabbitClient = outputRabbitClient;
        this.dbClient = dbClient;
    }

    public void filtering(String message) {
        String limitingCharacter = "'\r''\n' .,;!?";
        StringBuilder resultMessage = new StringBuilder();
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            if (limitingCharacter.indexOf(message.charAt(i)) == -1) {
                // не разделяющий символ
                strBuilder.append(message.charAt(i));
            } else {
                // разделяющий символ
                resultMessage.append(getComparison(strBuilder).append(message.charAt(i)));
                strBuilder.setLength(0);
            }
        }
        resultMessage.append(getComparison(strBuilder));
        outputRabbitClient.outputMessage(resultMessage.toString());
    }

    private StringBuilder getComparison(StringBuilder strBuilder) {
        StringBuilder result = new StringBuilder();
        int length = strBuilder.length();
        if (dbClient.findByWord(strBuilder)) {
            result.append(strBuilder.substring(0, 1));
            result.append("*".repeat(Math.max(0, length - 2)));
            result.append(strBuilder.substring(length - 1));
        } else {
            result.append(strBuilder);
        }
        return result;
    }
}
