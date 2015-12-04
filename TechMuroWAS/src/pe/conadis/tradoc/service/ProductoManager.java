package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.Producto;

public interface ProductoManager extends Service<Producto> {

	public List<Producto> buscarProducto(String criterio, String tipoCliente);
	public List<Producto> buscarProductoOrden(String tipoCliente);
	public boolean up(Integer id);
	public boolean down(Integer id);
	public String guardarProducto(Producto producto);
	public String editarProducto(Producto producto);
	public String eliminarProducto(int id);
	public List<Producto> buscarProductoConCapitulos(String string,
			String string2);
	public List<Producto> getProductosPorTipoCliente(String codigoTipoCliente);
}
