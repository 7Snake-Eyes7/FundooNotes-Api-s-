package com.bridgelabz.fundoo_notes.services;

import java.util.List;

import com.bridgelabz.fundoo_notes.Entity.LabelDto;
import com.bridgelabz.fundoo_notes.Entity.LabelInformation;
import com.bridgelabz.fundoo_notes.Entity.LabelUpdate;
import com.bridgelabz.fundoo_notes.Entity.NoteInformation;

public interface LabelService {
	
	void createLabel(LabelDto label,String token);
	
	void editLabel(LabelUpdate label, String userid);
	
	void deleteLabel(LabelUpdate info, String token);
	
	void addLabel(Long labelId, Long noteId, String token);
	
//	List<LabelInformation> getLabel(Long userId);
	
	List<LabelInformation> getLabel(String token);
	
	List<NoteInformation> getAllNotes(String token,Long labelId);
	
	void removeLabel(Long labelId, Long noteId, String token);
	
	void createLabelAndMap(LabelDto label, String token, Long noteId);

}
