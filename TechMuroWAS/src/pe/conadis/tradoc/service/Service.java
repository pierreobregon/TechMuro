package pe.conadis.tradoc.service;

import java.util.List;

public interface Service<E> {

	public void add(E entity) throws Exception;
    public List<E> getAll() throws Exception;
    public void delete(Integer id) throws Exception;
    public E finById(Integer id) throws Exception;
    public void update(E entity) throws Exception;
    public List<E> getAnything() throws Exception;
    
}
