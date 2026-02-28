package model;

import java.util.Comparator;
import java.util.Objects;

public class MyCustomModel implements Comparable<MyCustomModel> {
    private final String name;
    private final Integer number;
    private final Boolean isTrue;

    private MyCustomModel(Builder builder) {
        this.name = builder.name;
        this.number = builder.number;
        this.isTrue = builder.isTrue;
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }

    public Boolean getStatus() {
        return isTrue;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Integer number;
        private Boolean isTrue;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder number(Integer number) {
            this.number = number;
            return this;
        }

        public Builder isTrue(Boolean isTrue) {
            this.isTrue = isTrue;
            return this;
        }

        public MyCustomModel build() {
            if (number == null) {
                throw new IllegalStateException("Для примера 0 - это неправильно");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalStateException("Объект ДОЛЖЕН обладать именем");
            }
            return new MyCustomModel(this);

        }

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyCustomModel book = (MyCustomModel) o;
        return Objects.equals(name, book.name) && Objects.equals(number, book.number) && Objects.equals(isTrue, book.isTrue);
    }

    @Override
    public String toString() {
        String done="not done";
        if(isTrue) done = "done";
        return "Task{" +
                "name='" + name + '\'' +
                ", priority=" + number +
                ", status=" + done +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number, isTrue);
    }

    @Override
    public int compareTo(MyCustomModel o) {
        return Comparator.comparing(MyCustomModel::getNumber)
                .thenComparing(MyCustomModel::getName)
                .thenComparing(MyCustomModel::getStatus)
                .compare(this, o);
    }

}