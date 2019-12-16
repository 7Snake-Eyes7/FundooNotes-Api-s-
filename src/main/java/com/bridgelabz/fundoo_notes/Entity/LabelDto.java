package com.bridgelabz.fundoo_notes.Entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class LabelDto {
	
//	@NotBlank(message="field should not be empty")
//	@Pattern(regexp="[a-zA-Z]*",message="only alphabets are allowed")
	private String name;

}
