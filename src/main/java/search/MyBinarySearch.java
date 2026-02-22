package search;

import collections.MyCustomCollection;

public class MyBinarySearch<T> {
    private final MyCustomCollection<? extends Comparable<? super T>> list;

    public MyBinarySearch(MyCustomCollection<? extends Comparable<? super T>> list) {
        this.list = list;
    }

    public int getIndexedBinarySearch(T target) {
        return 0;
    }
}
