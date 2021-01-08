package com.revature.service;

import com.revature.beans.PitchStage;

public interface PitchStageService {
	//crud
	public Integer addPitchStage(PitchStage c);
	
	public PitchStage getPitchStageById(Integer id);
	
	public void updatePitchStage(PitchStage c);
	
	public void deletePitchStage(PitchStage c);
}
