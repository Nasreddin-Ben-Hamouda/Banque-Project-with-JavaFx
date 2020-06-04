package entities;

import java.util.Date;

import entities_enums.OperationType;

public class Operation {
   private long id;
   private OperationType opType;
   private Date execDate;
   
   private Account sourceAccount;
   private Account destAccount;
   private Person createdBy;
   
   public Account getSourceAccount() {
	return sourceAccount;
}

public void setSourceAccount(Account sourceAccount) {
	this.sourceAccount = sourceAccount;
}

public Account getDestAccount() {
	return destAccount;
}

public void setDestAccount(Account destAccount) {
	this.destAccount = destAccount;
}

public Operation() {}
public Operation(long id ,OperationType opType,Date execDate,Account sourceAccount,Account destAccount) {
	this.id=id;
	this.opType=opType;
	this.execDate=execDate;
	this.sourceAccount=sourceAccount;
	this.destAccount=destAccount;
}
public Operation(long id ,OperationType opType,Date execDate,Account sourceAccount) {
	this.id=id;
	this.opType=opType;
	this.execDate=execDate;
	this.sourceAccount=sourceAccount;
}
public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public OperationType getOpType() {
	return opType;
}

public void setOpType(OperationType opType) {
	this.opType = opType;
}

public Date getExecDate() {
	return execDate;
}

public void setExecDate(Date execDate) {
	this.execDate = execDate;
}
public Person getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(Person createdBy) {
	this.createdBy = createdBy;
}

public void showOperation() {
	if(this.destAccount!=null) {
		System.out.println("the id is "+this.id+"\nthe operation Type is "+this.opType+"\nthe execution date is :"+this.execDate+"\nthe source account id is :"
				+this.sourceAccount.getId()+"\nthe destination account id if it exists: "+this.destAccount.getId());
		}
	else {
		System.out.println("the id is "+this.id+"\nthe operation Type is "+this.opType+"\nthe execution date is :"+this.execDate+"\nthe source account id is :"
				+this.sourceAccount.getId());
	}
	}
}
