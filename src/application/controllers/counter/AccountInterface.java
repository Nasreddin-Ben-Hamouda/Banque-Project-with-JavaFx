package application.controllers.counter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import entities.Account;
import entities.Client;
import entities.Counter;
import entities.CurrentAccount;
import entities.Person;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class AccountInterface {
    private  final SimpleLongProperty id;
    private  final SimpleStringProperty type;
    private  final SimpleDoubleProperty money;
    
    private final SimpleStringProperty openingDate;
    private final SimpleStringProperty closingDate;
    private final SimpleStringProperty isAvailable;
    private final SimpleDoubleProperty threshold;
    private final SimpleStringProperty owner;
    private final SimpleStringProperty createdBy;
      
    
    public AccountInterface(Account a) {
    	id = new SimpleLongProperty(a.getId());
    	type = new SimpleStringProperty(a instanceof CurrentAccount ? "Courant" : "Epargne");
    	money = new SimpleDoubleProperty(a.getMoney());

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		String d = dateFormat.format(a.getOpeningDate()).toString();
		openingDate = new SimpleStringProperty(d);
		d = dateFormat.format(a.getClosingDate()).toString();
		closingDate = new SimpleStringProperty(d);
		isAvailable = new SimpleStringProperty(a.isAvailable() ? "Valable" : "Non Valable");
		threshold = new SimpleDoubleProperty(a.getThreshold());
		Person own = a.getOwner();
		
		owner = new SimpleStringProperty((own!= null) ? own.getFirstname()+" "+own.getLastname()+"  [numCli = "+ own.getId()+"]": "non specifié!");
		Counter creator = (Counter) a.getCreatedBy();
		createdBy = new SimpleStringProperty((creator!= null) ? creator.getFirstname()+" "+creator.getLastname() : "non specifié!");
    }


	public Long getId() {
		return id.get();
	}


	public String getType() {
		return type.get();
	}


	public Double getMoney() {
		return money.get();
	}


	public String getOpeningDate() {
		return openingDate.get();
	}


	public String getClosingDate() {
		return closingDate.get();
	}


	public String getIsAvailable() {
		return isAvailable.get();
	}


	public Double getThreshold() {
		return threshold.get();
	}


	public String getOwner() {
		return owner.get();
	}

	public String getCreatedBy() {
		return createdBy.get();
	}
}
