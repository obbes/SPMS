package com.revature.services;

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
import com.revature.data.CommitteeDAO;
import com.revature.data.GenreDAO;
import com.revature.service.CommitteeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CommitteeServiceTest {
	@Mock
	static CommitteeDAO cDao;
	
	@Mock
	static GenreDAO gDao;
	
	@InjectMocks
	static CommitteeServiceImpl cServ;
	
	static Set<Committee> cMock = new HashSet<>();
	static Integer sequenceMock =1;
	
	@Test
	public void testGetAllCommittees() {
		when(cDao.getAll()).thenReturn(cMock);
		assertEquals(cMock, cServ.getCommittees());
		verify(cDao).getAll();
	}
}
