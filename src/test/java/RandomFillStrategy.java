import collections.MyCustomCollection;
import inputStrategy.MyCustomModelFiller;
import inputStrategy.randomFill;
import model.MyCustomModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RandomFillStrategy {
    static MyCustomCollection<MyCustomModel> list;
    static MyCustomModelFiller bf;

    @BeforeAll
    public static void init() {
        bf = new MyCustomModelFiller();
        bf.setStrategy(new randomFill());
        list = bf.fill(355);
    }

    @Test
    public void sizeTest() {
        int expected = 355;
        int result = list.size();
        Assertions.assertEquals(expected, result);
    }


}