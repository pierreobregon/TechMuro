package pe.conadis.tradoc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.AficheOficinaDAO;
import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.entity.AficheOficina;
import pe.conadis.tradoc.service.AficheOficinaManager;

@Service
public class AficheOficinaManagerImpl extends ServiceImpl<AficheOficina> implements AficheOficinaManager{
	
	private static final Logger logger = Logger.getLogger(AficheOficinaManagerImpl.class);

	@Autowired
	private AficheOficinaDAO aficheOficinaDAO;
	
	@Override
	@Transactional
	protected Dao<AficheOficina> getDAO() {
		return aficheOficinaDAO;
	}

	@Override
	@Transactional
	public List<AficheOficina> getAO() throws Exception {
		return aficheOficinaDAO.getAO();
	}
	
	@Override
	@Transactional
	public List<AficheOficina> buscarAfi(String criterio) throws Exception {
		return aficheOficinaDAO.buscarAfi(criterio);
	}
	
	@Override
	@Transactional
	public Integer deleteAfi(Integer idA, Integer idO) throws Exception {
		Integer idAfiche = null;
		if(aficheOficinaDAO.deleteAfi(idA, idO).size() > 0){
			
			AficheOficina aficheOficina = aficheOficinaDAO.deleteAfi(idA, idO).get(0);
			logger.debug(aficheOficina.getAfiche());
			idAfiche = aficheOficina.getAfiche().getIdafiche();
			aficheOficinaDAO.deleteFisico(aficheOficina);
			
		}
		return idAfiche;
	}
	
	@Override
	@Transactional
	public AficheOficina byIdAO(Integer criterio1, Integer criterio2) throws Exception {
		return aficheOficinaDAO.deleteAfi(criterio1, criterio2).get(0);
	}
	
	@Override
	public boolean oficinaTieneAfiches(Integer oficinaId) {

//		// Se obtienen afiches activos para la oficina 0 (todas)
//		List<AficheOficina> afichesPorOficina0Todos = aficheOficinaDAO.getAfichesPorOficina(0);
//		if(afichesPorOficina0Todos.size() > 0) return true;
		
		// Se obtienen afiches activos para la oficina que llega como parametro
		List<AficheOficina> afichesPorOficina = aficheOficinaDAO.getAfichesPorOficina(oficinaId);
		if(afichesPorOficina.size() > 0) return true;
		
		return false;
	
	}
	

}
