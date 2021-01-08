package com.revature.data;

import java.util.Set;

public interface GenericDAO <T> {
	// CRUD operations (create, read, update, delete)
	public T add(T t) throws NullPointerException;
	public T getById(Integer id) throws NullPointerException;
	public Set<T> getAll();
	public void update(T t);
	public void delete(T t);
	
}
