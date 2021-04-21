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

    private static List<Record> phonebook = null;
    private static String[] finders;

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

//        System.out.println("dirFile : " + dirFile.toString());
//        System.out.println("findFile: " + findFile.toString());

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
            swap(endIndex, max);
            if (System.currentTimeMillis() - timeStart > timeLimit) {
//                System.out.println();
                return false;
            }
        }
//        System.out.println();
        return true;
    }

    private void swap(int from, int to) {
        var temp = phonebook.get(from);
        phonebook.set(from, phonebook.get(to));
        phonebook.set(to, temp);
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

    protected void doQuickSort() {
        quickSort(0, phonebook.size() - 1);
    }

    private void quickSort(int left, int right) {
        if (left < right) {
            int pivotIndex = partition(left, right);
            quickSort(left, pivotIndex - 1);
            quickSort(pivotIndex + 1, right);
        }
    }

    private int partition(int left, int right) {
        Record pivot = phonebook.get(right);
        int partitionIndex = left;

        // move larger values into the right side of the array
        for (int index = left; index < right; index++) {
            if (phonebook.get(index).getName().compareTo(pivot.getName()) <= 0) {
                swap(index, partitionIndex);
                partitionIndex++;
            }
        }

        swap(partitionIndex, right);
        return partitionIndex;
    }

    protected int doBinarySearch() {
        int found = 0;
        for (var find : finders) {
            if (binarySearch(find) != -1) {
                found++;
            }
        }
        return found;
    }

    private int binarySearch(String find) {
        int left = 0;
        int right = phonebook.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int result = phonebook.get(mid).getName().compareTo(find);
            if (result == 0) {
                return mid;
            } else if (result > 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }
}
