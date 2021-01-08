package com.revature.service;

import com.revature.beans.StoryType;
import com.revature.data.StoryTypeDAO;
import com.revature.data.StoryTypeHibernate;

public class StoryTypeServiceImpl implements StoryTypeService {
	private StoryTypeDAO stDao = new StoryTypeHibernate();
	@Override
	public Integer addStoryType(StoryType st) {
		return stDao.add(st).getId();
	}

	@Override
	public StoryType getStoryTypeById(Integer id) {
		return stDao.getById(id);
	}

	@Override
	public void updateStoryType(StoryType st) {
		stDao.update(st);
	}

	@Override
	public void deleteStoryType(StoryType st) {
		stDao.delete(st);
	}

}
