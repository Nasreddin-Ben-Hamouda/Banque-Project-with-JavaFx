package application.controllers.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.controllers.DashboardInterface;
import application.controllers.UpdatePersonCredentialsController;
import application.controllers.client.ShowAccountController;
import application.helpers.FxmlLoader;
import entities.Client;
import entities.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientDashboardController implements Initializable,DashboardInterface {
	    @FXML
	    HBox ouvrirCompte;
	    
	    @FXML
	    HBox profile,deconnexion;
	    
	    private FxmlLoader loader ;
	    
	    @FXML
	    ScrollPane displayTarget;
	    
	    @FXML
	    Label diplayTargetHeaderTitle;
	    
	    @FXML 
	    private TextField chercherCompte; 
	    
	    @FXML
	    private Text usernameText;
	    //private Long userId;
	    
	    private Client client;
	    
	    public void setLoggedOnUser(Person p) {
	    	usernameText.setText(p.getFirstname()+" "+p.getLastname());
	    	this.client = (Client)p;
	    	
	    }
	    
	
	    
	    public void onListerComptesClick(MouseEvent event) throws IOException {
	    	FXMLLoader l= loader.getPage("client/listClientAccounts");
			Pane p = l.load();
			
			ListAccountsForClientController c =l.getController();
			c.setClientId(this.client.getId());
	    	
	    	displayTarget.setContent(p);
	    	diplayTargetHeaderTitle.setText("Lister les comptes");
	    }

	
	    public void onChercherCompteKeyPress(KeyEvent event) throws IOException {
	    	if (event.getCode() == KeyCode.ENTER) {
	    		String input = chercherCompte.getText();
	    		if(input!=null && !input.equals("")) {
	    		  FXMLLoader l= loader.getPage("client/showAccount");
	   		      Pane p = l.load();
	   		      
	   		      ShowAccountController c = l.getController();
	   		      c.setClientId(this.client.getId());
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
	    	
	    	/*
	    	 * 
	    	 *   	if (event.getCode() == KeyCode.ENTER) {
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
    	}*/
	    }
	    

	    
	    public void onProfileClick(MouseEvent event) throws IOException {
	    	FXMLLoader l= loader.getPage("editProfile");
			Pane p = l.load();
			UpdatePersonCredentialsController c = l.getController();
			c.setUserId(this.client.getId());
	    	displayTarget.setContent(p);
	    	diplayTargetHeaderTitle.setText("Modifier le profile");
	    	
	    }
	    
	    
	   public void onRetirerArgentClick(MouseEvent event) throws IOException {
		    FXMLLoader l= loader.getPage("client/retrieveMoney");
			Pane p = l.load();
	    
	    	displayTarget.setContent(p);
	    	diplayTargetHeaderTitle.setText("Retirer de l'argent");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
	    }
		 
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			loader = new FxmlLoader();
			
		}

}
