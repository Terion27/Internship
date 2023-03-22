package ylab.lesson3.filesort;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Validator {

    public Validator() {
        // to disable the public default constructor
    }

    public boolean isSorted(File file) {
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            long prev = Long.MIN_VALUE;
            while (scanner.hasNextLong()) {
                long current = scanner.nextLong();
                if (current < prev) {
                    return false;
                } else {
                    prev = current;
                }
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean isEqualsCount(File file, File fileTwo) {
        try (Scanner scanner = new Scanner(new FileInputStream(file)); Scanner scanTwo = new Scanner(new FileInputStream(fileTwo))) {
            Scanner scan = scanner;
            long countOne = 0L;
            long countTwo = 0L;
            for (int i = 0; i < 2; i++) {
                long curCount = 0L;
                while (scan.hasNext()) {
                    scan.nextLine();
                    curCount++;
                }
                scan = scanTwo;
                if (i == 0) {
                    countOne = curCount;
                } else {
                    countTwo = curCount;
                }
            }
            return countOne == countTwo;

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
