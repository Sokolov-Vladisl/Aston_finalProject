package search;

import collections.MyCustomCollection;

public class MyBinarySearch<T extends Comparable<T>> {
    private final MyCustomCollection<T> list;

    public MyBinarySearch(MyCustomCollection<T> list) {
        this.list = list;
    }

    public int getIndexedBinarySearch(T target) {
        if (list == null || list.size() == 0) {
            return -1;
        }

        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            T midVal = list.get(mid);

            int cmp = midVal.compareTo(target);

            if (cmp < 0) {
                left = mid + 1;
            } else if (cmp > 0) {
                right = mid - 1;
            } else {
                return mid;
            }
        }

        return -(left + 1);
    }

    public boolean isSorted() {
        if (list == null || list.size() <= 1) return true;

        for (int i = 0; i < list.size() - 1; i++) {
            T current = list.get(i);
            T next = list.get(i + 1);

            if (current.compareTo(next) > 0) {
                return false;
            }
        }
        return true;
    }
}
