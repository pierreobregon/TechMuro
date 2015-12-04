package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.AficheOficinaDAO;
import pe.conadis.tradoc.entity.AficheOficina;

@Repository
public class AficheOficinaDaoImpl extends AbstractDAO<AficheOficina> implements AficheOficinaDAO{
	
	private static final Logger logger = Logger.getLogger(AficheOficinaDaoImpl.class);
	
//	@Autowired
//	private SessionFactory sessionFactory;
//	
//	protected Class<AficheOficina> a;
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AficheOficina> getAO() throws Exception {
		try {
			
			List<AficheOficina> result = this.getSessionFactory().getCurrentSession().createQuery("from AficheOficina").list();
			
//			StringBuilder sqlBuilder = new StringBuilder("SELECT obj FROM ")
//					.append(a.getSimpleName()).append(" obj");
//			String sql = sqlBuilder.toString();
			
//			List<AficheOficina> l = this.sessionFactory.getCurrentSession().createQuery(sql).list();
//			List<AficheOficina> l = result;
			logger.debug("dimension RESULT -- "+result.size());
			for(AficheOficina a : result){
			
				logger.debug("ofi id 1-- "+a.getOficina().getIdoficina());
				logger.debug("ofi id 2-- "+a.getOficina().getNombre());
				logger.debug("ofi id 3-- "+a.getAfiche().getIdafiche());
				logger.debug("ofi id 4-- "+a.getAfiche().getDescripcion());
			}
			return result;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AficheOficina> buscarAfi(String criterio) throws Exception{
		
		List<AficheOficina> result  = this.getSessionFactory().getCurrentSession().createQuery("from AficheOficina where afiche.estado='A'and upper(afiche.descripcion) like upper(:descripcion)").setParameter("descripcion",criterio).list();
		
		logger.debug(" truchaso-- " + result);
//			Iterator<Afiche> it = result.iterator();
			for(AficheOficina a: result){
				logger.debug("ofi id -- "+a.getOficina().getIdoficina());
				logger.debug("ofi id -- "+a.getOficina().getNombre());
				logger.debug("ofi id -- "+a.getAfiche().getIdafiche());
				logger.debug("ofi id -- "+a.getAfiche().getDescripcion());
			}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AficheOficina> deleteAfi(Integer criterio1,Integer criterio2) throws Exception{
		
		List<AficheOficina> result  = this.getSessionFactory().getCurrentSession().createQuery("from AficheOficina where afiche.estado='A'and afiche.idafiche = (:criterio1) and oficina.idoficina =(:criterio2)").setParameter("criterio1",criterio1).setParameter("criterio2", criterio2).list();
		
	
				
			for(AficheOficina a: result){
				logger.debug("ofi id -- "+a.getOficina().getIdoficina());
				logger.debug("ofi id -- "+a.getOficina().getNombre());
				logger.debug("ofi id -- "+a.getAfiche().getIdafiche());
				logger.debug("ofi id -- "+a.getAfiche().getDescripcion());
			}
			return result;

	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AficheOficina> getAfichesPorOficina(Integer idOficina) {
		List<AficheOficina> result  = this.getSessionFactory().getCurrentSession().createQuery("from AficheOficina where afiche.estado='A' and oficina.idoficina =(:criterio1)").setParameter("criterio1",idOficina).list();
		return result;
	}
	

}
