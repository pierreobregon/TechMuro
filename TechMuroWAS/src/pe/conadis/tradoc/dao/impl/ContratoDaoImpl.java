package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.ContratoDAO;
import pe.conadis.tradoc.entity.Contrato;
import pe.conadis.tradoc.entity.Notaria;
import pe.conadis.tradoc.entity.NotariaContrato;
import pe.conadis.tradoc.entity.Plaza;

public class ContratoDaoImpl extends AbstractDAO<Contrato> implements ContratoDAO   {

	private static final Logger logger = Logger.getLogger(ContratoDaoImpl.class);

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Contrato> contratoList() {
		try {
			
			List<Contrato> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Contrato "
							+ "where estado = 'A' order by fechacreacion")
					.list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error en contratoList"+ex);
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public Contrato findByNombreGastos(String descripcion, String gastos) {
		try {
			
			Contrato result = (Contrato)this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Contrato "
							+ "where trim(upper(descripcion)) = trim(upper(:descripcion)) "
							+ "and trim(upper(gastos)) = trim(upper(:gastos)) and estado = 'A'")
					.setParameter("descripcion", descripcion)
					.setParameter("gastos", gastos)
					.uniqueResult();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error en contratoList"+ex);
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<NotariaContrato> findByPlaza(Plaza plaza) {
		
		try {
			
//			List<NotariaContrato> result = this
//					.getSessionFactory()
//					.getCurrentSession()
//					.createQuery("from NotariaContrato nc "
//							+ "where nc.id.idnotaria in ( "
//							+ "select distinct n.idnotaria from Notaria n "
//							+ "where n.plaza.idplaza = :idplaza and n.estado = 'A' "
//							+ ") order by nc.notaria.nombre asc")
//					.setParameter("idplaza", plaza.getIdplaza())
//					.list();
			
			
			List<NotariaContrato> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from NotariaContrato nc "
					+ "where nc.notaria.plaza.idplaza = :idplaza and nc.notaria.estado = 'A' order by nc.notaria.nombre asc")
					.setParameter("idplaza", plaza.getIdplaza())
					.list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error en contratoList"+ex);
			return null;
		}
	}
}
