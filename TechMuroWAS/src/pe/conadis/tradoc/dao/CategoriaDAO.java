package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.Categoria;

public interface CategoriaDAO extends Dao<Categoria>{

	public List<Categoria> buscarCategoria(Categoria categoria);
	public Categoria validaCategoria(Categoria categoria);
	public Integer getMaxOrder(Categoria categoria);
	public Categoria findByOrden(Categoria categoria, int valor);
	public List<Categoria> findByRubro(int id);
	public Categoria findByDescripcion(Categoria cat);
	public Categoria validaCategoriaSinCategoria(Categoria categoria);
	public List<Categoria> getCategoriasPorProductoId(Integer idproducto);
}