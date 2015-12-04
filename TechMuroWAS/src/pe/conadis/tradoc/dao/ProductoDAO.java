package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.Producto;

public interface ProductoDAO extends Dao<Producto>{

	public List<Producto> buscarProducto(String criterio, String tipoCliente);

	public List<Producto> buscarProductoOrden(String tipoCliente, String orden);
	
	public Producto findByOrden(Producto producto, int valor);
	
	public Integer getMaxOrder(String tipoCliente);
	
	public Producto validaNombre(Producto producto);

	public List<Producto> buscarProducto(String criterio, String tipoCliente,
			boolean conCapitulos);

	public List<Producto> getProductosPorTipoCliente(String codigoTipoCliente);
}