package com.bridgelabz.fundoo_notes.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgelabz.fundoo_notes.Entity.NoteInformation;
import com.bridgelabz.fundoo_notes.responses.Response;
import com.bridgelabz.fundoo_notes.services.CollaboratorService;

@RestController
@RequestMapping("/collaborator")
public class Collaborator {
	
	@Autowired
	private CollaboratorService service;
	
	@PostMapping("/addCollab")
	public ResponseEntity<Response> addCollaborator(@RequestParam("noteId") Long noteId,@RequestParam("email") String email,
			@RequestHeader("token") String token) {
		//System.out.println(noteId);
		NoteInformation note=service.addCollaborator(noteId, email, token);
		//System.out.println(note.getCollabList().toString());
		
	

		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("collaborator added", 200, note));

	}
	@GetMapping("/fetchColabNote")
	public ResponseEntity<Response> getAllNotes(@RequestHeader("token") String token) throws JWTVerificationException, IllegalArgumentException, UnsupportedEncodingException {

		List<NoteInformation> notes = service.getColabNotes(token);
System.out.println(notes);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("The notes are", 200, notes));
	}

	@DeleteMapping("/removeCollab")
	public ResponseEntity<Response> removeCollaborator(@RequestParam("noteId") Long noteId,@RequestParam("email") String email,
			@RequestHeader("token") String token) {
		//System.out.println(noteId);
		NoteInformation note=service.removeCollaborator(noteId, email, token);
		//System.out.println(note.getCollabList().toString());
	

		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("collaborator removed", 200, note));

	}

}
