package pe.conadis.tradoc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import pe.conadis.tradoc.dao.ColumnaDAO;
import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.entity.Columna;
import pe.conadis.tradoc.service.ColumnaManager;

@Service
public class ColumnaManagerImpl extends ServiceImpl<Columna> implements ColumnaManager {
	
	private static final Logger logger = Logger.getLogger(ColumnaManagerImpl.class);
	@Autowired
	private ColumnaDAO columnaDAO;
	
	@Override
	protected Dao<Columna> getDAO(){
		return columnaDAO;
	}
	
	@Override
	@Transactional
	public boolean deleteByRubro(Columna columna){
		
		for(Columna col: columnaDAO.findByRubro(columna)){
			try {
				columnaDAO.deleteFisico(col);
			} catch (Exception e) {
				logger.error("Error eliminar Columna "+e);
				return false;
			}
			
		}

		return true;
	}
	
	@Override
	@Transactional
	public List<Columna> findByRubro(Columna columna){
		
		List<Columna> columnaList = columnaDAO.findByRubro(columna);
		

		return columnaList;
	}
	
	@Transactional
	public Columna findByPosicion(Columna columna){
		return columnaDAO.findByPosicion(columna);
	}
}
