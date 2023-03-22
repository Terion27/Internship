package ylab.lesson3.orgstructure;

import java.io.File;

public class OrgStructureTest {
    public static void main(String[] args) {
        File fileOrg = new File("src/lesson3/orgstructure/organization.csv");
        Employee boss = new OrgStructureImpl().parseStructure(fileOrg);
        System.out.println(boss.getName() + " - " + boss.getPosition());
        System.out.println(boss.getSubordinate().stream().toList());
    }
}
