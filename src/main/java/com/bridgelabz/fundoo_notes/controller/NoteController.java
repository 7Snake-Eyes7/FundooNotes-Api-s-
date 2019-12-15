package com.bridgelabz.fundoo_notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo_notes.Entity.NoteDto;
import com.bridgelabz.fundoo_notes.Entity.NoteInformation;
import com.bridgelabz.fundoo_notes.Entity.NoteUpdation;
import com.bridgelabz.fundoo_notes.Entity.ReminderDto;
import com.bridgelabz.fundoo_notes.responses.Response;
import com.bridgelabz.fundoo_notes.services.NoteService;

@RestController
@RequestMapping
public class NoteController {

	@Autowired
	private NoteService service;

	@PostMapping("/note/create")
	public ResponseEntity<Response> registration(@RequestBody NoteDto information,
			@RequestHeader("token") String token) {
		System.out.println(information.getDescription());
		service.createNote(information, token);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 200, information));

	}

	@PutMapping("/note/update")
	public ResponseEntity<Response> update(@RequestBody NoteUpdation note, @RequestHeader("token") String token) {
		System.out.println("inside update controller" + note.getId());
		service.updateNote(note, token);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("note updated", 200));
	}
	
	@PostMapping("/note/archieve/{id}")
	public ResponseEntity<Response> archieve(@PathVariable long id, @RequestHeader("token") String token) {
		System.out.println("inside delete controller" + id);
		service.archievNote(id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note archieved", 200));

	}
	

	@PostMapping("/note/pin/{id}")
	public ResponseEntity<Response> pin(@PathVariable long id, @RequestHeader("token") String token) {
		System.out.println("inside pin" + id);
		service.pin(id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note pinned", 200));

	}
	
	
	@DeleteMapping("/note/delete/{id}")
	public ResponseEntity<Response> delete(@PathVariable long id, @RequestHeader("token") String token) {
		System.out.println("inside delete controller" + id);
		service.deleteNote(id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note deleted", 200));

	}
	
	@DeleteMapping("/note/deletePermanently/{id}")
	public ResponseEntity<Response> deletePermenently(@PathVariable long id, @RequestHeader("token") String token) {
		System.out.println("inside delete controller" + id);
		service.deleteNotePemenetly(id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note deleted permanently", 200));

	}
	
	
	@GetMapping("/note/fetchNote")
	public ResponseEntity<Response> getAllNotes(@RequestHeader("token") String token) {

		List<NoteInformation> notes = service.getAllNotes(token);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("The notes are", 200, notes));
	}

	@GetMapping("/note/fetchTrashedNote")
	public ResponseEntity<Response> getTrashedNotes(@RequestHeader("token") String token) {
		List<NoteInformation> notes = service.getTrashedNotes(token);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("The trashed notes are", 200, notes));

	}
	
	@GetMapping("/note/fetcharchivenote")
	public ResponseEntity<Response> getArchiveNote(@RequestHeader("token") String token) {
		List<NoteInformation> notes = service.getArchiveNote(token);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("The archieved notes are", 200, notes));

	}
	
	@GetMapping("/note/fetchpinnednote")
	public ResponseEntity<Response> getPinnedNote(@RequestHeader("token") String token) {
		List<NoteInformation> notes = service.getAllPinnedNotes(token);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("The pinned notes are", 200, notes));

	}
	@PostMapping("/note/addColour")
	public ResponseEntity<Response> addColour(@RequestParam("noteId") Long noteId,
			@RequestParam("colour") String colour, @RequestHeader("token") String token) {
		service.addColour(noteId, token, colour);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("colour added", 200, colour));

	}
	
	@PostMapping("/note/addreminder")
	public ResponseEntity<Response> addReminder(@RequestParam("noteId") Long noteId,
			 @RequestHeader("token") String token,@RequestBody ReminderDto reminder) {
		service.addReminder(noteId, token, reminder);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Reminder added", 200, reminder));

	}
	
	@PostMapping("/note/removereminder")
	public ResponseEntity<Response> removeReminder(@RequestParam("noteId") Long noteId,
			 @RequestHeader("token") String token) {
		service.removeReminder(noteId, token, null);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Reminder removed", 200, noteId));

	}
	
	
	@GetMapping("/note/search")
	public ResponseEntity<Response> search(@RequestParam("title") String title,
			 @RequestHeader("token") String token) {
		     List<NoteInformation> notes=service.searchByTitle(title);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("The note you are looking for is", 200, notes));

	}
	
	
	

	
	

}
