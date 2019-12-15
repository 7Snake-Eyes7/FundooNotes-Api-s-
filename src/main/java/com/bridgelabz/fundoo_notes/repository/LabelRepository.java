package com.bridgelabz.fundoo_notes.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo_notes.Entity.LabelInformation;
import com.bridgelabz.fundoo_notes.Entity.NoteInformation;

@Repository
public class LabelRepository {

	@Autowired
	private EntityManager entityManager;

	public LabelInformation save(LabelInformation labelInformation) {

		Session session = entityManager.unwrap(Session.class);
		session.save(labelInformation);
		return labelInformation;
	}
	
	public NoteInformation saveNote(NoteInformation noteInformation) {

		Session session = entityManager.unwrap(Session.class);
		session.save(noteInformation);
		return noteInformation;
	}
	

	public LabelInformation fetchLabel(Long userid, String labelname) {

		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("from LabelInformation where user_id=:id and name=:name");
		q.setParameter("id", userid);
		q.setParameter("name", labelname);
		return (LabelInformation) q.uniqueResult(); 

	}

	public LabelInformation fetchLabelById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("from LabelInformation where label_id=:id");
		q.setParameter("id", id);

		return (LabelInformation) q.uniqueResult();

	}

	public int deleteLabel(Long i) {
		String hql = "DELETE FROM LabelInformation " + "WHERE label_id = :id";
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery(hql);
		query.setParameter("id", i);
		int result = query.executeUpdate();
		return result;
	}
	
	public List<LabelInformation> getAllLabel(Long id)
	{
		String hql = "FROM LabelInformation " + "WHERE user_id=:id";
		Session session = entityManager.unwrap(Session.class);
		//Query query = session.createQuery(hql);
		return session.createQuery("from LabelInformation where user_Id='"+id+"'").getResultList();
		/*
		 * query.setParameter("id", id); return query.getResultList();
		 */
	}
	
	public LabelInformation getLabel(Long id)
	{
		String hql = "FROM LabelInformation " + "WHERE label_id=:id";
		Session session = entityManager.unwrap(Session.class);
		//Query query = session.createQuery(hql);
		return (LabelInformation) session.createQuery("from LabelInformation where label_id='"+id+"'").uniqueResult();
		/*
		 * query.setParameter("id", id); return query.getResultList();
		 */
	}
	
	

}
