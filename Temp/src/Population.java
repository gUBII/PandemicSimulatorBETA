import java.util.*;
import java.awt.geom.*;

// OPEN CONSOLE FOR RUNTIME VIEW.. THANKS!
class Population {
	public ArrayList<Simulant> sims = new ArrayList<Simulant>();
	public Random rand = new Random();

	public int getPopulationSize() {
        return sims.size();
    }

	public int getNumberSick() {
        int sickCount = 0;
        for (Simulant s : sims) {
            if (s.sick > 0) {
                sickCount++;
            }
        }
        return sickCount;
    }

    public int getNumberImmune() {
        int immuneCount = 0;
        for (Simulant s : sims) {
            if (s.immune) {
                immuneCount++;
            }
        }
        return immuneCount;
    }


	public Population() {
		for (int i = 0; i < Main.SIMS; i++) {
			sims.add(new Simulant());
		}

		// make the first one sick
		
			sims.get(0).sick = 1; // to be completed
		}
	

	/**
	 * update an entire population for each simulant update their location and
	 * illness then check each pair of simulants to see if they are in touch with
	 * one another. If they are, they might get sick
	 **/
	public void update() {
		for (Simulant s : sims) {
			s.updateLoc();
			s.updateIllness();
		}
		// check every pair of sims to see if they are in contact, if so, pass on any
		// sickness
		for (Simulant s1 : sims) {
			for (Simulant s2 : sims) {
				if (s1.loc.distance(s2.loc) < Simulant.SIZE) {
					s1.sick = s1.sick == 0 && s2.sick > Main.CONT_AFTER && !s1.immune
							&& rand.nextInt(100) < Main.TRANS_RATE ? 1 : s1.sick;
					s2.sick = s2.sick == 0 && s1.sick > Main.CONT_AFTER && !s2.immune
							&& rand.nextInt(100) < Main.TRANS_RATE ? 1 : s2.sick;
				}
			}
		}
	}

	/**
	 * @return the average of the x locations of all sick simulants answer is zero
	 *         if no sims are sick
	 **/
	public double averageXOfSick() {
		double count = 0;
		double avg = 0;
		double counter = 0;
		for (Simulant dims : sims) {
			if (dims.sick > 0) {
				counter++;
				count = count + dims.getsickx();
			}
		}

		avg = (count / counter);
		System.out.println();
		// System.out.println("the average x of sick is" + avg);
		System.out.println();
		return avg;
		// to be completed
	}

	/**
	 * @return the average of the y locations of all sick simulants answer is zero
	 *         if no sims are sick
	 **/
	public double averageYOfSick() {
		double count = 0;
		double avg = 0;
		double counter = 0;

		for (Simulant dims : sims) {
			if (dims.sick > 0) {
				counter++;
				count = count + dims.getsicky();
			}
		}

		avg = (count / counter);
		System.out.println();
		// System.out.println("the average Y of sick is" + avg);
		System.out.println();
		return avg;
	}

	/**
	 * @return the first sim found who's is over the given point you should use the
	 *         whole area the simulant is drawn on (i.e. center plus radius) to
	 *         determine if the sim is at this location. Return null if no sim
	 *         found.
	 **/
	public Simulant simAtLocation(Point2D loc) {
		ListIterator<Simulant> iter = sims.listIterator(0);
		int i = 0;
		while (iter.hasNext()) {
			if (iter.next().loc == loc) {
				System.out.println("The sim at location " + loc + " is SimNumber: " + sims.get(i));
				return sims.get(i);
			} else {
				i++;
			}
		}
		return null; // to be completed
	}

	/**
	 * @param sim the simulant to find in the population
	 * @return the simulant one index greater than the given simulant. Return null
	 *         if `sim` not found or there is no simulant after the found one.
	 **/
	public Simulant nextAfter(Simulant sim) {
		ListIterator<Simulant> iter = sims.listIterator(0);

		if (sims.get(sims.size() - 1).equals(sim)) {
			return null;
		} else {
			while (iter.hasNext()) {
				if (iter.next().equals(sim)) {
					return iter.next();
				} else {

				}
			}
		}
		return null;
	}

	/**
	 * @param sim the simulant to find in the population
	 * @return the simulant one index less than the given simulant. Return null if
	 *         `sim` not found or there is no simulant before the found one.
	 **/
	public Simulant prevBefore(Simulant sim) {

		ListIterator<Simulant> iter = sims.listIterator(sims.size());

		if (sims.get(0).equals(sim)) {
			return null;
		} else {
			while (iter.hasPrevious()) {
				if (iter.previous().equals(sim)) {
					return iter.previous();
				} else {

				}
			}
		}
		return null;
	}

	/**
	 * @return the total number of sick simulants
	 **/
	public int numberSick() {

		int sick = 0;
		int dead = 0;
		ListIterator<Simulant> iter = sims.listIterator(0);
		ListIterator<Simulant> diter = sims.listIterator(0);

		while (iter.hasNext()) {
			if (iter.next().sick > 0) {

				sick++;
			} else {
				while (diter.hasNext()) {
					if (diter.next().sick < 0) {
						dead++;
					}
				}

			}
		}
		int z = (sims.size() - sick);
		System.out.println("gUUBI@gUUBI~razerQ895:~$ hello Ari <3");
		System.out.println();
		System.out.println("run main.java*javadoc");
		System.out.println();
		System.out.println("Simulator :ON");
		System.out.println();
		System.out.println("{==SIMULATION SIZE:[[" + Main.SIMS + "]]:SIMULATION SIZE}");
		System.out.println();
		System.out.println("===========CALCULATING===============");
		System.out.println();
		System.out.println("[GeTTinG[SICK:((" + sick + ")):SICK]GeTTnG]");
		System.out.println();
		System.out.println("===============YOUR===================");
		System.out.println();
		System.out.println("[Immune{NOT((" + (z - dead) + "))AFFECTED]Immune]");
		System.out.println();
		System.out.println("===============VALUE=================");
		System.out.println();
		System.out.println("       [DEAD((" + dead + "))DEAD]");
		System.out.println();
		System.out.println("=======PLEASE====WAIT==B)==B)========");
		System.out.println();
		System.out.println("=REAL TIME SORTING:SELECTION SORT()=");

		return sick;

	}

	/**
	 * @return An array list of all the simulants sorted by "sickness". Sorting
	 *         order is determined by the `compareTo` method on the simulants.
	 **/
	public ArrayList<Simulant> sort() {
		ArrayList<Simulant> sorter = new ArrayList<>(sims);
		quickSort(sorter, 0, sorter.size() - 1);
		return sorter;
	}
	
	private void quickSort(ArrayList<Simulant> sorter, int low, int high) {
		if (low < high) {
			int pivotIndex = partition(sorter, low, high);
			quickSort(sorter, low, pivotIndex - 1);
			quickSort(sorter, pivotIndex + 1, high);
		}
	}
	
	private int partition(ArrayList<Simulant> sorter, int low, int high) {
		Simulant pivot = sorter.get(high);
		int i = low - 1;
		for (int j = low; j < high; j++) {
			if (sorter.get(j).compareTo(pivot) <= 0) {
				i++;
				swap(sorter, i, j);
			}
		}
		swap(sorter, i + 1, high);
		return i + 1;
	}
	
	private void swap(ArrayList<Simulant> sorter, int i, int j) {
		Simulant temp = sorter.get(i);
		sorter.set(i, sorter.get(j));
		sorter.set(j, temp);
	}
	

}