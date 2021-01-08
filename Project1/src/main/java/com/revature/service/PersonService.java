package com.revature.service;

import java.util.Set;

import com.revature.beans.Person;
import com.revature.beans.Pitch;
import com.revature.exceptions.NonUniqueUsernameException;

public interface PersonService {
	// create
	public Integer addPerson(Person p) throws NonUniqueUsernameException;
	// read
	public Person getPersonById(Integer id);
	public Person getPersonByUsername(String username);
	public Set<Pitch> getAllPitchesByPersonId (Integer id);

	// update
	public void updatePerson(Person p);
	// delete
	public void deletePerson(Person p);

}
