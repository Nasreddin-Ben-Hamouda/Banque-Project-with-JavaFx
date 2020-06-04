package entities;

import java.util.LinkedList;
import java.util.List;

//l entite Guichet
public class Window {
   private long id;
   private Agency agency;
   private List<Counter> counters = new LinkedList<>();
   private static int counterNbr;
   
   public static int getCounterNbr() {
	return counterNbr;
   }

	public static void setCounterNbr(int counterNbr) {
		Window.counterNbr = counterNbr;
	}

	public Window() {}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Agency getAgency() {
		return agency;
	}
	
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	
	public List<Counter> getCounters() {
		return counters;
	}
	
	public void setCounters(List<Counter> counters) {
		this.counters = counters;
	}
   
}
