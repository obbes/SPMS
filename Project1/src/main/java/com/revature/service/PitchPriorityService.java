package com.revature.service;

import com.revature.beans.PitchPriority;

public interface PitchPriorityService {
	//crud
	public Integer addPitchPriority(PitchPriority c);
	
	public PitchPriority getPitchPriorityById(Integer id);
	
	public void updatePitchPriority(PitchPriority c);
	
	public void deletePitchPriority(PitchPriority c);
}
