package application.controllers.client;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import DAO.DaoClientTable;
import DAO.DaoPersonTable;
import application.controllers.UpdatePersonCredentialsController;
import application.controllers.counter.OpenAccountController;
import application.helpers.FxmlLoader;
import entities.Address;
import entities.Client;
import entities.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ShowClientController implements Initializable{
	private Long clientId,counterId;
	@FXML
	private VBox infosCard;
	@FXML
	private GridPane gridPane;
	
	@FXML
	private Text viewText,message;
	@FXML
	private HBox btnsContainer;
	
	@FXML
	private AnchorPane parentOne;
	
	@FXML
	private Text nom,prenom,email,cin,adresse,etatCivile,sex,tel,dateNaiss;
	
	
	public void onOpenAccountBtnClicked(MouseEvent e) {
		message.setVisible(false);
		FXMLLoader loader = new FxmlLoader().getPage("account/openAccount");
		 try {
			Pane p = loader.load();
			OpenAccountController c = loader.getController();
			c.setClientId(this.clientId);
			c.setCounterId(this.counterId);
			
		    
		    Scene scene = parentOne.getScene();
		    ScrollPane  scrollPane = (ScrollPane)scene.lookup("#displayTarget");
		    Label l  = (Label)scene.lookup("#diplayTargetHeaderTitle");
		    l.setText("Creation de compte");
		    if(scrollPane != null) {
		    	  scrollPane.setContent(p);
		    }else {
		    	System.out.println("Merde unfound scrollpane");
		    }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public void onEditBtnClicked(MouseEvent e) {
		message.setVisible(false);
		FXMLLoader loader = new FxmlLoader().getPage("updatePersonCredentials");
		try {
			Pane p = loader.load();
			UpdatePersonCredentialsController c = loader.getController();
			c.setUserId(this.clientId);
		
		    Scene scene = parentOne.getScene();
		    
		    ScrollPane  scrollPane = (ScrollPane)scene.lookup("#displayTarget");
		    Label l  = (Label)scene.lookup("#diplayTargetHeaderTitle");
		    l.setText("Modification");
		    if(scrollPane != null) {
		    	  scrollPane.setContent(p);
		    }else {
		    	System.out.println("Merde unfound scrollpane");
		    }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	

	public void onDeleteBtnClicked(MouseEvent e) {
		message.setVisible(false);
	   boolean res =DaoClientTable.DeleteClient(clientId);
	   message.setVisible(true);
	  
	   if(res) {
		   btnsContainer.setVisible(false);
		   gridPane.setVisible(false);
		   message.setText("client supprimé avec success!");
	   }else {
		   message.setText("un erreur est survenu, ressayez plus tard !");
	   }
	}
	
	public void setCounterId(Long id) {
		this.counterId = id;
	}
	
	
	public void setClientId(Long id) {
		if(id!=null) {
			Person p = DaoPersonTable.getPersonById("*", id);
			if(p!= null && p instanceof Client && p.getId() > 0) {
			    Client c = (Client)p;
				//viewText.setText("Informations Personnelles");
				gridPane.setVisible(true);
				btnsContainer.setVisible(true);
				System.out.println(c.getBirthdate());
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
				String d = dateFormat.format(c.getBirthdate()).toString();
				nom.setText(c.getFirstname());	prenom.setText(c.getLastname());email.setText(c.getEmail());
				Address cliAdr = c.getAddress();
				cin.setText(""+c.getCin()+"");adresse.setText(cliAdr.getContinent()+" "+cliAdr.getCountry()+" "+cliAdr.getState()+" "+cliAdr.getCity()+" "+cliAdr.getStreet()+"["+cliAdr.getZipCode()+"]");
				etatCivile.setText(c.getCivilState().toString());
				sex.setText(c.getSex().toString());
				tel.setText(c.getTel());
				dateNaiss.setText(d);
			}else {
				gridPane.setVisible(false);
				btnsContainer.setVisible(false);
				viewText.setText("Aucun client ne porte ce numero");
			}
		}else{
			gridPane.setVisible(false);
			btnsContainer.setVisible(false);
			viewText.setText("Le numero du client est incorrecte");
		}
		this.clientId  = id;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 
	}

}
