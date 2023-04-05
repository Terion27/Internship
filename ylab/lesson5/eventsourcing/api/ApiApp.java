package ylab.lesson5.eventsourcing.api;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {
    public static void main(String[] args) throws Exception {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        // пишем взаимодействие с PersonApi
        System.out.println(personApi.findAll());
        personApi.savePerson(1L, "firstName1", "lastName1", "middleName1");
        personApi.savePerson(2L, "firstName2", "lastName2", "middleName2");
        personApi.savePerson(3L, "firstName3", "lastName3", "middleName3");
        System.out.println(personApi.findAll());
        personApi.deletePerson(1L);
        personApi.deletePerson(1L);
        personApi.savePerson(2L, "new_firstName2", "new_lastName2", "new_middleName2");
        System.out.println(personApi.findPerson(3L));
        System.out.println(personApi.findPerson(4L));
        System.out.println(personApi.findAll());
    }
}
