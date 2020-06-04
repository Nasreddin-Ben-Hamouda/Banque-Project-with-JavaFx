package application.controllers.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import entities.Address;
import entities.Client;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class ClientInterface {
    private  final SimpleLongProperty id;
    private  final SimpleStringProperty nom;
    private  final SimpleStringProperty email;
    private  final SimpleStringProperty tel;
    private final SimpleStringProperty cin;
    

    private final SimpleStringProperty dateNaiss;
    private final SimpleStringProperty sex;
    private final SimpleStringProperty etatCivile;
    private final SimpleStringProperty adresse;
   
    
    public ClientInterface(Client c)
    {      
       id = new SimpleLongProperty(c.getId());
       
       nom = new SimpleStringProperty(c.getFirstname()+" "+c.getLastname());
       email =  new SimpleStringProperty(c.getEmail());
       tel =  new SimpleStringProperty(c.getTel());
       cin = new SimpleStringProperty(""+c.getCin());
       
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	   String d = dateFormat.format(c.getBirthdate()).toString();
       dateNaiss = new SimpleStringProperty(d);
       sex =  new SimpleStringProperty(c.getSex().toString());
       etatCivile =  new SimpleStringProperty(c.getCivilState().toString());
       
       Address adr = c.getAddress();
       adresse = new SimpleStringProperty(""+adr.getContinent()+"-"+adr.getCountry().toString()+"-"+adr.getState()+"-"+adr.getCity()+" "+adr.getStreet()+" [ "+adr.getZipCode()+" ]");
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
