package co.codehaven.seco.repository;

import java.util.List;

import co.codehaven.seco.entities.Manhole;
import co.codehaven.seco.exceptions.ManholeAlreadyInsertedException;
import co.codehaven.seco.exceptions.ManholeNotFoundException;

public interface RepositoryManhole {
	
	public void insert (Manhole info) throws ManholeAlreadyInsertedException;
	
	public void update (Manhole info) throws ManholeNotFoundException;
	
	public void remove (Integer id) throws ManholeNotFoundException;
	
	public Manhole search(Integer id) throws ManholeNotFoundException;
	
	public List<Manhole> getAll() throws ManholeNotFoundException;

	public boolean has(Integer id);

}
