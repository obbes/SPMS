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

import com.revature.beans.InfoRequest;
import com.revature.beans.Person;
import com.revature.data.InfoRequestDAO;
import com.revature.data.PersonDAO;
import com.revature.service.InfoRequestServiceImpl;


@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {
	
	@Mock
	InfoRequestDAO irdao;
	
	@Mock
	PersonDAO pdao;
	
	@InjectMocks
	static InfoRequestServiceImpl irserv;
	
	static Set<InfoRequest> irmock = new HashSet<>();
	static Integer sequenceMock = 1;
	
	@Test
	public void testAddRequest() {
		InfoRequest ir = new InfoRequest();
		ir.setId(1);
		Person r = new Person();
		Person s = new Person();
		ir.setRecipient(r);
		ir.setSender(s);
		ir.setQuestion("");
		
		InfoRequest ir2 = new InfoRequest();
		ir2.setId(sequenceMock += 1);
		irmock.add(ir);
		when(irdao.add(ir)).thenReturn(ir2);
		assertFalse(irmock.isEmpty());
		assertNotEquals(ir.getId().intValue(), irserv.addInfoRequest(ir).intValue());
		verify(irdao).add(ir);
		
	}
	
	@Test
	public void testUpdateRequest() {
		InfoRequest ir = new InfoRequest();
		irserv.updateInfoRequest(ir);
		verify(irdao).update(ir);
	}
}
