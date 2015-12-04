package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.CategoriaDAO;
import pe.conadis.tradoc.dao.TransaccionDAO;
import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;

@Repository
public class TransaccionDaoImpl extends AbstractDAO<Transaccion> implements
	TransaccionDAO {

	private static final Logger logger = Logger.getLogger(TransaccionDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Transaccion> buscarTransaccion(Transaccion transaccion) {
		try {
			List<Transaccion> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Transaccion "
							+ "where categoria.idcategoria = :idcategoria "
							+ "and upper(nombre) like upper('%'||:nombre||'%') and estado = 'A' "
							+ "order by orden asc")
					.setParameter("nombre", transaccion.getNombre())
					.setParameter("idcategoria", transaccion.getCategoria().getIdcategoria()).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error buscarTransaccion ->" +ex);
			return null;
		}
	}
	
	@Transactional
	public Transaccion validaTransaccion(Transaccion transaccion){
		
		return (Transaccion)this.getSessionFactory().getCurrentSession().
				createQuery("from Transaccion where categoria.idcategoria= :idcategoria "
							+ "and trim(upper(nombre)) = trim(upper(:nombre)) and estado = 'A'")  
		          .setParameter("nombre", transaccion.getNombre())
		          .setParameter("idcategoria", transaccion.getCategoria().getIdcategoria())
		          .uniqueResult();
	}
	
	@Transactional
	public Integer getMaxOrder(Transaccion transaccion){
		
		return (Integer)this.getSessionFactory().getCurrentSession().
				createQuery("select nvl(max(orden),0)+1 from Transaccion "
							+ "where categoria.idcategoria=:idcategoria and estado = 'A'")  
		          .setParameter("idcategoria", transaccion.getCategoria().getIdcategoria())
		          .uniqueResult();
	}
	
	@Transactional
	public Transaccion findByOrden(Transaccion transaccion, int valor){
		
		return (Transaccion)this.getSessionFactory().getCurrentSession().
				createQuery("from Transaccion where orden =:orden and categoria.idcategoria=:idcategoria and estado = 'A'")
		          .setParameter("orden", transaccion.getOrden()+valor)
		          .setParameter("idcategoria", transaccion.getCategoria().getIdcategoria())
		          .uniqueResult();
	}
	
//	@Transactional
//	public Categoria findByDescripcion(Categoria categoria){
//		
//		return (Categoria)this.getSessionFactory().getCurrentSession().
//				createQuery("from Categoria where descripcion =:descripcion and capitulo.idcapitulo=:idcapitulo and estado = 'A'")
//		          .setParameter("descripcion", capitulo.getDescripcion())
//		          .setParameter("idcapitulo", capitulo.getCapitulo().getIdcapitulo())
//		          .uniqueResult();
//	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Transaccion> findByCategoria(int id) {

		try {

			List<Transaccion> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Transaccion "
							+ "where categoria.idcategoria = :idcategoria "
							+ "and estado = 'A' "
							+ "order by orden asc")
					.setParameter("idcategoria", id).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error findByRubro ->" +ex);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<TransaccionDetalle> findDetalle(Transaccion transaccion){
		
		List<TransaccionDetalle> result = this.getSessionFactory().getCurrentSession().
				createQuery("from TransaccionDetalle where transaccion.idtransaccion=:idtransaccion and estado = 'A' order by posicionx, posiciony")
		          .setParameter("idtransaccion", transaccion.getIdtransaccion())
		          .list();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public TransaccionDetalle findByPosicion(TransaccionDetalle transaccion){
		
		TransaccionDetalle result = (TransaccionDetalle)this.getSessionFactory().getCurrentSession().
				createQuery("from TransaccionDetalle "
						+ "where transaccion.idtransaccion=:idtransaccion "
						+ "and posicionx = :posicionx "
						+ "and posiciony = :posiciony")
						.setParameter("idtransaccion", transaccion.getTransaccion().getIdtransaccion())
		        		.setParameter("posicionx", transaccion.getPosicionx())
		        		.setParameter("posiciony", transaccion.getPosiciony())
		          .uniqueResult();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Transaccion> getTransaccionesPorProductoId(Integer idproducto) {
		
		try {
			List<Transaccion> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(" SELECT * FROM MURTEC.TRANSACCION WHERE IDPRODUCTO = :idproducto AND ESTADO = 'A' ORDER BY ORDEN ASC ").addEntity(Transaccion.class)
					.setParameter("idproducto", idproducto).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error buscarSubCapitulo ->" +ex);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public void deleteDetalle(Integer idDetalle) {
		
		try {
			this
			.getSessionFactory()
			.getCurrentSession()
			.createSQLQuery(" delete from transaccion_detalle where iddetalle = :iddet").setParameter("iddet",idDetalle).executeUpdate();
		} catch (Exception ex) {
			logger.error("Error buscarSubCapitulo ->" +ex);
		
		}
	}
	

	
}
