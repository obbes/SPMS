package com.revature.service;

import com.revature.beans.PitchStage;
import com.revature.data.PitchStageDAO;
import com.revature.data.PitchStageHibernate;

public class PitchStageServiceImpl implements PitchStageService {
	private PitchStageDAO psDao = new PitchStageHibernate();
	@Override
	public Integer addPitchStage(PitchStage c) {
		return psDao.add(c).getId();
	}

	@Override
	public PitchStage getPitchStageById(Integer id) {
		return psDao.getById(id);
	}

	@Override
	public void updatePitchStage(PitchStage c) {
		psDao.update(c);
	}

	@Override
	public void deletePitchStage(PitchStage c) {
		psDao.delete(c);
	}

}
