package pe.conadis.tradoc.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.exception.DAOException;

public class AbstractDAO<E> implements Dao<E> {

	protected Class<E> entity;

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		entity = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
	}

	public Class<E> getEntity() {
		return entity;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void add(E entity) throws Exception {
		try {
			this.sessionFactory.getCurrentSession().save(entity);
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> getAll() throws Exception {
		try {
			StringBuilder sqlBuilder = new StringBuilder("SELECT obj FROM ")
					.append(entity.getSimpleName()).append(" obj WHERE obj.estado='A'");
			String sql = sqlBuilder.toString();
			return this.sessionFactory.getCurrentSession().createQuery(sql)
					.list();
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<E> getAllTableState() throws Exception {
		try {
			StringBuilder sqlBuilder = new StringBuilder("SELECT obj FROM ")
					.append(entity.getSimpleName()).append(" obj WHERE obj.indEstado='1'");
			String sql = sqlBuilder.toString();
			return this.sessionFactory.getCurrentSession().createQuery(sql)
					.list();
		} catch (Exception ex) {
			throw ex;
		}
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<E> getAnything() throws Exception {
		try {
			StringBuilder sqlBuilder = new StringBuilder("SELECT obj FROM ")
					.append(entity.getSimpleName()).append(" obj");
			String sql = sqlBuilder.toString();
			return this.sessionFactory.getCurrentSession().createQuery(sql)
					.list();
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public void delete(Integer id) throws Exception {
		try {
			E entity = findById(id);
			if (null != entity) {
				this.sessionFactory.getCurrentSession().delete(entity);
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}
	}
	


	@SuppressWarnings("unchecked")
	public List<E> pagination(Query q, int first, int max) throws Exception {
		return q.setFirstResult((first - 1) * max).setMaxResults(max).list();
	}

	public List<E> findByPage(int first, int max) throws Exception {
		StringBuilder sqlBuilder = new StringBuilder("SELECT obj FROM ")
				.append(entity.getSimpleName()).append(" obj");
		String sql = sqlBuilder.toString();
		return this.pagination(this.sessionFactory.getCurrentSession()
				.createQuery(sql), first, max);
	}

	@SuppressWarnings("unchecked")
	@Override
	public E findById(Integer id) throws Exception {
		return (E) sessionFactory.getCurrentSession().get(entity, id);
	}
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void update(E entity) throws Exception {
		try {
			if (null != entity) {
				this.sessionFactory.getCurrentSession().update(entity);
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}
	}

	@Override
	public void deleteFisico(E entity) throws Exception {
		try {
			if (null != entity) {
				this.sessionFactory.getCurrentSession().delete(entity);
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}
		
	}
	

}
