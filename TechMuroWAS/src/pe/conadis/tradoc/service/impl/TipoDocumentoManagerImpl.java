package pe.conadis.tradoc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.TipoDocumentoDAO;
import pe.conadis.tradoc.dao.VariableDAO;
import pe.conadis.tradoc.entity.VariablesGenerales;
import pe.conadis.tradoc.entity.beans.TipoDocumento;
import pe.conadis.tradoc.service.TipoDocumentoManager;
import pe.conadis.tradoc.service.VariableManager;
import pe.conadis.tradoc.util.Constants;

@Service
public class TipoDocumentoManagerImpl extends ServiceImpl<TipoDocumento> implements TipoDocumentoManager {
	
	private static final Logger logger = Logger.getLogger(TipoDocumentoManagerImpl.class);
	@Autowired
	private TipoDocumentoDAO tipoDocumentoDAO;
	
	@Override
	protected Dao<TipoDocumento> getDAO(){
		return tipoDocumentoDAO;
	}
	
	@Transactional
	public List<TipoDocumento> listaTipoDocumento(){
		List<TipoDocumento> lista = new ArrayList<TipoDocumento>();
		try{
			lista = tipoDocumentoDAO.getAllTableState();
			
		}catch(Exception e){
			logger.error("Error al cargar lista tipo documentos ->"+e);
			return null;
		}
		
		return lista;
	}


	

}
