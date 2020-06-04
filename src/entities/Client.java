package entities;

import java.util.Date;

import DAO.DaoPersonTable;
import entities_enums.CivilState;
import entities_enums.Sex;

public class Client extends Person{
   private static int clientsNbr;
   private int accountsNbr;
   
   
   public Client() {super();}
   public Client(String email,String password) {
	   super(email,password);
   }
   public Client(int cin,String email,String password,String last_name,String first_name,String tel,Date birthdate,CivilState civilState,Sex sex) {
	   super(cin,email,password,last_name,first_name,tel,birthdate,civilState,sex);
	   clientsNbr++;
	   
   }
   
   public Client(int cin,String email,String password,Address address,String last_name,String first_name,String tel,Date birthdate,CivilState civilState,Sex sex) {
	   super(cin,email,password,address,last_name,first_name,tel,birthdate,civilState,sex);
	   clientsNbr++;
	   
   }

public static int getClientsNbr() {
	return clientsNbr;
}


public static void setClientsNbr(int clientsNbr) {
	Client.clientsNbr = clientsNbr;
}


public int getAccountsNbr() {
	return accountsNbr;
}


public void setAccountsNbr(int accountsNbr) {
	this.accountsNbr = accountsNbr;
}
//DAO Functions 
public Client updateEntities() {
	return (Client)DaoPersonTable.updatePersonDatabase(this);
	
}

}
