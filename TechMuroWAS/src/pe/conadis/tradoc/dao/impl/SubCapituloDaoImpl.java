package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.SubCapituloDAO;
import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.util.Constants;

@Repository
public class SubCapituloDaoImpl extends AbstractDAO<Subcapitulo> implements
	SubCapituloDAO {

	private static final Logger logger = Logger.getLogger(SubCapituloDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Subcapitulo> buscarSubCapitulo(Subcapitulo subCapitulo) {
		try {
			List<Subcapitulo> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Subcapitulo "
							+ "where capitulo.idcapitulo = :idcapitulo "
							+ "and upper(nombre) like upper('%'||:nombre||'%') and estado = 'A' "
							+ "order by orden asc")
					.setParameter("nombre", subCapitulo.getNombre())
					.setParameter("idcapitulo", subCapitulo.getCapitulo().getIdcapitulo()).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error buscarSubCapitulo ->" +ex);
			return null;
		}
	}
	
	@Transactional
	public Subcapitulo validaSubCapitulo(Subcapitulo subCapitulo){
		
		return (Subcapitulo)this.getSessionFactory().getCurrentSession().
				createQuery("from Subcapitulo where capitulo.idcapitulo = :idcapitulo "
							+ "and trim(upper(nombre)) = trim(upper(:nombre)) and estado = 'A'")  
		          .setParameter("nombre", subCapitulo.getNombre())
		          .setParameter("idcapitulo", subCapitulo.getCapitulo().getIdcapitulo())
		          .uniqueResult();
	}
	
	@Transactional
	public Subcapitulo validaSubCapituloSinSubCapitulo(Subcapitulo subCapitulo){
		
		return (Subcapitulo)this.getSessionFactory().getCurrentSession().
				createQuery("from Subcapitulo where capitulo.idcapitulo = :idcapitulo "
							+ "and trim(upper(nombre)) = trim(upper(:nombre)) and estado = 'A'")  
		          .setParameter("nombre", Constants.SINSUBCAP)
		          .setParameter("idcapitulo", subCapitulo.getCapitulo().getIdcapitulo())
		          .uniqueResult();
	}
	
	@Transactional
	public Integer getMaxOrder(Subcapitulo subCapitulo){
		
		return (Integer)this.getSessionFactory().getCurrentSession().
				createQuery("select nvl(max(orden),0)+1 from Subcapitulo "
							+ "where capitulo.idcapitulo=:idcapitulo and estado = 'A'")  
		          .setParameter("idcapitulo", subCapitulo.getCapitulo().getIdcapitulo())
		          .uniqueResult();
	}
	
	@Transactional
	public Subcapitulo findByOrden(Subcapitulo capitulo, int valor){
		
		return (Subcapitulo)this.getSessionFactory().getCurrentSession().
				createQuery("from Subcapitulo where orden =:orden and capitulo.idcapitulo=:idcapitulo and estado = 'A'")
		          .setParameter("orden", capitulo.getOrden()+valor)
		          .setParameter("idcapitulo", capitulo.getCapitulo().getIdcapitulo())
		          .uniqueResult();
	}
	
	@Transactional
	public Subcapitulo findByDescripcion(Subcapitulo capitulo){
		
		return (Subcapitulo)this.getSessionFactory().getCurrentSession().
				createQuery("from Subcapitulo where descripcion =:descripcion and capitulo.idcapitulo=:idcapitulo and estado = 'A'")
		          .setParameter("descripcion", capitulo.getDescripcion())
		          .setParameter("idcapitulo", capitulo.getCapitulo().getIdcapitulo())
		          .uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Subcapitulo> findByCapitulo(int id) {

		try {

			List<Subcapitulo> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Subcapitulo "
							+ "where capitulo.idcapitulo = :idcapitulo "
							+ "and estado = 'A' "
							+ "order by orden asc")
					.setParameter("idcapitulo", id).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error findByCapitulo ->" +ex);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Subcapitulo> getSubCapitulosPorProductoId(Integer idproducto) {


		try {
			List<Subcapitulo> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(" SELECT * FROM MURTEC.SUBCAPITULO WHERE IDPRODUCTO = :idproducto AND ESTADO = 'A' ORDER BY ORDEN ASC ").addEntity(Subcapitulo.class)
					.setParameter("idproducto", idproducto).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error buscarSubCapitulo ->" +ex);
			return null;
		}
		
	}
	
}
