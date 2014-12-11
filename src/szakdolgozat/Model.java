package szakdolgozat;

import java.io.*;
import java.util.*;

public class Model {
	public List<Double> network = new ArrayList<Double>();
	public List<User> users = new ArrayList<User>();
	public List<Double> linkFlowsTotal = new ArrayList<Double>();
	public double gamma;
	
	Model() {
		network = new ArrayList<Double>();
		users = new ArrayList<User>();
		linkFlowsTotal = new ArrayList<Double>();
		gamma = 0.0;
	}
	
	public void configUsers(int userCount, Scanner sin) {
		gamma = 0.0;
		
		for (int i = 0; i < userCount; i ++) {
			User temp = new User();
			temp.setId(i);
			System.out.println("Gamma for user " + i +":");
			temp.setGamma(sin.nextDouble());
			gamma += temp.getGamma();
			users.add(temp);
		}		
	} 
	
	public void configGamma(List<Double> gammas) {
		users.clear();
		gamma = 0.0;
		int i = 0;
		for (Double gammai : gammas) {
			User temp = new User();
			temp.setId(i);
			temp.setGamma(gammai);
			gamma += gammai;
			users.add(temp);
		}
						
	}
	
	public void configNetwork(File config) throws FileNotFoundException {
		Scanner fin = new Scanner(config);
		while (fin.hasNextLine()) {
			network.add(fin.nextDouble());
		}
		fin.close();
	}
	
	public boolean countNetwork() {
		boolean isFeasible = true;
		linkFlowsTotal.clear();
		for (Double capacity : network) {
			double cl = capacity;
			int n = users.size();
			double discr = Math.sqrt((n-1)*(n-1) + 4*gamma*cl);
			double result = cl - (n - 1 + discr)/(2*gamma);
			linkFlowsTotal.add(result);
			for (int j = 0; j < n; j++) {
				double gammaj = users.get(j).getGamma();
				double left = cl - result;
				double resultin = gammaj*left*left - left;
				users.get(j).addFlow(resultin);
				if(resultin <= 0.0) isFeasible = false;
			}
		}
		return isFeasible;
	}
	
	public boolean isFeasible() {
		boolean result = true;
		double link = network.get(network.size()-1);
		double userCount = users.size();
		for (User user : users) {
			double uGamma = user.getGamma();
			if (link < 1 / uGamma * (gamma / uGamma - (userCount - 1))) result = false;
		}
		return result;
	}
	
	public void printResult (PrintStream out) {
		for (User user : users) {
			out.print("\nUser " + user.getId() + ":");
			user.printFlow(out);
			}
	}
	
	public void printFlows (PrintStream out) {
		out.print("\nTotal:\t");
		for (Double link : linkFlowsTotal) {
			out.print(link + "\t");
		}
	}
}
