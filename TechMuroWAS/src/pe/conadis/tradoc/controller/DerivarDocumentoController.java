package pe.conadis.tradoc.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pe.conadis.tradoc.entity.Afiche;
import pe.conadis.tradoc.entity.Oficina;
import pe.conadis.tradoc.service.OficinaManager;


@Controller
public class DerivarDocumentoController {

	private static final Logger logger = Logger.getLogger(DerivarDocumentoController.class);
	
	@Autowired
	private OficinaManager oficinaManager;

	@RequestMapping(value = "/mesa_parte/derivarDocumento2.htm", method = RequestMethod.GET)
	public String cargaDerivarDocumento(ModelMap map) {
		
		try{

			
		}catch(Exception e){
			logger.error("Error lista notarias"+e) ;
		}	
		
		return "mesa_parte/derivarDocumentoBuscar";
	}
	
	
	

	
	
	
	
	
	@RequestMapping(value = "/mesa_parte/openDerivarDocumentoForm2.htm", method = RequestMethod.GET)
	public String cargaDerivar(ModelMap map) {
		
		try{			
			
		}catch(Exception e){
			logger.error("Error"+e) ;
		}		
		return "mesa_parte/derivarDocumento";
	}
}
