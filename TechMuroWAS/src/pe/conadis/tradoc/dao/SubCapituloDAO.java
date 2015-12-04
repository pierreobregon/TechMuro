package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.Subcapitulo;

public interface SubCapituloDAO extends Dao<Subcapitulo>{

	public List<Subcapitulo> buscarSubCapitulo(Subcapitulo subCapitulo);
	public Subcapitulo validaSubCapitulo(Subcapitulo subCapitulo);
	public Integer getMaxOrder(Subcapitulo subCapitulo);
	public Subcapitulo findByOrden(Subcapitulo capitulo, int valor);
	public List<Subcapitulo> findByCapitulo(int id);
	public Subcapitulo findByDescripcion(Subcapitulo capitulo);
	public Subcapitulo validaSubCapituloSinSubCapitulo(Subcapitulo subCapitulo);
	public List<Subcapitulo> getSubCapitulosPorProductoId(Integer idproducto);
}