package co.codehaven.seco.datastore;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.FetchGroup;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import co.codehaven.seco.datastore.util.PMF;
import co.codehaven.seco.entities.Manhole;
import co.codehaven.seco.exceptions.ManholeAlreadyInsertedException;
import co.codehaven.seco.exceptions.ManholeNotFoundException;
import co.codehaven.seco.repository.RepositoryManhole;

public class DatastoreManhole implements RepositoryManhole{

	private List<Manhole> cache;
	
	private static DatastoreManhole instance;

	public synchronized static DatastoreManhole getInstance() {
		if (instance == null) {
			instance = new DatastoreManhole();
		}
		return instance;
	}
	
	private DatastoreManhole() {
		cache = new LinkedList<Manhole>();
	}

	@Override
	public void insert(Manhole info) throws ManholeAlreadyInsertedException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		if (has(info.getId()))
		{
			throw new ManholeAlreadyInsertedException();
		}
		
		Key key = KeyFactory.createKey(Manhole.class.getSimpleName(), info.getId());
		System.out.println("Insert: "+key);
       
        info.setKey(key);
		
		try{
			pm.makePersistent(info);			
		} finally {
			pm.close();
		}
	}

	@Override
	public void update(Manhole info) throws ManholeNotFoundException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.getFetchPlan().setGroup(FetchGroup.ALL);
	    
		try {
			Manhole toUpdate = search(info.getId());
	        toUpdate.update(info);
	        pm.makePersistent(toUpdate);
		} finally {
	        pm.close();
	    }

	}

	@Override
	public void remove(Integer id) throws ManholeNotFoundException {
		
		Manhole info;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
        try{
        	info = search(id);
        	pm.deletePersistent(info);
        }finally{
        	pm.close();
        }
		
	}

	@Override
	public Manhole search(Integer id) throws ManholeNotFoundException {

		Manhole info;
		Key key = KeyFactory.createKey(Manhole.class.getSimpleName(), id);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.getFetchPlan().setGroup(FetchGroup.ALL);
		
		System.out.println("Search: "+key);
        
        try{
        	info = pm.getObjectById(Manhole.class, key);
        } catch (Exception e) {
        	throw new ManholeNotFoundException();
        }finally{
        	pm.close();
        }
        
        if(info==null){
        	throw new ManholeNotFoundException();
        }
        
        return info;
	}
	
	@SuppressWarnings("unchecked")
	public List<Manhole> getAll(){

		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query query = pm.newQuery(Manhole.class); // Will query all from User class. Replace User with your class

		return (List<Manhole>) query.execute();
	}

	@Override
	public boolean has(Integer id) {
		
		try{
			search(id);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}
}
