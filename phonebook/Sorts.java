package phonebook;

import java.util.ArrayList;
import java.util.List;

public class Sorts {
    private final List<Record> sortMe;

    Sorts(List<Record> sort) {
        this.sortMe = new ArrayList<>(sort);
    }

    List<Record> returnSorted() {
        return sortMe;
    }

    private void swap(int from, int to) {
        var temp = sortMe.get(from);
        sortMe.set(from, sortMe.get(to));
        sortMe.set(to, temp);
    }

    protected boolean bubbleSort(long timeLimit) {
        long timeStart = System.currentTimeMillis();
        for (int endIndex = sortMe.size() - 1; endIndex > 0; endIndex--) {
            int max = 0;
            for (int index = 1; index < endIndex; index++) {
                if (sortMe.get(index).getName()
                        .compareTo(sortMe.get(max).getName()) > 0) {
                    max = index;
                }
            }
            swap(endIndex, max);
            if (System.currentTimeMillis() - timeStart > timeLimit) {
                return false;
            }
        }
        return true;
    }

    protected void quickSort(int left, int right) {
        if (left < right) {
            int pivotIndex = partition(left, right);
            quickSort(left, pivotIndex - 1);
            quickSort(pivotIndex + 1, right);
        }
    }

    private int partition(int left, int right) {
        Record pivot = sortMe.get(right);
        int partitionIndex = left;

        // move larger values into the right side of the array
        for (int index = left; index < right; index++) {
            if (sortMe.get(index).getName().compareTo(pivot.getName()) <= 0) {
                swap(index, partitionIndex);
                partitionIndex++;
            }
        }

        swap(partitionIndex, right);
        return partitionIndex;
    }
}
