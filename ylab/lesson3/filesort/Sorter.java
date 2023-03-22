package ylab.lesson3.filesort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;

public class Sorter {
    private static final int MEMORY_BLOCKS = 1_000; //  initial value of the blocks for sorting in memory, size depending on memory.
    private boolean sortingCompleted = true;
    private final File resultFile = new File("Result.txt");
    private final File tapeOne = new File("tape_one");
    private final File tapeTwo = new File("tape_two");

    public File sortFile(File dataFile) {
        if (breakdownSortSectors(dataFile)) {
            return resultFile;  // External sorting by merging is not required.
        }
        while (!sortingCompleted) {
            System.out.println("Старт разделения " + new Date());
            splittingIntoFiles();
            System.out.println("Старт слияния " + new Date());
            sortingCompleted = merge();
            System.out.println("Завершение прохода. " + new Date());
            System.out.println();
        }
        try {
            Files.deleteIfExists(tapeOne.toPath());
            Files.deleteIfExists(tapeTwo.toPath());
        } catch (IOException e) {
            System.out.println("Failed to delete the files.");
        }

        return resultFile;
    }

    /**
     * Initial breakdown and blocks sorting.
     *
     * @param dataFile - Original file.
     * @return - if resulted:
     * true - the file is too small, external sorting by merging is not required. Decrease the MEMORY_BLOCKS parameter.
     * false - file is too big, external merge sorting is required.
     */
    private boolean breakdownSortSectors(File dataFile) {
        try (PrintWriter fResult = new PrintWriter(resultFile); Scanner scan = new Scanner(dataFile)) {
            System.out.println("Начальное разбиение " + new Date());
            while (scan.hasNextLong()) {
                List<Long> list = new ArrayList<>();
                for (int i = 0; i < MEMORY_BLOCKS; i++) {
                    list.add(scan.nextLong());
                    if (!scan.hasNextLong()) {
                        break;
                    }
                }
                Collections.sort(list);
                list.forEach(fResult::println);
                if (scan.hasNextLong()) {
                    fResult.println("#");
                    sortingCompleted = false;
                }
            }
            fResult.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sortingCompleted;
    }

    // The blocks are alternately moved to the files
    private void splittingIntoFiles() {
        boolean fistOfBlocks = true;
        String tempStr;
        try (Scanner scan = new Scanner(resultFile); PrintWriter fTapeOne = new PrintWriter(tapeOne);
             PrintWriter fTapeTwo = new PrintWriter(tapeTwo)) {
            PrintWriter currTape = fTapeOne;
            while (scan.hasNext()) {
                tempStr = scan.nextLine();
                if (tempStr.equals("#")) {
                    currTape = (currTape == fTapeOne) ? fTapeTwo : fTapeOne;
                    if (fistOfBlocks) {
                        fistOfBlocks = false;
                    } else {
                        currTape.println("#");
                    }
                } else {
                    currTape.println(tempStr);
                }
            }
            fTapeOne.flush();
            fTapeTwo.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //  Merging with tape sorting.
    private boolean merge() {
        sortingCompleted = true;
        try (Scanner scanOne = new Scanner(tapeOne); Scanner scanTwo = new Scanner(tapeTwo);
             PrintWriter fResult = new PrintWriter(resultFile)) {
            String elOne = getEl(scanOne);
            String elTwo = getEl(scanTwo);
            while (!elOne.isEmpty() || !elTwo.isEmpty()) {
                if ((!elOne.isEmpty() && !elOne.equals("#")) && (!elTwo.isEmpty() && !elTwo.equals("#"))) {
                    if (Long.parseLong(elOne) < Long.parseLong(elTwo)) {
                        fResult.println(elOne);
                        elOne = getEl(scanOne);
                    } else {
                        fResult.println(elTwo);
                        elTwo = getEl(scanTwo);
                    }
                } else if (!elOne.isEmpty() && !elOne.equals("#")) {
                    fResult.println(elOne);
                    elOne = getEl(scanOne);
                } else if (!elTwo.isEmpty() && !elTwo.equals("#")) {
                    fResult.println(elTwo);
                    elTwo = getEl(scanTwo);
                } else {
                    fResult.println("#");
                    elOne = getEl(scanOne);
                    elTwo = getEl(scanTwo);
                    sortingCompleted = false;
                }
            }
            fResult.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return sortingCompleted;
    }

    private String getEl(Scanner scanTape) {
        if (scanTape.hasNext()) {
            return scanTape.nextLine();
        }
        return "";
    }

}