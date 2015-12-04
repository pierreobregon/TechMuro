package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.Notaria;

public interface NotariaDAO extends Dao<Notaria>  {

	public List<Notaria> buscarNotaria(String nombre);
	
	public List<Notaria> buscarNotariaEquals(String nombre);

	public Notaria getNotariaPorNombreYPlazaId(String nombreNotaria,
			Integer idplaza);

	public List<Notaria> getNotariasPorPlaza(Integer idplaza);
}
