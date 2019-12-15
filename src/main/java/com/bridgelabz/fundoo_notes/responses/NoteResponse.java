package com.bridgelabz.fundoo_notes.responses;

import com.bridgelabz.fundoo_notes.Entity.NoteDto;
import lombok.Data;

@Data
public class NoteResponse {
	
	private NoteDto note;
	
	public  NoteResponse(NoteDto note) {
		this.note=note;
	
	}

}
