package sorting;

import collections.MyCustomCollection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class EvenQuickSort<T> implements SortStrategy<T> {

    private final Function<T, Number> numberExtractor;

    public EvenQuickSort(Function<T, Number> numberExtractor) {
        this.numberExtractor = numberExtractor;
    }

    @Override
    public void sort(MyCustomCollection<T> list) {
        if (list == null || list.size() <= 1) return;

        for (int i = 0; i < list.size(); i++) {
            if (!(list.get(i) instanceof Comparable)) {
                throw new IllegalStateException(
                        "Элемент с индексом " + i + " не реализует Comparable"
                );
            }
        }

        @SuppressWarnings("unchecked")
        Comparator<T> comparator = (o1, o2) -> {
            Comparable<T> comp1 = (Comparable<T>) o1;
            return comp1.compareTo(o2);
        };

        sortEvenValues(list, comparator);
    }

    @Override
    public void sort(MyCustomCollection<T> list, Comparator<T> comparator) {
        if (list == null || list.size() <= 1) return;
        sortEvenValues(list, comparator);
    }

    private void sortEvenValues(MyCustomCollection<T> list, Comparator<T> comparator) {
        List<Integer> evenIndices = new ArrayList<>();
        List<T> evenValues = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            Number number = numberExtractor.apply(element);

            if (isEven(number)) {
                evenIndices.add(i);
                evenValues.add(element);
            }
        }

        if (evenValues.isEmpty()) {
            System.out.println("Нет элементов с четными числами");
            return;
        }

        quickSortList(evenValues, 0, evenValues.size() - 1, comparator);

        for (int i = 0; i < evenIndices.size(); i++) {
            list.set(evenIndices.get(i), evenValues.get(i));
        }

        System.out.println("Сортировка завершена");
    }

    private boolean isEven(Number number) {
        if (number == null) return false;

        if (number instanceof Integer) {
            return ((Integer) number) % 2 == 0;
        } else if (number instanceof Long) {
            return ((Long) number) % 2 == 0;
        } else if (number instanceof Short) {
            return ((Short) number) % 2 == 0;
        } else if (number instanceof Byte) {
            return ((Byte) number) % 2 == 0;
        } else if (number instanceof Double) {
            double d = (Double) number;
            return ((long) d) % 2 == 0;
        } else if (number instanceof Float) {
            float f = (Float) number;
            return ((long) f) % 2 == 0;
        }
        return false;
    }

    private void quickSortList(List<T> list, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivot = partitionList(list, low, high, comparator);
            quickSortList(list, low, pivot - 1, comparator);
            quickSortList(list, pivot + 1, high, comparator);
        }
    }

    private int partitionList(List<T> list, int low, int high, Comparator<T> comparator) {
        T pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                swapList(list, i, j);
            }
        }

        swapList(list, i + 1, high);
        return i + 1;
    }

    private void swapList(List<T> list, int i, int j){
        if (i == j) return;
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}