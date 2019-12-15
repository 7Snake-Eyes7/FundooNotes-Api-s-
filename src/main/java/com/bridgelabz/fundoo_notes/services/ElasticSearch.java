package com.bridgelabz.fundoo_notes.services;

import java.util.List;

import com.bridgelabz.fundoo_notes.Entity.NoteInformation;

public interface ElasticSearch {
	
	String CreateNote(NoteInformation note);

	String UpdateNote(NoteInformation note);

	String DeleteNote(NoteInformation note);

	List<NoteInformation> searchbytitle(String title);

}
