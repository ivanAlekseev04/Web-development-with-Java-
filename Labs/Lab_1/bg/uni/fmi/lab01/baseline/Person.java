package bg.uni.fmi.lab01.baseline;

public class Person {
    private static final String NOT_SET_NAME = "No name";
    private static final int NOT_SET_AGE = -1;
    private String name;
    private int age;

    public Person() {
        name = NOT_SET_NAME;
        age = NOT_SET_AGE;
    }
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public Person(String name) {
        this.name = name;
        age = NOT_SET_AGE;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if(age == NOT_SET_AGE) {
            return String.format("Hello, I'm <%n>.", name);
        } else if(name == NOT_SET_NAME) {
            return "Hello, I'm Jojn Doe";
        }

        return String.format("Hello, I'm <%n>. I'm <%a> years old", name, age);
    }
}
