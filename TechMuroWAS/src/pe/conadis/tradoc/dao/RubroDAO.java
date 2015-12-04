package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.Rubro;

public interface RubroDAO extends Dao<Rubro>{

	public List<Rubro> buscarRubro(Rubro rubro);
	public Rubro validaRubro(Rubro rubro);
	public Integer getMaxOrder(Rubro subCapitulo);
	public Rubro findByOrden(Rubro rubro, int valor);
	public List<Rubro> findBySubCapitulo(int id);
	public List<Rubro> getRubrosPorProductoId(Integer idproducto);
}