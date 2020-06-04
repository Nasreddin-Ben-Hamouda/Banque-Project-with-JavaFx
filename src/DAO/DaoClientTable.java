package DAO;
import DBConnection.DBConnection;
import entities.Client;
import entities.Address;
import entities.Account;
import entities.SavingAccount;
import entities.CurrentAccount;
import entities_enums.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.sql.*;

public class DaoClientTable {
	private static final String tableName="personne";
	
	public static Client insertClient(Client c, Account compte,long counterId) {
		try {
			//Adding Address of client to database
			PreparedStatement myStmt = DBConnection.getPreparedStatementWithReturnedKey("insert into adresse (continent,pays,gouvernorat,ville,rue,codePostal) values (?,?,?,?,?,?)");
			myStmt.setString(1,c.getAddress().getContinent().name());
			myStmt.setString(2,c.getAddress().getCountry());
			myStmt.setString(3,c.getAddress().getState());
			myStmt.setString(4,c.getAddress().getCity());
			myStmt.setString(5,c.getAddress().getStreet());
			myStmt.setInt(6,c.getAddress().getZipCode());
			int res1 = myStmt.executeUpdate();
			long addresseId= DBConnection.getKey(myStmt);
			
			//Adding client to database
			PreparedStatement myStmt2 = DBConnection.getPreparedStatementWithReturnedKey("insert into personne (cin,email,password,nom,address,prenom,dateNaiss,TYPE,tel,sex,etatCivil) values (?,?,?,?,?,?,?,?,?,?,?)");
			myStmt2.setInt(1,c.getCin());
			myStmt2.setString(2, c.getEmail());
			myStmt2.setString(3,c.getPassword());
			myStmt2.setString(4,c.getLastname());
			myStmt2.setLong(5,addresseId);
			myStmt2.setString(6,c.getFirstname());
			myStmt2.setTimestamp(7,new java.sql.Timestamp(c.getBirthdate().getTime()));
			myStmt2.setString(8,Enum.valueOf(TYPE.class,"CLIENT").name());
			myStmt2.setString(9,c.getTel());
			myStmt2.setString(10,c.getSex().name());
			myStmt2.setString(11,c.getCivilState().name());
			int res2 = myStmt2.executeUpdate();
			long clientId= DBConnection.getKey(myStmt2);
			//Adding Card 
			PreparedStatement myStmt3 = DBConnection.getPreparedStatementWithReturnedKey("insert into carte (codeInternet,codeDab,valableJusqua) values (?,?,?)");
			
			myStmt3.setLong(1,compte.getCard().getInternetCode());
			myStmt3.setLong(2,compte.getCard().getDabCode());
			myStmt3.setTimestamp(3,new java.sql.Timestamp(compte.getCard().getExpirigDate().getTime()));
			int res3 = myStmt3.executeUpdate();
			long cardId= DBConnection.getKey(myStmt3);
			//Adding Account 
			PreparedStatement myStmt4 = DBConnection.getPreparedStatementWithReturnedKey("insert into compte (solde,dateCreation,seuil,estValable,TYPE,client,guichetier,carte,dateFermeture) values (?,?,?,?,?,?,?,?,?)");
			myStmt4.setDouble(1,compte.getMoney());
			myStmt4.setTimestamp(2,new java.sql.Timestamp(new java.util.Date().getTime()));
			myStmt4.setDouble(3,compte.getThreshold());
			myStmt4.setInt(4,1);
			if(compte instanceof CurrentAccount) {
				System.out.println("DAO COURANT");
				myStmt4.setString(5,Enum.valueOf(TYPE.class,"COURANT").name());
			}else {
				System.out.println("DAO EPARGNE");
				myStmt4.setString(5,Enum.valueOf(TYPE.class,"EPARGNE").name());
			}
			myStmt4.setLong(6,clientId);
			myStmt4.setLong(7,counterId);
			myStmt4.setLong(8,cardId);
			myStmt4.setTimestamp(9,new java.sql.Timestamp(compte.getClosingDate().getTime()));

			int res4 = myStmt4.executeUpdate();
			long compteId = DBConnection.getKey(myStmt4);
			if(res1>0 && res2>0 && res3>0 && res4>0) {
				c.setId(clientId);
				compte.setId(compteId);
				compte.getCard().setConfCode(cardId);
				List<Account> accounts = new LinkedList<>();
				accounts.add(compte);
				c.setAccounts(accounts);
				return c;
			}
				
			else 
				return null ;

		}
		catch (Exception exc) {
			 exc.printStackTrace();
			 return null ;
		 }
	}
	
	public static boolean DeleteClient(Long Id) {
		try {
			PreparedStatement myStmt= DBConnection.getPreparedStatement("delete from personne where id=? and TYPE=?");
			myStmt.setLong(1,Id);
			myStmt.setString(2,Enum.valueOf(TYPE.class,"CLIENT").name());
			int res = myStmt.executeUpdate();
			//Removing ADDRESS(to be done)
			if (res > 0) 
				return true ;
			else return false ;
			
		}
		catch (Exception exc) {
			 exc.printStackTrace();
			 return false ;
		 }
		
	}
	
	public static List<Client> getAllClients() {
		LinkedList<Client> L1 = new LinkedList<Client>();
		try {
			PreparedStatement myStmt = DBConnection.getPreparedStatement("select p.*,a.* from personne p join adresse a on a.id=p.address where TYPE=?");
			myStmt.setString(1, "CLIENT");
			ResultSet res = myStmt.executeQuery();
			while(res.next()) {
				Address adresse = new Address(Enum.valueOf(Continent.class, res.getString("a.continent")),res.getString("a.pays"),res.getString("a.gouvernorat"),res.getString("a.ville")
						   ,res.getString("a.rue"),res.getInt("a.codePostal"));
				adresse.setId(res.getLong("a.id"));
				Client C = new Client(res.getInt("p.cin"),res.getString("p.email"),res.getString("p.password")
						,adresse,res.getString("p.nom"),res.getString("p.prenom"),res.getString("p.tel"),new Date(res.getTimestamp("p.dateNaiss").getTime())
						,Enum.valueOf(CivilState.class, res.getString("p.etatCivil")),Enum.valueOf(Sex.class,res.getString("p.sex"))); 
				C.setId(res.getLong("p.id"));
				L1.add(C);
			}
			return L1;
		}
		catch (Exception exc) {
			 exc.printStackTrace();
			 
		 }return null ;
	}
	
}
