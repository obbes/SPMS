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
import com.revature.service.PitchPriorityService;
import com.revature.service.PitchServiceImpl;


@ExtendWith(MockitoExtension.class)
public class PitchServiceTest {
	@Mock
	static PitchDAO pDao;
	
	@Mock
	static PersonDAO personDao;
	
	@InjectMocks
	static PitchServiceImpl pServ;
	
	static Set<Pitch> pMock = new HashSet<>();
	static Integer sequenceMock = 1;
	
	@Test
	public void testGetPitchById() {
		Pitch p = new Pitch();
		p.setId(55);
		pMock.add(p);
		
		when(pDao.getById(55)).thenReturn(p);
		assertEquals(p, pServ.getPitchById(55));
		verify(pDao).getById(55);
		pMock.remove(p);
	}
	
	@Test
	public void testAddPitch() {
		Pitch p = new Pitch();
		PitchPriority pp = new PitchPriority();
		pp.setId(1);
		p.setPriority(pp);
		PitchStage ps = new PitchStage();
		ps.setId(1);
		p.setStage(ps);
		pMock.add(p);
		
		Pitch p2 = new Pitch();
		p2.setId(sequenceMock +=1);
		p2.setPriority(p.getPriority());
		p2.setStage(p.getStage());
		
		when(pDao.add(p)).thenReturn(p2);
		assertNotEquals(p.getId().intValue(), pServ.addPitch(p).intValue());
		verify(pDao).add(p);
	}
	
	@Test
	public void testUpdatePitch() {
		Pitch p = new Pitch();
		pServ.updatePitch(p);
		verify(pDao).update(p);
	}
	
	@Test
	public void testGetPitches() {
		when(pDao.getAll()).thenReturn(pMock);
		assertEquals(pMock, pServ.getPitches());
		verify(pDao).getAll();
	}
	
	@Test
	public void testGetCommitteePitches() {
		Committee c = new Committee();
		c.setId(19);
		Genre g = new Genre();
		g.setId(19);
		Pitch p = new Pitch();
		Pitch p2 = new Pitch();
		Pitch p3 = new Pitch();
		p.setGenre(g);
		p2.setGenre(g);
		p3.setGenre(g);
		pMock.add(p);
		pMock.add(p2);
		pMock.add(p3);
		when(pServ.getCommitteePitches(19)).thenReturn(pMock);
		assertEquals(pMock, pServ.getCommitteePitches(19));
		Pitch p4 = new Pitch();
		pMock.add(p4);
//		assertEquals(pMock.contains(p), pServ.getCommitteePitches(19).contains(p));  too many invocations
		verify(pDao).getPitchesByCommitteeId(19);
	}
	
	
}
