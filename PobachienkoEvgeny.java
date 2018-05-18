package period6;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * PobachienkoEvgeny - a robot by Ya Boi
 */
public class PobachienkoEvgeny extends Robot
{
	double myHeading = 0.0;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	public void run() {
		while(true) {
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		int id = id(e.getName());
		if(id == -1){
			enemies.add(new Enemy(e.getBearing(), e.getDistance(), e.getEnergy(), e.getHeading(), e.getVelocity(), e.getName()));
		}else{
			enemies.set(id, new Enemy(e.getBearing(), e.getDistance(), e.getEnergy(), e.getHeading(), e.getVelocity(), e.getName()));
		}
	}

	public void onHitByBullet(HitByBulletEvent e) {
		int id = id(e.getName());
		if(id == -1){
			enemies.add(new Enemy(e.getBearing(), e.getDistance(), e.getEnergy(), e.getHeading(), e.getVelocity(), e.getName()));
		}else{
			enemies.set(id, new Enemy(e.getBearing(), e.getDistance(), e.getEnergy(), e.getHeading(), e.getVelocity(), e.getName()));
		}
		enemies.get(id).threat = 50;
	}

	public void onHitWall(HitWallEvent e) {
		back(20);
	}

	public void onHitRobot(HitRobotEvent event) {
       if (event.getBearing() > -90 && event.getBearing() <= 90) {
           back(100);
       } else {
           ahead(100);
       }
   }
	
	private int id(String name){
		for(int i = 0; i < enemies.length(); i++){
			if(enemies.get(i).name.equals(name)){
				return i;
			}
		}
		return -1;
	}
	
	private void shootAll(){
		for(int i = 0; i < enemies.length; i++){
			enemies.get(i).threat--;
		}
	}
	
	private void calcHead(){
		double sum = 0;
		double threats = 0;
		for(int i = 0; i < enemies.length; i++){
			if(enemies.get(i).threat > 0){
				sum += enemies.get(i).heading;
				threat++;
			}
		}
		myHeading = sum/threats;
	}
}

public class Enemy{
	public double bearing, dist, energy, heading, velocity, threat, prio;
	public String name;
	
	public Enemy(double b, double d, double e, double h, double v, String n){
		bearing = b;
		dist = d;
		energy = e;
		heading = h;
		velocity = v;
		name = n;
		threat = 0;
		prio = 0;
	}
}