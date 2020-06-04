package application.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class Utilities {
   public static List<String> getCountries(String fileLocation) {
	   List<String> res = new LinkedList<>();
	   BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File(fileLocation)));
			String line = reader.readLine();
			while (line != null) {
				res.add(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   return res;
   }
   
   public Long[] getCodesFromPropsFile() {
	   InputStream is = getClass().getResourceAsStream("/config.properties");
	   OutputStream os;
	   
	   Properties props = new Properties();
	   Long ret[] = new Long[2];
	   try {
		  props.load(is);
		  ret[0] =  Long.valueOf(props.getProperty("INT_CODE"));
		  ret[1] =  Long.valueOf(props.getProperty("DAB_CODE"));
		  props.setProperty("INT_CODE", String.valueOf(ret[0]+1));
		  props.setProperty("DAB_CODE", String.valueOf(ret[1]+1));
		  try {
			os = new FileOutputStream(new File("src/config.properties"));
			props.store(os,"");
		 } catch (FileNotFoundException e1) {
			e1.printStackTrace();
		 }
		return ret;
	   } catch (IOException e) {
	      e.printStackTrace();
	   }
	   return null;
   }
   
   public static String getRandomPwd() {
	   String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	   
	   StringBuilder builder = new StringBuilder();
	   int count =8;
	   while (count-- != 0) {
	   int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
	   builder.append(ALPHA_NUMERIC_STRING.charAt(character));
	   }
	   return builder.toString();
   }
   
   public static Date getDateAfterXDays(int nbrDays) {
	   Calendar c = Calendar.getInstance();
  	   c.setTime(new Date());
  	   c.add(Calendar.DATE, nbrDays); 
  	   return c.getTime();
   }
   
}
