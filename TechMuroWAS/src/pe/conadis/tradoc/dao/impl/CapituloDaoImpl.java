package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.controller.NotariaController;
import pe.conadis.tradoc.dao.CapituloDAO;
import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.Producto;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.util.Constants;

@Repository
public class CapituloDaoImpl extends AbstractDAO<Capitulo> implements
	CapituloDAO {

	private static final Logger logger = Logger.getLogger(CapituloDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Capitulo> buscarCapitulo(Capitulo capitulo) {

		try {

			List<Capitulo> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Capitulo "
							+ "where producto.idproducto = :idproducto "
							+ "and upper(nombre) like upper('%'||:nombre||'%') and estado = 'A' "
							+ "order by orden asc")
					.setParameter("nombre", capitulo.getNombre())
					.setParameter("idproducto", capitulo.getProducto().getIdproducto()).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error buscarCapitulo ->" +ex);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Capitulo> findByProducto(int id) {

		try {

			List<Capitulo> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Capitulo "
							+ "where producto.idproducto = :idproducto "
							+ "and estado = 'A' "
							+ "order by orden asc")
					.setParameter("idproducto", id).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error findByProducto ->" +ex);
			return null;
		}
	}
	
	@Transactional
	public Capitulo validaCapitulo(Capitulo capitulo){
		
		return (Capitulo)this.getSessionFactory().getCurrentSession().
				createQuery("from Capitulo where producto.idproducto = :idproducto "
							+ "and trim(upper(nombre)) =trim(upper(:nombre)) and estado = 'A'")  
		          .setParameter("nombre", capitulo.getNombre())
		          .setParameter("idproducto", capitulo.getProducto().getIdproducto())
		          .uniqueResult();
	}
	
	@Transactional
	public Capitulo validaSinCapitulo(Capitulo capitulo){
		
		return (Capitulo)this.getSessionFactory().getCurrentSession().
				createQuery("from Capitulo where producto.idproducto = :idproducto "
							+ "and trim(upper(nombre)) = trim(upper(:nombre)) and estado = 'A'")
				  .setParameter("nombre", Constants.SINCAP)
		          .setParameter("idproducto", capitulo.getProducto().getIdproducto())
		          .uniqueResult();
	}
	
	@Transactional
	public Integer getMaxOrder(Capitulo capitulo){
		
		return (Integer)this.getSessionFactory().getCurrentSession().
				createQuery("select nvl(max(orden),0)+1 from Capitulo "
							+ "where producto.idproducto=:idproducto and estado = 'A'")  
		          .setParameter("idproducto", capitulo.getProducto().getIdproducto())
		          .uniqueResult();
	}
	
	@Transactional
	public Capitulo findByOrden(Capitulo capitulo, int valor){
		
		return (Capitulo)this.getSessionFactory().getCurrentSession().
				createQuery("from Capitulo where orden =:orden and producto.idproducto=:idproducto and estado = 'A'")
		          .setParameter("orden", capitulo.getOrden()+valor)
		          .setParameter("idproducto", capitulo.getProducto().getIdproducto())
		          .uniqueResult();
	}

	@Transactional
	public Capitulo findByDescripcion(Capitulo cap) {
		return (Capitulo)this.getSessionFactory().getCurrentSession().
				createQuery("from Capitulo where descripcion =:descripcion and producto.idproducto=:idproducto and estado = 'A'")
		          .setParameter("descripcion", cap.getDescripcion())
		          .setParameter("idproducto", cap.getProducto().getIdproducto())
		          .uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Capitulo> getCapitulosPorProductoId(Integer idproducto) {
		try {

			List<Capitulo> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Capitulo "
							+ " where producto.idproducto = :idproducto "
							+ " and estado = 'A' "
							+ "order by orden asc")
					.setParameter("idproducto", idproducto).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error buscarCapitulo ->" +ex);
			return null;
		}
	}
	
}
