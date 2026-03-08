package sorting;

import collections.MyCustomCollection;
import model.MyCustomModel;

import java.util.Comparator;

public class SortService {
    private SortStrategy<MyCustomModel> strategy;

    public void setStrategy(SortStrategy<MyCustomModel> strategy) {
        this.strategy = strategy;
    }

    public void sort(MyCustomCollection<MyCustomModel> list) {
        if(strategy == null) throw new IllegalStateException("Стратегия сортировки не выбрана!");
        strategy.sort(list);
    }

    public void sort(MyCustomCollection<MyCustomModel> list, Comparator<MyCustomModel> comparator) {
        if(strategy == null) throw new IllegalStateException("Стратегия сортировки не выбрана!");
        strategy.sort(list, comparator);
    }
}
