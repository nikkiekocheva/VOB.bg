package util.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bg.VOB.model.User;

public class ObjectToJSON {

	public static String convertUserToJson(User user) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(user);
			
		} catch (JsonProcessingException e) {
			System.out.println("JSON not good!");
			e.printStackTrace();
		}
		
		return jsonInString;
	}
	
}
