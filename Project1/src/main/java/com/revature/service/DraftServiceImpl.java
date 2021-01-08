package com.revature.service;

import com.revature.beans.Draft;
import com.revature.data.DraftDAO;
import com.revature.data.DraftHibernate;

public class DraftServiceImpl implements DraftService {
	private DraftDAO dao = new DraftHibernate();

	
	@Override
	public Integer addDraft(Draft d) {
		return dao.add(d).getId();
	}

	@Override
	public Draft getDraftById(Integer id) {
		return dao.getById(id);
	}

	@Override
	public void updateDraft(Draft d) {
		dao.update(d);
		
	}

	@Override
	public void deleteDraft(Draft d) {
		dao.delete(d);
		
	}

}
