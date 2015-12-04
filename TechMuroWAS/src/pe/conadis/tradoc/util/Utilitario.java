package pe.conadis.tradoc.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import pe.conadis.tradoc.controller.DocumentoController;
import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Columna;
import pe.conadis.tradoc.entity.Nota;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;
import pe.conadis.tradoc.entity.beans.Documento;
import pe.conadis.tradoc.entity.beans.Expediente;
import pe.conadis.tradoc.entity.beans.NumeracionDocumento;
import antlr.StringUtils;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;

public class Utilitario {
	private static final Logger logger = Logger.getLogger(DocumentoController.class);
	
	public static List<Subcapitulo> filterSubCapitulo(List<Subcapitulo> subcapitulos, final Integer idCapitulo){
		Collection<Subcapitulo> filterSubCapitulos = Collections2.filter(subcapitulos, new Predicate<Subcapitulo>() {

			@Override
			public boolean apply(Subcapitulo subcapitulo) {
				return subcapitulo.getCapitulo().getIdcapitulo().equals(idCapitulo);
			}
		});
		return new ArrayList<Subcapitulo>(filterSubCapitulos);
	}
	
	public static String obtenerNumeroExpediente(Expediente expediente){
		try {
		if(expediente!= null && expediente.getNumeracionDocumento()!=null){
		   return  expediente.getNumeracionDocumento().getAnioDocumento()+"-"+
				   String.format("%04d",expediente.getNumeracionDocumento().getNumDocumento())+"-"+
				   expediente.getNumeracionDocumento().getIndExpediente();
		}
		}catch(Exception e){
			logger.error("Error obteniendo el numero de expediente:"+e) ;
			return null;
		}
	   return null;            
	}
	
	public static String obtenerNumeroExpediente(NumeracionDocumento numeracionDocumento){
		
		try {
		if(numeracionDocumento!= null){
		   return  numeracionDocumento.getAnioDocumento()+"-"+
				   String.format("%04d",numeracionDocumento.getNumDocumento())+"-"+
				   numeracionDocumento.getIndExpediente();
		}
		}catch(Exception e){
			logger.error("Error obteniendo el numero de expediente:"+e) ;
			return null;
		}		
	   return null;            
	}	
	
	public static String obtenerNumeroExpediente(Documento documento){
		try {
		if(documento!=null && documento.getExpediente()!= null && documento.getExpediente().getNumeracionDocumento()!=null){
		   return  documento.getExpediente().getNumeracionDocumento().getAnioDocumento()+"-"+
				   String.format("%04d",documento.getExpediente().getNumeracionDocumento().getNumDocumento())+"-"+
				   documento.getExpediente().getNumeracionDocumento().getIndExpediente();
		}
		}catch(Exception e){
			logger.error("Error obteniendo el numero de expediente:"+e) ;
			return null;
		}
	   return null;        
	}		
	
	public static String obtenerTipoDocumento(Documento documento){
		try {
		if(documento!=null && documento.getTipoDocumento()!=null){
		   return  documento.getTipoDocumento().getDesTipDocumento();
		}
		}catch(Exception e){
			logger.error("Error obteniendo el tipo de documento de ID: "+(documento==null?"":documento.getCodDocumento())+ ":"+e) ;
			return null;
		}
	   return null;        
	}		
	
	public static String obtenerNumeroDocumento (Documento documento){
		try {
		if(documento!=null && documento.getNumeracionDocumento()!=null){
		   return  String.format("%04d",documento.getNumeracionDocumento().getNumDocumento())+"-"+
				   documento.getNumeracionDocumento().getAnioDocumento()+"-"+
				   (documento.getNumeracionDocumento().getUnidadOrganica()!=null?
				       documento.getNumeracionDocumento().getUnidadOrganica().getDesSigla():
				    	   "");
		   
		}
		if(documento!=null && documento.getNumeracionDocumento()==null){
		   return documento.getDesNumDocumento();
		}
		}catch(Exception e){
			logger.error("Error obteniendo el tipo de documento de ID: "+(documento==null?"":documento.getCodDocumento())+ ":"+e) ;
			e.printStackTrace();
			return null;
		}
	   return null;        
	}		
	
		
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    System.out.println(String.format("%04d", Integer.parseInt("12")));
	}
	
}
