package DAO;
import DBConnection.DBConnection;
import entities.Client;
import entities.Address;
import entities.Account;
import entities.Person;
import entities.Counter;
import entities.Card;
import entities.SavingAccount;
import entities.CurrentAccount;
import entities.Operation;
import entities_enums.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.sql.*;

public class DaoAccountTable {
	public static List<Operation> getLastThreeOperationForAccount(long idAccount) {
		LinkedList<Operation> L1 = new LinkedList<Operation>();
		try {
			PreparedStatement myStmt1 = DBConnection.getPreparedStatement("select op.*,cs.* from operation op join compte cs on op.compteSource = cs.id"
					+ " where op.compteSource=? ORDER BY op.dateExec DESC LIMIT 3 ");
			myStmt1.setLong(1, idAccount);
			ResultSet res = myStmt1.executeQuery() ;
			 while (res.next()) {
				 Account sourceAccount = null ;
				 Operation op = new Operation();
				 if (res.getString("cs.TYPE").equals(Enum.valueOf(TYPE.class,"COURANT").name()) && res.getInt("cs.estValable")==1) {
					 CurrentAccount tmp = new CurrentAccount();
					 tmp.setId(res.getLong("cs.id"));
					 sourceAccount= tmp;
				 }
				 else {
					 SavingAccount tmp = new SavingAccount(); 
					 tmp.setId(res.getLong("cs.id"));
					 sourceAccount= tmp;
				 }
				 if (res.getString("op.type").equals("VER")) {
					 op = new Operation(res.getLong("op.id"),Enum.valueOf(OperationType.class,"DEPOSIT_MONEY"),new Date(res.getTimestamp("op.dateExec").getTime()),sourceAccount);
				 }
				 else if (res.getString("op.type").equals("RET")) {
					 op = new Operation(res.getLong("op.id"),Enum.valueOf(OperationType.class,"GET_MONEY"),new Date(res.getTimestamp("op.dateExec").getTime()),sourceAccount);
				 }
				 else {
					 Account destinationAccount= new CurrentAccount();
					 destinationAccount.setId(res.getLong("op.compteDestination"));
					 op = new Operation(res.getLong("op.id"),Enum.valueOf(OperationType.class,"TRANSFER_MONEY"),new Date(res.getTimestamp("op.dateExec").getTime()),sourceAccount,destinationAccount);
				 }
				 L1.add(op);
			 }
			 return L1;
		}
		catch (Exception exc) {
			 exc.printStackTrace();
		 }
			return null;
	}
	public static boolean addAccountMonney(long idAccount,long creator,double amount,double threshhold) {
		String accType= threshhold == Account.THRESHOLD_CURRENT_ACCOUNT ? "COURANT" : "EPARGNE";
		try  {
			PreparedStatement myStmt1 = DBConnection.getPreparedStatement("update compte set solde=solde+? where solde+?>=? and id=? and TYPE=?");
			myStmt1.setDouble(1, amount);
			myStmt1.setDouble(2, amount);
			myStmt1.setDouble(3, threshhold);
			myStmt1.setLong(4, idAccount);
			myStmt1.setString(5, accType);
			int res1 = myStmt1.executeUpdate();
			if(res1>0) 
			{
				PreparedStatement myStmt2 = DBConnection.getPreparedStatement("insert into operation (type,dateExec,compteSource,createur) values (?,?,?,?)");
				if (amount >= 0) 
					{myStmt2.setString(1,"VER");}
				else 
					{myStmt2.setString(1,"RET");}
				myStmt2.setTimestamp(2,new java.sql.Timestamp(new java.util.Date().getTime()));
				myStmt2.setLong(3,idAccount);
				myStmt2.setLong(4,creator);
				int resOp=myStmt2.executeUpdate();
				if (resOp >0) return true ;
				else return false ;
			}
			else 
				return false;
		}catch (Exception exc) {
			 exc.printStackTrace();
			 return false ;
		 }
	}
	
	
	public static boolean retrieveAccountMonneyWithCard(long cardNumber,double amount,double threshhold) {
		String accType= threshhold == Account.THRESHOLD_CURRENT_ACCOUNT ? "COURANT" : "EPARGNE";
		try  {
			PreparedStatement myStmt1 = DBConnection.getConnection().prepareStatement("update compte set solde=solde - ? where solde - ?>=? and carte=? and TYPE=?");
			
			myStmt1.setDouble(1, amount);
			myStmt1.setDouble(2, amount);
			myStmt1.setDouble(3, threshhold);
			myStmt1.setLong(4, cardNumber);
			myStmt1.setString(5, accType);
			int res1 = myStmt1.executeUpdate();
			
			
			PreparedStatement myStmt = DBConnection.getConnection().prepareStatement("SELECT co.id  FROM compte co JOIN carte ca ON co.carte = ca.numero AND ca.numero =?");
			myStmt.setLong(1, cardNumber);
			ResultSet resSet = myStmt.executeQuery() ;
			
			if(res1 >0 && resSet.next()) {
				System.out.println("BENNE BENNE 1");
			    Long idAccount = resSet.getLong("id");
			    System.out.println("idAccoutn =======>"+idAccount);
				PreparedStatement myStmt2 = DBConnection.getPreparedStatement("insert into operation (type,dateExec,compteSource) values (?,?,?)");
				
				myStmt2.setString(1,"RET");
				myStmt2.setTimestamp(2,new java.sql.Timestamp(new java.util.Date().getTime()));
				myStmt2.setLong(3,idAccount);
				int resOp=myStmt2.executeUpdate();
				if (resOp >0) {
					System.out.println("BENNE BENNE 2");
					return true ;
				}else {
					System.out.println("NOT BENNE BENNE 1");
					return false ;
				}
			}else {
				System.out.println("NOT BENNE BENNE 1");
				return false;
			}
		}catch (Exception exc) {
			System.out.println("NOT BENNE BENNE 3");
			 exc.printStackTrace();
			 return false ;
		 }
	}
	
	
	public static boolean TransactionFromAccounts(long idSource,long idDestinataire,long creator,double amount,double threshhold) {
		Connection con = DBConnection.getConnection();;
		try {
			String accType= threshhold == Account.THRESHOLD_CURRENT_ACCOUNT ? "COURANT" : "EPARGNE";
			con.setAutoCommit(false);
			PreparedStatement myStmt1 = con.prepareStatement("update compte c1 set c1.solde=c1.solde-? where c1.solde-?>=? and c1.id=? and TYPE = ?");
			PreparedStatement myStmt2 =con.prepareStatement("update compte c2 set c2.solde=c2.solde+? where c2.id=?");
			myStmt1.setDouble(1, amount);
			myStmt1.setDouble(2, amount);
			myStmt1.setDouble(3, threshhold);
			myStmt1.setLong(4, idSource);
			myStmt1.setString(5, accType);
			int res1=myStmt1.executeUpdate();
			myStmt2.setDouble(1, amount);
			myStmt2.setLong(2, idDestinataire);
			int res2=myStmt2.executeUpdate();
			if(res1>0 && res2>0 ) {
				con.commit();
				con.setAutoCommit(true);
				PreparedStatement myStmt3 = DBConnection.getPreparedStatement("insert into operation (type,dateExec,compteSource,compteDestination,createur) values (?,?,?,?,?)");
				myStmt3.setString(1,"VIR");
				myStmt3.setTimestamp(2,new java.sql.Timestamp(new java.util.Date().getTime()));
				myStmt3.setLong(3,idSource);
				myStmt3.setLong(4,idDestinataire);
				myStmt3.setLong(5,creator);
				int resOp = myStmt3.executeUpdate();
				if (resOp>0) return true;
				else return false ;
			}
			else 
				return false;
		}catch (Exception exc) {
			 try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 exc.printStackTrace();
			 return false ;
		 }
	}
	
	@SuppressWarnings("finally")
	public static Account getAccountById(String parametersList , Long id) {
		StringBuilder sb = DBUtilities.prepareForSelectFullAccountDetails(parametersList);
		sb.append(" where a.id =  ?");
		Account account = null ;
		try {
			 Address adresseClient,adresseCounter = null;
			 Person client,counter = null;
			 Card card = null;
			 PreparedStatement myStmt = DBConnection.getPreparedStatement(sb.toString());
			   myStmt.setLong(1, id);
			   ResultSet res= myStmt.executeQuery();
			   while (res.next()) {
				    adresseClient = adresseCounter = null;
				    client = counter = null;
				    card = null;
				   
				   if(res.getLong("cl.id")>0) {
				      adresseClient = new Address(res.getLong("cl.address"));
				   
				      client = new Client(res.getInt("cl.cin"),res.getString("cl.email"),res.getString("cl.password")
							,adresseClient,res.getString("cl.nom"),res.getString("cl.prenom"),res.getString("cl.tel"),new Date(res.getTimestamp("cl.dateNaiss").getTime())
							,Enum.valueOf(CivilState.class, res.getString("cl.etatCivil")),Enum.valueOf(Sex.class,res.getString("cl.sex")));
				     client.setId(res.getLong("cl.id"));
				   }
				   if(res.getLong("g.id")>0) {
				     adresseCounter = new Address(res.getLong("g.address"));
				     counter = new Counter(res.getInt("g.cin"),res.getString("g.email"),res.getString("g.password")
							,adresseCounter,res.getString("g.nom"),res.getString("g.prenom"),res.getString("g.tel"),new Date(res.getTimestamp("g.dateNaiss").getTime())
							,Enum.valueOf(CivilState.class, res.getString("g.etatCivil")),Enum.valueOf(Sex.class,res.getString("g.sex")),res.getDouble("g.salaire"),new Date(res.getTimestamp("g.dateEmbauche").getTime()),res.getLong("g.guichet"));
				    counter.setId(res.getLong("g.id"));
				   }
				   if(res.getLong("cd.numero")>0) {
				    card = new Card(res.getLong("cd.numero"),res.getInt("cd.codeInternet"),res.getShort("cd.codeDab"),new Date(res.getTimestamp("cd.valableJusqua").getTime()));
				   }
				   
				  if(res.getString("a.TYPE").equals(Enum.valueOf(TYPE.class,"COURANT").name()) ) {//&& res.getInt("a.estValable")==1
					   account = new CurrentAccount(res.getDouble("a.solde"),new Date(res.getTimestamp("a.dateCreation").getTime()),true
							   ,res.getDouble("a.seuil"),client,counter,card);
					   account.setId(res.getLong("a.id"));
					   account.setAvailable(res.getInt("a.estValable")==1 ? true  : false);
					   account.setClosingDate(new Date(res.getTimestamp("a.dateFermeture").getTime())); 
				  }
				   else if(res.getString("a.TYPE").equals(Enum.valueOf(TYPE.class,"EPARGNE").name()) ) {//&& res.getInt("a.estValable")==1
					   account = new SavingAccount(res.getDouble("a.solde"),new Date(res.getTimestamp("a.dateCreation").getTime()),true
							   ,res.getDouble("a.seuil"),client,counter,card);
					   account.setId(res.getLong("a.id"));
					   account.setAvailable(res.getInt("a.estValable")==1 ? true  : false);
					   account.setClosingDate(new Date(res.getTimestamp("a.dateFermeture").getTime()));
				  }
				   else {
					   account = null;
				   }
				   
			   }
		}
		catch (Exception exc) {
			 exc.printStackTrace();
		 }
		finally {
				return account ;
		}
	}
	public static Account InsertAccountSeperatly(Account compte) {
		try {
			PreparedStatement myStmt = DBConnection.getPreparedStatementWithReturnedKey("insert into carte (codeInternet,codeDab,valableJusqua) values (?,?,?)");
			myStmt.setLong(1,compte.getCard().getInternetCode());
			myStmt.setLong(2,compte.getCard().getDabCode());
			myStmt.setTimestamp(3,new java.sql.Timestamp(compte.getCard().getExpirigDate().getTime()));
			int resCard = myStmt.executeUpdate();
			long cardId= DBConnection.getKey(myStmt);
			PreparedStatement myStmt1 = DBConnection.getPreparedStatementWithReturnedKey("insert into compte (solde,dateCreation,seuil,estValable,TYPE,client,guichetier,carte,dateFermeture) values (?,?,?,?,?,?,?,?,?)");
			myStmt1.setDouble(1,compte.getMoney());
			myStmt1.setTimestamp(2,new java.sql.Timestamp(new java.util.Date().getTime()));
			myStmt1.setDouble(3,compte.getThreshold());
			myStmt1.setInt(4,1);
			if(compte instanceof CurrentAccount) {
				myStmt1.setString(5,Enum.valueOf(TYPE.class,"COURANT").name());
			}
			else {
				myStmt1.setString(5,Enum.valueOf(TYPE.class,"EPARGNE").name());
			}
			myStmt1.setLong(6,compte.getOwner().getId());
			myStmt1.setLong(7,compte.getCreatedBy().getId());
			myStmt1.setDouble(8,cardId);
			myStmt1.setTimestamp(9, new java.sql.Timestamp(compte.getClosingDate().getTime()));
			int resAccount = myStmt1.executeUpdate();
			long compteId = DBConnection.getKey(myStmt1);
			if (resCard > 0 &&  resAccount > 0 ) {
				compte.getCard().setConfCode(cardId);
				compte.setId(compteId);
				compte.getOwner().getAccounts().add(compte);
				return compte;
			}
			else 
				return null ;
		}catch (Exception exc) {
			 exc.printStackTrace();
			 return null ;
		}
	}
	public static boolean deleteAccount(long id) {
		try {
			PreparedStatement myStmt= DBConnection.getPreparedStatement("delete from compte where id=?");
			myStmt.setLong(1,id);
			int res = myStmt.executeUpdate();
			if (res > 0) 
				return true ;
			else return false ;
		}
		catch (Exception exc) {
			 exc.printStackTrace();
			 return false ;
		 }
	}
	public static List<Account> getAllAccountsForOneClient(Long clientId) {
		LinkedList<Account> L1 = new LinkedList<Account>();
		StringBuilder sb = DBUtilities.prepareForSelectFullAccountDetails("*");
		sb.append(" where a.client =  ?");
		try {
			PreparedStatement myStmt = DBConnection.getPreparedStatement(sb.toString());
			   myStmt.setLong(1, clientId);
			   ResultSet res= myStmt.executeQuery();
			   while (res.next()) {
				   Account account = null ;
				   
				   Person client = new Client();
				   client.setId(res.getLong("cl.id"));
				   client.setFirstname(res.getString("cl.nom"));
				   client.setLastname(res.getString("cl.prenom"));
				   //client.setId(res.getLong("cl.id"));
		
				   Person counter = new Counter();
				   counter.setFirstname(res.getString("g.nom"));
				   counter.setLastname(res.getString("g.prenom"));
				   		   
				   if(res.getString("a.TYPE").equals(Enum.valueOf(TYPE.class,"COURANT").name()) && res.getInt("a.estValable")==1) {
		
					   account = new CurrentAccount();
					   account.setAvailable(true);
					   account.setCreatedBy(counter);
					   account.setOwner(client);
					   account.setMoney(res.getDouble("a.solde"));
					   account.setThreshold(res.getDouble("a.seuil"));
					   account.setOpeningDate(new Date(res.getTimestamp("a.dateCreation").getTime()));
					   //account.setId(res.getLong("a.id"));
					   account.setClosingDate(new Date(res.getTimestamp("a.dateFermeture").getTime()));
					   
				   }
				   else if(res.getString("a.TYPE").equals(Enum.valueOf(TYPE.class,"EPARGNE").name()) && res.getInt("a.estValable")==1) {
					   
					   account = new SavingAccount();
					   account.setAvailable(true);
					   account.setCreatedBy(counter);
					   account.setOwner(client);
					   account.setMoney(res.getDouble("a.solde"));
					   account.setThreshold(res.getDouble("a.seuil"));
					   account.setOpeningDate(new Date(res.getTimestamp("a.dateCreation").getTime()));
					   //account.setId(res.getLong("a.id"));
					   account.setClosingDate(new Date(res.getTimestamp("a.dateFermeture").getTime()));
				   }
				   else {
					   account = null;
				   }
				   L1.add(account);
			   }
			  return L1; 
		}
		catch (Exception exc) {
			 exc.printStackTrace();
		 }
			return null;
	}
	
	
	public static List<Account> getAllAccounts() {
		LinkedList<Account> L1 = new LinkedList<Account>();
		StringBuilder sb = DBUtilities.prepareForSelectFullAccountDetails("*");
		
		try {
			  PreparedStatement myStmt = DBConnection.getPreparedStatement(sb.toString());
			   ResultSet res= myStmt.executeQuery();
			   while (res.next()) {
				   Account account = null ;
				   
				   Person client = new Client();
				   client.setId(res.getLong("cl.id"));
				   client.setFirstname(res.getString("cl.nom"));
				   client.setLastname(res.getString("cl.prenom"));
				   //client.setId(res.getLong("cl.id"));
		
				   Person counter = new Counter();
				   counter.setFirstname(res.getString("g.nom"));
				   counter.setLastname(res.getString("g.prenom"));
				   		   
				   if(res.getString("a.TYPE").equals(Enum.valueOf(TYPE.class,"COURANT").name()) && res.getInt("a.estValable")==1) {
		
					   account = new CurrentAccount();
					   account.setId(res.getLong("a.id"));
					   account.setAvailable(true);
					   account.setCreatedBy(counter);
					   account.setOwner(client);
					   account.setMoney(res.getDouble("a.solde"));
					   account.setThreshold(res.getDouble("a.seuil"));
					   account.setOpeningDate(new Date(res.getTimestamp("a.dateCreation").getTime()));
					   //account.setId(res.getLong("a.id"));
					   account.setClosingDate(new Date(res.getTimestamp("a.dateFermeture").getTime()));
					   
				   }
				   else if(res.getString("a.TYPE").equals(Enum.valueOf(TYPE.class,"EPARGNE").name()) && res.getInt("a.estValable")==1) {
					   
					   account = new SavingAccount();
					   account.setId(res.getLong("a.id"));
					   account.setAvailable(true);
					   account.setCreatedBy(counter);
					   account.setOwner(client);
					   account.setMoney(res.getDouble("a.solde"));
					   account.setThreshold(res.getDouble("a.seuil"));
					   account.setOpeningDate(new Date(res.getTimestamp("a.dateCreation").getTime()));
					   //account.setId(res.getLong("a.id"));
					   account.setClosingDate(new Date(res.getTimestamp("a.dateFermeture").getTime()));
				   }
				   else {
					   account = null;
				   }
				   L1.add(account);
			   }
			  return L1; 
		}
		catch (Exception exc) {
			 exc.printStackTrace();
		 }
			return null;
	}
	
	
	@SuppressWarnings("finally")
	public static Account getClientAccountById(String parametersList,Long clientId , Long id) {
		StringBuilder sb = DBUtilities.prepareForSelectFullAccountDetails(parametersList);
		sb.append(" where a.id =  ? AND client = ?");
		Account account = null ;
		try {
			 Address adresseClient,adresseCounter = null;
			 Person client,counter = null;
			 Card card = null;
			 PreparedStatement myStmt = DBConnection.getPreparedStatement(sb.toString());
			   myStmt.setLong(1, id);
			   myStmt.setLong(2, clientId);
			   ResultSet res= myStmt.executeQuery();
			   while (res.next()) {
				    adresseClient = adresseCounter = null;
				    client = counter = null;
				    card = null;
				   
				   if(res.getLong("cl.id")>0) {
				      adresseClient = new Address(res.getLong("cl.address"));
				   
				      client = new Client(res.getInt("cl.cin"),res.getString("cl.email"),res.getString("cl.password")
							,adresseClient,res.getString("cl.nom"),res.getString("cl.prenom"),res.getString("cl.tel"),new Date(res.getTimestamp("cl.dateNaiss").getTime())
							,Enum.valueOf(CivilState.class, res.getString("cl.etatCivil")),Enum.valueOf(Sex.class,res.getString("cl.sex")));
				     client.setId(res.getLong("cl.id"));
				   }
				   if(res.getLong("g.id")>0) {
				     adresseCounter = new Address(res.getLong("g.address"));
				     counter = new Counter(res.getInt("g.cin"),res.getString("g.email"),res.getString("g.password")
							,adresseCounter,res.getString("g.nom"),res.getString("g.prenom"),res.getString("g.tel"),new Date(res.getTimestamp("g.dateNaiss").getTime())
							,Enum.valueOf(CivilState.class, res.getString("g.etatCivil")),Enum.valueOf(Sex.class,res.getString("g.sex")),res.getDouble("g.salaire"),new Date(res.getTimestamp("g.dateEmbauche").getTime()),res.getLong("g.guichet"));
				    counter.setId(res.getLong("g.id"));
				   }
				   if(res.getLong("cd.numero")>0) {
				    card = new Card(res.getLong("cd.numero"),res.getInt("cd.codeInternet"),res.getShort("cd.codeDab"),new Date(res.getTimestamp("cd.valableJusqua").getTime()));
				   }
				   
				  if(res.getString("a.TYPE").equals(Enum.valueOf(TYPE.class,"COURANT").name()) ) {//&& res.getInt("a.estValable")==1
					   account = new CurrentAccount(res.getDouble("a.solde"),new Date(res.getTimestamp("a.dateCreation").getTime()),true
							   ,res.getDouble("a.seuil"),client,counter,card);
					   account.setId(res.getLong("a.id"));
					   account.setAvailable(res.getInt("a.estValable")==1 ? true  : false);
					   account.setClosingDate(new Date(res.getTimestamp("a.dateFermeture").getTime())); 
				  }
				   else if(res.getString("a.TYPE").equals(Enum.valueOf(TYPE.class,"EPARGNE").name()) ) {//&& res.getInt("a.estValable")==1
					   account = new SavingAccount(res.getDouble("a.solde"),new Date(res.getTimestamp("a.dateCreation").getTime()),true
							   ,res.getDouble("a.seuil"),client,counter,card);
					   account.setId(res.getLong("a.id"));
					   account.setAvailable(res.getInt("a.estValable")==1 ? true  : false);
					   account.setClosingDate(new Date(res.getTimestamp("a.dateFermeture").getTime()));
				  }
				   else {
					   account = null;
				   }
				   
			   }
		}
		catch (Exception exc) {
			 exc.printStackTrace();
		 }
		finally {
				return account ;
		}
	}
	
	
}