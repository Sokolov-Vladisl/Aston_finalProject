package sorting;

import collections.MyCustomCollection;

import java.util.Comparator;

public class QuickSort<T> implements SortStrategy<T>{

    @Override
    public void sort(MyCustomCollection<T> list) {
        if (list == null || list.size() <= 1) return;

        for (int i = 1; i < list.size(); i++) {
            if (!(list.get(i) instanceof Comparable)) {
                throw new IllegalStateException(
                        "Элемент с индексом " + i + " не реализует Comparable"
                );
            }
        }

        @SuppressWarnings("unchecked")
        Comparator<T> comparator = (o1, o2) -> {
            Comparable<T> comp1;
            comp1 = (Comparable<T>) o1;
            return comp1.compareTo(o2);
        };
        quickSort(list, 0, list.size() - 1, comparator);
    }

    @Override
    public void sort(MyCustomCollection<T> list, Comparator<T> comparator) {
        if (list == null || list.size() <= 1) return;
        quickSort(list, 0, list.size() - 1, comparator);
    }

    private void quickSort(MyCustomCollection<T> list, int low, int high, Comparator<T> comparator) {

        if (low < high ){
           int pivot = partition(list, low, high, comparator);
           quickSort(list, low, pivot-1, comparator);
           quickSort(list, pivot+1, high, comparator);

        }
    }

    private int partition(MyCustomCollection<T> list, int low, int high, Comparator<T> comparator) {
        T pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++){
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i+1, high);
        return i+1;
    }

    private void swap(MyCustomCollection<T> list, int i, int j) {
        if (i == j) return;
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
