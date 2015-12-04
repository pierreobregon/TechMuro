package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.CategoriaDAO;
import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.util.Constants;

@Repository
public class CategoriaDaoImpl extends AbstractDAO<Categoria> implements
	CategoriaDAO {

	private static final Logger logger = Logger.getLogger(CategoriaDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Categoria> buscarCategoria(Categoria categoria) {
		try {
			List<Categoria> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Categoria "
							+ "where rubro.idrubro = :idrubro "
							+ "and upper(nombre) like upper('%'||:nombre||'%') and estado = 'A' "
							+ "order by orden asc")
					.setParameter("nombre", categoria.getNombre())
					.setParameter("idrubro", categoria.getRubro().getIdrubro()).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error buscarCategoria ->" +ex);
			return null;
		}
	}
	
	@Transactional
	public Categoria validaCategoria(Categoria categoria){
		
		return (Categoria)this.getSessionFactory().getCurrentSession().
				createQuery("from Categoria where rubro.idrubro = :idrubro "
							+ "and trim(upper(nombre||denominacion)) = trim(upper(:nombre||:denominacion)) and estado = 'A'")  
		          .setParameter("nombre", categoria.getNombre())
		          .setParameter("denominacion", categoria.getDenominacion())
		          .setParameter("idrubro", categoria.getRubro().getIdrubro())
		          .uniqueResult();
	}
	
	@Transactional
	public Categoria validaCategoriaSinCategoria(Categoria categoria){
		
		return (Categoria)this.getSessionFactory().getCurrentSession().
				createQuery("from Categoria where rubro.idrubro = :idrubro "
							+ "and trim(upper(nombre)) = trim(upper(:nombre)) and estado = 'A'")  
		          .setParameter("nombre", Constants.SINCAT)
		          .setParameter("idrubro", categoria.getRubro().getIdrubro())
		          .uniqueResult();
	}
	
	@Transactional
	public Integer getMaxOrder(Categoria categoria){
		
		return (Integer)this.getSessionFactory().getCurrentSession().
				createQuery("select nvl(max(orden),0)+1 from Categoria "
							+ "where rubro.idrubro=:idrubro and estado = 'A'")  
		          .setParameter("idrubro", categoria.getRubro().getIdrubro())
		          .uniqueResult();
	}
	
	@Transactional
	public Categoria findByOrden(Categoria categoria, int valor){
		
		return (Categoria)this.getSessionFactory().getCurrentSession().
				createQuery("from Categoria where orden =:orden and rubro.idrubro=:idrubro and estado = 'A'")
		          .setParameter("orden", categoria.getOrden()+valor)
		          .setParameter("idrubro", categoria.getRubro().getIdrubro())
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
	
	@Transactional
	public Categoria findByDescripcion(Categoria categoria){
		
		return (Categoria)this.getSessionFactory().getCurrentSession().
				createQuery("from Categoria where descripcion =:descripcion and rubro.idrubro=:idrubro and estado = 'A'")
		          .setParameter("descripcion", categoria.getDescripcion())
		          .setParameter("idrubro", categoria.getRubro().getIdrubro())
		          .uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Categoria> findByRubro(int id) {

		try {

			List<Categoria> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Categoria "
							+ "where rubro.idrubro = :idrubro "
							+ "and estado = 'A' "
							+ "order by orden asc")
					.setParameter("idrubro", id).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error findByRubro ->" +ex);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Categoria> getCategoriasPorProductoId(Integer idproducto) {
		try {
			List<Categoria> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(" SELECT * FROM MURTEC.CATEGORIA WHERE IDPRODUCTO = :idproducto AND ESTADO = 'A' ORDER BY ORDEN ASC ").addEntity(Categoria.class)
					.setParameter("idproducto", idproducto).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error buscarSubCapitulo ->" +ex);
			return null;
		}

	}
	
}
