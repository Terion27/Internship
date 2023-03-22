package ylab.lesson3.filesort;

import java.io.File;
import java.io.IOException;

public class FileSortTest {
    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate("data.txt", 100_001);
        System.out.println(new Validator().isSorted(dataFile)); // false
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println(new Validator().isSorted(sortedFile)); // true
        System.out.println(new Validator().isEqualsCount(dataFile, sortedFile)); // true
    }
}
