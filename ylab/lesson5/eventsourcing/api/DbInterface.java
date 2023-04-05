package ylab.lesson5.eventsourcing.api;

import ylab.lesson5.eventsourcing.Person;

import java.util.List;

public interface DbInterface {
    Person findPerson(long personId);

    List<Person> findAll();
}
