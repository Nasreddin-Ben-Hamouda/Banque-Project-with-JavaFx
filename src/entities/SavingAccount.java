package entities;
import java.util.Date;
public class SavingAccount extends Account {
	
	public SavingAccount() {super();}
	public SavingAccount(double money,Date openingDate,boolean isAvailable,double threshold,Person owner,Person createdBy,Card card) {
		super(money,openingDate,isAvailable,threshold,owner,createdBy,card);
	}
	public SavingAccount(double money,double threshold,Card card) {
	super(money,threshold,card);
	}
}
