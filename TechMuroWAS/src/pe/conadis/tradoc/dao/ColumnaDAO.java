package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.Columna;

public interface ColumnaDAO extends Dao<Columna>{

	public List<Columna> findByRubro(Columna columna);
	public Columna findByPosicion(Columna columna);
	public List<Columna> getColumnasRubroPorProductoId(Integer idproducto);
}
