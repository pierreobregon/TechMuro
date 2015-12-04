package pe.conadis.tradoc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.TransaccionDetalleDAO;
import pe.conadis.tradoc.dao.VariableDAO;
import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;
import pe.conadis.tradoc.entity.VariablesGenerales;
import pe.conadis.tradoc.service.TransaccionDetalleManager;
import pe.conadis.tradoc.service.VariableManager;
import pe.conadis.tradoc.util.Constants;

@Service
public class TransaccionDetalleManagerImpl extends ServiceImpl<TransaccionDetalle> implements TransaccionDetalleManager {
	
	private static final Logger logger = Logger.getLogger(TransaccionDetalleManagerImpl.class);
	@Autowired
	private TransaccionDetalleDAO transaccionDetalleDAO;
	
	@Override
	protected Dao<TransaccionDetalle> getDAO(){
		return transaccionDetalleDAO;
	}

	@Transactional
	public void actualizarTransaccionEnDetalle(Transaccion transaccion) {
		TransaccionDetalle transaccionDetalle = transaccionDetalleDAO.obtenerTransaccion(transaccion.getIdtransaccion());
		transaccionDetalle.setContenido(transaccion.getNombre());
		try {
			transaccionDetalleDAO.update(transaccionDetalle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



}
