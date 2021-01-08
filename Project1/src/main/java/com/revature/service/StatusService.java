package com.revature.service;

import com.revature.beans.Status;

public interface StatusService {
	//crud
	public Integer addStatus(Status c);
	
	public Status getStatusById(Integer id);
	
	public void updateStatus(Status c);
	
	public void deleteStatus(Status c);
}
