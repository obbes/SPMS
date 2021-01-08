package com.revature.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.beans.Genre;
import com.revature.utils.HibernateUtil;

public class GenreHibernate implements GenreDAO {
private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public Genre add(Genre t) throws NullPointerException {
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
	public Genre getById(Integer id) throws NullPointerException {
		Session s = hu.getSession();
		Genre c = s.get(Genre.class,  id);
		s.close();
		return c;
	}

	@Override
	public Set<Genre> getAll() {
		Session s = hu.getSession();
		String query = "FROM Genre";
		//query.Query
		Query<Genre> q = s.createQuery(query, Genre.class);
		List<Genre> comList = q.getResultList();
		Set<Genre> comSet = new HashSet<>();
		comSet.addAll(comList);
		s.close();
		
		return comSet;
	}

	@Override
	public void update(Genre t) {
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
	public void delete(Genre t) {
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
