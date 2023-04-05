package ylab.lesson5.eventsourcing.api;

import ylab.lesson5.eventsourcing.Person;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonApiImpl implements PersonApi {
    private final DbClient dbClient;
    private final RabbitClient rabbitClient;

    public PersonApiImpl(DbClient dbClient, RabbitClient rabbitClient) {
        this.dbClient = dbClient;
        this.rabbitClient = rabbitClient;
    }

    @Override
    public void deletePerson(Long personId) {
        String message = getStringDelete(personId);
        rabbitClient.postMessage(message);
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        String message = getStringSave(personId, firstName, lastName, middleName);
        rabbitClient.postMessage(message);
    }

    @Override
    public Person findPerson(Long personId) {
        return dbClient.findPerson(personId);
    }

    @Override
    public List<Person> findAll() {
        return dbClient.findAll();
    }

    private String getStringSave(Long personId, String firstName, String lastName, String middleName) {
        return "{\"operation\":\"savePerson\"," +
                "\"personId\":\"" + personId + "\"," +
                "\"firstName\":\"" + firstName + "\"," +
                "\"lastName\":\"" + lastName + "\"," +
                "\"middleName\":\"" + middleName + "\"}";
    }

    private String getStringDelete(Long personId) {
        return "{\"operation\":\"deletePerson\"," +
                "\"personId\":\"" + personId + "\"}";
    }
}
