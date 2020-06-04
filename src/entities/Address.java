package entities;


import entities_enums.Continent;

public class Address {
	 private long id;
	 private Continent continent;
	 private String country;
	 private String state;
	 private String city;
	 private String street;
	 private int zipCode;
	 
	 public Address() {}
	 public Address(Long id) {
		 this.id=id;
	 }
	 public Address(Continent continent,String country,String state,String city,String street,int zipCode) {
		 this.continent=continent;
		 this.country=country;
		 this.state=state;
		 this.city=city;
		 this.street=street;
		 this.zipCode=zipCode;
	 }
	 public Address(Address a) {
		 	this.id=a.id;
	    	this.continent=a.continent;
	    	this.country=a.country;
	    	this.state=a.state;
	    	this.city=a.city;
	    	this.street=a.street;
	    	this.zipCode=a.zipCode;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Continent getContinent() {
		return continent;
	}

	public void setContinent(Continent continent) {
		this.continent = continent;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	public void showAdress() {
		System.out.println("continent :"+this.continent+"\ncountry : "+this.country+"\nstate : "+this.state+"\ncity : "+this.city+"\nstreet : "+this.street+"\nzipCode : "+this.zipCode);
	}
	
	
}
