package com.bridgelabz.fundoo.Entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UserDto {

//	@NotBlank(message="field should not be empty")
//	@Pattern(regexp="[a-zA-Z]*",message="only alphabets are allowed")
	String name;
	
//	@Email
	String email;
	
//	@NotBlank(message="field should not be empty")
//	@Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",message="Enter a valid password")
	String password;
	
//	@NotNull(message="field should not be empty")
	Long mobileNumber;

}
