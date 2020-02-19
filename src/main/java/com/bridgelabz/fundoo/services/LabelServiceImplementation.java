package com.bridgelabz.fundoo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.Entity.LabelDto;
import com.bridgelabz.fundoo.Entity.LabelInformation;
import com.bridgelabz.fundoo.Entity.LabelUpdate;
import com.bridgelabz.fundoo.Entity.NoteInformation;
import com.bridgelabz.fundoo.Entity.UserInformation;
import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.repository.LabelRepository;
import com.bridgelabz.fundoo.repository.NoteRepository;
import com.bridgelabz.fundoo.util.JwtGenerator;

@Service
public class LabelServiceImplementation implements LabelService {
	@Autowired
	private LabelRepository repository;

	private LabelInformation labelInformation = new LabelInformation();

	@Autowired
	private IUserRepository userRepository;

	private UserInformation user = new UserInformation();

	@Autowired
	private JwtGenerator tokenGenerator;

	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	@Override
	public void createLabel(LabelDto label, String token) {
		Long id = null;

		try {
			id = (long) tokenGenerator.parseJWT(token);
		} catch (Exception e) {

			throw new UserException("user does not exist");
		}

		UserInformation user = userRepository.getUserById(id);
		if (user != null) {
			LabelInformation labelInfo = repository.fetchLabel(user.getUserId(), label.getName());
			if (labelInfo == null) {
				labelInformation=modelMapper.map(label, LabelInformation.class);
//				BeanUtils.copyProperties(label, LabelInformation.class);
				labelInformation.getLabelId();
				labelInformation.getName();
				labelInformation.setUserId(user.getUserId());
				repository.save(labelInformation);
			} else {
				throw new UserException("label with the given name is already present");
			}
		} else {
			throw new UserException("Note does not exist with the given id");
		}
	}
	
	@Transactional
	@Override
	public void createLabelAndMap(LabelDto label, String token,Long noteId) {
		Long id = null;

		try {
			id = (long) tokenGenerator.parseJWT(token);
		} catch (Exception e) {

			throw new UserException("user does not exist");
		}

		UserInformation user = userRepository.getUserById(id);
		if (user != null) {
			LabelInformation labelInfo = repository.fetchLabel(user.getUserId(), label.getName());
			if (labelInfo == null) {
				BeanUtils.copyProperties(label, LabelInformation.class);
//				labelInformation.getLabelId();
//				labelInformation.getName();
				labelInformation.setUserId(user.getUserId());
				repository.save(labelInformation);
				NoteInformation note=noteRepository.findById(noteId);
				note.getList().add(labelInformation);
				noteRepository.save(note);	
				
			} else {
				throw new UserException("label with the given name is already present");
			}
		} else {
			throw new UserException("Note does not exist with the given id");
		}

	}
	
	@Transactional
	@Override
	public void addLabel(Long labelId, Long noteId, String token) {
		NoteInformation note = noteRepository.findById(noteId);
		LabelInformation label = repository.fetchLabelById(labelId);
		label.getList().add(note);
		repository.save(label);
	}

	@Transactional
	@Override
	public void removeLabel(Long labelId, Long noteId, String token) {
		NoteInformation note = noteRepository.findById(noteId);
		LabelInformation label = repository.fetchLabelById(labelId);
		note.getList().remove(label);
		
		noteRepository.save(note);
	}

	
	@Transactional
	@Override
	public void editLabel(LabelUpdate label, String token) {
		Long id = null;

		try {
			id = (long) tokenGenerator.parseJWT(token);
		} catch (Exception e) {

			throw new UserException("user is not present ");
		}

		UserInformation user = userRepository.getUserById(id);
		if (user != null) {
			LabelInformation labelInfo = repository.fetchLabelById(label.getLabelId());
			if (labelInfo != null) {
				labelInfo.setName(label.getLabelName());
				repository.save(labelInfo);
			} else {
				throw new UserException("label with the given id does not exist");
			}

		} else {
			throw new UserException("user does not exist");
		}

	}

	@Transactional
	@Override
	public void deleteLabel(LabelUpdate info, String token) {
		Long id = null;

		try {
			id = (long) tokenGenerator.parseJWT(token);
		} catch (Exception e) {

			throw new UserException("User does not exist");
		}

		UserInformation user = userRepository.getUserById(id);
		if (user != null) {
			LabelInformation labelInfo = repository.fetchLabelById(info.getLabelId());
			if (labelInfo != null) {
				repository.deleteLabel(info.getLabelId());
			} else {
				throw new UserException("Note does not exist");
			}
		}

	}

	@Override
	public List<LabelInformation> getLabel(String token) {

		Long id;
		try {
			id = (long) tokenGenerator.parseJWT(token);
		} catch (Exception e) {

			throw new UserException("note does not exist");
		}

		List<LabelInformation> labels = repository.getAllLabel(id);
		return labels;

	}

	@Override
	public List<NoteInformation> getAllNotes(String token, Long labelId) {
		LabelInformation label=repository.getLabel(labelId);
		List<NoteInformation> list=label.getList();
		System.out.println("label is"+list);
		
		return list;
	}

}
