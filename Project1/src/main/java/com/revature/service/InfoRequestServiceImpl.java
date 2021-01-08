package com.revature.service;

import java.util.Set;

import com.revature.beans.InfoRequest;
import com.revature.data.InfoRequestDAO;
import com.revature.data.InfoRequestHibernate;

public class InfoRequestServiceImpl implements InfoRequestService {
	private InfoRequestDAO irDao = new InfoRequestHibernate();
	@Override
	public Integer addInfoRequest(InfoRequest ir) {
		System.out.println("In the service impl to add: "+ir);
		return irDao.add(ir).getId();
	}

	@Override
	public InfoRequest getInfoRequestById(Integer id) {
		return irDao.getById(id);
	}

	@Override
	public void updateInfoRequest(InfoRequest ir) {
		irDao.update(ir);
	}

	@Override
	public void deleteInfoRequest(InfoRequest ir) {
		irDao.delete(ir);
	}

	@Override
	public Set<InfoRequest> getReqsByUserId(Integer id) {
		System.out.println("in the serviceimpl layer with user Id" + id);
		return irDao.reqsByUserId(id);
	}

}
