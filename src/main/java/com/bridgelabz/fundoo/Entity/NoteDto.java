package com.bridgelabz.fundoo.Entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class NoteDto {
	
//	@NotBlank(message="field should not be empty")
//	@Pattern(regexp="[a-zA-Z]*",message="only alphabets are allowed")
	String title;
	
//	@NotBlank(message="field should not be empty")
//	@Pattern(regexp="[a-zA-Z]*",message="only alphabets are allowed")
	String description;

}
