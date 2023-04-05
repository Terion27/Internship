package ylab.lesson5.eventsourcing.db;

import java.util.Map;

public interface DbInterface {
    void savePerson(Map<String, String> map);

    void deletePerson(Map<String, String> map);
}
