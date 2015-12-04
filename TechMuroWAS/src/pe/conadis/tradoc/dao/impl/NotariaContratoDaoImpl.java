package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.NotariaContratoDAO;
import pe.conadis.tradoc.entity.Notaria;
import pe.conadis.tradoc.entity.NotariaContrato;

@Repository
public class NotariaContratoDaoImpl extends AbstractDAO<NotariaContrato> implements NotariaContratoDAO {

	@SuppressWarnings("unchecked")
	public List<NotariaContrato> getNotariasContratos(Integer notariaId,
			Integer contratoId) {

		try {

			List<NotariaContrato> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from NotariaContrato "
							+ " where contrato.idcontrato = :contratoId "
							+ " and notaria.idnotaria = :notariaId")
					.setParameter("contratoId", contratoId)
					.setParameter("notariaId", notariaId)
					.list();
			
			return result;
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<NotariaContrato> getNotariasContratosOrdenados(Integer idnotaria) {
		try {

			List<NotariaContrato> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from NotariaContrato "
							+ " where notaria.idnotaria = :notariaId order by orden")
					.setParameter("notariaId", idnotaria)
					.list();
			
			return result;
		} catch (Exception ex) {
			return null;
		}
	}

}
