package bg.VOB.model;

import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.annotation.JsonIgnore;

import util.exceptions.InvalidUserDataException;
import util.validation.Validator;

public class User {

	@JsonIgnore
	private int id;
	private String username;
	private String password;
	private String email;
	private String phoneNumber;
	private int age;
	
	public User(String username, String password, String email,String phoneNumber, int age) throws InvalidUserDataException {
		if(Validator.verifyUsername(username)) {
			this.username = username;
		}else {
			throw new InvalidUserDataException("Not a valid username");
		}
		
		if(Validator.verifyPassword(password)) {
			this.password = BCrypt.hashpw(password,BCrypt.gensalt(5));
		}
		
		if(Validator.verifyEmail(email)) {
			this.email = email;
		}else {
			throw new InvalidUserDataException("Not a valid email");
		}
		
		if(Validator.verifyPhoneNumber(phoneNumber)) {
			this.phoneNumber = phoneNumber;
		}else {
			throw new InvalidUserDataException("Not a valid phone number");
		}
		
		if(age > 0 && age < 120) {
			this.age = age;	
		}else {
			throw new InvalidUserDataException("Not a valid age");
		}
	}
	
	public User(int id, String username, String password, String email,String phoneNumber, int age) throws InvalidUserDataException {
		this(username, password, email, phoneNumber, age);
		if(this.password == null) {
			this.password = password;
		}
		this.id = id;
	}
	
	public User(int id, String username, String email,String phoneNumber, int age) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.age = age;
	}
	
	public User(String username, String email,String phoneNumber, int age) {
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.age = age;
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

	public void setPassword(String password) {
		if(Validator.verifyPassword(password)) {
			this.password = BCrypt.hashpw(password,BCrypt.gensalt(5));
		}
	}

	public void setEmail(String email) {
		if(Validator.verifyEmail(email)) {
			this.email = email;
		}
	}

	public void setPhoneNumber(String phoneNumber) {
		if(Validator.verifyPhoneNumber(phoneNumber)) {
			this.phoneNumber = phoneNumber;
		}
	}
	
}
