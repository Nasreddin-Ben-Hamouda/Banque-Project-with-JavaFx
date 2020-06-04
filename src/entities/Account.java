package entities;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public abstract class Account {
   private long id;
   private double money;
   private Date openingDate;
   private Date closingDate;
   private boolean isAvailable;
   private double threshold;
   
   private Person owner;
   private Person createdBy;
   private Card card;
   private List<Operation> operations = new LinkedList<>();
   public static final double THRESHOLD_CURRENT_ACCOUNT=50;
   public static final double THRESHOLD_SAVING_ACCOUNT=200;
   
   public Account() {}
   public Account(double money,Date openingDate,boolean isAvailable,double threshold,Person owner,Person createdBy,Card card) {
	   this.money=money;
	   this.openingDate=openingDate;
	   this.isAvailable=isAvailable;
	   this.threshold=threshold;
	   this.owner=owner;
	   this.createdBy=createdBy;
	   this.card=card;
   }
   public Account(double money,double threshold,Card card) {
	   this.money=money;
	   this.isAvailable=true;
	   this.threshold=threshold;
	   this.card=card;
   }
   public boolean depositMoney(double money) {
	   return true;
   }
   
   public boolean getMoney(double money) {
	   
	   return true;
   }
   
   public static Account getAccount(long id) {
	   return null;
   }
   
   public Operation[] showLastThreeOps() {
	   return new Operation[3];
   }

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public double getMoney() {
	return money;
}

public void setMoney(double money) {
	this.money = money;
}

public Date getOpeningDate() {
	return openingDate;
}

public void setOpeningDate(Date openingDate) {
	this.openingDate = openingDate;
}

public Date getClosingDate() {
	return closingDate;
}

public void setClosingDate(Date closingDate) {
	this.closingDate = closingDate;
}

public boolean isAvailable() {
	return isAvailable;
}

public void setAvailable(boolean isAvailable) {
	this.isAvailable = isAvailable;
}

public double getThreshold() {
	return threshold;
}

public void setThreshold(double threshold) {
	this.threshold = threshold;
}

public Person getOwner() {
	return owner;
}

public void setOwner(Person owner) {
	this.owner = owner;
}

public Person getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(Person createdBy) {
	this.createdBy = createdBy;
}

public Card getCard() {
	return card;
}

public void setCard(Card card) {
	this.card = card;
}

public List<Operation> getOperations() {
	return operations;
}

public void setOperations(List<Operation> operations) {
	this.operations = operations;
}
   
   
   
	 
}
