package entities;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import entities_enums.CivilState;
import entities_enums.Sex;

public abstract class Person {
    private long id;
    private int cin;
    private String email;
    private String password;
    private String firstname,lastname,tel;
    private Date birthdate;
    private Address address;
    
    private CivilState civilState;
    private Sex sex;
    
    
    public String getEmail() {
    	return this.email;
    }
    public void setEmail(String email) {
    	this.email=email;
    }
    public String getPassword() {
    	return this.password;
    }
    public void setPassword(String password) {
    	this.password=password;
    }
    public Address getAddress() {
    	return this.address;
    }
    public void setAddress(Address a) {
    	this.address=a;
    }
    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getCin() {
		return cin;
	}
	public void setCin(int cin) {
		this.cin = cin;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	
	public CivilState getCivilState() {
		return civilState;
	}
	public void setCivilState(CivilState civilState) {
		this.civilState = civilState;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	private List<Account> accounts = new LinkedList<>();
    public Person() {}
    public Person(String email , String password) {
    	this.email=email;
    	this.password=password ;
    }
    public Person(int cin,String email,String password,String last_name,String first_name,String tel,Date birthdate,CivilState civilState,Sex sex) {
    	this.cin=cin;
    	this.email=email;
    	this.password=password;
    	this.lastname=last_name;
    	this.firstname=first_name;
    	this.tel=tel;
    	this.birthdate=birthdate;
    	this.civilState=civilState;
    	this.sex=sex;
    }
    public Person(int cin,String email,String password,Address address,String last_name,String first_name,String tel,Date birthdate,CivilState civilState,Sex sex) {
    	this.cin=cin;
    	this.email=email;
    	this.password=password;
    	this.setAddress(address);
    	this.lastname=last_name;
    	this.firstname=first_name;
    	this.tel=tel;
    	this.birthdate=birthdate;
    	this.civilState=civilState;
    	this.sex=sex;
    }
    public void showPerson()  {
    	if (this instanceof Client) {
    		Client c = (Client)this;
    		System.out.println("cin :"+c.getCin()+"\nemail :"+c.getEmail())  ;
    		c.getAddress().showAdress();
    		System.out.println("\nlastname : "+ c.getLastname() +"\nfirstname : "+c.getFirstname()+"\ntel :"+c.getTel()+"\nbirthdate :"+c.getBirthdate()+"\nCivilState :"
    				+ c.getCivilState()+"\nSex : "+c.getSex());
    	}
    	else {
    		Counter tmp = (Counter)this;
    		System.out.println("cin :"+tmp.getCin()+"\nemail :"+tmp.getEmail())  ;
    		tmp.getAddress().showAdress();
    		System.out.println("\nlastname : "+ tmp.getLastname() +"\nfirstname : "+tmp.getFirstname()+"\ntel :"+tmp.getTel()+"\nbirthdate :"+tmp.getBirthdate()+"\nCivilState :"
    				+ tmp.getCivilState()+"\nSex : "+tmp.getSex()+"\n salary :"+tmp.getSalary()+"\n hiring Date :"+tmp.getHiringDate()+"\n window : "+tmp.getWindow());
    	}
    	
    }
}
