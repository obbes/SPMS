package com.revature.service;

import com.revature.beans.Status;
import com.revature.data.StatusDAO;
import com.revature.data.StatusHibernate;

public class StatusServiceImpl implements StatusService {
	private StatusDAO statDao = new StatusHibernate();
	@Override
	public Integer addStatus(Status c) {
		return statDao.add(c).getId();
	}

	@Override
	public Status getStatusById(Integer id) {
		return statDao.getById(id);
	}

	@Override
	public void updateStatus(Status c) {
		statDao.update(c);
	}

	@Override
	public void deleteStatus(Status c) {
		statDao.delete(c);
	}

}
