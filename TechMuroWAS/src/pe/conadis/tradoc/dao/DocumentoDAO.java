package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.beans.Documento;

public interface DocumentoDAO extends Dao<Documento>{
	
	
	public List<Documento> listarDocumentosDerivarExterno(Documento documento) throws Exception;
	public List<Documento> obtenerDocumentosXEntidad(Documento documento)
			throws Exception;
	
	
}
