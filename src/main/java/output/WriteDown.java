package output;

import collections.MyCustomCollection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteDown {

    //функционал для записи отсортированных коллекций
    public static <T> void MyOutput(MyCustomCollection<T> collection, String filename /*название файла*/) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            for (T item : collection) {
                writer.write(item.toString()+"\n");
            }
            writer.write(" = = = = =\n");
        } catch (IOException e) {
            System.err.println("Ошибка записи: " + e.getMessage());
        }
    }

}