package period6;

public class Enemy {
    public double bearing, dist;
    public int age;
    public String name;

    public Enemy(double b, double d, String n) {
        bearing = b;
        dist = d;
        name = n;
        age = 0;
    }

    public Enemy(double b, String n) {
        bearing = b;
        name = n;
        age = 0;
    }

    public void update(double b, double d) {
        bearing = b;
        dist = d;
        age = 0;
    }

    public void update(double b) {
        bearing = b;
        age = 0;
    }

    public int age() {
        age++;
        return age;
    }
}