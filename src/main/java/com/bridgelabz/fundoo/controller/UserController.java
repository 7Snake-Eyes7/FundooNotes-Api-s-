package com.bridgelabz.fundoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.Entity.LoginInformation;
import com.bridgelabz.fundoo.Entity.PasswordUpdate;
import com.bridgelabz.fundoo.Entity.UserDto;
import com.bridgelabz.fundoo.Entity.UserInformation;
import com.bridgelabz.fundoo.responses.Response;
import com.bridgelabz.fundoo.responses.UsersDetail;
import com.bridgelabz.fundoo.services.Services;
import com.bridgelabz.fundoo.util.JwtGenerator;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private Services service;
	
	@Autowired
	private JwtGenerator generate;
	
	@PostMapping("/user/registration")
	@CachePut(value="user", key="#token")
//	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<Response> registration(@RequestBody UserDto information) {

		boolean result = service.register(information);
		if (result) {
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("registration successfull", 200, information));

		} else {
                   
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Response("user already exist", 400, information));

		}

	}

	@PostMapping("/user/login")
	public ResponseEntity<UsersDetail> login(@RequestBody LoginInformation information) {

		UserInformation userInformation = service.login(information);
		System.out.println("inside login controler");
		if (userInformation!=null) {
			String token=generate.jwtToken(userInformation.getUserId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("login successfull", information.getUsername())
					.body(new UsersDetail(token, 200, information));

		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UsersDetail("Login failed", 400, information));
		}

	}

	@GetMapping("/user/verify/{token}")
	public ResponseEntity<Response> userVerification(@PathVariable("token") String token) throws Exception {

		System.out.println("token for verification" + token);
		boolean update = service.verify(token);
		if (update) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified", 200));
		} else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("not verified", 400));

		}

	}

	@PostMapping("user/forgotpassword")
	public ResponseEntity<Response> forgogPassword(@RequestParam("email") String email) {
		System.out.println(email);

		boolean result = service.isUserExist(email);
		if (result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("user exist", 200));
		} else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("user does not exist with given email id", 400));
		}

	}

	@PutMapping("user/update/{token}")
	public ResponseEntity<Response> update(@PathVariable("token") String token, @RequestBody PasswordUpdate update) {
		System.out.println("inside controller  " +update.getConfirmPassword());
		System.out.println("inside controller  " +token);
		boolean result = service.update(update, token);
		if (result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("password updated successfully", 200, update));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Response("password doesn't match", 401, update));

		}

	}
	
	@GetMapping("user/getusers")
	@Cacheable(value="users")
	public ResponseEntity<Response> getUsers(){
		System.out.println("inside get users contr...");
		List<UserInformation> users=service.getUsers();
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response("The user's are", 200, users));
		
	}
	
	
	@GetMapping("user/getOneUser")
	public ResponseEntity<Response> getOneUsers(@RequestHeader("token") String token){
	UserInformation user=service.getSingleUser(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response("user is", 200, user));
		
	}
	

}
