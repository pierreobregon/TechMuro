package pe.conadis.tradoc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.AvisoDAO;
import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.entity.Aviso;
import pe.conadis.tradoc.service.AvisoManager;

@Service
public class AvisoManagerImpl extends ServiceImpl<Aviso> implements AvisoManager {

	private static final Logger logger = Logger.getLogger(AvisoManagerImpl.class);
	
	

	@Override
	public List<Aviso> getAnything() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<Aviso> getCO() throws Exception {
		return avisoDAO.getCO();
	}

	@Override
	@Transactional
	public List<Aviso> listarAll() throws Exception {
		return avisoDAO.listarAll();
	}

	@Override
	@Transactional
	public List<Aviso> buscarAviso(String criterio) throws Exception {
		return avisoDAO.buscarAviso(criterio);
	}

	@Override
	@Transactional
	public void deleteAviso(Integer id) throws Exception {
		
		Aviso avi = avisoDAO.findById(id);
	    avi.setEstado('I');
		logger.debug(avi.getTitulo());
                         					
	}
	
	@Override
	@Transactional
	public List<Aviso> getAvisoFilter() throws Exception {

		return avisoDAO.getAvisoFilter();
	}

	@Autowired
	private AvisoDAO avisoDAO;
	
	@Override
	protected Dao<Aviso> getDAO() {
		return avisoDAO;
	}

}
