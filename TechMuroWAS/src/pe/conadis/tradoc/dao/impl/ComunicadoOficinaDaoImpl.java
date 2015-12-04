package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.ComunicadoOficinaDAO;
import pe.conadis.tradoc.entity.ComunicadoOficina;

@Repository
public class ComunicadoOficinaDaoImpl  extends AbstractDAO<ComunicadoOficina> implements ComunicadoOficinaDAO{

	private static final Logger logger = Logger.getLogger(ComunicadoOficinaDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ComunicadoOficina> getCO() throws Exception {
		try {
			
			
			List<ComunicadoOficina> result = this.getSessionFactory().getCurrentSession().createQuery("from ComunicadoOficina").list();
			logger.debug("dimension RESULT -- "+result.size());
			if(result.size()>0){
			for(ComunicadoOficina a : result){
				
				logger.debug("ofi id 1-- "+a.getOficina().getIdoficina());
				logger.debug("ofi nombre 2-- "+a.getOficina().getNombre());
				logger.debug("Comu id 3-- "+a.getComunicado().getIdcomunicado());
				logger.debug("Comu nombre 4-- "+a.getComunicado().getTitulo());
			}}
			return result;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComunicadoOficina> buscarComunicado(String criterio) throws Exception{
		
		List<ComunicadoOficina> result  = this.getSessionFactory().getCurrentSession().createQuery("from ComunicadoOficina where comunicado.estado='A'and upper(comunicado.titulo) like upper(:titulo)").setParameter("titulo",criterio).list();
		
		logger.debug(" truchaso-- " + result);
//			Iterator<Afiche> it = result.iterator();
			for(ComunicadoOficina a: result){
				logger.debug("ofi id 1-- "+a.getOficina().getIdoficina());
				logger.debug("ofi nombre 2-- "+a.getOficina().getNombre());
				logger.debug("Comu id 3-- "+a.getComunicado().getIdcomunicado());
				logger.debug("Comu nombre 4-- "+a.getComunicado().getTitulo());
			}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ComunicadoOficina> buscarComunicadoTodas(String codigoOficina)
			throws Exception {

		List<ComunicadoOficina> result = this
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from ComunicadoOficina where oficina.codigo in ('Todas', :codigoOficina) order by fechacreacion desc")
				.setParameter("codigoOficina", codigoOficina)
				.list();

		logger.debug(" truchaso-- " + result);
		// Iterator<Afiche> it = result.iterator();
		for (ComunicadoOficina a : result) {
			logger.debug("ofi id 1-- " + a.getOficina().getIdoficina());
			logger.debug("ofi nombre 2-- " + a.getOficina().getNombre());
			logger.debug("Comu id 3-- " + a.getComunicado().getIdcomunicado());
			logger.debug("Comu nombre 4-- " + a.getComunicado().getTitulo());
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComunicadoOficina> deleteComunicado(Integer criterio1,Integer criterio2) throws Exception{
		
		List<ComunicadoOficina> result  = this.getSessionFactory().getCurrentSession().createQuery("from ComunicadoOficina where comunicado.estado='A'and comunicado.idcomunicado = (:criterio1) and oficina.idoficina =(:criterio2)").setParameter("criterio1",criterio1).setParameter("criterio2", criterio2).list();
		
		logger.debug(" truchaso-- " + result);
			for(ComunicadoOficina a: result){
				logger.debug("ofi id -- "+a.getOficina().getIdoficina());
				logger.debug("ofi id -- "+a.getOficina().getNombre());
				logger.debug("ofi id -- "+a.getComunicado().getIdcomunicado());
				logger.debug("ofi id -- "+a.getComunicado().getDescripcion());
			}
			return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComunicadoOficina> getComunicadosPorOficina(Integer idOficina) {


		List<ComunicadoOficina> result  = this.getSessionFactory().getCurrentSession().createQuery("from ComunicadoOficina where comunicado.estado='A' and oficina.idoficina =(:criterio1)").setParameter("criterio1",idOficina).list();
		return result;
	}
}
