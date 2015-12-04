package pe.conadis.tradoc.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.ExpedienteDAO;
import pe.conadis.tradoc.entity.beans.Expediente;
import pe.conadis.tradoc.service.ExpedienteManager;

@Service
public class ExpedienteManagerImpl extends ServiceImpl<Expediente> implements ExpedienteManager{

	@Autowired
	private ExpedienteDAO expedienteDAO;
	
	private static final Logger logger = Logger.getLogger(ExpedienteManagerImpl.class);
	
	@Override
	protected Dao<Expediente> getDAO() {
		return expedienteDAO;
	}

}
