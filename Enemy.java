package period6;

public class Enemy{
    public double bearing, dist, energy, heading, velocity;
    public int threat, age;
    public String name;

    public Enemy(double b, double d, double e, double h, double v, String n){
        bearing = b;
        dist = d;
        energy = e;
        heading = h;
        velocity = v;
        name = n;
        threat = 0;
        age = 0;
    }

    public Enemy(double b, double h, double v, String n){
        bearing = b;
        heading = h;
        velocity = v;
        name = n;
        threat = 0;
        age = 0;
    }

    public Enemy(double b, String n){
        bearing = b;
        name = n;
        threat = 0;
        age = 0;
    }

    public void update(double b, double d, double e, double h, double v){
        bearing = b;
        dist = d;
        energy = e;
        heading = h;
        velocity = v;
        age = 0;
    }

    public void update(double b, double h, double v){
        bearing = b;
        heading = h;
        velocity = v;
        age = 0;
    }

    public void update(double b){
        bearing = b;
        age = 0;
    }

    public void age(){
        age++;
    }
}