package com.bridgelabz.fundoo.services;

import java.util.List;

import com.bridgelabz.fundoo.Entity.LoginInformation;
import com.bridgelabz.fundoo.Entity.PasswordUpdate;
import com.bridgelabz.fundoo.Entity.UserDto;
import com.bridgelabz.fundoo.Entity.UserInformation;

public interface Services {

	UserInformation login(LoginInformation information);

	boolean register(UserDto ionformation);
	
	

	boolean verify(String token) throws Exception;
	
	boolean isUserExist(String email);

	boolean update(PasswordUpdate information, String token);

	List<UserInformation> getUsers();

	UserInformation getSingleUser(String token);

	

}
