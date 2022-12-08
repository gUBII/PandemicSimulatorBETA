 
public class testerfresh {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Population x = new Population();
		System.out.println("The average of x: " + x.averageXOfSick());
		System.out.println("The average of y: " + x.averageYOfSick());
		System.out.println("The number of sick: " + x.numberSick());
		
		Simulant sim = new Simulant();
		Simulant dim = new Simulant();
		
		System.out.println("comparing "+sim+" with "+dim+" we get " +sim.compareTo(dim));
		
		
		System.out.println("The next sim is "+ x.nextAfter(sim));
		
	}

}