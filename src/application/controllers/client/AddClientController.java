package application.controllers.client;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import DAO.DaoAccountTable;
import DAO.DaoClientTable;
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
public class AddClientController implements Initializable {
	private ObservableList<String> sexObsList;
	private  ObservableList<String> civStateObsList ;
	private  ObservableList<String> contitentsObsList ;
	private  ObservableList<String> statesObsList ;

	@FXML
	private ChoiceBox sexSelect,civStateSelect,contSelect,etatSelect;
	
	@FXML
	private TextField cin,nom,prenom,tel,pays,ville,rue,codePostale,somme,email;
	
	@FXML
	private Text error,nomErr,prenomErr,cinErr,dateNaissErr,telErr,sexErr,contErr,etatErr,paysErr,rueErr,codePostaleErr,sommeErr,emailErr,msgSucc;
	
	@FXML
	private DatePicker dateNaiss;
	
	@FXML
	private RadioButton compteCourant,compteEpargne;
	
	@FXML
	private Button submitBtn;
	
	@FXML 
	private ToggleGroup accountType;
	
	private Long counterId;
	
	
	private String prenomVal,nomVal,telVal,sexVal,etatCivilVal,continentVal,etatVal,paysVal,villeVal,rueVal,emailVal;
	private int codePostaleVal,typeCompte,cinVal;
    private double sommeVal;
    private Date dateNaissVal;
	
	public void dismissAllErrors() {
		nomErr.setVisible(false);prenomErr.setVisible(false);cinErr.setVisible(false);
		dateNaissErr.setVisible(false);telErr.setVisible(false);sexErr.setVisible(false);
		contErr.setVisible(false);etatErr.setVisible(false);paysErr.setVisible(false);
		rueErr.setVisible(false);codePostaleErr.setVisible(false);sommeErr.setVisible(false);
		emailErr.setVisible(false);error.setVisible(false);msgSucc.setVisible(false);
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
		   
		   if(accountType.getSelectedToggle() == compteCourant) {
			   typeCompte = 0;
		   }else {
			   typeCompte = 1;
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
			   sommeVal  = Double.parseDouble(somme.getText());
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
	    Date after ;
	    if(res) {
	    	Account acc;
	    	if(typeCompte==0) {
	    		after = Utilities.getDateAfterXDays(365*2);
	    		acc = new CurrentAccount();
	    		acc.setThreshold(Account.THRESHOLD_CURRENT_ACCOUNT);
	    		System.out.println("COURANT");
	    	} else {
	    		after = Utilities.getDateAfterXDays(365*4);
	    		acc = new SavingAccount();
	    		acc.setThreshold(Account.THRESHOLD_SAVING_ACCOUNT);
	    		System.out.println("EPARGNE");
	    	}
	    	 acc.setMoney(sommeVal);
	    	 acc.setAvailable(true);
	    	 acc.setClosingDate(after);
	    
	    	 Client client = new Client();
	    	 client.setFirstname(nomVal);client.setLastname(prenomVal);client.setCin(cinVal);client.setEmail(emailVal);
	    	 client.setBirthdate(dateNaissVal);client.setTel(telVal);client.setSex(sexVal.equals("Homme") ? Sex.MAL : Sex.FEM);
	    	 client.setPassword(Utilities.getRandomPwd());
	    	 if(etatCivilVal.equals("Célibataire")) {
	    		 client.setCivilState(CivilState.SING);
	    	 }else if(etatCivilVal.equals("Marié")) {
	    		 client.setCivilState(CivilState.MAR);
	    	 }else {
	    		 client.setCivilState(CivilState.DIV);
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
	    	  Card card = new Card();
	    	  
	    	  Utilities utils = new Utilities();
	    	  card.setExpiringDate(after);
	    	  Long ret []= utils.getCodesFromPropsFile();
	    	  if(ret != null) {
	    	   card.setInternetCode(ret[0]);
	    	   card.setDabCode(ret[1]);
	    		 
		    	client.setAddress(adr);
		    	Counter counter = new Counter();counter.setId(this.counterId);
		    	acc.setCreatedBy(counter);
		    	acc.setCard(card);
		    	DaoClientTable.insertClient(client, acc,this.counterId );
		    	msgSucc.setVisible(true);
	    	  }else {
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
