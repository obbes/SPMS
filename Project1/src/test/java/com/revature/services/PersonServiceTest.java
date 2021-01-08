package com.revature.services;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.beans.Committee;
import com.revature.beans.Genre;
import com.revature.beans.InfoRequest;
import com.revature.beans.Person;
import com.revature.beans.Pitch;
import com.revature.beans.PitchPriority;
import com.revature.beans.PitchStage;
import com.revature.data.InfoRequestDAO;
import com.revature.data.PersonDAO;
import com.revature.data.PitchDAO;
import com.revature.service.InfoRequestServiceImpl;
import com.revature.service.PersonServiceImpl;
import com.revature.service.PitchPriorityService;
import com.revature.service.PitchServiceImpl;


@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
	@Mock
	static PersonDAO pDao;
	
	@Mock
	static PitchDAO pitchDao;
	
	@Mock
	static InfoRequestDAO irDoa;
	
	
	@InjectMocks
	static PersonServiceImpl pServ;
	
	@InjectMocks
	static InfoRequestServiceImpl irServ;
	
	static Set<Person> personMock = new HashSet<>();
	static Integer sequenceMock = 1;
	
	@Test
	public void testCheckLogin() {
		Person p = new Person();
		p.setUsername("user");
		p.setPasswd("pass");
		personMock.add(p);
		
		when(pDao.getByUsername("user")).thenReturn(p);
		assertEquals(p, pServ.getPersonByUsername("user"));
		verify(pDao).getByUsername("user");
	}
	
	@Test
	public void testGetPersonById() {
		Person p = new Person();
		p.setId(35);
		personMock.add(p);
		
		when(pDao.getById(35)).thenReturn(p);
		assertEquals(p, pServ.getPersonById(35));
		verify(pDao).getById(35);
		personMock.remove(p);
	}
	
	@Test
	public void testGetPitchesByUserId() {
		Set<Pitch> pitches = new HashSet<>();
		Pitch p = new Pitch();
		Pitch p2 = new Pitch();
		Pitch p3 = new Pitch();
		pitches.add(p);
		pitches.add(p3);
		Person person = new Person();
		person.setPitches(pitches);
		person.setId(24);
		
		when(pDao.getPitchesByPersonId(24)).thenReturn(pitches);
		assertEquals(pitches, pServ.getAllPitchesByPersonId(24));
		verify(pDao).getPitchesByPersonId(24);	
	}
	
	@Test
	public void testGetRequestsByUser() {
		InfoRequest ir = new InfoRequest();
		InfoRequest ir2 = new InfoRequest();
		Person p = new Person();
		p.setId(24);
		ir.setRecipient(p);
		ir2.setSender(p);
		Set<InfoRequest> reqs = new HashSet<>();
		reqs.add(ir);
		reqs.add(ir2);
		when(irDoa.reqsByUserId(24)).thenReturn(reqs);
		assertEquals(reqs, irServ.getReqsByUserId(24));
		verify(irDoa).reqsByUserId(24);
	}
}
