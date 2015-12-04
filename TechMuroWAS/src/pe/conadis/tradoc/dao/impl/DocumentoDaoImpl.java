package pe.conadis.tradoc.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.DocumentoDAO;
import pe.conadis.tradoc.dao.OficinaDAO;
import pe.conadis.tradoc.entity.AficheOficina;
import pe.conadis.tradoc.entity.ComunicadoOficina;
import pe.conadis.tradoc.entity.Oficina;
import pe.conadis.tradoc.entity.beans.Accion;
import pe.conadis.tradoc.entity.beans.Documento;
import pe.conadis.tradoc.entity.beans.MenuSistema;

@Repository
public class DocumentoDaoImpl extends AbstractDAO<Documento> implements DocumentoDAO{
	
	
	private static final Logger logger = Logger.getLogger(DocumentoDaoImpl.class);
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Documento> listarDocumentosDerivarExterno(Documento Documento) throws Exception {
			
			List<Documento> lista = null ;
			try{
				lista = this.getSessionFactory().getCurrentSession().createQuery("from Documento d"
			+ "	inner join fetch  Expediente e  on( e.codExpediente=d.expediente.codExpediente)")
			/*+ " where us.codUsuario =:codUsuario")  	
      .setParameter("codUsuario", strCodigoUsuario)*/
      .list();
				
			return lista;
		} catch (Exception ex) {
			logger.error("Error en DocumentoDaoImpl.listarDocumentosDerivarExterno:"+ex);
			ex.printStackTrace();
			throw ex;
		}
	
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
