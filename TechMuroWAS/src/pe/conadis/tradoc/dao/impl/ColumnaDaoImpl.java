package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.ColumnaDAO;
import pe.conadis.tradoc.entity.Columna;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;

@Repository
public class ColumnaDaoImpl extends AbstractDAO<Columna> implements ColumnaDAO {

	private static final Logger logger = Logger.getLogger(ColumnaDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Columna> findByRubro(Columna columna) {

		try {

			List<Columna> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Columna "
							+ "where rubro.idrubro = :idRubro order by posicionx, posiciony")
					.setParameter("idRubro", columna.getRubro().getIdrubro()).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error findByRubro ->" +ex);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Columna findByPosicion(Columna columna){
		
		Columna result = (Columna)this.getSessionFactory().getCurrentSession().
				createQuery("from Columna "
						+ "where rubro.idrubro=:idRubro "
						+ "and posicionx = :posicionx "
						+ "and posiciony = :posiciony")
						.setParameter("idRubro", columna.getRubro().getIdrubro())
		        		.setParameter("posicionx", columna.getPosicionx())
		        		.setParameter("posiciony", columna.getPosiciony())
		          .uniqueResult();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Columna> getColumnasRubroPorProductoId(Integer idproducto) {


		try {
			List<Columna> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(" SELECT * FROM MURTEC.COLUMNA WHERE IDPRODUCTO = :idproducto order by posicionx, posiciony ").addEntity(Columna.class)
					.setParameter("idproducto", idproducto).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error Columna ->" +ex);
			return null;
		}
	}
	
}
