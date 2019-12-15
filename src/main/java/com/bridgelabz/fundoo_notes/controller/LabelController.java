package com.bridgelabz.fundoo_notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo_notes.Entity.LabelDto;
import com.bridgelabz.fundoo_notes.Entity.LabelInformation;
import com.bridgelabz.fundoo_notes.Entity.LabelUpdate;
import com.bridgelabz.fundoo_notes.Entity.NoteInformation;
import com.bridgelabz.fundoo_notes.responses.Response;
import com.bridgelabz.fundoo_notes.services.LabelService;

@RestController
@RequestMapping
public class LabelController {

	@Autowired
	private LabelService service;

	@PostMapping("/label/create")
	public ResponseEntity<Response> createLabel(@RequestBody LabelDto label, @RequestHeader("token") String token) {
		System.out.println(label.getName());
		service.createLabel(label, token);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created", 200, label));
	}
	

	@PostMapping("/label/createandmap")
	public ResponseEntity<Response> createLabelAndMap(@RequestBody LabelDto label, @RequestHeader("token") String token,@RequestParam("noteId") Long noteId) {
		System.out.println("label is"+label.getName());
		System.out.println("note id is"+noteId);
		service.createLabelAndMap(label, token, noteId);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created", 200, label));
	}

	@PostMapping("/label/addlabel")
	public ResponseEntity<Response> addLabel(@RequestParam("labelId") Long labelId,
			@RequestHeader("token") String token, @RequestParam("noteId") Long noteId) {
		System.out.println(labelId);
		service.addLabel(labelId, noteId, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label added", 200));

	}
	
	@PostMapping("/label/removelabel")
	public ResponseEntity<Response> removeLabel(@RequestParam("labelId") Long labelId,
			@RequestHeader("token") String token, @RequestParam("noteId") Long noteId) {
		System.out.println(labelId);
		service.removeLabel(labelId, noteId, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label removed", 200));

	}

	@PutMapping("/label/update")
	public ResponseEntity<Response> updateLabel(@RequestBody LabelUpdate labelInfo,
			@RequestHeader("token") String token) {

		service.editLabel(labelInfo, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label updated", 200, labelInfo));
	}

	@PostMapping("/label/delete")
	public ResponseEntity<Response> delete(@RequestBody LabelUpdate labelInfo, @RequestHeader("token") String token) {
		System.out.println("conroller");
		service.deleteLabel(labelInfo, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label deleted", 200, labelInfo));

	}

	@GetMapping("/labels/getAllLabel")
	public ResponseEntity<Response> get(@RequestHeader("token") String token) {
		List<LabelInformation> labels = service.getLabel(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("All the labels that you are using", 200, labels));

	}

	@GetMapping("/label/getLabelNotes")
	public ResponseEntity<Response> getNotes(@RequestHeader("token") String token,@RequestParam("id") Long labelId) {
	List<NoteInformation> list=service.getAllNotes(token, labelId);
		
	return ResponseEntity.status(HttpStatus.OK).body(new Response("The result is", 200, list));

	}

}
