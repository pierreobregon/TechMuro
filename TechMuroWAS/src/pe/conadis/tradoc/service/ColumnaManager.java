package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.Columna;

public interface ColumnaManager extends Service<Columna> {

	public boolean deleteByRubro(Columna columna);
	public List<Columna> findByRubro(Columna columna);
	
	public Columna findByPosicion(Columna columna);
}
