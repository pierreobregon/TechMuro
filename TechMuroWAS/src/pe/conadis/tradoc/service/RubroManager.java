package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.Rubro;

public interface RubroManager extends Service<Rubro> {

	public List<Rubro> buscarRubro(Rubro rubro);
	public String guardarRubro(Rubro rubro);
	public String editarRubro(Rubro rubro);
	public String eliminarRubro(int id);
	public boolean up(Integer id);
	public boolean down(Integer id);
	public List<Rubro> findBySubCapitulo(int id);
}
