package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.NotariaContrato;

public interface NotariaContratoDAO extends Dao<NotariaContrato>{
	
	public List<NotariaContrato> getNotariasContratos(Integer notariaId, Integer contratoId);
	
	public List<NotariaContrato> getNotariasContratosOrdenados(Integer idnotaria);
	
}