package phonebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final Path WORKING_DIR = Path.of("/home/keri/IdeaProjects/Phone Book");
    private static String DIR_FILE = "directory.txt";
    private static String FIND_FILE = "find.txt";
    private static String[] directory = null;
    private static String[] finders = null;

    public static void main(String[] args) {
        boolean test = false;
        if (args.length == 1 && args[0].equals("test")) {
            test = true;
            DIR_FILE = "small_directory.txt";
            FIND_FILE = "small_find.txt";
        }

        if (test) {
            System.out.println("Working directory: " + WORKING_DIR.toString());
            System.out.println("Directory file   : " + DIR_FILE);
            System.out.println("Find file        : " + FIND_FILE);
        }

        readDirectory();

        if (test) {
            System.out.println("Directory size   : " + directory.length);
            System.out.println("Finders size     : " + finders.length);
        }

        linearSearch();
    }

    private static void readDirectory() {
        Path dirFile = Path.of(WORKING_DIR.toString(), "Phone Book", DIR_FILE);
        Path findFile = Path.of(WORKING_DIR.toString(), "Phone Book", FIND_FILE);

        System.out.println("dirFile : " + dirFile.toString());
        System.out.println("findFile: " + findFile.toString());

        try {
            BufferedReader reader = Files.newBufferedReader(dirFile);
            directory = reader.lines().toArray(String[]::new);
            reader = Files.newBufferedReader(findFile);
            finders = reader.lines().toArray(String[]::new);
        } catch (IOException e) {
            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }

    private static void linearSearch() {
        System.out.println("Start searching...");
        int found = 0;
        var timeStart = System.currentTimeMillis();
        for (var findMe : finders) {
            for (var entry : directory) {
                if (entry.contains(findMe)) {
                    found++;
                    break;
                }
            }
        }
        var timeEnd = System.currentTimeMillis();
        var elapsed = timeEnd - timeStart;
        var minutes = elapsed / 60_000;
        var seconds = (elapsed % 60_000) / 1_000;
        var millis = (elapsed % 60_000) % 1_000;

        System.out.printf("Found %d / %d entries. Time taken: %3d min. %2d sec. %3d ms.%n",
                found, finders.length, minutes, seconds, millis);
    }
}