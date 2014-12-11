package szakdolgozat;

import java.io.PrintStream;
import java.util.*;

public class User {
	private int id;
	private double gamma;
	private List<Double> flowRates;
	
	public User() {
		id = 0;
		gamma = 0.0;
		flowRates = new ArrayList<Double>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public List<Double> getFlowRates() {
		return flowRates;
	}

	public void setFlowRates(List<Double> flowRates) {
		this.flowRates = flowRates;
	}
	
	public void addFlow(double d) {
		this.flowRates.add(d);
	}
	
	public void printFlow(PrintStream out) {
		for (Double link : flowRates) {
			out.print("\t"+link);
		}
	}
}
