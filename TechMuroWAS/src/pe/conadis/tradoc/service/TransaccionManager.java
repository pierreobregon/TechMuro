package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;

public interface TransaccionManager extends Service<Transaccion> {

	public List<Transaccion> findByCategoria(int id);
	public List<Transaccion> buscarTransaccion(Transaccion transaccion);
	public String guardarTransaccion(Transaccion transaccion);
	public String editarTransaccion(Transaccion transaccion);
	public String eliminarTransaccion(int id);
	public boolean up(Integer id);
	public boolean down(Integer id);
	public List<TransaccionDetalle> findDetalle(Transaccion transaccion);
	public TransaccionDetalle findByPosicion(TransaccionDetalle transaccion);
	public boolean deleteByTransaccion(Transaccion transaccion);
}
