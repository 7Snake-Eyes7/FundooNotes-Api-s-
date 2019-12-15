package com.bridgelabz.fundoo_notes.Entity;

import lombok.Data;

@Data
public class PasswordUpdate {
	
	String email;
	
	String newPassword;
	
	String confirmPassword;

}
