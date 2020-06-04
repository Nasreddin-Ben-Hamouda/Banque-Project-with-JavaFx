package application.controllers.counter;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import DAO.DaoAccountTable;
import DAO.DaoClientTable;
import DAO.DaoPersonTable;
import application.helpers.Utilities;
import entities.Account;
import entities.Address;
import entities.Card;
import entities.Client;
import entities.Counter;
import entities.CurrentAccount;
import entities.SavingAccount;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import application.helpers.FormValidation;
public class AddCounterController implements Initializable {
	private ObservableList<String> sexObsList;
	private  ObservableList<String> civStateObsList ;
	private  ObservableList<String> contitentsObsList ;
	private  ObservableList<String> statesObsList ;

	@FXML
	private ChoiceBox sexSelect,civStateSelect,contSelect,etatSelect;
	
	@FXML
	private TextField cin,nom,prenom,tel,pays,ville,rue,codePostale,email,salaire;
	
	@FXML
	private Text error,nomErr,prenomErr,cinErr,dateNaissErr,telErr,sexErr,contErr,etatErr,paysErr,rueErr,codePostaleErr,emailErr,msgSucc,dateEmchErr,sommeErr;
	
	@FXML
	private DatePicker dateNaiss,dateEmbch;
	
	@FXML
	private Button submitBtn;
	
	
	private Long counterId;
	
	
	private String prenomVal,nomVal,telVal,sexVal,etatCivilVal,continentVal,etatVal,paysVal,villeVal,rueVal,emailVal;
	private int codePostaleVal,cinVal;
	private double salaireVal;
    private Date dateNaissVal,dateEmbchVal;
	
	public void dismissAllErrors() {
		nomErr.setVisible(false);prenomErr.setVisible(false);cinErr.setVisible(false);
		dateNaissErr.setVisible(false);telErr.setVisible(false);sexErr.setVisible(false);
		contErr.setVisible(false);etatErr.setVisible(false);paysErr.setVisible(false);
		rueErr.setVisible(false);codePostaleErr.setVisible(false);
		emailErr.setVisible(false);error.setVisible(false);msgSucc.setVisible(false);sommeErr.setVisible(false);dateEmchErr.setVisible(false);
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
		  LocalDate dateEmchVal1 = dateEmbch.getValue();
		  if(dateEmchVal1 == null) {
			  test = false;
			  dateEmchErr.setVisible(true);
		  }else {
			 ZoneId defaultZoneId = ZoneId.systemDefault();
			 this.dateEmbchVal =  Date.from(dateEmchVal1.atStartOfDay(defaultZoneId).toInstant());
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
		   try {
			   salaireVal  = Double.parseDouble(salaire.getText());
		   }catch(NumberFormatException e) {
			    test = false;
			    sommeErr.setVisible(true);
		    	sommeErr.setText("valeur non numerique ! ");
		   }
		   
		 
		  return test;
	}
	
	public void setCounterId(Long id) {
	   this.counterId = id;
	   System.out.println("COUNTER ACC CREATOR ID = "+id);
	}
	

   public void onSubmit(MouseEvent event) {
	    boolean res = validateForm();
	    if(res) {
	    	
	    	 Counter counter = new Counter();
	    	 counter.setFirstname(nomVal);counter.setLastname(prenomVal);counter.setCin(cinVal);counter.setEmail(emailVal);
	    	 counter.setBirthdate(dateNaissVal);counter.setTel(telVal);counter.setSex(sexVal.equals("Homme") ? Sex.MAL : Sex.FEM);
	    	 counter.setPassword(Utilities.getRandomPwd());
	    	 counter.setSalary(salaireVal);
	    	 counter.setHiringDate(dateEmbchVal);
	    	 if(etatCivilVal.equals("Célibataire")) {
	    		 counter.setCivilState(CivilState.SING);
	    	 }else if(etatCivilVal.equals("Marié")) {
	    		 counter.setCivilState(CivilState.MAR);
	    	 }else {
	    		 counter.setCivilState(CivilState.DIV);
	    	 }
	    	 Address adr = new Address();
	    	 switch(continentVal) {
	    	   case "Amérique":
	    		    adr.setContinent(Continent.AME);
	    	   break;
	    	   case "Afrique":
	    		   adr.setContinent(Continent.AFR);
		       break;
	    	   case "Asie":
	    		   adr.setContinent(Continent.ASI);
			   break;	
	    	   case "Europe":
	    		   adr.setContinent(Continent.EUR);
			   break;
	    	   case "Océanie":
	    		   adr.setContinent(Continent.AUS);
			   break;	    	 
			  }
	    	  adr.setState(paysVal);adr.setCountry(etatVal);adr.setCity(villeVal);adr.setStreet(rueVal);adr.setZipCode(codePostaleVal);
	    	 
	    	    counter.setAddress(adr);
		    	Counter counter2 = new Counter();counter2.setId(this.counterId);
		    
		    	
		    	if(DaoPersonTable.insertCounter(counter,this.counterId )!=null) {
		    		msgSucc.setVisible(true);
		    	}
		    	else {
	    		  error.setVisible(true);
	    	  }
	    
	    }else {
	    	System.out.println("we can not submit data");
	    }
   }
   
   public void initialize(URL arg0, ResourceBundle arg1) {
       sexObsList=  FXCollections.observableArrayList("Femme","Homme");
       sexSelect.setItems(sexObsList);
	   sexSelect.setValue("Homme");
	   
	   civStateObsList =  FXCollections.observableArrayList("Célibataire","Marié","Divorcé");
	   civStateSelect.setItems(civStateObsList);
	   civStateSelect.setValue("Célibataire");
	   
	   contitentsObsList = FXCollections.observableArrayList("Amérique","Afrique","Asie","Europe","Océanie"); 
	   contSelect.setItems(contitentsObsList);
	   contSelect.setValue("Afrique");
	   
	   String fileName = getClass().getResource("/application/views/countries.txt").getFile();

	   statesObsList = FXCollections.observableArrayList(Utilities.getCountries(fileName)); 
	   etatSelect.setItems(statesObsList);
	   etatSelect.setValue("Tunisia");
	}
}

