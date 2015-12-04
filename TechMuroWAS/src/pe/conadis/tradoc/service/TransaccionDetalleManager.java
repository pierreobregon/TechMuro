package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;
import pe.conadis.tradoc.entity.VariablesGenerales;

public interface TransaccionDetalleManager extends Service<TransaccionDetalle> {

	void actualizarTransaccionEnDetalle(Transaccion transaccion);

	
}
