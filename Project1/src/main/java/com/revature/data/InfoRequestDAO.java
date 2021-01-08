package com.revature.data;

import java.util.Set;

import com.revature.beans.InfoRequest;

public interface InfoRequestDAO extends GenericDAO<InfoRequest> {

	Set<InfoRequest> reqsByUserId(Integer id);
	

}
