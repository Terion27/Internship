package ylab.lesson3.orgstructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrgStructureImpl implements OrgStructureParser {
    private Long bossId = null;
    private final Map<Long, Employee> structure = new HashMap<>();  // Коллекция для хранения сотрудников.

    @Override
    public Employee parseStructure(File csvFile) {
        try {
            getOrganizationEmployees(csvFile);
            writeStructureOrganization();
            return structure.get(bossId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // формирование структуры сотрудников на основании csv файла.
    private void getOrganizationEmployees(File csvFile) throws FileException {
        try (FileInputStream fileStream = new FileInputStream(csvFile)) {
            Scanner scan = new Scanner(fileStream);
            scan.nextLine();
            while (scan.hasNextLine()) {
                String[] str = scan.nextLine().split(";");
                Employee employee = new Employee();
                employee.setId(Long.valueOf(str[0]));
                if (str[1].equals("")) {
                    employee.setBossId(null);
                    bossId = Long.valueOf(str[0]);
                } else {
                    employee.setBossId(Long.valueOf(str[1]));
                }
                employee.setName(str[2]);
                employee.setPosition(str[3]);
                structure.put(employee.getId(), employee);
            }
        } catch (IOException e) {
            throw new FileException(e);
        }
    }

    // проход по структуре сотрудников и заполнение полей boss и subordinate
    private void writeStructureOrganization() {
        for (Map.Entry<Long, Employee> employee : structure.entrySet()) {
            Long myBossId = employee.getValue().getBossId();
            Employee myBoss = structure.get(myBossId);
            if (myBossId != null) {
                employee.getValue().setBoss(myBoss);  // заполнение поля boss текущего работника.
                structure.get(myBossId).getSubordinate().add(employee.getValue());  // добавление работника в поле subordinate вышестоящему.
            }
        }
    }

}
