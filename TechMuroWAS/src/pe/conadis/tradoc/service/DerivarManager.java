package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.beans.Derivar;
import pe.conadis.tradoc.entity.beans.Documento;

public interface DerivarManager extends Service<Derivar>{
	
	public List<Derivar> listarDocumentosDerivarExterno(Documento documento) throws Exception;
}
