package com.bridgelabz.fundoo.responses;

import com.bridgelabz.fundoo.Entity.NoteDto;

import lombok.Data;

@Data
public class NoteResponse {
	
	private NoteDto note;
	
	public  NoteResponse(NoteDto note) {
		this.note=note;
	
	}

}
