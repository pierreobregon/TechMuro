package pe.conadis.tradoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.PlazaDAO;
import pe.conadis.tradoc.entity.Plaza;
import pe.conadis.tradoc.service.PlazaManager;

@Service
public class PlazaManagerImpl extends ServiceImpl<Plaza> implements PlazaManager {
	
	@Autowired
	private PlazaDAO plazaDAO;
	@Override
	protected Dao<Plaza> getDAO() {
		return plazaDAO; 
	}

}
