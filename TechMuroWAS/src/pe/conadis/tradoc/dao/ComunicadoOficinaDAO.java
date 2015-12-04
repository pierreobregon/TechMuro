package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.ComunicadoOficina;

public interface ComunicadoOficinaDAO extends Dao<ComunicadoOficina> {
	
	
	public List<ComunicadoOficina> getCO() throws Exception;
	
	public List<ComunicadoOficina> buscarComunicado(String criterio) throws Exception;
	
	public List<ComunicadoOficina> deleteComunicado(Integer criterio1,Integer criterio2) throws Exception;
	
	public List<ComunicadoOficina> buscarComunicadoTodas(String codigoOficina) throws Exception;

	public List<ComunicadoOficina> getComunicadosPorOficina(Integer idOficina);
}
