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
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EvenQuickSortTest {
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
    public void testEventQuickSort() {
        MyCustomCollection<MyCustomModel> originalCollection = new MyCustomCollection<>(collection.size());

        for (int i = 0; i < collection.size(); i++) {
            originalCollection.add(collection.get(i));
        }

        sortService.setStrategy(new EvenQuickSort<>(MyCustomModel::getNumber));
        Comparator<MyCustomModel> comparator = Comparator.comparing(MyCustomModel::getNumber);

        sortService.sort(collection, comparator);

        for (int i = 0; i < collection.size(); i++) {
            MyCustomModel original = originalCollection.get(i);
            MyCustomModel current = collection.get(i);

            if (original.getNumber() % 2 != 0) {
                assertEquals(original.getName(), current.getName(),
                        "Нечетный элемент на индексе " + i + " изменил позицию");
                assertEquals(original.getNumber(), current.getNumber(),
                        "Нечетный элемент на индексе " + i + " изменил число");
            }
        }

        List<MyCustomModel> evenElements = new ArrayList<>();
        List<Integer> evenIndices = new ArrayList<>();

        for (int i = 0; i < collection.size(); i++) {
            MyCustomModel model = collection.get(i);
            if (model.getNumber() % 2 == 0) {
                evenElements.add(model);
                evenIndices.add(i);
            }
        }

        // Проверяем, что четные элементы отсортированы
        for (int i = 0; i < evenElements.size() - 1; i++) {
            MyCustomModel current = evenElements.get(i);
            MyCustomModel next = evenElements.get(i + 1);

            assertTrue(current.getNumber() <= next.getNumber(),
                    "Четные элементы не отсортированы: " + current.getNumber() + " > " + next.getNumber());
        }

    }
}
