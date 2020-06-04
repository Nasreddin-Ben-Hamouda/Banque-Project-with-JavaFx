package entities;

import java.util.Date;

import DAO.DaoAccountTable;
import DAO.DaoClientTable;
import DAO.DaoPersonTable;
import entities_enums.CivilState;
import entities_enums.Sex;
//l'entite Guichetier
public class Counter extends Person {
	private double salary;
	private Date hiringDate;
	private long window ;
	private static int countersNbr;
	
	public Counter() {super();}
	public Counter(String email,String password) {
		super(email,password);
	}
	public Counter(int cin,String email,String password,String last_name,String first_name,String tel,Date birthdate,CivilState civilState,Sex sex,double salary,Date hiringDate,long window) {
		super(cin,email,password,last_name,first_name,tel,birthdate,civilState,sex);
		this.salary=salary;
		this.hiringDate=hiringDate;
		this.window=window;
	}
	public Counter(int cin,String email,String password,Address address,String last_name,String first_name,String tel,Date birthdate,CivilState civilState,Sex sex,double salary,Date hiringDate,long window) {
		super(cin,email,password,address,last_name,first_name,tel,birthdate,civilState,sex);
		this.salary=salary;
		this.hiringDate=hiringDate;
		this.window=window;
	}
	public long getWindow() {
		return this.window;
	}
	public void setWindow(long window) {
		this.window=window;
	}
	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Date getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(Date hiringDate) {
		this.hiringDate = hiringDate;
	}

	public static int getCountersNbr() {
		return countersNbr;
	}

	public static void setCountersNbr(int countersNbr) {
		Counter.countersNbr = countersNbr;
	}
	
	public Client saveClient(Client c) {
		return null;
	}
	
	public boolean deleteClient(Client c) {
		return true;
	}
	
	// DAO FUNCTIONS:
	
	public Client insertClientIntoDatabase(Client c,Account cmpt) {
		return DaoClientTable.insertClient(c,cmpt,this.getId());
	}
	public boolean deleteClientFromDatabase(Long Id) {
		return DaoClientTable.DeleteClient(Id);
	}
	public boolean addAccountMonneyForClient(long idAccount,long creator, double amount,double threshhold) {
		return DaoAccountTable.addAccountMonney(idAccount, creator ,amount, threshhold);
	}
	public boolean TransactionBeetweenAccounts(long idSource,long idDestinataire,long creator, double amount,double threshhold) {
		return DaoAccountTable.TransactionFromAccounts(idSource, idDestinataire,creator, amount, threshhold);
	}
	public Counter updateEntities() {
		 return (Counter)DaoPersonTable.updatePersonDatabase(this);
	}
	public Account openAccount(Account c) {
		return DaoAccountTable.InsertAccountSeperatly(c);
	}
	public boolean closeAccount(Long Id) {
		return DaoAccountTable.deleteAccount(Id);
	}
}
