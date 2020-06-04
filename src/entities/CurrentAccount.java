package entities;
import java.util.Date;

public class CurrentAccount extends Account {
	
	public CurrentAccount() {super();}
	public CurrentAccount(double money,Date openingDate,boolean isAvailable,double threshold,Person owner,Person createdBy,Card card) {
		super(money,openingDate,isAvailable,threshold,owner,createdBy,card);
	}
	public CurrentAccount(double money,double threshold,Card card) {
		super(money,threshold,card);
	}
    public boolean transfer(double money,Account c) {
    	return true;
    }
}
