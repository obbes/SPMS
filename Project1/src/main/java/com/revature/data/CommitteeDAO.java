package com.revature.data;

import com.revature.beans.Committee;
import com.revature.beans.Genre;

public interface CommitteeDAO extends GenericDAO<Committee>{

	Committee getByGenre(Genre g);

}
