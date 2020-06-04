package application.controllers.client;

import java.net.URL;
import java.util.ResourceBundle;

import DAO.DaoAccountTable;
import entities.Account;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class RetrieveMoneyController implements Initializable{
	
	@FXML
	private TextField codeDab,numCarte,somme;
	
	@FXML
	private Text codeDabErr,sommeErr,numCarteErr,message;
	
	@FXML
	private RadioButton compteCourant,compteEpargne;
	
	
	@FXML 
	private ToggleGroup accountType;
	
	private Long clientId,accountId;
	
	public void setClientId(Long id)   {
		this.clientId = id;
	}
	
	public void setAccountId(Long id)   {
		this.accountId = id;
		//here we must call function
	}
	
	//a verifier cette methode et extraire aussi le num carte et invoquer la methode DaoAccountTable.retrieveAccountMonneyWithCard(cardNumber,amount,threshold);
	public void onSubmit(MouseEvent event) {
		codeDabErr.setVisible(false);sommeErr.setVisible(false);message.setVisible(false);
		try {
			Long cardNumber = Long.parseLong(numCarte.getText());
			Long codeDabNumber = Long.parseLong(codeDab.getText());
			Double sommeNbr = Double.parseDouble(somme.getText());
			if(sommeNbr <=0) {
				sommeErr.setVisible(true);
				sommeErr.setText("valeur négative non permise!");
				return ;
			}
			   
			RadioButton rb = (RadioButton)accountType.getSelectedToggle();
			double threshold = 0;
			if(rb==compteCourant) {
				   threshold = Account.THRESHOLD_CURRENT_ACCOUNT;
			}else if(rb ==compteEpargne) {
				   threshold = Account.THRESHOLD_SAVING_ACCOUNT;
			}
			boolean res = DaoAccountTable.retrieveAccountMonneyWithCard(cardNumber,sommeNbr,threshold);
			message.setVisible(true);
			if(res) {
				message.setText("opération effectué avec success ! ");
			}else {
				message.setStyle("-fx-text-fill:red;");
				message.setText("opération a echoué (probablement la somme a retiré n'est alloué ou le type de compte est incorrecte) ! ");
			}
		}catch(NumberFormatException e) {
			e.printStackTrace();
			message.setVisible(true);
			message.setText("opération a echoué (probablement la somme a retiré n'est alloué ou le type de compte est incorrecte ou  num de carte ou code dab erronés) ! ");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		
	}

}
