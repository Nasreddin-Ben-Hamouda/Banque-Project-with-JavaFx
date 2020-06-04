package application.controllers.client;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import DAO.DaoAccountTable;
import entities.Account;
import entities.CurrentAccount;
import entities.Operation;
import entities_enums.OperationType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ShowLastThreeOperationsController  implements Initializable {
	@FXML
	private Text title1,title2,title3,message;
	
	@FXML
	private Text type1,type2,type3,dateExec1,dateExec2,dateExec3,cptSrc1,cptSrc2,cptSrc3,cptDest1,cptDest2,cptDest3;
	
	@FXML
	private GridPane gridPane1,gridPane2,gridPane3;
	
	private Long accountId;
	
	public void setAccountId(Long id) {
		this.accountId = id;
		System.out.println("From Last 3 ops account id = "+id);
		List<Operation> res = new LinkedList<>();
		
		
		List<Operation> ops = DaoAccountTable.getLastThreeOperationForAccount(this.accountId);
	
		if(ops.size()==0) {
			message.setVisible(true);
		}else if(ops.size()==1) {
			showOpOne(ops);
		}else if(ops.size()==2) {
			showOpOne(ops);
			showOpTwo(ops);
		}else {
			showOpOne(ops);
			showOpTwo(ops);
			showOpThree(ops);
		}
	}
	
	
	private void showOpOne(List<Operation> l) {
	   this.gridPane1.setVisible(true);
	   this.title1.setVisible(true);
	   Operation op =  l.get(0);
	   
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		String d = dateFormat.format(op.getExecDate()).toString();
		dateExec1.setText(d);
		cptSrc1.setText(op.getSourceAccount().getId()+"");
		cptDest1.setText(op.getDestAccount() == null ? " non specifié " :op.getDestAccount().getId()+"" );
		type1.setText(op.getOpType().toString());
	}
	
	private void showOpTwo(List<Operation> l) {
		   this.gridPane2.setVisible(true);
		   this.title2.setVisible(true);
		   Operation op =  l.get(1);
		   
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
			String d = dateFormat.format(op.getExecDate()).toString();
			dateExec2.setText(d);
			cptSrc2.setText(op.getSourceAccount().getId()+"");
			cptDest2.setText(op.getDestAccount() == null ? " non specifié " :op.getDestAccount().getId()+"" );
			type2.setText(op.getOpType().toString());
	}
	
	
	
	private void showOpThree(List<Operation> l) {
		   this.gridPane3.setVisible(true);
		   this.title3.setVisible(true);
		   Operation op =  l.get(2);
		   
		   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		   String d = dateFormat.format(op.getExecDate()).toString();
		   dateExec3.setText(d);
		   cptSrc3.setText(op.getSourceAccount().getId()+"");
		   cptDest3.setText(op.getDestAccount()== null ? " non specifié " :op.getDestAccount().getId()+"" );
		   type3.setText(op.getOpType().toString());
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		
	}

}
