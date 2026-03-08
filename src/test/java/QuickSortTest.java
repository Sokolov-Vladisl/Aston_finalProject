package sorting;

import collections.MyCustomCollection;
import inputStrategy.MyCustomModelFiller;
import inputStrategy.randomFill;
import model.MyCustomModel;
import model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Comparator;

public class QuickSortTest {
    private MyCustomCollection<MyCustomModel> collection;
    private SortService sortService;
    private MyCustomModelFiller filler;

    @BeforeEach
    public void setUp() {
        filler = new MyCustomModelFiller();
        filler.setStrategy(new randomFill());
        sortService = new SortService();
    }

    @Test
    public void testQuickSortName(){
        list = filler.fill(50);
        sortService.setStrategy(new QuickSort<>());
        Comparator<MyCustomModel> comparator = Comparator.comparing(MyCustomModel::getNumber);
        MyCustomCollection<MyCustomModel> sorted = sortService.sort(collection, comparator);
        Assertions.assertTrue(isSortedByNumber(sorted));
    }

}
