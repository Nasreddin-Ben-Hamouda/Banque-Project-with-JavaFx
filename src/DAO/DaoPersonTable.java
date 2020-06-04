package DAO;
import DBConnection.DBConnection;
import entities.Client;
import entities.Counter;
import entities.CurrentAccount;
import entities.Operation;
import entities.Account;
import entities.Address;
import entities.Person;
import entities.SavingAccount;
import entities_enums.*;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
public class DaoPersonTable {

	@SuppressWarnings("finally")
		public static Person Authentificate(String email,String pwd) {
			Person p = null;
			try {
				PreparedStatement myStmt = DBConnection.getPreparedStatement("select p.*,a.* from personne p  join adresse a on p.address = a.id where p.email=? and p.password=?");
				myStmt.setString(1,email);
				myStmt.setString(2, pwd);
				ResultSet res=myStmt.executeQuery();
				while (res.next()) {
					Address adresse = new Address(Enum.valueOf(Continent.class, res.getString("continent")),res.getString("pays"),res.getString("gouvernorat"),res.getString("ville")
							   ,res.getString("rue"),res.getInt("codePostal"));
					if(res.getString("TYPE").equals("GUICHETIER")) {				 
						p = new Counter(res.getInt("cin"),res.getString("email"),res.getString("password")
								,adresse,res.getString("nom"),res.getString("prenom"),res.getString("tel"),new Date(res.getTimestamp("dateNaiss").getTime())
								,Enum.valueOf(CivilState.class, res.getString("etatCivil")),Enum.valueOf(Sex.class,res.getString("sex")),res.getDouble("salaire"),new Date(res.getTimestamp("dateEmbauche").getTime()),res.getLong("guichet"));
								p.setId(res.getLong("id"));
					}else {
						p = new Client(res.getInt("cin"),res.getString("email"),res.getString("password")
								,adresse,res.getString("nom"),res.getString("prenom"),res.getString("tel"),new Date(res.getTimestamp("dateNaiss").getTime())
								,Enum.valueOf(CivilState.class, res.getString("etatCivil")),Enum.valueOf(Sex.class,res.getString("sex"))); 
						p.setId(res.getLong("id"));
					}	
				}
			}catch (Exception exc) {
				 exc.printStackTrace();
			 }finally {
				return p;
			}
		}
	
	@SuppressWarnings("finally")
	public static Person getPersonById(String parametersList,Long id)   {
		
		 StringBuilder sb = DBUtilities.prepareForSelectPersonAddress(parametersList);
		 sb.append(" where p.id =  ?");
		 Person p = null;
		 try {
		   PreparedStatement myStmt = DBConnection.getPreparedStatement(sb.toString());
		   myStmt.setLong(1, id);
		   ResultSet res= myStmt.executeQuery();
		   while (res.next()) {
			   Address adresse = new Address(Enum.valueOf(Continent.class, res.getString("continent")),res.getString("pays"),res.getString("gouvernorat"),res.getString("ville")
					   ,res.getString("rue"),res.getInt("codePostal"));
			   adresse.setId(res.getLong("a.id"));
			   if(res.getString("TYPE").equals(Enum.valueOf(TYPE.class,"GUICHETIER").name())) {				 
					 p = new Counter(res.getInt("cin"),res.getString("email"),res.getString("password")
							,adresse,res.getString("nom"),res.getString("prenom"),res.getString("tel"),new Date(res.getTimestamp("dateNaiss").getTime())
							,Enum.valueOf(CivilState.class, res.getString("etatCivil")),Enum.valueOf(Sex.class,res.getString("sex")),res.getDouble("salaire"),new Date(res.getTimestamp("dateEmbauche").getTime()),res.getLong("guichet"));
					 p.setId(res.getLong("id"));
				}
				else {
					p = new Client(res.getInt("cin"),res.getString("email"),res.getString("password")
							,adresse,res.getString("nom"),res.getString("prenom"),res.getString("tel"),new Date(res.getTimestamp("dateNaiss").getTime())
							,Enum.valueOf(CivilState.class, res.getString("etatCivil")),Enum.valueOf(Sex.class,res.getString("sex"))); 
					p.setId(res.getLong("id"));
				}	
		 }
		 }
		 catch(SQLException e) {
			 e.printStackTrace();
			 return null ;
		 }
		 finally {
				return p;
			}
	}
	
	
	@SuppressWarnings("finally")
	public static Person updatePersonDatabase(Person person) {
		PreparedStatement myUpdateStmt = null ;
		PreparedStatement myUpdateStmtforadresse = null ;
		Person returnedPerson =null;
		try {
			if (person instanceof Client) {
			 Client C = (Client)person;
			 myUpdateStmt =  DBConnection.getPreparedStatement("update personne set email= ?,password= ?,cin= ?,nom= ?,prenom= ?,dateNaiss=?,"
						+ "tel= ?,sex= ?,etatCivil= ? where id=?");
			 myUpdateStmt.setString(1,C.getEmail());
			 myUpdateStmt.setString(2,C.getPassword());
			 myUpdateStmt.setInt(3,C.getCin());
			 myUpdateStmt.setString(4,C.getLastname());
			 myUpdateStmt.setString(5,C.getFirstname());
			 myUpdateStmt.setTimestamp(6,new java.sql.Timestamp(C.getBirthdate().getTime()));
			 myUpdateStmt.setString(7,C.getTel());
			 myUpdateStmt.setString(8,C.getSex().name());
			 myUpdateStmt.setString(9,C.getCivilState().name());
			 myUpdateStmt.setLong(10,C.getId());
			 int res = myUpdateStmt.executeUpdate();
			 myUpdateStmtforadresse=DBConnection.getPreparedStatement("update adresse set continent= ?,pays= ?,gouvernorat= ?,ville= ?,rue= ?,codePostal=? where id=?");
			 myUpdateStmtforadresse.setString(1, C.getAddress().getContinent().name());
			 myUpdateStmtforadresse.setString(2, C.getAddress().getCountry());
			 myUpdateStmtforadresse.setString(3, C.getAddress().getState());
			 myUpdateStmtforadresse.setString(4, C.getAddress().getCity());
			 myUpdateStmtforadresse.setString(5, C.getAddress().getStreet());
			 myUpdateStmtforadresse.setInt(6, C.getAddress().getZipCode());
			 myUpdateStmtforadresse.setLong(7, C.getAddress().getId());
			 int resAd = myUpdateStmtforadresse.executeUpdate();
			 if(res>0 && resAd>0) {
				 returnedPerson=C;
			 }
			}
			else {
				Counter G=(Counter)person ;
				System.out.println(G.getAddress().getId());
				myUpdateStmt =  DBConnection.getPreparedStatement("update personne set email= ?,password= ?,cin= ?,nom= ?,prenom= ?,dateNaiss=?,"
						+ "tel= ?,sex= ?,etatCivil= ?,salaire=?,dateEmbauche=? where id=?");
				myUpdateStmt.setString(1,G.getEmail());
				 myUpdateStmt.setString(2,G.getPassword());
				 myUpdateStmt.setInt(3,G.getCin());
				 myUpdateStmt.setString(4,G.getLastname());
				 myUpdateStmt.setString(5,G.getFirstname());
				 myUpdateStmt.setTimestamp(6,new java.sql.Timestamp(G.getBirthdate().getTime()));
				 myUpdateStmt.setString(7,G.getTel());
				 myUpdateStmt.setString(8,G.getSex().name());
				 myUpdateStmt.setString(9,G.getCivilState().name());
				 myUpdateStmt.setDouble(10,G.getSalary());
				 myUpdateStmt.setTimestamp(11,new java.sql.Timestamp(G.getHiringDate().getTime()));
				 myUpdateStmt.setLong(12,G.getId());
				 int res = myUpdateStmt.executeUpdate();
				 myUpdateStmtforadresse=DBConnection.getPreparedStatement("update adresse set continent= ?,pays= ?,gouvernorat= ?,ville= ?,rue= ?,codePostal=? where id=?");
				 myUpdateStmtforadresse.setString(1, G.getAddress().getContinent().name());
				 myUpdateStmtforadresse.setString(2, G.getAddress().getCountry());
				 myUpdateStmtforadresse.setString(3, G.getAddress().getState());
				 myUpdateStmtforadresse.setString(4, G.getAddress().getCity());
				 myUpdateStmtforadresse.setString(5, G.getAddress().getStreet());
				 myUpdateStmtforadresse.setInt(6, G.getAddress().getZipCode());
				 myUpdateStmtforadresse.setLong(7, G.getAddress().getId());
				 int resAd = myUpdateStmtforadresse.executeUpdate();
				 if(res>0 && resAd>0) {
					returnedPerson=G;
				 }
			}
		}
		catch (Exception exc) {
			 exc.printStackTrace();
			 return null;
		 }	
		finally {
			return returnedPerson;
		}
	}
	public static List<Operation> getLastThreeOperationForCounter(long idCounter) {
		LinkedList<Operation> L1 = new LinkedList<Operation>();
		try {
			PreparedStatement myStmt1 = DBConnection.getPreparedStatement("select op.*,cs.* from operation op join compte cs on op.compteSource = cs.id"
					+ " where op.createur=? ORDER BY op.dateExec DESC LIMIT 3 ");
			myStmt1.setLong(1,idCounter);
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

	public static Counter insertCounter(Counter c,long counterId) {
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
			
			//Adding counter to database
			PreparedStatement myStmt2 = DBConnection.getPreparedStatementWithReturnedKey("insert into personne (cin,email,password,nom,address,prenom,dateNaiss,TYPE,tel,sex,etatCivil,salaire,dateEmbauche) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
			myStmt2.setInt(1,c.getCin());
			myStmt2.setString(2, c.getEmail());
			myStmt2.setString(3,c.getPassword());
			myStmt2.setString(4,c.getLastname());
			myStmt2.setLong(5,addresseId);
			myStmt2.setString(6,c.getFirstname());
			myStmt2.setTimestamp(7,new java.sql.Timestamp(c.getBirthdate().getTime()));
			myStmt2.setString(8,Enum.valueOf(TYPE.class,"GUICHETIER").name());
			myStmt2.setString(9,c.getTel());
			myStmt2.setString(10,c.getSex().name());
			myStmt2.setString(11,c.getCivilState().name());
			myStmt2.setDouble(12,c.getSalary());
			myStmt2.setTimestamp(13,new java.sql.Timestamp(c.getHiringDate().getTime()));

			int res2 = myStmt2.executeUpdate();			
		
			if(res1>0 && res2>0) {
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
	public static List<Counter> getAllGuichetier() {
		LinkedList<Counter> L1 = new LinkedList<Counter>();
		try {
			PreparedStatement myStmt = DBConnection.getPreparedStatement("select p.*,a.* from personne p join adresse a on a.id=p.address where TYPE=?");
			myStmt.setString(1, "GUICHETIER");
			ResultSet res = myStmt.executeQuery();
			while(res.next()) {
				Address adresse = new Address(Enum.valueOf(Continent.class, res.getString("a.continent")),res.getString("a.pays"),res.getString("a.gouvernorat"),res.getString("a.ville")
						   ,res.getString("a.rue"),res.getInt("a.codePostal"));
				adresse.setId(res.getLong("a.id"));
				Counter C =new Counter(res.getInt("p.cin"),res.getString("p.email"),res.getString("p.password")
						,adresse,res.getString("p.nom"),res.getString("p.prenom"),res.getString("p.tel"),new Date(res.getTimestamp("p.dateNaiss").getTime())
						,Enum.valueOf(CivilState.class, res.getString("p.etatCivil")),Enum.valueOf(Sex.class,res.getString("p.sex")),res.getDouble("p.salaire"),new Date(res.getTimestamp("p.dateEmbauche").getTime()),res.getLong("p.guichet"));
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
