package pe.conadis.tradoc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.IncidenciaMensajeriaDAO;
import pe.conadis.tradoc.entity.IncidenciaMensajeria;
import pe.conadis.tradoc.entity.beans.Documento;

@Repository
public class IncidenciaMensajeriaDaoImpl extends
		AbstractDAO<IncidenciaMensajeria> implements IncidenciaMensajeriaDAO {

	@Transactional
	public List<IncidenciaMensajeria> obtenerIncidenciaMensajesXCodDocumento(
			Integer codDocumento) throws Exception {
		
		List<IncidenciaMensajeria> lstIncidenciaMensajeria = new ArrayList<IncidenciaMensajeria>();
		lstIncidenciaMensajeria = (List<IncidenciaMensajeria>) this
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from IncidenciaMensajeria ic "
								+ " inner join fetch ic.documento "
								+ " inner join fetch ic.courier "
						+ " where ic.documento.codDocumento =:codDocumento ").setParameter("codDocumento", codDocumento).list();
		return lstIncidenciaMensajeria;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Documento> obtenerDocumentosXEntidad(Documento documento)
			throws Exception {
		List<Documento> lstDocumento = new ArrayList<Documento>();
		lstDocumento = (List<Documento>) this
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from Documento d").list();
		return lstDocumento;
	}

}
