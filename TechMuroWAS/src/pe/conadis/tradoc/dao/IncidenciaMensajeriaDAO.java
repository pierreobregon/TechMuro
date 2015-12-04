package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.IncidenciaMensajeria;

public interface IncidenciaMensajeriaDAO extends Dao<IncidenciaMensajeria> {
	
	public List<IncidenciaMensajeria> obtenerIncidenciaMensajesXCodDocumento(Integer codDocumento) throws Exception;

}
