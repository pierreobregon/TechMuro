package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.TransaccionDetalleDAO;
import pe.conadis.tradoc.dao.VariableDAO;
import pe.conadis.tradoc.entity.Columna;
import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;
import pe.conadis.tradoc.entity.VariablesGenerales;


@Repository
public class TransaccionDetalleDaoImpl extends AbstractDAO<TransaccionDetalle> implements TransaccionDetalleDAO {

	@SuppressWarnings("unchecked")
	@Transactional
	public List<TransaccionDetalle> getTransaccioneDetallePorProductoId(
			Integer idproducto) {
		// TODO Auto-generated method stub
		try {
			List<TransaccionDetalle> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(" SELECT * FROM MURTEC.TRANSACCION_DETALLE WHERE IDPRODUCTO = :idproducto AND ESTADO = 'A' order by posicionx, posiciony ").addEntity(TransaccionDetalle.class)
					.setParameter("idproducto", idproducto).list();
			
			return result;
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public TransaccionDetalle obtenerTransaccion(Integer idtransaccion) {
	
		return (TransaccionDetalle) this.getSessionFactory().getCurrentSession().
		createQuery("from TransaccionDetalle where posiciony= 0 "
					+ "and transaccion.idtransaccion = :idtransaccion and estado = 'A'")  
          .setParameter("idtransaccion", idtransaccion)
          .uniqueResult();

	}

	
	
}
