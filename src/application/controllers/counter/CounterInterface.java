package application.controllers.counter;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import entities.Address;
import entities.Counter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;


public class CounterInterface {
    private  final SimpleLongProperty id;
    private  final SimpleStringProperty nom;
    private  final SimpleStringProperty email;
    private  final SimpleStringProperty tel;
    private final SimpleStringProperty cin;
    

    private final SimpleStringProperty dateNaiss;
    private final SimpleStringProperty dateEmbch;
    private final  SimpleDoubleProperty salaire;
    private final SimpleStringProperty sex;
    private final SimpleStringProperty etatCivile;
    private final SimpleStringProperty adresse;
   
    
    public CounterInterface(Counter c)
    {      
       id = new SimpleLongProperty(c.getId());
       salaire = new SimpleDoubleProperty(c.getSalary());
       nom = new SimpleStringProperty(c.getFirstname()+" "+c.getLastname());
       email =  new SimpleStringProperty(c.getEmail());
       tel =  new SimpleStringProperty(c.getTel());
       cin = new SimpleStringProperty(""+c.getCin());
       
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	   String d = dateFormat.format(c.getBirthdate()).toString();
       dateNaiss = new SimpleStringProperty(d);
       String de = dateFormat.format(c.getHiringDate()).toString();
       dateEmbch = new SimpleStringProperty(de);
       sex =  new SimpleStringProperty(c.getSex().toString());
       etatCivile =  new SimpleStringProperty(c.getCivilState().toString());
       
       
       Address adr = c.getAddress();
       adresse = new SimpleStringProperty(""+adr.getContinent()+"-"+adr.getCountry().toString()+"-"+adr.getState()+"-"+adr.getCity()+" "+adr.getStreet()+" [ "+adr.getZipCode()+" ]");
    }


	public String getDateEmbch() {
		return dateEmbch.get();
	}


	public Double getSalaire() {
		return salaire.get();
	}


	public Long getId() {
		return id.get();
	}


	public String getNom() {
		return nom.get();
	}


	public String getEmail() {
		return email.get();
	}


	public String getTel() {
		return tel.get();
	}


	public String getCin() {
		return cin.get();
	}


	public String getDateNaiss() {
		return dateNaiss.get();
	}


	public String getSex() {
		return sex.get();
	}


	public String getEtatCivile() {
		return etatCivile.get();
	}


	public String getAdresse() {
		return adresse.get();
	} 
}

