package com.revature.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.beans.PitchStage;
import com.revature.utils.HibernateUtil;

public class PitchStageHibernate implements PitchStageDAO{
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public PitchStage add(PitchStage t) throws NullPointerException {
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
	public PitchStage getById(Integer id) throws NullPointerException {
		Session s = hu.getSession();
		PitchStage c = s.get(PitchStage.class,  id);
		s.close();
		return c;
	}
	
	@Override
	public Set<PitchStage> getAll() {
		Session s = hu.getSession();
		String query = "FROM PitchStage";
		//query.Query
		Query<PitchStage> q = s.createQuery(query, PitchStage.class);
		List<PitchStage> comList = q.getResultList();
		Set<PitchStage> comSet = new HashSet<>();
		comSet.addAll(comList);
		s.close();
		
		return comSet;
	}
	
	@Override
	public void update(PitchStage t) {
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
	public void delete(PitchStage t) {
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
