package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.AficheOficina;

public interface AficheOficinaDAO extends Dao<AficheOficina> {
	
	public List<AficheOficina> getAO() throws Exception;
	
	public List<AficheOficina> buscarAfi(String criterio) throws Exception;
	
	public List<AficheOficina> deleteAfi(Integer criterio1,Integer criterio2) throws Exception;

	public List<AficheOficina> getAfichesPorOficina(Integer idOficina) ;

}
