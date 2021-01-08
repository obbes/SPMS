package com.revature.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.beans.AdditionalFile;
import com.revature.beans.Person;
import com.revature.beans.Pitch;
import com.revature.utils.HibernateUtil;

public class PitchHibernate implements PitchDAO {
private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public Pitch add(Pitch t) throws NullPointerException {
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
	public Pitch getById(Integer id) throws NullPointerException {
		Session s = hu.getSession();
		Pitch c = s.get(Pitch.class,  id);
		s.close();
		return c;
	}

	@Override
	public Set<Pitch> getAll() {
		Set<Pitch> pitches = null;
		
		try (Session s = hu.getSession()) {
			s.beginTransaction();
			String hql = "From Pitch";
			Query<Pitch> q = s.createQuery(hql, Pitch.class);
			List<Pitch> resultList = q.getResultList();
			if (resultList.size() > 0) {
				pitches = new HashSet<Pitch>(resultList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pitches;
	//}
		
		
		//		Session s = hu.getSession();
//		String query = "FROM Pitch";
//		//query.Query
//		Query<Pitch> q = s.createQuery(query, Pitch.class);
//		List<Pitch> comList = q.getResultList();
//		System.out.println(comList.size());
//		Set<Pitch> comSet = new HashSet<>();
//		comSet.addAll(comList);
//		System.out.println(comSet.size());
//		s.close();
//		
//		return  comSet;
	}
	



	@Override
	public void update(Pitch t) {
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
	public void delete(Pitch t) {
		//12/12 This method does not work because it can't delete the pitch from the person_pitch table
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			
			Object persistentInstance = s.load(Pitch.class, t.getId());
			if (persistentInstance != null) {
				tx = s.beginTransaction();
			    s.delete(persistentInstance);
				tx.commit();
				Object persistentInstance2 = s.load(Person.class, t.getId());
				if(persistentInstance2 != null) {
					String hql = "From Person where pitch_id = :id";
				}
			}else if(tx !=null) {
				tx.rollback(); //says its dead code
				System.out.println("hit the dead code");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			s.close();
		}
	}

	@Override
	public Set<Pitch> getPitchesByCommitteeId(Integer id) {
		
		Set<Pitch> pitches = null;
		
		try (Session s = hu.getSession()) {
			s.beginTransaction();
			String hql = "From Pitch where genre_id = :id";
			Query<Pitch> q = s.createQuery(hql, Pitch.class);
			q.setParameter("id", id);
			List<Pitch> resultList = q.getResultList();
			if (resultList.size() > 0) {
				pitches = new HashSet<Pitch>(resultList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pitches;
	}

	@Override
	public Pitch getByAdditionalFile(AdditionalFile af) {
		Pitch p = null;
		try (Session s = hu.getSession()) {
			s.beginTransaction();
			String hql = "From Pitch where file_id = :id";

//			String hql = "SELECT p FROM Pitch p JOIN p.additionalFiles a WHERE a.id = :add_file_id";
			Query<Pitch> q = s.createQuery(hql, Pitch.class);
			q.setParameter("id", af.getId());
			p = q.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return p;
	}
}
