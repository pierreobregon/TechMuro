package pe.conadis.tradoc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.AficheDAO;
import pe.conadis.tradoc.entity.Afiche;

@Repository
public class AficheDaoImpl extends AbstractDAO<Afiche> implements AficheDAO {

	
	private static final Logger logger = Logger.getLogger(AficheDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Afiche> buscarAfiche(String criterio) throws Exception{
		
		List<Afiche> result  = this.getSessionFactory().getCurrentSession().createQuery("from Afiche where estado ='A' and upper(descripcion) like upper(:descripcion)").setParameter("descripcion",criterio).list();
		
		if(result!=null){
//			Iterator<Afiche> it = result.iterator();
			for(Afiche afiche: result){
				logger.debug("result .--->  "+afiche.getAficheOficinas().size());
				afiche.setAficheOficinas(null);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object> listarAll() throws Exception{
		
		List<Object> result  = this.getSessionFactory().getCurrentSession().createQuery("from AficheOficina a , Afiche b where a.afiche=b.idafiche").list();
		
		logger.debug("result .--->  "+result);
		if(result==null){
			
			return new ArrayList<Object>();
		}
		return result;
	}

}
