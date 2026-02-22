package inputStrategy;

import collections.MyCustomCollection;
import model.MyCustomModel;

public class MyCustomModelFiller  {
    private DataFillStrategy<MyCustomModel> strategy;

    public void setStrategy(DataFillStrategy<MyCustomModel> strategy) {
        this.strategy = strategy;
    }

    public MyCustomCollection<MyCustomModel> fill(int size) {
        if(strategy == null) throw new IllegalStateException("Стратегия заполнения не выбрана!");
        return strategy.fill(size);
    }
}
