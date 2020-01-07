package com.bridgelabz.fundoo_notes.Entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Entity
@Table(name = "usersdetail")
public class UserInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private Long mobileNumber;
	
	private LocalDateTime createdDate;
	
	private boolean isVerified;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	
	private List<NoteInformation> note;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Collaborator_Note", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "note_id") })
	
//	@JsonManagedReference
	@JsonIgnore
	private List<NoteInformation> colaborateNote;

}
