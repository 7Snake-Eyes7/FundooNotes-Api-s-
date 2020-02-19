package com.bridgelabz.fundoo.services;

import java.util.List;

import com.bridgelabz.fundoo.Entity.NoteDto;
import com.bridgelabz.fundoo.Entity.NoteInformation;
import com.bridgelabz.fundoo.Entity.NoteUpdation;
import com.bridgelabz.fundoo.Entity.ReminderDto;

public interface NoteService {

	void createNote(NoteDto information, String token);

	void updateNote(NoteUpdation information, String token);

	void deleteNote(long id, String token);

	List<NoteInformation> getAllNotes(String token);

	List<NoteInformation> getTrashedNotes(String token);

	boolean deleteNotePemenetly(long id, String token);

	void archievNote(long id, String token);

	List<NoteInformation> getArchiveNote(String token);

	void addColour(Long noteId, String token, String colour);
	
	void addReminder(Long noteId,String token,ReminderDto reminder);
	
	void removeReminder(Long noteId,String token,ReminderDto reminder);

	void pin(long id, String token);

	List<NoteInformation> searchByTitle(String title);

	List<NoteInformation> getAllPinnedNotes(String token);
	
}
