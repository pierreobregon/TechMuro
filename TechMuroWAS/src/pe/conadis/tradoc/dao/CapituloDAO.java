package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.Capitulo;

public interface CapituloDAO extends Dao<Capitulo>{

	public List<Capitulo> buscarCapitulo(Capitulo capitulo);
	public List<Capitulo> findByProducto(int id);
	public Capitulo validaCapitulo(Capitulo capitulo);
	public Integer getMaxOrder(Capitulo capitulo);
	public Capitulo findByOrden(Capitulo capitulo, int valor);
	public Capitulo findByDescripcion(Capitulo cap);
	public Capitulo validaSinCapitulo(Capitulo capitulo);
	public List<Capitulo> getCapitulosPorProductoId(Integer idProducto);
}