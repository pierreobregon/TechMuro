package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.Nota;

public interface NotaDAO extends Dao<Nota>{

	List<Nota> getNotasPorProductoId(Integer idproducto);

}
