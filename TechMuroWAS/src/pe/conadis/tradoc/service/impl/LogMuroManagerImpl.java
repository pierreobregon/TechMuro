package pe.conadis.tradoc.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.LogMuroDAO;
import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.service.LogMuroManager;

@Service
public class LogMuroManagerImpl extends ServiceImpl<LogMuro> implements LogMuroManager {
	
	@Autowired
	private LogMuroDAO logMuroDAO;
	
	@Override
	protected Dao<LogMuro> getDAO() {
		return logMuroDAO;
	}

	@Transactional
	public List<LogMuro> buscarLog(Date fechaIni, Date fechaFin) {
		List<LogMuro> result = logMuroDAO.buscarLog(fechaIni, fechaFin); 
		for(LogMuro log: result){
			System.out.println(log.getPrivilegio().getDescripcion());
		}
		
		return result;
	}

}
