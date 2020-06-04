package DAO;



public class DBUtilities {
	public static StringBuilder prepareForSelectFullAccountDetails(String parametersList) {
		StringBuilder sb= new StringBuilder();
		if(parametersList.equals("*")) {
			sb.append("select a.*,cl.*,g.*,cd.* from compte a left join personne cl on a.client = cl.id"
					+ " left join personne g on a.guichetier =g.id"
					+ " left join carte cd on a.carte=cd.numero");
			
		}
		else {
			sb.append("select ");
			String [] tmp = parametersList.split(",");
			for(String s : tmp) {
				sb.append("a."+s+",");
			}
			sb.append("cl.*,");
			sb.append("g.*,");
			sb.append("cd.*");
			sb.append(" from compte a left join personne cl on a.client = cl.id left join personne g on a.guichetier =g.id left join carte cd on a.carte=cd.numero");
		}
		 return sb;
	}
	public static StringBuilder prepareForSelectPersonAddress(String parametersList) {
		StringBuilder sb= new StringBuilder();
		if(parametersList.equals("*")) {
			sb.append("select p.*,a.* from personne p join adresse a on p.address = a.id");
			
		}
		else {
			sb.append("select ");
			String [] tmp = parametersList.split(",");
			for(String s : tmp) {
				sb.append("p."+s+",");
			}
			sb.append("a.*");
			sb.append(" from personne p join adresse a on p.address = a.id");
		}
		 return sb;
	}
	
	public static StringBuilder prepareForSelect(String tablename,String parametersList) {
		StringBuilder sb= new StringBuilder();
		if(parametersList.equals("*")) {
			sb.append("select * from "+ tablename);
			
		}else {
			sb.append("select ");
			String [] tmp = parametersList.split(",");
			for(String s : tmp) {
				sb.append(s+",");
			}
			sb.replace(sb.length()-1, sb.length(), "");
			sb.append(" from "+tablename);
		}
		 return sb;
	}
	
}