package pe.conadis.tradoc.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.DerivarDAO;
import pe.conadis.tradoc.dao.DocumentoDAO;
import pe.conadis.tradoc.entity.beans.Documento;
import pe.conadis.tradoc.service.DocumentoManager;

@Service
public class DocumentoManagerImpl extends ServiceImpl<Documento> implements DocumentoManager {
	
	private static final Logger logger = Logger.getLogger(DocumentoManagerImpl.class);
	
	@Autowired
	private DocumentoDAO documentoDAO;
	
	@Autowired
	private DerivarDAO derivarDAO;	
	

	
	@Override
	protected Dao<Documento> getDAO() {
		return documentoDAO;
	}
	

	
	

}
