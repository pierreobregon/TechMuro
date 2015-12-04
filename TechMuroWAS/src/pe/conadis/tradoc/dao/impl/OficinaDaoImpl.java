package pe.conadis.tradoc.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.OficinaDAO;
import pe.conadis.tradoc.entity.AficheOficina;
import pe.conadis.tradoc.entity.ComunicadoOficina;
import pe.conadis.tradoc.entity.Oficina;
import pe.conadis.tradoc.entity.beans.Accion;

@Repository
public class OficinaDaoImpl extends AbstractDAO<Oficina> implements OficinaDAO{
	
	
	private static final Logger logger = Logger.getLogger(AficheOficinaDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Oficina> getOficinasFilter() throws Exception {
		try {
			
			List<Object> x = this.getSessionFactory().getCurrentSession().createQuery("from AficheOficina where oficina = 0").list();
			
			logger.debug("contador Todas -- > "+ x.size());
			
			List<Oficina> result = this.getSessionFactory().getCurrentSession().createQuery("from Oficina where estado ='A'").list();
			
			List<Oficina> filter = new ArrayList<Oficina>();
			
			for(Oficina a : result){
				if((a.getAficheOficinas().size() + x.size())<8){
					logger.debug("add - " + a.getCodigo());
					filter.add(a);
				}
			}
			
			for(Oficina b: filter){
				logger.debug("Nrp de Ofi -- "+b.getAficheOficinas().size());
				logger.debug("Nro de Afiche Ofi -- "+b.getAficheOficinas().size());
				Set<AficheOficina> aficheOficinas = b.getAficheOficinas();
				
				for(AficheOficina n : aficheOficinas){
					logger.debug("id Oficina-- "+n.getAfiche().getIdafiche());
					logger.debug("Desc Oficina -- "+n.getAfiche().getDescripcion());
					logger.debug(" id  Afiche-- "+n.getOficina().getIdoficina());
				}
				logger.debug("Afiches Oficinas -- "+b.getAficheOficinas());
			}
			logger.debug("total : " + filter.size());
			
		
			return filter;
			
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Transactional
	@Override
	public Oficina getOficinasVisor(Integer id) throws Exception {
		try {
			
			Oficina oficina = (Oficina) this.getSessionFactory().getCurrentSession().get(Oficina.class, id);
			
			logger.debug("contador Todas -- > "+ oficina.getAficheOficinas().size());
			
			for(AficheOficina a : oficina.getAficheOficinas()){
				
				logger.debug("id Oficina-- "+a.getAfiche().getIdafiche());
				logger.debug("Desc Oficina -- "+a.getAfiche().getDescripcion());
				logger.debug(" id  Afiche-- "+a.getOficina().getIdoficina());
			}
			
			return oficina;
			
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Oficina getOficinasByCodigoVisor(String criterio) throws Exception {
		try {
			List<Oficina> result = this.getSessionFactory().getCurrentSession().createQuery("from Oficina where TRIM(LEADING '0' FROM upper(codigo)) = upper(:codigo)").setParameter("codigo",criterio).list();
			
			Oficina oficina = result.get(0);
			logger.debug("contador Todas -- > "+ oficina.getAficheOficinas().size());
			
			for(AficheOficina a : oficina.getAficheOficinas()){
				
				logger.debug("id Oficina-- "+a.getAfiche().getIdafiche());
				logger.debug("Desc Oficina -- "+a.getAfiche().getDescripcion());
				logger.debug(" id  Afiche-- "+a.getOficina().getIdoficina());
			}
			
			for(ComunicadoOficina a : oficina.getComunicadoOficinas()){
				
				logger.debug("id Oficina-- "+a.getComunicado().getIdcomunicado());
				logger.debug("Desc Oficina -- "+a.getComunicado().getTitulo());
				logger.debug(" id  Afiche-- "+a.getOficina().getIdoficina());
			}
			
			return oficina;
			
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Transactional
	@Override
	public Oficina getOficinasByCodigo(String codigoOficina) throws Exception {
		try {
			Oficina result = (Oficina)this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"from Oficina where upper(codigo) = upper(:codigo) and estado = 'A'")
					.setParameter("codigo", codigoOficina).uniqueResult();
			
			return result;
			 
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Oficina> getOficinasActivas() throws Exception {
		try {
			
			List<Oficina> result = this.getSessionFactory().getCurrentSession().createQuery("from Oficina where estado ='A'").list();
			
			logger.debug("dimension RESULT -- "+result.size());
			for(Oficina a : result){
				logger.debug("ofi id 1-- "+a.getPlaza().getIdplaza());
				logger.debug("ofi id 1-- "+a.getPlaza().getNombre());
			}
			
			return result;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Oficina> buscarOficina(String criterio) throws Exception{
		
		List<Oficina> result  = this.getSessionFactory().getCurrentSession().createQuery("from Oficina where estado='A'and upper(nombre) like upper(:nombre)").setParameter("nombre",criterio).list();
		
			for(Oficina a: result){
				logger.debug("ofi id 1-- "+a.getPlaza().getIdplaza());
				logger.debug("ofi id 1-- "+a.getPlaza().getNombre());
			}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Oficina> findByNombre(String nombre){
		return this.getSessionFactory().getCurrentSession().
				createQuery("from Oficina where trim(upper(nombre)) =trim(upper(:nombre)) and estado = 'A'")  
		          .setParameter("nombre", nombre).list();
		          
	}

	@SuppressWarnings("unchecked")
	public List<Oficina> findByCodigo(String codigo){
		return this.getSessionFactory().getCurrentSession().
				createQuery("from Oficina where trim(upper(codigo)) = trim(upper(:codigo)) and estado = 'A'")  
		          .setParameter("codigo", codigo).list();
		          
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Oficina getOficinaByIp(String ip) {
		List<Oficina> lista = this.getSessionFactory().getCurrentSession().
				createQuery("from Oficina where trim(ip) =trim(:ip) and estado = 'A'")  
		          .setParameter("ip", ip).list();
		
		return lista.size() == 0 ? null : lista.get(0);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Oficina> getOficinasPorPlaza(Integer idplaza) {
		List<Oficina> lista = this.getSessionFactory().getCurrentSession().
				createQuery("from Oficina where plaza.idplaza =:idplaza and estado = 'A'")  
		          .setParameter("idplaza", idplaza).list();
		
		return lista;
	}


}
