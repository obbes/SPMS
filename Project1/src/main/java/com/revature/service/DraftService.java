package com.revature.service;

import com.revature.beans.Draft;

public interface DraftService {
	//c
	public Integer addDraft(Draft d);
	//r
	public Draft getDraftById(Integer id);
	//u
	public void updateDraft(Draft d);
	//d
	public void deleteDraft(Draft d);
}
