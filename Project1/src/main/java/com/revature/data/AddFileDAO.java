package com.revature.data;

import com.revature.beans.AdditionalFile;

public interface AddFileDAO extends GenericDAO<AdditionalFile>{
	public AdditionalFile getByPath(String path);
}
