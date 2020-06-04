package application.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import DAO.DaoPersonTable;
import application.helpers.FormValidation;
import application.helpers.Utilities;
import entities.Address;
import entities.Client;
import entities.Counter;
import entities.Person;
import entities_enums.CivilState;
import entities_enums.Continent;
import entities_enums.Sex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class UpdatePersonCredentialsController implements Initializable {
	private  ObservableList<String> sexObsList;
	private  ObservableList<String> civStateObsList ;
	private  ObservableList<String> contitentsObsList ;
	private  ObservableList<String> statesObsList ;
	
	private Long userId;
	private Person p;
	
	@FXML
	private ChoiceBox sexSelect,civStateSelect,contSelect,etatSelect;
	
	@FXML
	private TextField cin,nom,prenom,tel,pays,ville,rue,codePostale,email;
	
	@FXML
	private PasswordField pwd;
	
	@FXML
	private Text nomErr,prenomErr,cinErr,dateNaissErr,telErr,sexErr,contErr,etatErr,paysErr,rueErr,codePostaleErr,emailErr,error,pwdErr;
	
	private String prenomVal,nomVal,telVal,sexVal,etatCivilVal,continentVal,etatVal,paysVal,villeVal,rueVal,emailVal,pwdVal;
	private int codePostaleVal,cinVal;
    private Date dateNaissVal;
	
	@FXML
	private DatePicker dateNaiss;
	
	public void dismissAllErrors() {
		nomErr.setVisible(false);prenomErr.setVisible(false);cinErr.setVisible(false);
		dateNaissErr.setVisible(false);telErr.setVisible(false);sexErr.setVisible(false);
		contErr.setVisible(false);etatErr.setVisible(false);paysErr.setVisible(false);
		rueErr.setVisible(false);codePostaleErr.setVisible(false);
		emailErr.setVisible(false);error.setVisible(false);
		pwdErr.setVisible(false);
	}
	
	public boolean validateForm() {
		  boolean test=true;
		  this.dismissAllErrors();
		  try {
			  cinVal  = Integer.parseInt(cin.getText());
			  if(cinVal < 0) {
				  test = false;
				  cinErr.setVisible(true);
				  cinErr.setText("valeur négative ! ");
			  }else if(cin.getText().length() <8) {
				  test = false;
				  cinErr.setVisible(true);
				  cinErr.setText("de 8 chiffres ");
			  }
		  }catch(NumberFormatException e) {
			  test = false;
			  cinErr.setVisible(true);
			  cinErr.setText("valeur non numérique!");
			  System.out.println("Erreur de conv str ===> nbr");
		  }
		  
		  nomVal = nom.getText();
		  if(nomVal == null || nomVal.length()==0) {
			  test = false;
			  nomErr.setVisible(true);
		  }
		  prenomVal = prenom.getText();
		  if(prenomVal == null || prenomVal.length()==0) {
			  test = false;
			  prenomErr.setVisible(true);
		  }
		  
		  pwdVal = pwd.getText();
		  if(pwdVal == null || pwdVal.length()<8) {
			  test = false;
			  pwdErr.setText("doit avoir au moin 8 caractéres");
			  pwdErr.setVisible(true);
		  }
		  
		  
		  emailVal = email.getText();
		  System.out.println(emailVal);
		  if(!FormValidation.isEmail(emailVal)) {
			 test = false;
			 
			 emailErr.setVisible(true);
			 emailErr.setText("adresse invalide");
		  }
		  
		  LocalDate dateNaissVal = dateNaiss.getValue();
		  if(dateNaissVal == null) {
			  test = false;
			  dateNaissErr.setVisible(true);
		  }else {
			 ZoneId defaultZoneId = ZoneId.systemDefault();
			 this.dateNaissVal =  Date.from(dateNaissVal.atStartOfDay(defaultZoneId).toInstant());
		  }
		  
		   telVal = tel.getText();
		   if(telVal != null && telVal.length()>0) {
		    if(!FormValidation.isPhoneNbr(telVal)) {
		    	test = false;
			   telErr.setVisible(true);
			   telErr.setText("doit avoir la forme : +code du pays puis num tel ");
		    }
		   }else {
			   test = false;
			   telErr.setVisible(true);
			   telErr.setText("*  obligatoire");
		   }
		   
		   sexVal = (String) sexSelect.getSelectionModel().getSelectedItem();
		   etatCivilVal = (String) etatSelect.getSelectionModel().getSelectedItem();
		   continentVal = (String) contSelect.getSelectionModel().getSelectedItem();
		   etatVal = (String) etatSelect.getSelectionModel().getSelectedItem();
		   
		   paysVal = pays.getText();
		   if(paysVal ==null || paysVal.length()==0) {
			   test = false;
			   paysErr.setVisible(true);
		   }
		   
		   villeVal = ville.getText();
		   
		   rueVal = rue.getText();
		   if(rueVal ==null || rueVal.length()==0) {
			   test = false;
			   rueErr.setVisible(true);
		   }
		   try {
			   codePostaleVal  = Integer.parseInt(codePostale.getText());
		   }catch(NumberFormatException e) {
			    test = false;
		    	System.out.println("zip erroné");
		    	codePostaleErr.setVisible(true);
		    	codePostaleErr.setText("valeur non numerique ! ");
		   } 
		  return test;
	}
	
	
	
	public void setUserId(Long id) {
		this.userId = id;
		String fileName = getClass().getResource("/application/views/countries.txt").getFile();

		statesObsList = FXCollections.observableArrayList(Utilities.getCountries(fileName)); 
		
		etatSelect.setItems(statesObsList);
		
		this.p = DaoPersonTable.getPersonById("*", userId);
		
		if(p!=null) {
			nom.setText(p.getFirstname());
			prenom.setText(p.getLastname());
			cin.setText(String.valueOf(p.getCin()));
			tel.setText(p.getTel());
			email.setText(p.getEmail());
			pwd.setText(p.getPassword());
			sexSelect.setValue(String.valueOf(p.getSex()).equals("MAL") ? "Homme":"Femme" );
			        
			String cs = String.valueOf(p.getCivilState()) ;
			civStateSelect.setValue(cs.equals("SING") ? "Célibataire": (cs.equals("MAR") ? "Marié" : "Divorcé"));
			Address adr= p.getAddress();
			etatSelect.setValue(String.valueOf(adr.getCountry()));
			        
			String continentVal = String.valueOf(adr.getContinent());
			if(p.getBirthdate()!=null)
			   dateNaiss.setValue(p.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			
			pays.setText(adr.getState());
			ville.setText(adr.getCity());
			rue.setText(adr.getStreet());
			codePostale.setText(String.valueOf(adr.getZipCode()));
			
			switch(continentVal) {
			 case "EUR":
			 	contSelect.setValue("Europe");
			 break;
			 case "AFR":
			 	contSelect.setValue("Afrique");
			 break;
			 case "AME":
			 	contSelect.setValue("Amérique");
			 break;	
			 case "ASI":
			 	contSelect.setValue("Asie");
			break;
			case "AUS":
			   contSelect.setValue("Océanie");
			break;	    	 
		  }
		}else {
			error.setVisible(true);
		}
		
	}
	
	public void onSubmit(MouseEvent event) {
	    boolean test = validateForm();
	    if(test) {
	    
	    	p.setFirstname(nomVal);p.setLastname(prenomVal);p.setEmail(emailVal);
	    	p.setPassword(pwdVal);p.setCin(cinVal);p.setBirthdate(dateNaissVal);
	    	p.setTel(telVal);p.setSex(sexVal.equals("Homme") ? Sex.MAL : Sex.FEM);
	    	p.setCivilState(etatCivilVal.equals("Célibataire") ? CivilState.SING : (etatCivilVal.equals("Marié") ? CivilState.MAR : CivilState.DIV));
	    	Address adr = p.getAddress();
	    	switch(continentVal) {
			 case "Europe":
				 adr.setContinent(Continent.EUR);
			 break;
			 case "Afrique":
				 adr.setContinent(Continent.AFR);
			 break;
			 case "Amérique":
				 adr.setContinent(Continent.AME);
			 break;	
			 case "Asie":
				 adr.setContinent(Continent.ASI);
			break;
			case "Océanie":
				 adr.setContinent(Continent.AUS);
			break;	    	 
		  }
	     adr.setState((String) etatSelect.getSelectionModel().getSelectedItem());
		 adr.setCountry(paysVal);adr.setCity(villeVal);
		 adr.setState(rueVal);adr.setZipCode(codePostaleVal);
		 if(p instanceof Client) {
			 Client cli = (Client)p;
			 p = cli.updateEntities();
		 }else if(p instanceof Counter){
			
			 Counter counter = (Counter)p;
			 p = counter.updateEntities();
		 }
		 error.setVisible(true);
		 if(p!=null) {
			 error.setFill(Color.GREEN);
			 error.setText("modifications effectués avec success");
		 }else {
			 error.setText("opération échoué, réessayer plus tard  ! ");
		 }
	    }else {
	     	System.out.println("NOT BENNE BENNE");
	    }
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		sexObsList=  FXCollections.observableArrayList("Femme","Homme");
	    sexSelect.setItems(sexObsList);
	   
		   
		civStateObsList =  FXCollections.observableArrayList("Célibataire","Marié","Divorcé");
		civStateSelect.setItems(civStateObsList);
		   
		   
		contitentsObsList = FXCollections.observableArrayList("Amérique","Afrique","Asie","Europe","Océanie"); 
		contSelect.setItems(contitentsObsList);
	
	}
}
