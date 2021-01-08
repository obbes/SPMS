package com.revature.service;

import java.util.Set;

import com.revature.beans.Committee;
import com.revature.beans.Genre;

public interface CommitteeService {
	//crud
	public Integer addCommittee(Committee c);
	
	public Committee getCommitteeById(Integer id);
	public Committee getCommitteeByGenre(Genre g);
	
	public void updateCommittee(Committee c);
	
	public void deleteCommittee(Committee c);

	public Set<Committee> getCommittees();
}
