
import java.awt.geom.*;
import java.util.*;

class Simulant implements Comparable<Simulant> {
	public static final int SIZE = 5;
	public Point2D loc;
	public int sick;
	public boolean immune;

	private double dir;
	private double speed;
	public Point2D homeLoc;
	public double mobility;
	private Random rand;

	public Simulant() {
		rand = new Random();
		homeLoc = new Point2D.Double(rand.nextInt(Main.WIDTH), rand.nextInt(Main.HEIGHT));
		loc = new Point2D.Double(homeLoc.getX(), homeLoc.getY());
		sick = 0;
		immune = false;
		dir = Math.random() * 2 * Math.PI;
		speed = Math.random() * 2;
		mobility = rand.nextInt(Main.MAX_MOVE);
	}

	/**
	 ** updates the location of the simulant on each animation frame according to
	 ** it's movement settings
	 **/
	public void updateLoc() {
		if (sick >= 0) {
			Point2D nLoc = (Point2D) loc.clone();
			nLoc.setLocation(nLoc.getX() + speed * Math.cos(dir), nLoc.getY() + speed * Math.sin(dir));

			if (nLoc.distance(homeLoc) > mobility || nLoc.getX() < 0 || nLoc.getY() < 0 || nLoc.getX() > Main.WIDTH
					|| nLoc.getY() > Main.HEIGHT) {
				dir = Math.random() * 2 * Math.PI;
			} else {
				loc = nLoc;
			}
		}
	}

	/**
	 ** adjusts the simulant's illness status according to the rules of the illness.
	 * Is asymptomatic for 240 frames, then is ill with a 3% chance of dying and
	 * then is immune if they survive to 240 frames
	 **/
	public void updateIllness() {
		if (sick > Main.CONT_AFTER) {
			if (rand.nextInt((Main.ILL_FOR - Main.CONT_AFTER) * 100) < Main.DEATH_RATE) {
				sick = -1;
			}
		}
		if (sick > 0) {
			sick++;
		}
		if (sick > Main.ILL_FOR) {
			sick = 0;
			immune = true;
		}

	}

	// this is my getters.
	public double getsickx() {
		double x = 0;
		x = (Double) this.loc.getX();
		// System.out.println("the X location is "+(Double)this.homeLoc.getX());
		return x;
	}

	public double getsicky() {
		double y = 0;
		y = (Double) this.loc.getY();
		// System.out.println("the Y location is "+(Double)this.homeLoc.getY());

		return y;
	}

	// objectthis.compareTo(simulant b){

	// this.sick -1 other.sick -1 (0)
	// this.sick -1 other.sick 0 (return thi);
	// this.sick -1 other.sick 1 (
	// this.sick 0 other.sick -1
	// this.sick 0 other.sick 0
	// this.sick 0 other.sick 1
	// this.sick 1 other.sick -1
	// this.sick 1 other.sick 0
	// this.sicl 1 other.sick -1 (0)
	// sick

	/**
	 * compares simulants using sickness. A simulant who is earlier in their
	 * sickness is "less than" one later in their sickness. A simulant who is dead
	 * is "less than" any other. A simulant who is immune is "greater than" any that
	 * is sick. A simulant that has never been sick is "less than" one that has been
	 * sick.
	 * 
	 * @return 0 if this simulant is equal to the other, less than zero if it is
	 *         less than and greater than zero if it is greater than
	 **/

	public int compareTo(Simulant other) {
		if (this.sick == other.sick) {
			return 0;
		} else if (this.sick < other.sick) {
			return -1;
		} else {
			return 1;
		}
	}
}
