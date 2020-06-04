package application.controllers.client;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import entities.Account;
import entities.CurrentAccount;
import entities.Operation;
import entities_enums.OperationType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ShowLastThreeOperations  implements Initializable {
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
		
		Account c1 = new CurrentAccount();Account c2 = new CurrentAccount();Account c3 = new CurrentAccount();
		c1.setMoney(1200);c1.setId(1);c2.setMoney(1400);c2.setId(2);c3.setMoney(1200);c3.setId(3);
		
		Operation op1 = new Operation();
		op1.setSourceAccount(c1);
		op1.setDestAccount(c2);
		op1.setOpType(OperationType.TRANSFER_MONEY);
		op1.setExecDate(new Date());
		
		Operation op2 = new Operation();
		op2.setSourceAccount(c3);
		op2.setDestAccount(c3);
		op2.setOpType(OperationType.DEPOSIT_MONEY);
		op2.setExecDate(new Date());
		
		Operation op3 = new Operation();
		op3.setSourceAccount(c2);
		op3.setDestAccount(c2);
		op3.setOpType(OperationType.GET_MONEY);
		op3.setExecDate(new Date());
		
		//res.add(op1);
		//res.add(op2);
		//res.add(op3);
		//logiquement le travail prec est unécessaire si on avait un linkedlist déja rempli
		if(res.size()==0) {
			message.setVisible(true);
		}else if(res.size()==1) {
			showOpOne(res);
		}else if(res.size()==2) {
			showOpOne(res);
			showOpTwo(res);
		}else {
			showOpOne(res);
			showOpTwo(res);
			showOpThree(res);
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
		cptDest1.setText(op.getDestAccount().getId() == op.getSourceAccount().getId() ? " non specifié " :op.getDestAccount().getId()+"" );
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
			cptDest2.setText(op.getDestAccount().getId() == op.getSourceAccount().getId() ? " non specifié " :op.getDestAccount().getId()+"" );
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
		   cptDest3.setText(op.getDestAccount().getId() == op.getSourceAccount().getId() ? " non specifié " :op.getDestAccount().getId()+"" );
		   type3.setText(op.getOpType().toString());
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		
	}

}
