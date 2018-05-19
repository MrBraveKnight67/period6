package period6;
import robocode.*;
import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * PobachienkoEvgeny - a robot by Ya Boi
 * Dat Dank Strategy:
 * find all dem normies and record them like its the cold war
 *
 */
public class PobachienkoEvgeny extends Robot
{
	double step = 0.0;
	int cooldown = 0;
	boolean off = true;
	Random rng = new Random();

	ArrayList<Enemy> shooters = new ArrayList<Enemy>();

	public void run() {
        setColors(Color.cyan, Color.blue, Color.cyan);
        step = Math.sqrt(getBattleFieldHeight()*getBattleFieldHeight()+getBattleFieldWidth()*getBattleFieldWidth())/10;
		while(true) {
		    cooldown--;
		    for(int i = 0; i < shooters.size(); i++){
                shooters.get(i).age();
            }
			if(rng.nextInt(2) == 0){
			    System.out.println("ahead");
				ahead(step);
			}else{
                System.out.println("back");
				back(step);
			}
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		if(off){

        } else {
		    int id = id(e.getName());
            if(id != -1 && shooters.get(id).age < 3){
                shooters.get(id).update(e.getBearing(), e.getDistance(), e.getEnergy(), e.getHeading(), e.getVelocity());
                //int b = e.getHeading();
                turnGunRight(e.getBearing() - getGunHeading());
            }
        }
	}

	public void onHitByBullet(HitByBulletEvent e) {
		int id = id(e.getName());
		if(id == -1){
			shooters.add(new Enemy(e.getBearing(), e.getHeading(), e.getVelocity(), e.getName()));
			id = shooters.size()-1;
		}else{
            shooters.get(id).update(e.getBearing(), e.getHeading(), e.getVelocity());
		}
		dodgeBack(e.getName(), e.getBearing());
	}

	public void onHitWall(HitWallEvent e) {
	    if(off) {
            turnRight(e.getBearing());
            back(step * 2);
            turnLeft(90);
        } else {
	        back(step);
        }
	}

	public void onHitRobot(HitRobotEvent e) {
       if (e.getBearing() > -90 && e.getBearing() <= 90) {
           back(step);
       } else {
           ahead(step);
       }
		int id = id(e.getName());
		if(id == -1){
			shooters.add(new Enemy(e.getBearing(),e.getName()));
			id = shooters.size()-1;
		}else{
            shooters.get(id).update(e.getBearing());
		}
		shooters.get(id).threat = 70;
   }
	
	private int id(String name){
		for(int i = 0; i < shooters.size(); i++){
			if(shooters.get(i).name.equals(name)){
				return i;
			}
		}
		return -1;
	}

	private void dodgeBack(String name, double from){
	    turnRadarRight(from);

	    if(cooldown == 0) {
	        cooldown = 5;
	        if(from <= 0){
	            turnLeft(0 - from - 90);
            } else {
	            turnRight(90 - from);
            }
        }
    }
}