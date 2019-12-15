package com.bridgelabz.fundoo_notes.services;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgelabz.fundoo_notes.Entity.NoteInformation;
import com.bridgelabz.fundoo_notes.Entity.UserInformation;
import com.bridgelabz.fundoo_notes.exception.UserException;
import com.bridgelabz.fundoo_notes.repository.IUserRepository;
import com.bridgelabz.fundoo_notes.repository.NoteRepository;
import com.bridgelabz.fundoo_notes.util.JwtGenerator;

@Service
@Transactional
public class CollaboratorServiceImplementation implements CollaboratorService{

	@Autowired
	private JwtGenerator tokenGenerator;
	
	@Autowired
	private UserInformation userInformation;

	@Autowired
	private IUserRepository repository;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Override
	public NoteInformation addCollaborator(Long noteId, String email, String  token) {

		UserInformation user;
		UserInformation  collaborator = repository.getUser(email);
		try {
			System.out.println("in service");
			Long userid = (long) tokenGenerator.parseJWT(token);
			System.out.println("inside note service" + userid);

			user = repository.getUserById(userid);
		}
		
		catch(Exception e) {
			throw new UserException("user is not present with the given id ");
		}
		if(user !=null) {
			if(collaborator !=null) {
		
				NoteInformation note=noteRepository.findById(noteId);
				//note.getCollabList().add(collaborator);
				//user.getColaborateNote().add(note);
				//noteRepository.save(note);
				collaborator.getColaborateNote().add(note);
//				repository.save(collaborator);
//				noteRepository.save(note);
				return note;
			}
			else {
				throw new UserException("user is not present with the given id ");
			}
		}
		else {
			throw new UserException("collaborator does not exist ");
		}
		
		
	}
	@Override
	public List<NoteInformation> getColabNotes(String token) throws JWTVerificationException, IllegalArgumentException, UnsupportedEncodingException{
		
		Long userid = (long) tokenGenerator.parseJWT(token);
		UserInformation	user = repository.getUserById(userid);
		List<NoteInformation> notes=user.getColaborateNote();
		return notes;
		
		
	}
	@Override
	public NoteInformation removeCollaborator(Long noteId, String email, String  token) {
		UserInformation user;
		UserInformation  collaborator = repository.getUser(email);
		try {
			System.out.println("in service");
			Long userid = (long) tokenGenerator.parseJWT(token);
			System.out.println("inside note service" + userid);

			user = repository.getUserById(userid);
		}
		
		catch(Exception e) {
			throw new UserException("user is not present with the given id ");
		}
		NoteInformation note=noteRepository.findById(noteId);
		note.getColabUser().remove(collaborator);

//		UserInformation user;
//		UserInformation  collaborator = repository.getUser(email);
//		try {
//			System.out.println("in service");
//			Long userid = (long) tokenGenerator.parseJWT(token);
//			System.out.println("inside note service" + userid);
//
//			user = repository.getUserById(userid);
//		}
//		
//		catch(Exception e) {
//			throw new UserException("user is not present with the given id ");
//		}
//		if(user !=null) {
//			if(collaborator !=null) {
//				NoteInformation note=noteRepository.findById(noteId);
//				note.getCollabList().remove(collaborator);
//				noteRepository.save(note);
//				return note;
//			}
//			else {
//				throw new UserException("user is not present with the given id ");
//			}
//		}
//		else {
//			throw new UserException("collorator not exist ");
//		}
//		
	return null;
	}


}
