package pe.conadis.tradoc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.ComunicadoOficinaDAO;
import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.entity.AficheOficina;
import pe.conadis.tradoc.entity.ComunicadoOficina;
import pe.conadis.tradoc.service.ComunicadoOficinaManager;

@Service
public class ComunicadoOficinaManagerImpl extends ServiceImpl<ComunicadoOficina> implements ComunicadoOficinaManager{
	
	private static final Logger logger = Logger.getLogger(ComunicadoOficinaManagerImpl.class);
	
	@Autowired
	private ComunicadoOficinaDAO comunicadoOficinaDAO;
	
	@Override
	protected Dao<ComunicadoOficina> getDAO() {

		return comunicadoOficinaDAO;
	}
	
	@Override
	@Transactional
	public List<ComunicadoOficina> getCO() throws Exception {
		return comunicadoOficinaDAO.getCO();
	}
	
	@Override
	@Transactional
	public List<ComunicadoOficina> buscarComunicado(String criterio) throws Exception {
		return comunicadoOficinaDAO.buscarComunicado(criterio);
	}
	
	@Override
	@Transactional
	public Integer deleteComunicado(Integer idA, Integer idO) throws Exception {
		
		Integer idComunicado = null;
		if(comunicadoOficinaDAO.deleteComunicado(idA, idO).size() > 0){			
			ComunicadoOficina comunicadoOficina = comunicadoOficinaDAO.deleteComunicado(idA, idO).get(0);
			logger.debug(comunicadoOficina.getComunicado());
			idComunicado = comunicadoOficina.getComunicado().getIdcomunicado();
			comunicadoOficinaDAO.deleteFisico(comunicadoOficina);			
		}
		return idComunicado;
	}
	
	@Override
	@Transactional
	public ComunicadoOficina byIdCO(Integer criterio1, Integer criterio2) throws Exception {
		return comunicadoOficinaDAO.deleteComunicado(criterio1, criterio2).get(0);
	}
	
	
	@Transactional
	public List<ComunicadoOficina> buscarComunicadoTodas(String codigoOficina) throws Exception {
		return comunicadoOficinaDAO.buscarComunicadoTodas(codigoOficina);
	}

	@Override
	public boolean oficinaTieneComunicados(Integer oficinaId) {
		
		
		// Se obtienen afiches activos para la oficina 0 (todas)
//		List<ComunicadoOficina> comunicadosPorOficina0Todos = comunicadoOficinaDAO.getComunicadosPorOficina(0);
//		if(comunicadosPorOficina0Todos.size() > 0) return true;
		
		// Se obtienen comunicados activos para la oficina que llega como parametro
		List<ComunicadoOficina> comunicadosPorOficina = comunicadoOficinaDAO.getComunicadosPorOficina(oficinaId);
		if(comunicadosPorOficina.size() > 0) return true;
		
		return false;
	}

}
