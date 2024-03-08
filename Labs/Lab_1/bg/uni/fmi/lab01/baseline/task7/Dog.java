package bg.uni.fmi.lab01.baseline.task7;

public class Dog implements Comparable<Dog> {
    private double weight;
    private String breed;

    public Dog(double weight, String breed) {
        this.weight = weight;
        this.breed = breed;
    }

    @Override
    public int compareTo(Dog other) {
        return Double.compare(weight, other.weight);
    }
}
