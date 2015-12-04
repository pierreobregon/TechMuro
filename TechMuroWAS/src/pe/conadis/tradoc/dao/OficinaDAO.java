package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.Oficina;
import pe.conadis.tradoc.entity.beans.Accion;

public interface OficinaDAO extends Dao<Oficina>{
	
	public List<Oficina> getOficinasFilter() throws Exception;
	
	public Oficina getOficinasVisor(Integer id) throws Exception;
	
	public Oficina getOficinasByCodigoVisor(String criterio) throws Exception;

	public Oficina getOficinasByCodigo(String codigoOficina) throws Exception;
	
	public List<Oficina> getOficinasActivas() throws Exception;
	
	public List<Oficina> buscarOficina(String criterio) throws Exception;
	
	public List<Oficina> findByNombre(String nombre);
	
	public List<Oficina> findByCodigo(String codigo);

	public Oficina getOficinaByIp(String ip);

	public List<Oficina> getOficinasPorPlaza(Integer idplaza);

}
