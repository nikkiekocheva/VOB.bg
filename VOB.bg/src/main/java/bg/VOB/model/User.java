package bg.VOB.model;

import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import util.validation.Validator;

public class User {

	private int id;
	private String username;
	private String password;
	private String email;
	private String phoneNumber;
	private int age;
	
	public User(String username, String password, String email,String phoneNumber, int age) {
		if(Validator.verifyUsername(username)) {
			this.username = username;
		}
		
		if(Validator.verifyPassword(password)) {
			this.password = BCrypt.hashpw(password,BCrypt.gensalt(5));
		}
		
		if(Validator.verifyEmail(email)) {
			this.email = email;
		}
		
		if(Validator.verifyPhoneNumber(phoneNumber)) {
			this.phoneNumber = phoneNumber;
		}
		
		if(age > 0 && age < 120) {
			this.age = age;	
		}
	}
	
	public User(int id, String username, String password, String email,String phoneNumber, int age) {
		this(username, password, email, phoneNumber, age);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getAge() {
		return age;
	}
	
}
