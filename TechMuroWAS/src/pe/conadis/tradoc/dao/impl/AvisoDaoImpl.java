package pe.conadis.tradoc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.AvisoDAO;
import pe.conadis.tradoc.entity.Aviso;

@Repository
public class AvisoDaoImpl extends AbstractDAO<Aviso> implements AvisoDAO {

	private static final Logger logger = Logger.getLogger(AvisoDaoImpl.class);

	@Override
	public void delete(Integer id) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Aviso> pagination(Query q, int first, int max) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aviso> findByPage(int first, int max) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aviso> getAnything() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Aviso> buscarAviso(String criterio) throws Exception {
		
		List<Aviso> result  = this.getSessionFactory().getCurrentSession().createQuery("from Aviso where estado='A' and upper(titulo) like upper(:titulo)").setParameter("titulo",criterio).list();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Aviso> listarAll() throws Exception {
		
		List<Aviso> result  = this.getSessionFactory().getCurrentSession().createQuery("from Aviso where estado='A' order by idaviso asc").list();
			
		logger.debug("Listar Aviso BBVA .--->  "+result);
		if(result==null){
			return new ArrayList<Aviso>();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Aviso> deleteAviso(Integer criterio) throws Exception {

      List<Aviso> result  = this.getSessionFactory().getCurrentSession().createQuery("from Aviso where estado='A' and idaviso = (:criterio1) ").setParameter("criterio1",criterio).list();
		
		logger.debug(" truchaso-- " + result);
			for(Aviso a: result){
				logger.debug("Aviso ELiminado id -- "+a.getIdaviso());
			}
			return result;
		
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	@Transactional
	public List<Aviso> getCO() throws Exception {
        try {
			
			
			List<Aviso> result = this.getSessionFactory().getCurrentSession().createQuery("from Aviso where estado='A' order by idaviso asc").list();
			logger.debug("dimension RESULT -- "+result.size());
			
			if(result==null){	
				return new ArrayList<Aviso>();
			}
			
			for(Aviso a : result){			
				logger.debug(" getCO -- Aviso -- "+a.getTitulo());
			}
			
			return result;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Aviso> getAvisoFilter() throws Exception {
		try {
						
			List<Aviso> result = this.getSessionFactory().getCurrentSession().createQuery("from Aviso where estado ='A'").list();
			List<Aviso> filter = new ArrayList<Aviso>();
			
			for(Aviso a : result){
				filter.add(a);
			}
			
			return filter;
			
		} catch (Exception ex) {
			throw ex;
		}
	}
	

}
