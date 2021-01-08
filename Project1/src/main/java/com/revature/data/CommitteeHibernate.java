package com.revature.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.hibernate.Session;

import com.revature.beans.Committee;
import com.revature.beans.Genre;
import com.revature.utils.HibernateUtil;
import org.hibernate.Transaction;

public class CommitteeHibernate implements CommitteeDAO {
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public Committee add(Committee t) throws NullPointerException {
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
	public Committee getById(Integer id) throws NullPointerException {
		Session s = hu.getSession();
		Committee c = s.get(Committee.class,  id);
		s.close();
		return c;
	}

	@Override
	public Set<Committee> getAll() {
		Session s = hu.getSession();
		String query = "FROM Committee";
		//query.Query
		Query<Committee> q = s.createQuery(query, Committee.class);
		List<Committee> comList = q.getResultList();
		Set<Committee> comSet = new HashSet<>();
		comSet.addAll(comList);
		s.close();
		
		return comSet;
	}

	@Override
	public void update(Committee t) {
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
	public void delete(Committee t) {
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
	public Committee getByGenre(Genre g) {
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<Committee> criteria = cb.createQuery(Committee.class);
		Root<Committee> root = criteria.from(Committee.class);
		
		Predicate predicateForGenre = cb.equal(root.get("genre_id"), g.getId());
		
		criteria.select(root).where(predicateForGenre);
		Committee committee = s.createQuery(criteria).getSingleResult();
		return committee;
	}

}
