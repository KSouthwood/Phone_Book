package phonebook;

import java.util.List;

public class Searches {
    private final List<Record> searchMe;

    Searches(List<Record> search) {
        this.searchMe = search;
    }

    protected int linearSearch(String find) {
        for (int index = 0; index < searchMe.size(); index++) {
            if (find.equals(searchMe.get(index).getName())) {
                return index;
            }
        }

        return -1;
    }

    protected int jumpSearch(String find) {
        int currRight = 0;
        int prevRight = 0;

        // Can't find anything if we don't have anything
        if (searchMe.size() == 0) {
            return -1;
        }

        // Check the first element
        if (searchMe.get(currRight).getName().equals(find)) {
            return 0;
        }

        int blockSize = (int) Math.sqrt(searchMe.size());

        while (currRight < searchMe.size() - 1) {
            currRight = Math.min(searchMe.size(), currRight + blockSize);
            if (searchMe.get(currRight).getName().compareTo(find) >= 0) {
                break;
            }
            prevRight = currRight;
        }

        if (currRight == searchMe.size() - 1 && find.compareTo(searchMe.get(currRight).getName()) > 0) {
            return -1;
        }

        for (int index = currRight; index > prevRight; index--) {
            int result = searchMe.get(index).getName().compareTo(find);
            if (result == 0) {
                return index;
            }
            if (result < 0) {
                break; // stop searching at first element less than find term
            }
        }

        return -1;
    }

    protected int binarySearch(String find) {
        int left = 0;
        int right = searchMe.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int result = searchMe.get(mid).getName().compareTo(find);
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
