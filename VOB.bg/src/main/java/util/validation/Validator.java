package util.validation;

import java.util.Random;

public class Validator {
	
	private Validator() {
	}
	
	public static final int getRandomNum(int max) {
		Random r = new Random();
		return r.nextInt(max);
	}

	public static final int getRandomNum(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min) + min;
	}
	
	private static boolean validation(String input) {
		return input != null && !input.trim().isEmpty();
	}
	
	public static boolean verifyUsername(String username){
		//Check if username is a valid string
	   if(!validation(username)) {
		   return false;
	   }
	    //Check and return boolean if the input username matches the pattern    
	    return username.matches("[A-Za-z0-9_]+");
	}
	
	public static boolean verifyPassword(String password){
		//Check if password is a valid string
		if(!validation(password)) {
			return false;
	    }

		//This is a complex pattern for password   ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$ 
		
		//Check and return boolean if the input password matches the pattern 
	    return password.matches("[A-Za-z0-9_]+");
	}
	
	public static boolean verifyEmail(String email){
		//Check if email is a valid string
		if(!validation(email)) {
	    	 return false;
	    }
	      
		//Check if email matches the pattern
	    if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
	    	 return false;
	    }   
	    
	    return true;
	}
	
	
	public static boolean verifyPhoneNumber(String number){
		//Check if phone number is a valid string
		if(!validation(number)) {
			return false;
	    }
		//Check if phone number is 10 digits long and matches the pattern
	    if(number.length() != 10 || !number.matches("[0-9]+")) {
			return false;
	    }
	        
	    return true;
	}
	
	public static boolean verifyCommentContent(String content) {
		if(content.isEmpty() || content.length() > 1000) {
			return false;
		}
		return true;
	}
		
	public static boolean verifyVideoDescription(String desc) {
		if(desc.isEmpty() || desc.length() > 500) {
			return false;
		}
		return true;
	}
	
	public static int generateRegisterCode() {
		return new Random().nextInt(8888) + 1000;
	}
}
