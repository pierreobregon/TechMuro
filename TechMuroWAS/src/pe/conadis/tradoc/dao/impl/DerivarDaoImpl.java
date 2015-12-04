package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.DerivarDAO;
import pe.conadis.tradoc.entity.beans.Derivar;
import pe.conadis.tradoc.entity.beans.Documento;


@Repository
public class DerivarDaoImpl extends AbstractDAO<Derivar> implements DerivarDAO{
	
	
	private static final Logger logger = Logger.getLogger(DerivarDaoImpl.class);
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Derivar> listarDocumentosDerivarExterno(Documento documento) throws Exception {
			
			List<Derivar> lista = null ;
			try{
				String sql=new String("");
				Query query; 
				
				sql="from  Derivar r "
						+ " inner join fetch r.uoOrigen uo "
						+ " inner join fetch r.documento d "
						+ " inner join fetch d.expediente e "  						
						+ " left join fetch d.numeracionDocumento ndd"						
						+ " inner join fetch e.numeracionDocumento nde "
						+ " inner join fetch e.entidadExterna ee "  
						
						+ " where d.estadosDocumento in (1,2) "  //estado registrado o pendiente
						+ " and d.indDocExterno in ('0','1') "  //documentos generados en una UO/ documentos generados Mesa de PArtes
						+ " and r.indRecepcion='1' "  //se recepciono
						+ " and r.indDocumento='2' " //documento derivado a una UO
						+ " and r.indEnvioEntExterna='1' " 
						+ " and r.indDevolucion='0' "  //no es una devolucion
						+ " and r.indResAtencion='0' "//La uo es responsable de atender el documento
						+ " and r.indResAtencion='0' ";
						if(documento.getExpediente().getNumeracionDocumento().getIndExpediente()!=null && !documento.getExpediente().getNumeracionDocumento().getIndExpediente().equals(""))
							sql=sql+ " and nde.indExpediente=:indExpediente ";
						if(documento.getExpediente().getNumeracionDocumento().getNumDocumento()!=null && !documento.getExpediente().getNumeracionDocumento().getNumDocumento().equals(""))
							sql=sql+ " and nde.numDocumento=:numExpediente ";			   
						if(documento.getExpediente().getNumeracionDocumento().getAnioDocumento()!=null && !documento.getExpediente().getNumeracionDocumento().getAnioDocumento().equals(""))
							sql=sql+ " and nde.anioDocumento=:anioDocumentoExpediente ";							
						if(documento.getTipoDocumento().getCodTipDocumento()!=null && !documento.getTipoDocumento().getCodTipDocumento().equals(""))
							sql=sql+ " and d.tipoDocumento.codTipDocumento=:codTipDocumento ";							
						if(documento.getNumeracionDocumento().getNumDocumento()!=null && !documento.getNumeracionDocumento().getNumDocumento().equals(""))
							sql=sql+ " and ( (ndd is not null and ndd.numDocumento=:numDocumento) or "
							                     +"(ndd is  null and d.desNumDocumento=:numDocumento)"
							                 + ") ";	
						if(documento.getNumeracionDocumento().getAnioDocumento()!=null && !documento.getNumeracionDocumento().getAnioDocumento().equals(""))
							sql=sql+ " and ndd.anioDocumento=:anioDocumento ";					
						
				query = this.getSessionFactory().getCurrentSession().createQuery(sql);
				
				if(documento.getExpediente().getNumeracionDocumento().getIndExpediente()!=null && !documento.getExpediente().getNumeracionDocumento().getIndExpediente().equals(""))
					query.setParameter("indExpediente",documento.getExpediente().getNumeracionDocumento().getIndExpediente());
				if(documento.getExpediente().getNumeracionDocumento().getNumDocumento()!=null && !documento.getExpediente().getNumeracionDocumento().getNumDocumento().equals(""))
					query.setParameter("numExpediente",documento.getExpediente().getNumeracionDocumento().getNumDocumento());		   
				if(documento.getExpediente().getNumeracionDocumento().getAnioDocumento()!=null && !documento.getExpediente().getNumeracionDocumento().getAnioDocumento().equals(""))
					query.setParameter("anioDocumentoExpediente",documento.getExpediente().getNumeracionDocumento().getAnioDocumento());							
				if(documento.getTipoDocumento().getCodTipDocumento()!=null && !documento.getTipoDocumento().getCodTipDocumento().equals(""))
					query.setParameter("codTipDocumento",documento.getTipoDocumento().getCodTipDocumento());							
				if(documento.getNumeracionDocumento().getNumDocumento()!=null && !documento.getNumeracionDocumento().getNumDocumento().equals(""))
					query.setParameter("numDocumento",documento.getNumeracionDocumento().getNumDocumento());
				if(documento.getNumeracionDocumento().getAnioDocumento()!=null && !documento.getNumeracionDocumento().getAnioDocumento().equals(""))
					query.setParameter("anioDocumento",documento.getNumeracionDocumento().getAnioDocumento());
				
				lista=query.list();
			
			return lista;
		} catch (Exception ex) {
			logger.error("Error en DerivarDaoImpl.listarDocumentosDerivarExterno:"+ex);
			ex.printStackTrace();
			throw ex;
		}
	
	}
}
