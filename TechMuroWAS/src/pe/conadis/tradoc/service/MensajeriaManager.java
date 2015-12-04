package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.Courier;
import pe.conadis.tradoc.entity.IncidenciaMensajeria;
import pe.conadis.tradoc.entity.beans.Documento;

public interface MensajeriaManager extends Service<IncidenciaMensajeria> {
	
	public List<Documento> obtenerDocumentosXEntidad(Documento documento) throws Exception;
	
	public List<IncidenciaMensajeria> obtenerIncidenciaMensajesXCodDocumento(Integer codDocumento) throws Exception;

	public String guardarMensajeIncidencia(
			IncidenciaMensajeria incidenciaMensajeria) throws Exception;
	
	public Documento findById_documento(Integer id) throws Exception;
	
	public Courier findById_courier(Integer id) throws Exception;

	public List<Courier> getAnything_Courier() throws Exception;
	
}
