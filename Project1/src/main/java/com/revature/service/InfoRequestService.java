package com.revature.service;

import java.util.Set;

import com.revature.beans.InfoRequest;

public interface InfoRequestService {
	public Integer addInfoRequest(InfoRequest ir);
	
	public InfoRequest getInfoRequestById(Integer id);
	
	public void updateInfoRequest(InfoRequest ir);
	
	public void deleteInfoRequest(InfoRequest ir);

	public Set<InfoRequest> getReqsByUserId(Integer id);
	
}
