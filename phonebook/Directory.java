package phonebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Directory {
    private static final Path WORKING_DIR = Path.of("/home/keri/IdeaProjects/Phone Book");
    private static String DIR_FILE = null;
    private static String FIND_FILE = null;

    private static List<Record> phonebook;
    private static String[] finders;

    Directory(String directory, String findFile) {
        DIR_FILE = directory;
        FIND_FILE = findFile;
    }

    protected int getFindFileLength() {
        return finders.length;
    }

    protected void readFiles() {
        Path dirFile = Path.of(WORKING_DIR.toString(), "Phone Book", DIR_FILE);
        Path findFile = Path.of(WORKING_DIR.toString(), "Phone Book", FIND_FILE);

        System.out.println("dirFile : " + dirFile.toString());
        System.out.println("findFile: " + findFile.toString());

        try {
            BufferedReader reader = Files.newBufferedReader(dirFile);
            phonebook = reader.lines()
                    .map(Record::new)
                    .collect(Collectors.toList());
            reader = Files.newBufferedReader(findFile);
            finders = reader.lines().toArray(String[]::new);
        } catch (IOException e) {
            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }

    protected int linearSearch() {
        int found = 0;
        for (var findMe : finders) {
            for (var entry : phonebook) {
                if (entry.getName().equals(findMe)) {
                    found++;
                    break;
                }
            }
        }
        return found;
    }

    protected boolean bubbleSort(long timeLimit) {
//        int count = 0;
        long timeStart = System.currentTimeMillis();
        for (int endIndex = phonebook.size() - 1; endIndex > 0; endIndex--) {
            int max = 0;
//            if (++count % 10 == 0) { System.out.print("."); }
//            if (count % 500 == 0) { System.out.println(); }
            for (int index = 1; index < endIndex; index++) {
                if (phonebook.get(index).getName()
                        .compareTo(phonebook.get(max).getName()) > 0) {
                    max = index;
                }
            }
            var temp = phonebook.get(endIndex);
            phonebook.set(endIndex, phonebook.get(max));
            phonebook.set(max, temp);
            if (System.currentTimeMillis() - timeStart > timeLimit) {
//                System.out.println();
                return false;
            }
        }
//        System.out.println();
        return true;
    }

    protected int doJumpSearch() {
        int found = 0;
        for (var findMe : finders) {
            if (jumpSearch(findMe) != -1) {
                found++;
            }
        }
        return found;
    }

    private int jumpSearch(String find) {
        int currRight = 0;
        int prevRight = 0;

        // Can't find anything if we don't have anything
        if (phonebook.size() == 0) {
            return -1;
        }

        // Check the first element
        if (phonebook.get(currRight).getName().equals(find)) {
            return 0;
        }

        int blockSize = (int) Math.sqrt(phonebook.size());

        while (currRight < phonebook.size() - 1) {
            currRight = Math.min(phonebook.size(), currRight + blockSize);
            if (phonebook.get(currRight).getName().compareTo(find) >= 0) {
                break;
            }
            prevRight = currRight;
        }

        if (currRight == phonebook.size() - 1 && find.compareTo(phonebook.get(currRight).getName()) > 0) {
            return -1;
        }

        for (int index = currRight; index > prevRight; index--) {
            int result = phonebook.get(index).getName().compareTo(find);
            if (result == 0) {
                return index;
            }
            if (result < 0) {
                break; // stop searching at first element less than find term
            }
        }

        return -1;
    }
}
