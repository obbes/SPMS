package com.revature.service;

import com.revature.beans.StoryType;

public interface StoryTypeService {
	public Integer addStoryType(StoryType st);
	public StoryType getStoryTypeById(Integer id);
	public void updateStoryType(StoryType st);
	public void deleteStoryType(StoryType st);
}
