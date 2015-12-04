package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.beans.Derivar;
import pe.conadis.tradoc.entity.beans.Documento;

public interface DerivarDAO extends Dao<Derivar>{
	
	
	public List<Derivar> listarDocumentosDerivarExterno(Documento documento) throws Exception;
	
	
}
