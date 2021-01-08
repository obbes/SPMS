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

import com.revature.beans.Pitch;
import com.revature.beans.PitchStage;
import com.revature.data.PitchDAO;
import com.revature.data.PitchStageDAO;
import com.revature.service.GenreServiceImpl;
import com.revature.service.PersonServiceImpl;
import com.revature.service.PitchPriorityServiceImpl;
import com.revature.service.PitchServiceImpl;
import com.revature.service.PitchStageServiceImpl;
import com.revature.service.StatusServiceImpl;
import com.revature.service.StoryTypeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StageServiceTest {
	@Mock
	static PitchStageDAO psdao;
	
	@Mock
	static PitchDAO pDao;
	
	@InjectMocks
	static PersonServiceImpl personServ;
	
	@InjectMocks
	static GenreServiceImpl gServ;
	
	@InjectMocks
	static StoryTypeServiceImpl stServ;
	
	@InjectMocks
	static PitchServiceImpl pServ;
	
	@InjectMocks
	static StatusServiceImpl sServ;
	
	@InjectMocks
	static PitchPriorityServiceImpl ppServ;
	
	@InjectMocks
	static PitchStageServiceImpl psServ;
	
	static Set<PitchStage> psMock = new HashSet<>();
	static Set<Pitch> pitchMock = new HashSet<>();
	
	
	@Test
	public void testGetStageById() {
		PitchStage ps = new PitchStage();
		ps.setId(24);
		psMock.add(ps);
		
		when(psdao.getById(24)).thenReturn(ps);
		assertEquals(ps, psServ.getPitchStageById(24));
		verify(psdao).getById(24);
		psMock.remove(ps);
	}
	
	@Test
	public void testPitchAcceptedStageCheck() {
		Pitch p = new Pitch();
		p.setStage(psServ.getPitchStageById(1));
		PitchStage ps = new PitchStage();
		ps.setId(1);
		ps.setName("test");


		psServ.updatePitchStage(ps);
		verify(psdao).update(ps);
	}
	
}
