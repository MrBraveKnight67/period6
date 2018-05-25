package period6;

import robocode.*;

import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * PobachienkoEvgeny - a robot by Ya Boi
 * <p>
 * Dat Dank Strategy:
 * find all dem normies and record them like its the cold war
 * juke dem like ur life depends on it while shooting them
 * bug and freeze/ shoot in empty spaces and thus loose
 */
public class PobachienkoEvgeny extends Robot {
    double step, orStep;
    int cooldown = 0;
    int cooldownSt = 2;
    int old = 1;
    boolean off = true;
    Random rng = new Random();

    ArrayList<Enemy> shooters = new ArrayList<Enemy>();
    ArrayList<Enemy> runners = new ArrayList<Enemy>();

    public void run() {
        setColors(Color.cyan, Color.blue, Color.cyan);
        orStep = Math.sqrt(getBattleFieldHeight() * getBattleFieldHeight() + getBattleFieldWidth() * getBattleFieldWidth()) / 12;
        step = orStep;
        while (true) {
            cooldown--;
            for (int i = 0; i < shooters.size(); i++) {
                shooters.get(i).age();
                if (shooters.get(i).age > old) {
                    shooters.remove(i);
                }
            }
            tryOff();
            search();
            move();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (off) {
            dodge(e.getBearing(), e.getDistance());
            optFire(getHeading() + e.getBearing());
        } else {
            int id = id(e.getName());
            if (id != -1) {
                dodge(e.getBearing(), e.getDistance());
                shooters.get(id).update(e.getBearing(), e.getDistance());
                optFire(getHeading() + e.getBearing());
            }
        }
    }

    public void onHitByBullet(HitByBulletEvent e) {
        off = false;
        int id = id(e.getName());
        if (id == -1) {
            shooters.add(new Enemy(e.getBearing(), e.getName()));
        } else {
            shooters.get(id).update(e.getBearing());
        }
        dodge(e.getBearing());
    }

    public void onHitWall(HitWallEvent e) {
        if (off) {
            optTurn(e.getBearing());
            back(orStep);
            optTurn(-90);
        } else {
            optTurn(e.getBearing() - 90);
            back(step * 2);
        }
    }

    public void onHitRobot(HitRobotEvent e) {
        if (e.getBearing() > -90 && e.getBearing() <= 90) {
            back(step);
        } else {
            ahead(step);
        }
        int id = id(e.getName());
        if (id == -1) {
            shooters.add(new Enemy(e.getBearing(), e.getName()));
        } else {
            shooters.get(id).update(e.getBearing());
        }
    }

    private int id(String name) {
        for (int i = 0; i < shooters.size(); i++) {
            if (shooters.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private void search() {
        if (!off) {
            int youngestId = 0;
            for (int i = 0; i < shooters.size(); i++) {
                if (shooters.get(i).age < shooters.get(youngestId).age) {
                    youngestId = i;
                }
            }
            optTurnG(normalRelativeAngleDegrees(getHeading() + shooters.get(youngestId).bearing - getGunHeading()));
        }
        turnRadarRight(360);
    }

    private void tryOff() {
        if (shooters.size() == 0) {
            off = true;
            step = orStep;
        }
    }

    private void dodge(double bearing) {
        if (cooldown <= 0) {
            cooldown = cooldownSt;
            optTurn(bearing + 90);
            optTurnG(normalRelativeAngleDegrees(getHeading() + bearing - getGunHeading()));
            move();
        }
    }

    private void dodge(double bearing, double dist) {
        if (cooldown <= 0) {
            cooldown = cooldownSt;
            optTurn(bearing + 90);
            setStep(dist);
            optTurnG(normalRelativeAngleDegrees(getHeading() + bearing - getGunHeading()));
            move();
        }
    }

    private void move() {
        if (rng.nextInt(2) == 0) {
            ahead(step);
        } else {
            back(step);
        }
    }

    private void setStep(double dist) {
        if (dist < 100) {
            step = orStep;
        } else if (dist < 200) {
            step = orStep * 0.8;
        } else if (dist < 400) {
            step = orStep * 0.7;
        } else if (dist < 600) {
            step = orStep * 0.65;
        } else {
            step = orStep * 0.55;
        }
    }

    private void optFire(double absoluteBearing) {
        double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

        optTurnG(bearingFromGun);
        if (Math.abs(bearingFromGun) <= 3 && getGunHeat() == 0) {
            fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
        } else {
            scan();
        }
    }

    private void optTurn(double degrees) {
        degrees += 720;
        degrees = degrees % 360;
        if (degrees < 90) {
            turnRight(degrees);
        } else if (degrees < 180) {
            turnLeft(180 - degrees);
        } else if (degrees != 180 && degrees < 270) {
            turnRight(degrees - 180);
        } else if (degrees != 180) {
            turnLeft(360 - degrees);
        }
    }

    private void optTurnG(double degrees) {
        degrees += 720;
        degrees = degrees % 360;
        if (degrees > 180) {
            turnGunLeft(360 - degrees);
        } else {
            turnGunRight(degrees);
        }
    }
}