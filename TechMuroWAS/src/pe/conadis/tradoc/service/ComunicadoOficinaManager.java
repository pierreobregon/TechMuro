package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.ComunicadoOficina;

public interface ComunicadoOficinaManager extends Service<ComunicadoOficina> {
	
	public List<ComunicadoOficina> getCO() throws Exception;
	
	public List<ComunicadoOficina> buscarComunicado(String criterio) throws Exception;
		
	public Integer deleteComunicado(Integer idA, Integer idO) throws Exception;
	
	public ComunicadoOficina byIdCO(Integer criterio1, Integer criterio2) throws Exception;

	public List<ComunicadoOficina> buscarComunicadoTodas(String codigoOficina) throws Exception ;

	public boolean oficinaTieneComunicados(Integer oficinaId);
}
