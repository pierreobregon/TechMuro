package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;

public interface TransaccionDAO extends Dao<Transaccion>{

	public List<Transaccion> buscarTransaccion(Transaccion transaccion);
	public Transaccion validaTransaccion(Transaccion transaccion);
	public Integer getMaxOrder(Transaccion transaccion);
	public Transaccion findByOrden(Transaccion transaccion, int valor);
	public List<Transaccion> findByCategoria(int id);
	public List<TransaccionDetalle> findDetalle(Transaccion transaccion);
	public TransaccionDetalle findByPosicion(TransaccionDetalle transaccion);
	public List<Transaccion> getTransaccionesPorProductoId(Integer idproducto);
	public void deleteDetalle(Integer idDetalle);
}