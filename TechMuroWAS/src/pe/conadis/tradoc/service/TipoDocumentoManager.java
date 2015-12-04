package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.beans.TipoDocumento;

public interface TipoDocumentoManager extends Service<TipoDocumento> {

	public List<TipoDocumento> listaTipoDocumento()  throws Exception;

}
