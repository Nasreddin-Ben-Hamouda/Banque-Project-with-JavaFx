package entities;

import java.util.LinkedList;
import java.util.List;

// entite agence
public class Agency {
	private long id;
	private Address address;
	
	private List<Counter> counters = new LinkedList<>();
	private List<Window> windows = new LinkedList<>();
	
	public Agency() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Counter> getCounters() {
		return counters;
	}

	public void setCounters(List<Counter> counters) {
		this.counters = counters;
	}

	public List<Window> getWindows() {
		return windows;
	}

	public void setWindows(List<Window> windows) {
		this.windows = windows;
	}
	
}
