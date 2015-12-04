package pe.conadis.tradoc.service;

import pe.conadis.tradoc.entity.Comunicado;

public interface ComunicadoManager extends Service<Comunicado>{
	
	public byte[] byId(Integer id) throws Exception;

}
