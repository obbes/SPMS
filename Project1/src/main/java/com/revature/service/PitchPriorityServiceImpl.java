package com.revature.service;

import com.revature.beans.PitchPriority;
import com.revature.data.PitchPriorityDAO;
import com.revature.data.PitchPriorityHibernate;

public class PitchPriorityServiceImpl implements PitchPriorityService {
	private PitchPriorityDAO ppDao = new PitchPriorityHibernate();
	@Override
	public Integer addPitchPriority(PitchPriority c) {
		return ppDao.add(c).getId();
	}

	@Override
	public PitchPriority getPitchPriorityById(Integer id) {
		return ppDao.getById(id);
	}

	@Override
	public void updatePitchPriority(PitchPriority c) {
		ppDao.update(c);
	}

	@Override
	public void deletePitchPriority(PitchPriority c) {
		ppDao.delete(c);
	}

}
