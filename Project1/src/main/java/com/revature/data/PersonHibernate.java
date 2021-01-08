package com.revature.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.beans.Person;
import com.revature.beans.Pitch;
import com.revature.utils.HibernateUtil;

public class PersonHibernate implements PersonDAO {
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public Person add(Person t) throws NullPointerException {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.save(t);
			tx.commit();
		}catch(Exception e) {
			if(tx != null)
				tx.rollback();
		}finally {
			s.close();
		}
		return t;
	}

	@Override
	public Person getById(Integer id) throws NullPointerException {
		Session s = hu.getSession();
		Person c = s.get(Person.class,  id);
		s.close();
		return c;
	}


	@Override
	public Set<Person> getAll() {
		Session s = hu.getSession();
		String query = "FROM Person";
		//query.Query
		Query<Person> q = s.createQuery(query, Person.class);
		List<Person> comList = q.getResultList();
		Set<Person> comSet = new HashSet<>();
		comSet.addAll(comList);
		s.close();
		
		return comSet;
	}

	@Override
	public void update(Person t) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.update(t);
			tx.commit();
		} catch(Exception e) {
			if(tx !=null) {
				tx.rollback();
			}
		}finally {
			s.close();
		}

	}

	@Override
	public void delete(Person t) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.delete(t);
			tx.commit();
		}catch(Exception e) {
			if(tx != null) {
				tx.rollback();
			}
		}finally {
			s.close();
		}
	}

	@Override
	public Person getByUsername(String username) {
			Person p = null;
			
			try (Session s = hu.getSession()) {
				s.beginTransaction();
				String hql = "FROM Person WHERE username = :username";
				Query<Person> q = s.createQuery(hql, Person.class);
				q.setParameter("username", username);
				List<Person> resultList = q.getResultList();
				System.out.println(resultList.size());
				if (resultList.size() > 0) p = resultList.get(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return p;

		
		
		// Criteria API: a way of making queries in a programmatic syntax
//		Session s = hu.getSession();
//		CriteriaBuilder cb = s.getCriteriaBuilder();
//		CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
//		Root<Person> root = criteria.from(Person.class);
//		
//		Predicate predicateForUsername = cb.equal(root.get("username"), username);
//		// Predicate predicateForPassword = cb.equal(root.get("passwd"), password);
//		// Predicate predicateForBoth = cb.and(predicateForUsername, predicateForPassword);
//		
//		criteria.select(root).where(predicateForUsername);
//		
//		Person p = s.createQuery(criteria).getSingleResult();
//		return p;
	}

	@Override
	public Set<Pitch> getPitchesByPersonId(Integer id) {
		Session s = hu.getSession();
		String query = "FROM Pitch WHERE person_id = :id";
		Query<Pitch> q = s.createQuery(query, Pitch.class);
		q.setParameter("person_id", id);
		List<Pitch> pitchList = q.getResultList();
		Set<Pitch> pitchSet = new HashSet<>();
		pitchSet.addAll(pitchList);
		s.close();
		return pitchSet;
	}

}
