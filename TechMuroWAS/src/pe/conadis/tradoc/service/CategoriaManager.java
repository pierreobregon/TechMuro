package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Subcapitulo;

public interface CategoriaManager extends Service<Categoria> {

	public List<Categoria> findByRubro(int id);
	public List<Categoria> buscarCategoria(Categoria categoria);
	public String guardarCategoria(Categoria categoria);
	public String editarCategoria(Categoria categoria);
	public String eliminarCategoria(int id);
	public boolean up(Integer id);
	public boolean down(Integer id);
	public Categoria findByDescripcion(Categoria cat);
}
