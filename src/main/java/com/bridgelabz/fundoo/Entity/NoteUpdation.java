package com.bridgelabz.fundoo.Entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoteUpdation {
	
	private long id;
	
	private String title;
	
	private String description;
	
	private boolean isArchieved;
	
	private boolean isPinned;
	
	private boolean isTrashed;
	
	private LocalDateTime createdDateAndTime;
	
	private LocalDateTime upDateAndTime;

}
