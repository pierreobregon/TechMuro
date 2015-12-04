package pe.conadis.tradoc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.CourierDAO;
import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.DocumentoDAO;
import pe.conadis.tradoc.dao.IncidenciaMensajeriaDAO;
import pe.conadis.tradoc.entity.Courier;
import pe.conadis.tradoc.entity.IncidenciaMensajeria;
import pe.conadis.tradoc.entity.beans.Documento;
import pe.conadis.tradoc.service.MensajeriaManager;

@Service
public class MensajeriaManagerImpl extends ServiceImpl<IncidenciaMensajeria>
		implements MensajeriaManager {

	@Autowired
	private IncidenciaMensajeriaDAO incidenciaMensajeriaDAO;
	@Autowired
	private CourierDAO courierDAO;
	@Autowired
	private DocumentoDAO documentoDAO;

	@Override
	protected Dao<IncidenciaMensajeria> getDAO() {
		return incidenciaMensajeriaDAO;
	}

	@Override
	public List<Documento> obtenerDocumentosXEntidad(Documento documento)
			throws Exception {
		return documentoDAO.obtenerDocumentosXEntidad(documento);
	}

	@Override
	public List<IncidenciaMensajeria> obtenerIncidenciaMensajesXCodDocumento(
			Integer codDocumento) throws Exception {
		return incidenciaMensajeriaDAO.obtenerIncidenciaMensajesXCodDocumento(codDocumento);
	}

	@Transactional
	public String guardarMensajeIncidencia(
			IncidenciaMensajeria incidenciaMensajeria) throws Exception {
		if(incidenciaMensajeria.getCodIncMensajeria()!=null){
			incidenciaMensajeriaDAO.add(incidenciaMensajeria);	
		}else{
			incidenciaMensajeriaDAO.update(incidenciaMensajeria);
		}
		return null;
	}

	@Override
	@Transactional
	public Documento findById_documento(Integer id) throws Exception {
		return documentoDAO.findById(id);
	}

	@Override
	@Transactional
	public Courier findById_courier(Integer id) throws Exception {
		return courierDAO.findById(id);
	}
	
	@Override
	@Transactional
	public List<Courier> getAnything_Courier() throws Exception {
		return courierDAO.getAnything();
	}

}
