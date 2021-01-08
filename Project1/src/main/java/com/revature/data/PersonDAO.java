package com.revature.data;

import java.util.Set;

import com.revature.beans.Person;
import com.revature.beans.Pitch;

public interface PersonDAO extends GenericDAO<Person>{

	Person getByUsername(String username);

	Set<Pitch> getPitchesByPersonId(Integer id);

}
