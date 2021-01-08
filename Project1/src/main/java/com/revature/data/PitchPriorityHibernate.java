package com.revature.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.beans.PitchPriority;
import com.revature.beans.PitchPriority;
import com.revature.utils.HibernateUtil;

public class PitchPriorityHibernate implements PitchPriorityDAO {
private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public PitchPriority add(PitchPriority t) throws NullPointerException {
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
	public PitchPriority getById(Integer id) throws NullPointerException {
		Session s = hu.getSession();
		PitchPriority c = s.get(PitchPriority.class,  id);
		s.close();
		return c;
	}

	@Override
	public Set<PitchPriority> getAll() {
		Session s = hu.getSession();
		String query = "FROM PitchPriority";
		//query.Query
		Query<PitchPriority> q = s.createQuery(query, PitchPriority.class);
		List<PitchPriority> comList = q.getResultList();
		Set<PitchPriority> comSet = new HashSet<>();
		comSet.addAll(comList);
		s.close();
		
		return comSet;
	}

	@Override
	public void update(PitchPriority t) {
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
	public void delete(PitchPriority t) {
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
