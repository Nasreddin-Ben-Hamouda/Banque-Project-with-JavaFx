package application.controllers.counter;

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

public class TransferController implements Initializable{
	@FXML
	private TextField numCptSrc,numCptDest,somme;
	
	@FXML
	private Text numCptSrcErr,numCptDestErr,sommeErr,message;
	
	@FXML
	private RadioButton compteCourant,compteEpargne;
	
	@FXML 
	private ToggleGroup accountType;
	
	@FXML 
	private ToggleGroup srcAccountType;
	Long creator;
	
	public void onSubmit(MouseEvent event) {
		numCptSrcErr.setVisible(false)	;numCptDestErr.setVisible(false);sommeErr.setVisible(false);message.setVisible(false);
		try {
			Long creator=this.creator;
			Long srcAccountNumber = Long.parseLong(numCptSrc.getText());
			Long destAccountNumber = Long.parseLong(numCptDest.getText());
			Double sommeNbr = Double.parseDouble(somme.getText());
			
			if(srcAccountNumber <=0) {
				numCptSrcErr.setVisible(true);
				numCptSrcErr.setText("numero de compte invalide!");
				return ;
			}
			
			if(destAccountNumber <=0) {
				numCptDestErr.setVisible(true);
				numCptDestErr.setText("numero de compte invalide!");
				return ;
			}
			   
			RadioButton rb = (RadioButton)accountType.getSelectedToggle();
			double threshold = 0;
			if(rb==compteCourant) {
				   threshold = Account.THRESHOLD_CURRENT_ACCOUNT;
			}else if(rb ==compteEpargne) {
				   threshold = Account.THRESHOLD_SAVING_ACCOUNT;
			}
			
			boolean res = DaoAccountTable.TransactionFromAccounts(srcAccountNumber, destAccountNumber,creator, sommeNbr< 0 ? 0-sommeNbr : sommeNbr, threshold);
			message.setVisible(true);
			if(res) {
				message.setText("opération effectué avec success ! ");
			}else {
				message.setStyle("-fx-text-fill:red;");
				message.setText("opération a echoué (probablement la somme a retiré n'est alloué ou le type de compte est incorrecte) ! ");
			}
		}catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}
	public void setCounterId(Long id) {
		System.out.println("Setting counter id");
		this.creator = id;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
