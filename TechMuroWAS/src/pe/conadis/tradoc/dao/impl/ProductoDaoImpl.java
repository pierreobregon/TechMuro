package pe.conadis.tradoc.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.ProductoDAO;
import pe.conadis.tradoc.entity.Producto;

@Repository
public class ProductoDaoImpl extends AbstractDAO<Producto> implements
		ProductoDAO {


	@Transactional
	public List<Producto> buscarProducto(String criterio, String tipoCliente) {
		return buscarProducto(criterio, tipoCliente, false);
	}
	
	@Transactional
	public List<Producto> buscarProductoOrden(String tipoCliente, String orden) {
		
		List<Producto> result = new ArrayList<Producto>();
		try{
			
			if(orden.equals("no")){
				result = this
						.getSessionFactory()
						.getCurrentSession()
						.createQuery("from Producto "
								+ "where tipoCliente like :tipoCliente and estado = 'A' "
								+ "order by orden asc")
						.setParameter("tipoCliente", tipoCliente).list();
			}else{
				result = this
						.getSessionFactory()
						.getCurrentSession()
						.createQuery("from Producto "
								+ "where tipoCliente like :tipoCliente and estado = 'A' "
								+ "order by nombre asc")
						.setParameter("tipoCliente", tipoCliente).list();
			}
			 
			
		}catch(Exception e){
			
		}
		
		return result;
	}
	
	@Transactional
	public Producto findByOrden(Producto producto, int valor){
		
		return (Producto)this.getSessionFactory().getCurrentSession().
				createQuery("from Producto where orden =:orden and tipocliente=:tipocliente")  
		          .setParameter("orden", producto.getOrden()+valor)
		          .setParameter("tipocliente", producto.getTipocliente())
		          .uniqueResult();
	}
	
	@Transactional
	public Integer getMaxOrder(String tipoCliente){
		
		return (Integer)this.getSessionFactory().getCurrentSession().
				createQuery("select nvl(max(orden),0)+1 from Producto where tipocliente=:tipocliente")  
		          .setParameter("tipocliente", tipoCliente)
		          .uniqueResult();
	}
	
	@Transactional
	public Producto validaNombre(Producto producto){
		
		return (Producto)this.getSessionFactory().getCurrentSession().
				createQuery("from Producto where trim(upper(nombre)) =trim(upper(:nombre)) "
							+ "and tipocliente=:tipocliente and estado = 'A'")  
		          .setParameter("nombre", producto.getNombre())
		          .setParameter("tipocliente", producto.getTipocliente())
		          .uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Producto> buscarProducto(String criterio, String tipoCliente,
			boolean conCapitulos) {
		
		try {
			List<Producto> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Producto "
							+ "where tipoCliente like :tipoCliente "
							+ "and upper(nombre) like upper(:nombre) and estado = 'A' "
							+ "order by orden asc")
					.setParameter("nombre", "%"+criterio+"%")
					.setParameter("tipoCliente", tipoCliente).list();

			if (!conCapitulos && result != null) {
				Iterator<Producto> it = result.iterator();
				while (it.hasNext()) {
					it.next().setCapitulos(null);
				}
			}
			return result;
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Producto> getProductosPorTipoCliente(String codigoTipoCliente) {
		
		List<Producto> result = this
				.getSessionFactory()
				.getCurrentSession()
				.createQuery("from Producto "
						+ "where tipoCliente = :tipoCliente and estado = 'A' "
						+ "order by orden asc")
				.setParameter("tipoCliente", codigoTipoCliente).list();
		
		return result;
	}
	}
	

