package application.controllers.counter;

import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.controllers.DashboardInterface;
import application.controllers.UpdatePersonCredentialsController;
import application.controllers.client.AddClientController;
import application.controllers.client.ShowClientController;
import application.helpers.FxmlLoader;
import entities.Client;
import entities.Counter;
import entities.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class CounterDashboardController implements Initializable,DashboardInterface {
    @FXML
    HBox listerComptes,ajouterClient,listerClients;
    
    @FXML
    HBox profile,deconnexion;
    
    private FxmlLoader loader ;
    
    @FXML
    ScrollPane displayTarget;
    
    @FXML
    Label diplayTargetHeaderTitle;
    
    @FXML 
    private TextField chercherClient,chercherCompte,chercherGuichetier;
    
    @FXML
    private Text usernameText;
   
    
    private Counter counter;
    
    public void setLoggedOnUser(Person p) {
    	 usernameText.setText(p.getFirstname()+" "+p.getLastname());
 	    this.counter = (Counter)p;
    }
   
 
    
   /* public void onOuvrirCompteClick(MouseEvent event) throws IOException {
    	FXMLLoader l= loader.getPage("account/openAccount");
		Pane p = l.load();
    	
    	displayTarget.setContent(p);
    	diplayTargetHeaderTitle.setText("Ouvrir un nouveau compte");
    }*/
    
    public void onListerComptesClick(MouseEvent event) throws IOException {
    	FXMLLoader l= loader.getPage("account/listAccounts");
		Pane p = l.load();
    	
    	displayTarget.setContent(p);
    	diplayTargetHeaderTitle.setText("Lister les comptes");
    }
    
    /*public void onFermerCompteClick(MouseEvent event) throws IOException {
    	FXMLLoader l= loader.getPage("account/closeAccount");
		Pane p = l.load();
  
    	displayTarget.setContent(p);
    	diplayTargetHeaderTitle.setText("Fermer un compte");
    }*/
    
   
    
    public void onAjouterClientClick(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FxmlLoader().getPage("client/addClient");
 	  
 	    
    	//FXMLLoader l= loader.getPage("client/addClient");
    	
		Pane p = loader.load();
		AddClientController ctr = loader.getController();
		
		ctr.setCounterId(this.counter.getId());
		
    	
    	displayTarget.setContent(p);
     	diplayTargetHeaderTitle.setText("Ajouter un nouveau client");
    }
    
    public void onListerClientsClick(MouseEvent event) throws IOException {
    	FXMLLoader l= loader.getPage("client/listClients");
		Pane p = l.load();

    	displayTarget.setContent(p);
     	diplayTargetHeaderTitle.setText("Lister les clients");
    }
    
   /* public void onRadierClientClick(MouseEvent event) throws IOException {
    	FXMLLoader l= loader.getPage("client/deleteClient");
		Pane p = l.load();
    	
    	displayTarget.setContent(p);
    	diplayTargetHeaderTitle.setText("Radier un client");
    }*/
    
 
    
    public void onChercherCompteKeyPress(KeyEvent event) throws IOException {
    	if (event.getCode() == KeyCode.ENTER) {
    		String input = chercherCompte.getText();
    		if(input!=null && !input.equals("")) {
    		  FXMLLoader l= loader.getPage("account/showAccount");
   		      Pane p = l.load();
   		      ShowAccountController c = l.getController();
   		      Long id=null;
   		      
   		      try {
    		   id = Long.parseLong(input);
    		  }catch(NumberFormatException e) {
    			 e.printStackTrace();
    		  }finally {
    			 c.setAccountId(id);
    		  }
        	  displayTarget.setContent(p);
        	  diplayTargetHeaderTitle.setText("Informations du compte");
    		}
    	}
    }
    
    public void onChercherClientKeyPress(KeyEvent event) throws IOException  {
    	if (event.getCode() == KeyCode.ENTER) {
    		 String input = chercherClient.getText();
    		 if(input!=null && !input.equals("")) {
	    		 FXMLLoader l = loader.getPage("client/showClient");
	    		 Pane p = l.load();
	    		 ShowClientController c = l.getController();
	    		 Long id = null;
	    		 try {
	    		   id = Long.parseLong(input);
	    		 }catch(NumberFormatException e) {
	    			 e.printStackTrace();
	    		 }finally {
	    			 c.setClientId(id);  
	    			 c.setCounterId(counter.getId());
	    		 }
	    				 
	        	 displayTarget.setContent(p);
	        	 diplayTargetHeaderTitle.setText("Informations du client");
    		 }
    	}
    }
    
    public void onDeposerArgentClick(MouseEvent event) throws IOException {
    	FXMLLoader l = loader.getPage("counter/addMoney");
		 Pane p = l.load();
		 AddMoneyController c = l.getController();
		 c.setCounterId(this.counter.getId());
		 displayTarget.setContent(p);
    	 diplayTargetHeaderTitle.setText("Déposer de l'argent");
	}
	    
	public void onRetirerArgentClick(MouseEvent event) throws IOException {
	   	FXMLLoader l = loader.getPage("counter/retrieveMoney");
		Pane p = l.load();
		RetrieveMoneyController c = l.getController();
		c.setCounterId(this.counter.getId());
		displayTarget.setContent(p);
	    diplayTargetHeaderTitle.setText("Retirer de l'argent");
	}
	    
	public void onVirementClick(MouseEvent event) throws IOException {
	   	FXMLLoader l = loader.getPage("counter/transfer");
		Pane p = l.load();
		TransferController c = l.getController();
		c.setCounterId(this.counter.getId());
		displayTarget.setContent(p);
	    diplayTargetHeaderTitle.setText("Virement d'argent");
	}
    
    
    public void onProfileClick(MouseEvent event) throws IOException {
    	FXMLLoader l = loader.getPage("updatePersonCredentials");

		Pane p = l.load();
	    UpdatePersonCredentialsController c = l.getController();
		c.setUserId(this.counter.getId());
		   
	    displayTarget.setContent(p);
        diplayTargetHeaderTitle.setText("Modifier le profile");
    }
    
    public void onChercherGuichetierKeyPress(KeyEvent event) throws IOException  {
    	if (event.getCode() == KeyCode.ENTER) {
    		 String input = chercherGuichetier.getText();
    		 if(input!=null && !input.equals("")) {
	    		 FXMLLoader l = loader.getPage("counter/showCounter");
	    		 Pane p = l.load();
	    		 ShowCounterController c = l.getController();
	    		 Long id = null;
	    		 try {
	    		   id = Long.parseLong(input);
	    		 }catch(NumberFormatException e) {
	    			 e.printStackTrace();
	    		 }finally {
	    			 c.setGuichetierId(id);  
	    		 }
	    				 
	        	 displayTarget.setContent(p);
	        	 diplayTargetHeaderTitle.setText("Informations du Guichetier");
    		 }
    	}
    }
    
    public void onAjouterCounterClick(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FxmlLoader().getPage("counter/addCounter");
 	  
 	       	
		Pane p = loader.load();
		AddCounterController ctr = loader.getController();
		
		ctr.setCounterId(this.counter.getId());
		
    	
    	displayTarget.setContent(p);
     	diplayTargetHeaderTitle.setText("Ajouter un guichetier");
    }
    
    public void onDeconnexionClick(MouseEvent event) {
    	((Node) (event.getSource())).getScene().getWindow().hide();
   	     Parent root;
		try {
			FXMLLoader loader = new FxmlLoader().getPage("login");
			root = loader.load();
			
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setTitle("interface de connexion");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	 
    }
    public void onListerGuichetierClick(MouseEvent event) throws IOException {
    	FXMLLoader l= loader.getPage("counter/listeCounter");
		Pane p = l.load();

    	displayTarget.setContent(p);
     	diplayTargetHeaderTitle.setText("Lister les Guichetiers");
    }
    
	 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		loader = new FxmlLoader();
	}
	
}