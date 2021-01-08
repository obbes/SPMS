package com.revature.service;

import java.util.Set;

import com.revature.beans.Person;
import com.revature.beans.Pitch;
import com.revature.data.PersonDAO;
import com.revature.data.PersonHibernate;

public class PersonServiceImpl implements PersonService {
	private PersonDAO pDao = new PersonHibernate();
	@Override
	public Integer addPerson(Person p) {
		return pDao.add(p).getId();
	}

	@Override
	public Person getPersonById(Integer id) {
		return pDao.getById(id);
	}

	@Override
	public Person getPersonByUsername(String username) {
		return pDao.getByUsername(username);
	}

	@Override
	public void updatePerson(Person p) {
		pDao.update(p);
	}

	@Override
	public void deletePerson(Person p) {
		pDao.delete(p);
	}

	@Override
	public Set<Pitch> getAllPitchesByPersonId(Integer id) {
		return pDao.getPitchesByPersonId(id);
	}

}
