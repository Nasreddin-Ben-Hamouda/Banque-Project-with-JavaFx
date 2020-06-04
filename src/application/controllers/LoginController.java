package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DAO.DaoPersonTable;
import application.controllers.counter.CounterDashboardController;
import application.helpers.FxmlLoader;
import entities.Client;
import entities.Counter;
import entities.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController implements Initializable{

	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Button btn;
	
	@FXML
	private Text errorArea;
	
	public void login(MouseEvent event) throws IOException {
	        String username = this.username.getText();
	        String password = this.password.getText();
	    	//if login and pwd matches
	    	Person p = DaoPersonTable.Authentificate(username,password);
	    	if(p!=null) {
	    		((Node) (event.getSource())).getScene().getWindow().hide();
	   		    FXMLLoader loader ;
	    		if (p instanceof Counter) {
	    			loader = new FxmlLoader().getPage("counter/counterDashboard");
	    			
		    	}else{
		    		loader = new FxmlLoader().getPage("client/clientDashboard");
		    	}
	    		 Parent root = loader.load();
	    		
	    		 
	    		 DashboardInterface cdc = loader.getController();loader = new FxmlLoader().getPage("counter/counterDashboard");
	    		 //cdc.setUsertId(p.getId());
	    		 cdc.setLoggedOnUser(p);
	    		// cdc.setUsername(p.getFirstname()+" "+p.getLastname());
	    		 
	    		 Stage stage = new Stage();
	    		 Scene scene = new Scene(root);
	    		 
	    		 stage.setScene(scene);
	    		 //stage.setResizable(false);
	    		 stage.setTitle("tableau de bord");
	    		 stage.show();
	    	}else {
	    		errorArea.setText("nom d'utilisateur ou mot de passe incorrecte! ");
	    		errorArea.setVisible(true);
	    	}
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
	  // TODO Auto-generated method stub
	}

}