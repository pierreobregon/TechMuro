package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.NotaDAO;
import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Nota;


@Repository
public class NotaDaoImpl extends AbstractDAO<Nota> implements NotaDAO {

	private static final Logger logger = Logger.getLogger(NotaDaoImpl.class);
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Nota> getNotasPorProductoId(Integer idproducto) {
		try {
			List<Nota> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(" SELECT * FROM MURTEC.NOTA WHERE IDPRODUCTO = :idproducto AND ESTADO = 'A' ").addEntity(Nota.class)
					.setParameter("idproducto", idproducto).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error notas ->" +ex);
			return null;
		}
	}

	
}
