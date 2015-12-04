package pe.conadis.tradoc.dao;

import java.util.List;

import org.hibernate.Query;

public interface Dao<E> {
	
	public void add(E entity) throws Exception;
	public List<E> getAll() throws Exception;
	public List<E> getAllTableState() throws Exception;
	public E findById(Integer id) throws Exception;
	public void delete(Integer id) throws Exception;
	public void update(E entity) throws Exception;
	public List<E> pagination(Query q, int first, int max) throws Exception;
	public List<E> findByPage(int first, int max) throws Exception;
	public List<E> getAnything() throws Exception;
	public void deleteFisico(E entity) throws Exception;

}
