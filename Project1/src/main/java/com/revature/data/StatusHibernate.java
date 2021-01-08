package com.revature.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.beans.Status;
import com.revature.utils.HibernateUtil;

public class StatusHibernate implements StatusDAO {
private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public Status add(Status t) throws NullPointerException {
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
	public Status getById(Integer id) throws NullPointerException {
		Session s = hu.getSession();
		Status c = s.get(Status.class,  id);
		s.close();
		return c;
	}

	@Override
	public Set<Status> getAll() {
		Session s = hu.getSession();
		String query = "FROM Status";
		//query.Query
		Query<Status> q = s.createQuery(query, Status.class);
		List<Status> comList = q.getResultList();
		Set<Status> comSet = new HashSet<>();
		comSet.addAll(comList);
		s.close();
		
		return comSet;
	}

	@Override
	public void update(Status t) {
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
	public void delete(Status t) {
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
