package com.revature.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.AdditionalFile;
import com.revature.utils.HibernateUtil;
import org.hibernate.query.Query;

public class AddFileHibernate implements AddFileDAO {
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	@Override
	
	public AdditionalFile add(AdditionalFile t) throws NullPointerException {
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
	public AdditionalFile getById(Integer id) throws NullPointerException {
		Session s = hu.getSession();
		AdditionalFile af = s.get(AdditionalFile.class, id);
		s.close();
		return af;
	}

	@Override
	public Set<AdditionalFile> getAll() {
		Set<AdditionalFile> files = new HashSet<>();
		
		try (Session s = hu.getSession()) {
			s.beginTransaction();
			String hql = "FROM AdditionalFile";
			Query<AdditionalFile> q = s.createQuery(hql, AdditionalFile.class);
			List<AdditionalFile> resultList = q.getResultList();
			files = new HashSet<>(resultList);
		} catch (Exception e) {
			e.getCause().printStackTrace();
		}
		
		return files;

	}

	@Override
	public void update(AdditionalFile t) {
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
	public void delete(AdditionalFile t) {
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
	public AdditionalFile getByPath(String path) {
		AdditionalFile af = null;
		try (Session s = hu.getSession()) {
			s.beginTransaction();
			String hql = "FROM AdditionalFile where path = :path";
			Query<AdditionalFile> q = s.createQuery(hql, AdditionalFile.class);
			q.setParameter("path", path);
			af = q.getSingleResult();
		} catch (Exception e) {
			e.getCause().printStackTrace();
		}
		
		return af;
	}
}
