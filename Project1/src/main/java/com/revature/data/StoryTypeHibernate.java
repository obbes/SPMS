package com.revature.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.beans.StoryType;
import com.revature.utils.HibernateUtil;

public class StoryTypeHibernate implements StoryTypeDAO {

private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public StoryType add(StoryType t) throws NullPointerException {
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
	public StoryType getById(Integer id) throws NullPointerException {
		Session s = hu.getSession();
		StoryType c = s.get(StoryType.class,  id);
		s.close();
		return c;
	}

	@Override
	public Set<StoryType> getAll() {
		Session s = hu.getSession();
		String query = "FROM StoryType";
		//query.Query
		Query<StoryType> q = s.createQuery(query, StoryType.class);
		List<StoryType> comList = q.getResultList();
		Set<StoryType> comSet = new HashSet<>();
		comSet.addAll(comList);
		s.close();
		
		return comSet;
	}

	@Override
	public void update(StoryType t) {
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
	public void delete(StoryType t) {
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

}
