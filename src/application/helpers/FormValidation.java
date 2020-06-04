package application.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidation {
   public static boolean isPhoneNbr(String tel) {
	   String regex = "^\\+(?:[0-9] ?){6,14}[0-9]$";
	   
	   Pattern pattern = Pattern.compile(regex);
	   Matcher matcher = pattern.matcher(tel);
	   return  matcher.matches();
   }
   
   public static boolean isEmail(String email) {
	   //String regex = "^[\\\\w-\\\\+]+(\\\\.[\\\\w]+)*@[\\\\w-]+(\\\\.[\\\\w]+)*(\\\\.[a-z]{2,})$";
	   String regex =	"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	   Pattern pattern = Pattern.compile(regex);
	   Matcher matcher = pattern.matcher(email);
	   return  matcher.matches();
   }
}
