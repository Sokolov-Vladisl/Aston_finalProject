package validation;

import model.MyCustomModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Validation {
    private final Scanner scanner;
    private static final String DEFAULT_FILENAME = "tasks.csv";
    private static final String SORTED_PRIORITY_FILENAME = "output/sorted_by_even.csv";

    public Validation(Scanner scanner) {
        this.scanner = scanner;
    }

    public Validation() {
        this.scanner = new Scanner(System.in);
    }

    public List<MyCustomModel> selectFileSource() {
        while (true) {
            printFileMenu();
            int choice = readIntChoice();

            if (choice == -1) {
                continue;
            }

            switch (choice) {
                case 1:
                    List<MyCustomModel> manualResult = loadFromManualInput();
                    if (manualResult != null) {
                        return manualResult;
                    }
                    break;
                case 2:
                    List<MyCustomModel> defaultResult = loadFromDefaultFile();
                    if (defaultResult != null) {
                        return defaultResult;
                    }
                    break;
                case 3:
                    List<MyCustomModel> priorityResult = loadFromSortedByPriority();
                    if (priorityResult != null) {
                        return priorityResult;
                    }
                    break;
                case 4:
                    System.out.println("Возврат в предыдущее меню.");
                    return null;
                default:
                    System.out.println("Неверный выбор. Введите число от 1 до 5.");
            }
        }
    }

    private List<MyCustomModel> loadFromManualInput() {
        System.out.print("Введите имя файла: ");
        String filename = scanner.nextLine().trim();

        if (filename.isEmpty()) {
            System.out.println("Имя файла не может быть пустым.");
            return null;
        }

        return loadFromFile("output/"+filename);
    }

    private List<MyCustomModel> loadFromDefaultFile() {
        System.out.println("Использование файла по умолчанию: " + DEFAULT_FILENAME);
        return loadFromFile(DEFAULT_FILENAME);
    }

    private List<MyCustomModel> loadFromSortedByPriority() {
        System.out.println("Загрузка файла, отсортированного по чётности приоритета: " + SORTED_PRIORITY_FILENAME);
        return loadFromFile(SORTED_PRIORITY_FILENAME);
    }


    private List<MyCustomModel> loadFromFile(String filename) {
        try {
            Path path = Paths.get(filename);

            if (!Files.exists(path)) {
                System.out.println("Файл не найден: " + filename);
                System.out.println("Убедитесь, что файл существует или выберите другой вариант.");
                return null;
            }

            if (!Files.isReadable(path)) {
                System.out.println("Файл не доступен для чтения: " + filename);
                return null;
            }

            List<String> lines = Files.readAllLines(path);
            List<MyCustomModel> models = parseLines(lines);

            if (models.isEmpty()) {
                System.out.println("Файл пуст или не содержит корректных данных.");
                return null;
            }

            System.out.println("Успешно загружено " + models.size() + " записей из файла: " + filename);
            return models;

        } catch (InvalidPathException e) {
            System.out.println("Неверный путь к файлу: " + filename);
            System.out.println("Проверьте корректность имени файла.");
            return null;
        } catch (SecurityException e) {
            System.out.println("Нет доступа к файлу: " + filename);
            System.out.println("Проверьте права доступа.");
            return null;
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка формата данных в файле: неверное числовое значение.");
            return null;
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
            return null;
        }
    }

    private List<MyCustomModel> parseLines(List<String> lines) {
        List<MyCustomModel> models = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if (line.isEmpty() || line.startsWith("===") || line.startsWith("Дата:") ||
                line.startsWith("Поле") || line.startsWith("Всего") || line.startsWith("Содержимое") ||
                line.matches("^\\d+\\.$")) {
                continue;
            }

            try {
                MyCustomModel model = parseLine(line);
                if (model != null) {
                    models.add(model);
                }
            } catch (Exception e) {
                System.out.println("Пропущена строка " + (i + 1) + ": " + line);
            }
        }

        return models;
    }

    private MyCustomModel parseLine(String line) {
        if (line.contains("Task{")) {
            return parseOutputFormat(line);
        }

        return parseCsvFormat(line);
    }

    private MyCustomModel parseOutputFormat(String line) {
        try {
            line = line.replaceFirst("^\\d+\\.\\s*", "");

            int startIndex = line.indexOf("Task{");
            int endIndex = line.lastIndexOf("}");

            if (startIndex == -1 || endIndex == -1 || endIndex <= startIndex) {
                return null;
            }

            String content = line.substring(startIndex + 5, endIndex);

            String name = null;
            Integer priority = null;
            Boolean status = null;

            String[] parts = content.split(", ");
            for (String part : parts) {
                if (part.startsWith("name='")) {
                    name = part.substring(6, part.lastIndexOf("'"));
                } else if (part.startsWith("priority=")) {
                    priority = Integer.parseInt(part.substring(9));
                } else if (part.startsWith("status=")) {
                    String statusStr = part.substring(7);
                    status = "done".equals(statusStr);
                }
            }

            if (name == null || priority == null || status == null) {
                return null;
            }

            return MyCustomModel.builder()
                    .name(name)
                    .number(priority)
                    .isTrue(status)
                    .build();

        } catch (Exception e) {
            return null;
        }
    }

    private MyCustomModel parseCsvFormat(String line) {
        try {
            String[] parts = line.split(",");

            if (parts.length < 3) {
                return null;
            }

            String name = parts[0].trim();
            Integer priority = Integer.parseInt(parts[1].trim());
            Boolean status = "done".equalsIgnoreCase(parts[2].trim());

            return MyCustomModel.builder()
                    .name(name)
                    .number(priority)
                    .isTrue(status)
                    .build();

        } catch (NumberFormatException e) {
            return null;
        } catch (IllegalStateException e) {
            return null;
        }
    }

    private int readIntChoice() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод. Введите число.");
            return -1;
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода.");
            scanner.nextLine();
            return -1;
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка ввода.");
            return -1;
        }
    }

    private void printFileMenu() {
        System.out.println("\n=== Выбор источника данных ===");
        System.out.println("1. Ввести имя файла вручную");
        System.out.println("2. Использовать файл по умолчанию (tasks.csv)");
        System.out.println("3. Загрузить отсортированный по приоритету (output/sorted_by_even.csv)");
        System.out.println("4. Назад");
        System.out.print("Ваш выбор: ");
    }

    public static List<MyCustomModel> loadCsvFile(String filename) {
        Validation validation = new Validation();
        return validation.loadFromFile(filename);
    }

    public static boolean saveToCsvFile(List<MyCustomModel> models, String filename) {
        if (models == null || models.isEmpty()) {
            System.out.println("Нет данных для сохранения.");
            return false;
        }

        try {
            Path path = Paths.get(filename);
            Files.createDirectories(path.getParent());

            List<String> lines = models.stream()
                    .map(model -> String.format("%s,%d,%b",
                            model.getName(),
                            model.getNumber(),
                            model.getStatus()))
                    .collect(Collectors.toList());

            Files.write(path, lines);
            System.out.println("Данные сохранены в файл: " + filename);
            return true;

        } catch (IOException e) {
            System.out.println("Ошибка сохранения файла: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
            return false;
        }
    }
}
