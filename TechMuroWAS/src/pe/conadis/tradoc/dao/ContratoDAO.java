package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.Contrato;
import pe.conadis.tradoc.entity.NotariaContrato;
import pe.conadis.tradoc.entity.Plaza;

public interface ContratoDAO extends Dao<Contrato>  {

	public List<Contrato> contratoList();
	public Contrato findByNombreGastos(String descripcion, String gastos);
	
	public List<NotariaContrato> findByPlaza(Plaza plaza);
	
}
