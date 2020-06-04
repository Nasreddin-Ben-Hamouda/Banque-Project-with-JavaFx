package application.controllers.counter;

import java.net.URL;
import java.util.ResourceBundle;

import DAO.DaoAccountTable;
import entities.Account;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class AddMoneyController implements Initializable {
	@FXML
	private TextField numCpt,somme;
	
	@FXML
	private Text numCptErr,sommeErr,message;
	
	@FXML
	private RadioButton compteCourant,compteEpargne;
	
	
	@FXML 
	private ToggleGroup accountType;
	Long counterId;
	public void onSubmit(MouseEvent event) {
		
		numCptErr.setVisible(false);sommeErr.setVisible(false);message.setVisible(false);
		try {
			long creator=this.counterId;
			Long accountNumber = Long.parseLong(numCpt.getText());
			Double sommeNbr = Double.parseDouble(somme.getText());
			if(sommeNbr <=0) {
				sommeErr.setVisible(true);
				sommeErr.setText("valeur négative non permise!");
				return ;
			}
			
			if(accountNumber <=0) {
				numCptErr.setVisible(true);
				numCptErr.setText("numero de compte invalide!");
				return ;
			}
			   
			RadioButton rb = (RadioButton)accountType.getSelectedToggle();
			double threshold = 0;
			if(rb==compteCourant) {
				   threshold = Account.THRESHOLD_CURRENT_ACCOUNT;
			}else if(rb ==compteEpargne) {
				   threshold = Account.THRESHOLD_SAVING_ACCOUNT;
			}
			boolean res = DaoAccountTable.addAccountMonney(accountNumber,creator, sommeNbr, threshold);
			message.setVisible(true);
			if(res) {
				message.setText("opération effectué avec success ! ");
			}else {
				message.setStyle("-fx-text-fill:red;");
				message.setText("opération a echoué ! ");
			}
		}catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}
	public void setCounterId(Long id) {
		System.out.println("Setting counter id");
		this.counterId= id;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
