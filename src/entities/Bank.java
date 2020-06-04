package entities;

import java.util.LinkedList;
import java.util.List;

public class Bank {
   private long id;
   private double budget;
   private Address address;
   private static int employeesNbr;
   private static int clientsNbr;
   
   private List<Agency> agencies=new LinkedList<>();
   private List<Counter> counters  = new LinkedList<>();
   private List<Client> clients  = new LinkedList<>();
   
   
   public Bank() {}


public long getId() {
	return id;
}


public void setId(long id) {
	this.id = id;
}


public double getBudget() {
	return budget;
}


public void setBudget(double budget) {
	this.budget = budget;
}


public Address getAddress() {
	return address;
}


public void setAddress(Address address) {
	this.address = address;
}


public static int getEmployeesNbr() {
	return employeesNbr;
}


public static void setEmployeesNbr(int employeesNbr) {
	Bank.employeesNbr = employeesNbr;
}


public static int getClientsNbr() {
	return clientsNbr;
}


public static void setClientsNbr(int clientsNbr) {
	Bank.clientsNbr = clientsNbr;
}


public List<Agency> getAgencies() {
	return agencies;
}


public void setAgencies(List<Agency> agencies) {
	this.agencies = agencies;
}


public List<Counter> getCounters() {
	return counters;
}


public void setCounters(List<Counter> counters) {
	this.counters = counters;
}


public List<Client> getClients() {
	return clients;
}


public void setClients(List<Client> clients) {
	this.clients = clients;
}
  
}
