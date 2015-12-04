package pe.conadis.tradoc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.DerivarDAO;
import pe.conadis.tradoc.dao.DocumentoDAO;
import pe.conadis.tradoc.entity.beans.Derivar;
import pe.conadis.tradoc.entity.beans.Documento;
import pe.conadis.tradoc.service.DerivarManager;

@Service
public class DerivarManagerImpl extends ServiceImpl<Derivar> implements DerivarManager {
	
	private static final Logger logger = Logger.getLogger(DerivarManagerImpl.class);
	
	@Autowired
	private DocumentoDAO documentoDAO;
	
	@Autowired
	private DerivarDAO derivarDAO;	
	

	
	@Override
	protected Dao<Derivar> getDAO() {
		return derivarDAO;
	}
	

	@Override
	@Transactional
	public List <Derivar> listarDocumentosDerivarExterno(Documento documento) throws Exception {
		
		return derivarDAO.listarDocumentosDerivarExterno(documento);
	}
	

}
