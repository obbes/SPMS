package com.revature.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.beans.Title;
import com.revature.beans.Title;
import com.revature.utils.HibernateUtil;

public class TitleHibernate implements TitleDAO {
private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public Title add(Title t) throws NullPointerException {
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
	public Title getById(Integer id) throws NullPointerException {
		Session s = hu.getSession();
		Title c = s.get(Title.class,  id);
		s.close();
		return c;
	}

	@Override
	public Set<Title> getAll() {
		Session s = hu.getSession();
		String query = "FROM Title";
		//query.Query
		Query<Title> q = s.createQuery(query, Title.class);
		List<Title> comList = q.getResultList();
		Set<Title> comSet = new HashSet<>();
		comSet.addAll(comList);
		s.close();
		
		return comSet;
	}

	@Override
	public void update(Title t) {
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
	public void delete(Title t) {
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
