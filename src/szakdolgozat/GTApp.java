package szakdolgozat;

import java.io.*;
import java.util.*;

public class GTApp {

	public static void main(String[] args) throws Exception {
		Model m = new Model();
		
		
		File networkFile = new File("C:\\input.txt");
		m.configNetwork(networkFile);
		
		Scanner sin = new Scanner(System.in);
		System.out.println("Which option you want to test?");
		System.out.println("-- 1) Count specific flow rates");
		System.out.println("-- 2) Check which gamma rates are feasible");
		boolean opt = true;
		int option = sin.nextInt();
		while (opt) {
			if ((option != 1) && (option !=2 )) {
				System.out.println("Choose from 1 or 2");
				option = sin.nextInt();
			}
			else opt = false;
		}
		
		switch (option) {
		case 1: 
			System.out.println("How many users?");
			int userCount = sin.nextInt();
			m.configUsers(userCount, sin);
			
			m.countNetwork();
		
			PrintStream out = System.out;
			out.print("LinkID:");
			for (int i = 0; i < m.network.size(); i++) out.print("\t"+ i);
			out.print("\nMax");
			for (int i = 0; i < m.network.size(); i++) out.print("\t"+ m.network.get(i));
		
			m.printResult(out);
			m.printFlows(out);
			
			break;
		case 2:
			System.out.println("It's only implemented on two users, sorry.");
			System.out.println("Give minimum gamma:");
			double minGamma = sin.nextDouble();
			System.out.println("Give maximum gamma:");
			double maxGamma = sin.nextDouble();
			double pivot = (maxGamma - minGamma)*0.1;
			
			for (double gammaB = minGamma; gammaB <=maxGamma; gammaB+=pivot) {
				System.out.print("\t"+gammaB);
			}
			
			List<Double> gammas = new ArrayList<Double>();
			for (double gammaA = minGamma; gammaA <=maxGamma; gammaA+=pivot) {
				gammas.add(gammaA);
				System.out.print("\n"+gammaA);
				for (double gammaB = minGamma; gammaB <=maxGamma; gammaB+=pivot) {
					gammas.add(gammaB);
					m.configGamma(gammas);
					boolean isFeasible = m.isFeasible();
					if (isFeasible) System.out.print("\tOK");
					else System.out.print("\tFail");
					gammas.remove(gammaB);
				}
				gammas.clear();
			}
			
		}
		sin.close();
		
	}

}
