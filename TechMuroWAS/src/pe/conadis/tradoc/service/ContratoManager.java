package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.Contrato;
import pe.conadis.tradoc.entity.NotariaContrato;

public interface ContratoManager extends Service<Contrato> {

	public List<Contrato> contratoList();
	
	public List<NotariaContrato> findByCodigoOficina(String codigoOficina);
}
