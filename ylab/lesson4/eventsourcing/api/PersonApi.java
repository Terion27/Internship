package ylab.lesson4.eventsourcing.api;

import ylab.lesson4.eventsourcing.Person;

import java.util.List;

public interface PersonApi {
    void deletePerson(Long personId);

    void savePerson(Long personId, String firstName, String lastName, String middleName);

    Person findPerson(Long personId);

    List<Person> findAll();
}
