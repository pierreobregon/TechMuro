package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.Subcapitulo;

public interface SubCapituloManager extends Service<Subcapitulo> {

	public List<Subcapitulo> findByCapitulo(int id);
	public List<Subcapitulo> buscarSubCapitulo(Subcapitulo subCapitulo);
	public String guardarSubCapitulo(Subcapitulo subCapitulo);
	public String editarSubCapitulo(Subcapitulo subCapitulo);
	public String eliminarSubCapitulo(int id);
	public boolean up(Integer id);
	public boolean down(Integer id);
	public Subcapitulo findByDescripcion(Subcapitulo subCapitulo);
	
}
