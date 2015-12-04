package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;

public interface TransaccionDetalleDAO extends Dao<TransaccionDetalle>{

	List<TransaccionDetalle> getTransaccioneDetallePorProductoId(Integer idproducto);

	TransaccionDetalle obtenerTransaccion(Integer idtransaccion);

}
