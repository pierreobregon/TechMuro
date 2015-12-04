package pe.conadis.tradoc.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.NotaDAO;
import pe.conadis.tradoc.entity.Nota;
import pe.conadis.tradoc.service.NotaManager;

@Service
public class NotaManagerImpl extends ServiceImpl<Nota> implements NotaManager {
	
	private static final Logger logger = Logger.getLogger(NotaManagerImpl.class);
	@Autowired
	private NotaDAO notaDAO;
	
	@Override
	protected Dao<Nota> getDAO(){
		return notaDAO;
	}
	
}
