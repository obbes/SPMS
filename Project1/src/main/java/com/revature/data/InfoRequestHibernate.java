package com.revature.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.beans.InfoRequest;
import com.revature.utils.HibernateUtil;

public class InfoRequestHibernate implements InfoRequestDAO {
private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public InfoRequest add(InfoRequest t) throws NullPointerException {
		System.out.println("reached the ir hibernate layber ready to add " + t);
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.save(t);
			tx.commit();
			System.out.println("the request was added to the database");
		}catch(Exception e) {
			if(tx != null)
				System.out.println("The transaction was null");
				tx.rollback();
		}finally {
			s.close();
		}
		return t;
	}

	@Override
	public InfoRequest getById(Integer id) throws NullPointerException {
		Session s = hu.getSession();
		InfoRequest c = s.get(InfoRequest.class,  id);
		s.close();
		return c;
	}

	@Override
	public Set<InfoRequest> getAll() {
		Session s = hu.getSession();
		String query = "FROM InfoRequest";
		//query.Query
		Query<InfoRequest> q = s.createQuery(query, InfoRequest.class);
		List<InfoRequest> comList = q.getResultList();
		Set<InfoRequest> comSet = new HashSet<>();
		comSet.addAll(comList);
		s.close();
		
		return comSet;
	}

	@Override
	public void update(InfoRequest t) {
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
	public void delete(InfoRequest t) {
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
	public Set<InfoRequest> reqsByUserId(Integer id) {
		System.out.println("Made it to the hibernate layer and getting all requests");
		Set<InfoRequest> all = getAll();
		System.out.println("there are a total of: " + all.size() + " requests");
		Set<InfoRequest> userReqs = new HashSet<>();
		for(InfoRequest req : all) {
			System.out.println("for each request in all requests checking\n"
					+ " the recieving party: " + req.getRecipient().getId()
					+ "\n the sending party: " + req.getSender().getId() + " Which sould equal the id we have: " +id+
					"\n the pitch id number: " + req.getPitch().getId());
			if(req.getRecipient().getId().equals(id) || req.getSender().getId().equals(id)) {
				System.out.println("I saw that the above was true adn am adding it into this users requests");
				userReqs.add(req);
			}
		}
		return userReqs;
	}

}
