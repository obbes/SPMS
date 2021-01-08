package com.revature.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.beans.Draft;
import com.revature.utils.HibernateUtil;

public class DraftHibernate implements DraftDAO {
private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public Draft add(Draft t) throws NullPointerException {
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
	public Draft getById(Integer id) throws NullPointerException {
		Session s = hu.getSession();
		Draft c = s.get(Draft.class,  id);
		s.close();
		return c;
	}

	@Override
	public Set<Draft> getAll() {
		Session s = hu.getSession();
		String query = "FROM Draft";
		//query.Query
		Query<Draft> q = s.createQuery(query, Draft.class);
		List<Draft> comList = q.getResultList();
		Set<Draft> comSet = new HashSet<>();
		comSet.addAll(comList);
		s.close();
		
		return comSet;
	}

	@Override
	public void update(Draft t) {
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
	public void delete(Draft t) {
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
