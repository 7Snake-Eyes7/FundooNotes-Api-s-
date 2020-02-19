package com.bridgelabz.fundoo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.Entity.NoteInformation;

@Repository
public class NoteRepository {

	@Autowired
	private EntityManager entityManager;

	public NoteInformation save(NoteInformation noterInformation) {

		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(noterInformation);
		return noterInformation;

	}

	public NoteInformation findById(long id) {
		
		System.out.println("in repository");
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("from NoteInformation where id=:id ");
		q.setParameter("id", id);
		return (NoteInformation) q.uniqueResult();

	}

	public boolean deleteNote(long id, long userid) {
		
		Session session = entityManager.unwrap(Session.class);
		String hql = "DELETE FROM NoteInformation " + "WHERE id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
//		query.setParameter("userid", userid);
		int result = query.executeUpdate();
		if (result >= 1) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<NoteInformation> getNotes(long userid) {
		
		System.out.println("in repository");
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery(
				"from NoteInformation where user_id='" + userid + "'" + " and is_trashed=false and is_archieved=false ORDER BY id DESC")
				.getResultList();
	}

	@Transactional
	public List<NoteInformation> getTrashedNotes(long userid) {
		
		System.out.println("in repository");
		Session session = entityManager.unwrap(Session.class);

		return session.createQuery("from NoteInformation where user_Id='" + userid + "'" + " and is_trashed=true")
				.getResultList();
	}

	@Transactional
	public List<NoteInformation> getArchiveNotes(long userid) {
		
		System.out.println("in repository");
		Session session = entityManager.unwrap(Session.class);

		return session.createQuery("from NoteInformation where user_Id='" + userid + "'" + " and is_archieved=true"
				+ " and is_trashed=false").getResultList();
	}

	public boolean updateColour(long id, long userid, String colour) {
		
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("update NoteInformation u set u.colour = :colour" + " where u.id = :id");

		query.setParameter("colour", "red");
		query.setParameter("id", id);
		int result = query.executeUpdate();

		if (result >= 1) {
			return true;
		} else {
			return false;
		}
	}

}
