import collections.MyCustomCollection;
import inputStrategy.MyCustomModelFiller;
import inputStrategy.fileFill;
import model.MyCustomModel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileFillStrategyTest {
    static MyCustomCollection<MyCustomModel> list;
    static MyCustomModelFiller bf;

    @BeforeAll
    public static void init() {
        bf = new MyCustomModelFiller();
        bf.setStrategy(new fileFill("tasks.csv"));
        list = bf.fill(3);
    }

    @Test
    public void sizeTest() {
        int expected = 3;
        int result = list.size();
        Assertions.assertEquals(expected, result);
    }


}
