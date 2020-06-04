package application.controllers.counter;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;



import DAO.DaoAccountTable;
import DAO.DaoClientTable;
import application.controllers.client.ShowLastThreeOperationsController;
import application.helpers.FxmlLoader;
import entities.Account;
import entities.Card;
import entities.Client;
import entities.Counter;
import entities.CurrentAccount;
import entities.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ShowAccountController implements Initializable {
	@FXML
	private Text message,solde,type,threshold,dateOuv,dateFerm,estValable,proprietaire,creePar,numCarte,codeInt,codeDab,valableJus,errorMessage;
	@FXML
	private GridPane gridPane;
	@FXML
	private HBox  btnsContainer;
	
	private Long accountId;
	
	@FXML
	private AnchorPane parentOne;
	
	public void setAccountId(Long id) {
		btnsContainer.setVisible(true);
		gridPane.setVisible(true);
		errorMessage.setVisible(false);
		System.out.println("ACCOUNT ID =======> "+id );
		Account c = DaoAccountTable.getAccountById("*", id);
		if(c==null) {
			btnsContainer.setVisible(false);
			gridPane.setVisible(false);
			errorMessage.setVisible(true);
		}else {
			//fill up fields
			solde.setText(String.valueOf(c.getMoney()));
			type.setText((c instanceof CurrentAccount) ? "Compte Courant" : "Compte Epargné");
			threshold.setText(String.valueOf(c.getThreshold()));
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
			String d = dateFormat.format(c.getOpeningDate()).toString();
			
			dateOuv.setText(d);
			d = c.getClosingDate() != null ? dateFormat.format(c.getClosingDate() ).toString() : "valeur non spécifié";
			dateFerm.setText(d);
			
			estValable.setText(c.isAvailable() ? "valable " : "non valable");
			Client owner =(Client) c.getOwner();
			Counter creator = (Counter)c.getCreatedBy();
			Card carte= c.getCard();
			
			proprietaire.setText((owner!= null) ? owner.getFirstname()+" "+owner.getLastname() : "non spécifié");
			creePar.setText((creator!= null) ? creator.getFirstname()+" "+creator.getLastname() : "non spécifié");
			numCarte.setText((carte !=null )? String.valueOf(carte.getConfCode()) : "non spécifié");
			codeInt.setText((carte !=null )? String.valueOf(carte.getInternetCode()) : "non spécifié");
			codeDab.setText((carte !=null )? String.valueOf(carte.getDabCode()) : "non spécifié");
			
			valableJus.setText((carte !=null )? dateFormat.format(carte.getExpirigDate()).toString() : "non spécifié");
		}
		this.accountId = id;
	}
	
	public void onShowLastOperationsBtnClicked(MouseEvent event) {
		FXMLLoader loader = new FxmlLoader().getPage("client/showLastThreeOperations");
		try {
			Pane p = loader.load();
			ShowLastThreeOperationsController c = loader.getController();
			c.setAccountId(this.accountId);
				
		    Scene scene = parentOne.getScene();
			ScrollPane  scrollPane = (ScrollPane)scene.lookup("#displayTarget");
			Label l  = (Label)scene.lookup("#diplayTargetHeaderTitle");
			l.setText("3 dernieres operations");
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
	   boolean res = DaoAccountTable.deleteAccount(this.accountId);
	   message.setVisible(true);
	   if(res) {
		   btnsContainer.setVisible(false);
		   gridPane.setVisible(false);
		   message.setText("compte supprimé avec success!");
	   }else {
		   message.setText("un erreur est survenu, ressayez plus tard !");
	   }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
	}
}
