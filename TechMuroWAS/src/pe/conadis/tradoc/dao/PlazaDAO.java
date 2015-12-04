package pe.conadis.tradoc.dao;

import pe.conadis.tradoc.entity.Plaza;

public interface PlazaDAO extends Dao<Plaza>{

	
	public Plaza findByNombre(String nombre);
	
}
