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

    static List<Record> phonebook;
    static String[] finders;

    Directory(String directory, String findFile) {
        DIR_FILE = directory;
        FIND_FILE = findFile;
    }

    protected int getFindFileLength() {
        return finders.length;
    }

    protected void readFiles() {
        if (phonebook != null) {
            phonebook.clear();
        }
        finders = null;
        Path dirFile = Path.of(WORKING_DIR.toString(), "Phone Book", DIR_FILE);
        Path findFile = Path.of(WORKING_DIR.toString(), "Phone Book", FIND_FILE);

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
}
