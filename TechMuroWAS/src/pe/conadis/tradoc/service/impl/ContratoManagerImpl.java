package pe.conadis.tradoc.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.ContratoDAO;
import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.OficinaDAO;
import pe.conadis.tradoc.entity.Contrato;
import pe.conadis.tradoc.entity.Notaria;
import pe.conadis.tradoc.entity.NotariaContrato;
import pe.conadis.tradoc.entity.Oficina;
import pe.conadis.tradoc.service.ContratoManager;

@Service
public class ContratoManagerImpl extends ServiceImpl<Contrato> implements ContratoManager {
	
	private static final Logger logger = Logger.getLogger(ContratoManagerImpl.class);
	
	@Autowired
	private ContratoDAO contratoDAO;
	@Autowired
	private OficinaDAO oficinaDAO;
	
	@Override
	protected Dao<Contrato> getDAO() {
		return contratoDAO; 
	}

	@Override
	public List<Contrato> contratoList() {
		
		return contratoDAO.contratoList();
	}
	
	@Override
	@Transactional 
	public List<NotariaContrato> findByCodigoOficina(String codigoOficina) {
		try{
			
			Oficina oficina = oficinaDAO.getOficinasByCodigo(codigoOficina);
			
			logger.debug(oficina.getPlaza().getIdplaza());
			
			List<NotariaContrato> notariaContratos = contratoDAO.findByPlaza(oficina.getPlaza());
			
			for(NotariaContrato lista : notariaContratos){
				logger.debug(lista.getContrato().getDescripcion());
				logger.debug(lista.getContrato().getGastos());
				logger.debug(lista.getNotaria().getNombre());
			}
			
			
			return notariaContratos;
		}catch(Exception ex){
			logger.error("agregarNotariaContrato -->"+ex);
			return null;
		}
	}
	

}
