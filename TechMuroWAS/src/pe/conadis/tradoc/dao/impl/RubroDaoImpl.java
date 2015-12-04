package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.RubroDAO;
import pe.conadis.tradoc.dao.SubCapituloDAO;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Subcapitulo;

@Repository
public class RubroDaoImpl extends AbstractDAO<Rubro> implements
	RubroDAO {

	private static final Logger logger = Logger.getLogger(RubroDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Rubro> buscarRubro(Rubro rubro) {
		try {
			List<Rubro> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Rubro "
							+ "where subcapitulo.idsubcapitulo = :idsubcapitulo "
							+ "and upper(nombre) like upper('%'||:nombre||'%') and estado = 'A' "
							+ "order by orden asc")
					.setParameter("nombre", rubro.getNombre())
					.setParameter("idsubcapitulo", rubro.getSubcapitulo().getIdsubcapitulo()).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error buscarSubCapitulo ->" +ex);
			return null;
		}
	}
	
	@Transactional
	public Rubro validaRubro(Rubro rubro){
		
		return (Rubro)this.getSessionFactory().getCurrentSession().
				createQuery("from Rubro where subcapitulo.idsubcapitulo = :idsubcapitulo "
							+ "and trim(upper(nombre)) = trim(upper(:nombre)) and estado = 'A'")  
		          .setParameter("nombre", rubro.getNombre())
		          .setParameter("idsubcapitulo", rubro.getSubcapitulo().getIdsubcapitulo())
		          .uniqueResult();
	}
	
	@Transactional
	public Integer getMaxOrder(Rubro rubro){
		
		return (Integer)this.getSessionFactory().getCurrentSession().
				createQuery("select nvl(max(orden),0)+1 from Rubro "
							+ "where subcapitulo.idsubcapitulo=:idsubcapitulo and estado = 'A'")  
		          .setParameter("idsubcapitulo", rubro.getSubcapitulo().getIdsubcapitulo())
		          .uniqueResult();
	}
	
	@Transactional
	public Rubro findByOrden(Rubro rubro, int valor){
		
		return (Rubro)this.getSessionFactory().getCurrentSession().
				createQuery("from Rubro where orden =:orden and subcapitulo.idsubcapitulo=:idsubcapitulo and estado = 'A'")
		          .setParameter("orden", rubro.getOrden()+valor)
		          .setParameter("idsubcapitulo", rubro.getSubcapitulo().getIdsubcapitulo())
		          .uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Rubro> findBySubCapitulo(int id) {

		try {

			List<Rubro> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Rubro "
							+ "where subcapitulo.idsubcapitulo = :idsubcapitulo "
							+ "and estado = 'A' "
							+ "order by orden asc")
					.setParameter("idsubcapitulo", id).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error findBySubCapitulo ->" +ex);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Rubro> getRubrosPorProductoId(Integer idproducto) {
		try {
			List<Rubro> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(" SELECT * FROM MURTEC.RUBRO WHERE IDPRODUCTO = :idproducto AND ESTADO = 'A' ORDER BY ORDEN ASC ").addEntity(Rubro.class)
					.setParameter("idproducto", idproducto).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error buscarSubCapitulo ->" +ex);
			return null;
		}

	}
	
}
