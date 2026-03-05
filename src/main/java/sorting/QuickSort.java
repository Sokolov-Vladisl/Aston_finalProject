package sorting;

import collections.MyCustomCollection;

import java.util.Comparator;

public class QuickSort<T> implements SortStrategy<T>{

    @Override
    public void sort(MyCustomCollection<T> list) {
        if (list == null || list.size() <= 1) return;

        try {
            Comparator<T> comparator = (o1, o2) -> {
                Comparable<T> comp1 = (Comparable<T>) o1;
                return comp1.compareTo(o2);
            };
            quickSort(list, 0, list.size() - 1, comparator);
        } catch (Exception e){
            throw new IllegalStateException("Элементы не реализуют Comparable", e);
        }
               }

    @Override
    public void sort(MyCustomCollection<T> list, Comparator<T> comparator) {
        if (list == null || list.size() <= 1) return;
        quickSort(list, 0, list.size() - 1, comparator);
    }
    private void quickSort(MyCustomCollection<T> list, int low, int high, Comparator<T> comparator) {

    }



}
