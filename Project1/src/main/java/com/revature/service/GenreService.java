package com.revature.service;

import com.revature.beans.Genre;

public interface GenreService {
	public Integer addGenre(Genre g);
	public Genre getGenreById(Integer id);
	public void updateGenre(Genre g);
	public void deleteGenre(Genre g);
}
