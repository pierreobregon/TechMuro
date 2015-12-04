package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.Aviso;


public interface AvisoManager extends Service<Aviso> {

	public List<Aviso> getCO() throws Exception;
	
	public List<Aviso> listarAll() throws Exception;
	
	public List<Aviso> buscarAviso(String criterio) throws Exception;
	
	public void deleteAviso(Integer id) throws Exception;
	
	public List<Aviso> getAvisoFilter() throws Exception;
	
}
