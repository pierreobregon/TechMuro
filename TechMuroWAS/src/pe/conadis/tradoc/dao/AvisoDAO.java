package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.Aviso;

public interface AvisoDAO extends Dao<Aviso> {

	public List<Aviso> buscarAviso(String criterio) throws Exception;

	public List<Aviso> listarAll() throws Exception;
	
	public List<Aviso> deleteAviso(Integer criterio) throws Exception;
	
	public List<Aviso> getCO() throws Exception;
	
	public List<Aviso> getAvisoFilter() throws Exception;
}
