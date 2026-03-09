package sorting;

import collections.MyCustomCollection;
import inputStrategy.MyCustomModelFiller;
import inputStrategy.randomFill;
import model.MyCustomModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuickSortTest {
    private MyCustomCollection<MyCustomModel> collection;
    private SortService sortService;

    @BeforeEach
    public void setUp() {
        MyCustomModelFiller filler = new MyCustomModelFiller();
        filler.setStrategy(new randomFill());
        sortService = new SortService();
        collection = filler.fill(50);

    }

    @Test
    public void testQuickSortName(){
        sortService.setStrategy(new QuickSort<>());
        Comparator<MyCustomModel> comparator = Comparator.comparing(MyCustomModel::getName);
        List<MyCustomModel> expectedList = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            expectedList.add(collection.get(i));
        }
        expectedList.sort(comparator);

        sortService.sort(collection, comparator);

        for (int i = 0; i < collection.size(); i++) {
            assertEquals(
                    expectedList.get(i).getName(),
                    collection.get(i).getName(),
                    "Несовпадение на индексе " + i
            );
        }
    }

    @Test
    public void testQuickSortNumber(){
        sortService.setStrategy(new QuickSort<>());
        Comparator<MyCustomModel> comparator = Comparator.comparing(MyCustomModel::getNumber);
        List<MyCustomModel> expectedList = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            expectedList.add(collection.get(i));
        }
        expectedList.sort(comparator);

        sortService.sort(collection, comparator);

        for (int i = 0; i < collection.size(); i++) {
            assertEquals(
                    expectedList.get(i).getNumber(),
                    collection.get(i).getNumber(),
                    "Несовпадение на индексе " + i
            );
        }
    }

    @Test
    public void testQuickSortStatus(){
        sortService.setStrategy(new QuickSort<>());
        Comparator<MyCustomModel> comparator = Comparator.comparing(MyCustomModel::getStatus);
        List<MyCustomModel> expectedList = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            expectedList.add(collection.get(i));
        }
        expectedList.sort(comparator);

        sortService.sort(collection, comparator);

        for (int i = 0; i < collection.size(); i++) {
            assertEquals(
                    expectedList.get(i).getStatus(),
                    collection.get(i).getStatus(),
                    "Несовпадение на индексе " + i
            );
        }
    }

}
