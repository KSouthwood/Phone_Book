package phonebook;

public class Main {
    private static Directory directory;
    private static boolean test = false;

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("test")) {
            test = true;
            directory = new Directory("small_directory.txt", "small_find.txt");
        } else {
            directory = new Directory("directory.txt", "find.txt");
        }

        directory.readFiles();

        long linearSearchTime = stage1();
        System.out.println();

        stage2(linearSearchTime * 10);
        System.out.println();

        directory.readFiles();
        stage3();
    }

    private static String formatTime(long time) {
        return String.format("%3d min. %2d sec. %3d ms.",
                time / 60_000, (time % 60_000) / 1_000, (time % 60_000) % 1_000);
    }

    private static long stage1() {
        System.out.println("Start searching (linear search)...");
        long timeStart = System.currentTimeMillis();
        int found = directory.linearSearch();
        long timeEnd = System.currentTimeMillis();

        System.out.printf("Found %d / %d entries. Time taken: %s%n",
                found, directory.getFindFileLength(), formatTime(timeEnd - timeStart));
        return timeEnd - timeStart;
    }

    private static void stage2(long limit) {
        System.out.println("Start searching (bubble sort + jump search)...");
        var timeStart = System.currentTimeMillis();
        boolean result = directory.bubbleSort(limit);
        var timeEnd = System.currentTimeMillis();
        var sortTime = timeEnd - timeStart;

        timeStart = System.currentTimeMillis();
        var found = result ? directory.doJumpSearch() : directory.linearSearch();
        timeEnd = System.currentTimeMillis();
        var searchTime = timeEnd - timeStart;

        System.out.printf("Found %d / %d entries. Time taken: %s%n",
                found, directory.getFindFileLength(), formatTime(sortTime + searchTime));
        System.out.printf("Sorting time: %s%s%n", formatTime(sortTime),
                result ? "" : " - STOPPED, moved to linear search");
        System.out.printf("Searching time: %s%n", formatTime(searchTime));
    }

    private static void stage3() {
        System.out.println("Start searching (quick sort + binary search)...");

        // run quick sort
        var timeStart = System.currentTimeMillis();
        directory.doQuickSort();
        var timeEnd = System.currentTimeMillis();
        var sortTime = timeEnd - timeStart;

        // run binary search
        timeStart = System.currentTimeMillis();
        var found = directory.doBinarySearch();
        timeEnd = System.currentTimeMillis();
        var searchTime = timeEnd - timeStart;

        System.out.printf("Found %d / %d entries. Time taken: %s%n",
                found, directory.getFindFileLength(), formatTime(sortTime + searchTime));
        System.out.printf("Sorting time: %s%n", formatTime(sortTime));
        System.out.printf("Searching time: %s%n", formatTime(searchTime));
    }
}