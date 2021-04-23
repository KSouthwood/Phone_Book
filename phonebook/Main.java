package phonebook;

public class Main {
    private static Directory directory;

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("test")) {
            directory = new Directory("small_directory.txt", "small_find.txt");
        } else {
            directory = new Directory("directory.txt", "find.txt");
        }

        directory.readFiles();

        long linearSearchTime = stage1();
        System.out.println();

        stage2(linearSearchTime * 10);
        System.out.println();

        stage3();
        System.out.println();

        stage4();
    }

    private static String formatTime(long time) {
        return String.format("%3d min. %2d sec. %3d ms.",
                time / 60_000, (time % 60_000) / 1_000, (time % 60_000) % 1_000);
    }

    private static long stage1() {
        Searches searches = new Searches(Directory.phonebook);
        System.out.println("Start searching (linear search)...");
        long timeStart = System.currentTimeMillis();

        int found = 0;
        for (var find : Directory.finders) {
            if (searches.linearSearch(find) != -1) {
                found++;
            }
        }

        long time = System.currentTimeMillis() - timeStart;
        System.out.printf("Found %d / %d entries. Time taken: %s%n",
                found, directory.getFindFileLength(), formatTime(time));
        return time;
    }

    private static void stage2(long limit) {
        System.out.println("Start searching (bubble sort + jump search)...");
        Sorts sort = new Sorts(Directory.phonebook);
        var timeStart = System.currentTimeMillis();

        boolean result = sort.bubbleSort(limit);
        if (!result) {
            sort.quickSort(0, Directory.phonebook.size() - 1);
        }

        var sortTime = System.currentTimeMillis() - timeStart;
        Searches searches = new Searches(sort.returnSorted());
        timeStart = System.currentTimeMillis();

        int found = 0;
        for (var find : Directory.finders) {
            if (searches.jumpSearch(find) != -1) {
                found++;
            }
        }

        var searchTime = System.currentTimeMillis() - timeStart;

        System.out.printf("Found %d / %d entries. Time taken: %s%n",
                found, directory.getFindFileLength(), formatTime(sortTime + searchTime));
        System.out.printf("Sorting time: %s%s%n", formatTime(sortTime),
                result ? "" : " - Bubble sort took too long, used quick sort to finish.");
        System.out.printf("Searching time: %s%n", formatTime(searchTime));
    }

    private static void stage3() {
        System.out.println("Start searching (quick sort + binary search)...");
        Sorts sort = new Sorts(Directory.phonebook);

        // run quick sort
        var timeStart = System.currentTimeMillis();
        sort.quickSort(0, Directory.phonebook.size() - 1);
        var sortTime = System.currentTimeMillis() - timeStart;

        // run binary search
        Searches searches = new Searches(sort.returnSorted());
        timeStart = System.currentTimeMillis();
        int found = 0;
        for (var find : Directory.finders) {
            if (searches.binarySearch(find) != -1) {
                found++;
            }
        }
        var searchTime = System.currentTimeMillis() - timeStart;

        System.out.printf("Found %d / %d entries. Time taken: %s%n",
                found, directory.getFindFileLength(), formatTime(sortTime + searchTime));
        System.out.printf("Sorting time: %s%n", formatTime(sortTime));
        System.out.printf("Searching time: %s%n", formatTime(searchTime));
    }

    private static void stage4() {
        System.out.println("Start searching (hash table)...");
        HashTable<Record> hashTable = new HashTable<>(Directory.phonebook.size() * 2);

        var timeStart = System.currentTimeMillis();
        for (var record : Directory.phonebook) {
            hashTable.put(record.getName().hashCode() & Integer.MAX_VALUE, record);
        }
        var createTime = System.currentTimeMillis() - timeStart;

        timeStart = System.currentTimeMillis();
        int found = 0;
        for (var find : Directory.finders) {
            if (null != hashTable.get(find.hashCode() & Integer.MAX_VALUE)) {
                found++;
            }
        }
        var searchTime = System.currentTimeMillis() - timeStart;

        System.out.printf("Found %d / %d entries. Time taken: %s%n",
                found, directory.getFindFileLength(), formatTime(createTime + searchTime));
        System.out.printf("Creating time: %s%n", formatTime(createTime));
        System.out.printf("Searching time: %s%n", formatTime(searchTime));
    }
}