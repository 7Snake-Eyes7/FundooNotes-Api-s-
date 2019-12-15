package com.bridgelabz.fundoo_notes.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo_notes.Entity.LabelInformation;
import com.bridgelabz.fundoo_notes.Entity.NoteDto;
import com.bridgelabz.fundoo_notes.Entity.NoteInformation;
import com.bridgelabz.fundoo_notes.Entity.NoteUpdation;
import com.bridgelabz.fundoo_notes.Entity.ReminderDto;
import com.bridgelabz.fundoo_notes.Entity.UserInformation;
import com.bridgelabz.fundoo_notes.configurations.RedisConfig;
import com.bridgelabz.fundoo_notes.exception.UserException;
import com.bridgelabz.fundoo_notes.reddisrepository.RedisRepository;
import com.bridgelabz.fundoo_notes.repository.NoteRepository;
import com.bridgelabz.fundoo_notes.util.JwtGenerator;
import com.bridgelabz.fundoo_notes.repository.IUserRepository;

@Service
public class NoteServiceImplementation implements NoteService {

	@Autowired
	private JwtGenerator tokenGenerator;

	@Autowired
	private IUserRepository repository;

	@Autowired
	private UserInformation user;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private NoteInformation noteinformation;

	@Autowired
	private ElasticSearchServiceImpl elasticService;

	@Autowired
	private RedisConfig redisTemplete;
	
	@Autowired
	private RedisRepository redisRepository;
	
	

	@Transactional
	@Override
	public void createNote(NoteDto information, String token) {
		try {
			System.out.println("in service");
			Long userid = (long) tokenGenerator.parseJWT(token);
			System.out.println("inside note service" + userid);

			user = repository.getUserById(userid);
			System.out.println("inside service" + user);
			if (user != null) {
				BeanUtils.copyProperties(information, NoteInformation.class);
				noteinformation.setCreatedDateAndTime(LocalDateTime.now());
				noteinformation.setArchieved(false);
				noteinformation.setPinned(false);
				noteinformation.setTrashed(false);
				noteinformation.setColour("white");
				user.getNote().add(noteinformation);
				NoteInformation note = noteRepository.save(noteinformation);

//				if(note!=null) {
//					final String KEY=user.getEmail();
//					try {	
//						//redisTemplete.redistemplate().opsForValue().set(KEY, information);
//						System.out.println(noteinformation);
//						String check1 = elasticService.CreateNote(noteinformation);
//
//						System.out.println(check1);
//					} 
//					catch (Exception e) {
//						e.printStackTrace();
//					}
//				}

			} else {
				throw new UserException("note is not present with the given id ");
			}

		} catch (Exception e) {

			throw new UserException("user is not present with the given id ");
		}

	}

	@Transactional
	@Override
	public void updateNote(NoteUpdation information, String token) {
		try {
			Long userid = (long) tokenGenerator.parseJWT(token);
			System.out.println("inside note service" + userid);

			user = repository.getUserById(userid);

			NoteInformation note = noteRepository.findById(information.getId());
			if (note != null) {
				System.out.println("note is   " + note);
				note.setId(information.getId());
				note.setDescription(information.getDescription());
				note.setTitle(information.getTitle());
				note.setPinned(information.isPinned());
				note.setArchieved(information.isArchieved());
				note.setArchieved(information.isTrashed());
				note.setUpDateAndTime(LocalDateTime.now());
				NoteInformation note1=noteRepository.save(note);
				if(note!=null) {
					//elasticService.UpdateNote(note1);
				}
			} else {
				throw new UserException("note is not present");
			}

		} catch (Exception e) {
			throw new UserException("user is not present");
		}

	}

	@Transactional
	@Override
	public void deleteNote(long id, String token) {

		NoteInformation note = noteRepository.findById(id);
		// note.setTrashed(true);
		note.setTrashed(!note.isTrashed());
		noteRepository.save(note);

	}

	@Transactional
	@Override
	public void archievNote(long id, String token) {
		NoteInformation note = noteRepository.findById(id);
		// note.setTrashed(true);
		note.setArchieved(!note.isArchieved());
		noteRepository.save(note);

	}

	@Transactional
	@Override
	public void pin(long id, String token) {
		NoteInformation note = noteRepository.findById(id);
		// note.setTrashed(true);
		note.setPinned(!note.isPinned());
		noteRepository.save(note);

	}

	@Transactional
	@Override
	public boolean deleteNotePemenetly(long id, String token) {
		try {
			Long userid = (long) tokenGenerator.parseJWT(token);
			System.out.println("user id" + " " + userid);
			NoteInformation note = noteRepository.findById(id);
			if (note != null) {
				List<LabelInformation> labels = note.getList();
				if(labels!=null) {
				labels.clear();
				}
				noteRepository.deleteNote(id, userid);
				elasticService.DeleteNote(note);
			} else {
				throw new UserException("note is not present");
			}

		}

		catch (Exception e) {
			throw new UserException("user is not present");
		}
		return false;

	}

	@Override
	@Transactional
	public List<NoteInformation> getAllNotes(String token) {
		try {
			Long userId = (long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userId);
			if (user != null) {
				System.out.println("user logged in"+user.getUserId());
				System.out.println("user ");
				// List<NoteInformation> list=user.getNote();
				List<NoteInformation> list11 = noteRepository.getNotes(userId);
				//redisRepository.save(list11.get(0));
				
			List<NoteInformation> collaboratedNotes=	user.getColaborateNote();
			if(collaboratedNotes!=null) {
			list11.addAll(collaboratedNotes);
			}
//				user.getColaborateNote();
//				System.out.println(user.getColaborateNote());
//				System.out.println("note fetched is" + " " + list.get(0));
//				System.out.println("labels of notes" + list.get(0).getList());
//				List<NoteInformation> list1 =user.getNote();
//				for(NoteInformation  notes:list11) {
//					list.add(notes);
//				}
				System.out.println(list11.get(0));
				return list11;

			} else {
				System.out.println(user + "hello");
				throw new UserException("note doesn't exist");
			}

		} catch (Exception e) {
			throw new UserException("error occured");
		}

	}

	@Override
	public List<NoteInformation> getTrashedNotes(String token) {
		try {
			Long userId = (long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userId);

			if (user != null) {
				System.out.println(user);
				List<NoteInformation> list = noteRepository.getTrashedNotes(userId);
				System.out.println("note fetched is" + " " + list.get(0));
				return list;

			} else {
				System.out.println(user + "hello");
				throw new UserException("note does not exist");
			}

		} catch (Exception e) {
			throw new UserException("error occured");
		}

	}

	@Override
	public List<NoteInformation> getArchiveNote(String token) {
		try {
			Long userId = (long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userId);

			if (user != null) {
				System.out.println(user);
				List<NoteInformation> list = noteRepository.getArchiveNotes(userId);
				System.out.println("note fetched is" + " " + list.get(0).toString());
				return list;

			} else {
				System.out.println(user + "hello");
				throw new UserException("note doesn't exist");
			}

		} catch (Exception e) {
			throw new UserException("error occured");
		}

	}

	@Transactional
	@Override
	public void addColour(Long noteId, String token, String colour) {

		Long userid;

		try {
			userid = (long) tokenGenerator.parseJWT(token);
			System.out.println("user id" + " " + userid);
			NoteInformation note = noteRepository.findById(noteId);
//			if (note != null) {
//				System.out.println(note.getColour());
//				System.out.println(colour);
//				// noteRepository.updateColour(note.getId(), userid, colour);
				note.setColour(colour);
//				System.out.println(note.getColour());
				noteRepository.save(note);
//			} else {
//				throw new UserException("note is not present");
//			}

		} catch (Exception e) {
			throw new UserException("authentication failed");
		}
	}

	@Transactional
	@Override
	public void addReminder(Long noteId, String token, ReminderDto reminder) {
		Long userid;

		try {
			userid = (long) tokenGenerator.parseJWT(token);
			System.out.println("user id" + " " + userid);
			NoteInformation note = noteRepository.findById(noteId);
			if (note != null) {
				System.out.println(note.getReminder());
				System.out.println(reminder);

				note.setReminder(reminder.getReminder());
				System.out.println(note.getColour());
				noteRepository.save(note);
			} else {
				throw new UserException("note doesn't exist");
			}

		} catch (Exception e) {
			throw new UserException("authentication failed");
		}

	}

	@Override
	public void removeReminder(Long noteId, String token, ReminderDto reminder) {
		Long userid;

		try {
			userid = (long) tokenGenerator.parseJWT(token);
			System.out.println("user id" + " " + userid);
			NoteInformation note = noteRepository.findById(noteId);
			if (note != null) {
				System.out.println(note.getReminder());
				System.out.println(reminder);

				note.setReminder(null);
				System.out.println(note.getColour());
				noteRepository.save(note);
			} else {
				throw new UserException("note doesn't exist");
			}

		} catch (Exception e) {
			throw new UserException("authentication failed");
		}

	}
	
	
	@Override
	public List<NoteInformation> searchByTitle(String title) {
		List<NoteInformation> notes=elasticService.searchbytitle(title);
		if(notes!=null) {
		return notes;
		}
		else {
			return null;
		}
	}
	
	@Override
	@Transactional
	public List<NoteInformation> getAllPinnedNotes(String token) {
		List<NoteInformation> allNotes;
		try {
			Long userId = (long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userId);
			if (user != null) {
				System.out.println("user logged in"+user.getUserId());
				System.out.println("user ");
				// List<NoteInformation> list=user.getNote();
				List<NoteInformation> list11 = noteRepository.getNotes(userId);
			  if(list11!=null) {
			 allNotes=list11.stream().filter(note -> note.isPinned()==true).collect(Collectors.toList());
				  return allNotes;
			  }
			
			}
		} catch (Exception e) {
			throw new UserException("error occured");
		}
		return null;

	}
	
	
	
	
	
	

	/*
	 * NoteInformation updatedNotes =
	 * noteRepository.findById(information.getId()).map(currentNote -> {
	 * currentNote.setTitle(information != null ? information.getTitle() :
	 * currentNote.getTitle()); currentNote.setDescription(information != null ?
	 * information.getTitle() : currentNote.getDescription());
	 * 
	 * return currentNote; }).orElseThrow(() -> new
	 * UserException("note not present"));
	 */
	// noteRepository.save(updatedNotes);

}
