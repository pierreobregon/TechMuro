package pe.conadis.tradoc.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.conadis.tradoc.entity.beans.AnularExpediente;
import pe.conadis.tradoc.entity.beans.Documento;
import pe.conadis.tradoc.entity.beans.EntidadExterna;
import pe.conadis.tradoc.entity.beans.EstadoExpediente;
import pe.conadis.tradoc.entity.beans.Expediente;
import pe.conadis.tradoc.entity.beans.ExpedienteDocPersona;
import pe.conadis.tradoc.entity.beans.NumeracionDocumento;
import pe.conadis.tradoc.service.ExpedienteManager;
import pe.conadis.tradoc.util.Constants;
import pe.conadis.tradoc.vo.ExpedienteVO;



@Controller
public class ExpedienteController {
	
	private static final Logger logger = Logger.getLogger(ExpedienteController.class);

	@Autowired
	private ExpedienteManager expedienteManager;	
	
	@RequestMapping(value = "/mesa_parte/mesaParte.htm", method = RequestMethod.GET)
	public String cargaMesaParte(ModelMap map) {
		
		try{
			
			
		}catch(Exception e){
			logger.error("Error lista notarias"+e) ;
		}	
		
		return "mesa_parte/expedienteBuscar";
	}
	
	@RequestMapping(value = "/upAgregarExpediente.htm", method = RequestMethod.GET)
	public String upAgregarComunicado(ModelMap map) throws Exception {
		
		//Oficina oficina = new Oficina();
		
		ExpedienteVO expedienteVO  =new ExpedienteVO();
		
		expedienteVO.setFecCreacion(new Date());
		//oficina.setFechacreacion(new Date()); 
		//map.addAttribute("oficina", oficina);
		map.addAttribute("expedienteVO", expedienteVO);
		System.out.println("en controller");
		
		//map.addAttribute("plazaList", plazaManager.getAll());
		//map.addAttribute("tipo", "agregar");
		return "mesa_parte/expedienteAgregar";
	}
	
	@RequestMapping(value = "/addExpediente", method = RequestMethod.POST)
	public @ResponseBody String addExpediente(@ModelAttribute(value="expedienteVO") ExpedienteVO expedienteVO , ModelMap model) throws Exception 
	{
				Expediente expediente = new Expediente();
				
				List<AnularExpediente> listaAnularExpediente = new ArrayList<AnularExpediente>();
				List<Documento> listaDocumento = new ArrayList<Documento>();
				EntidadExterna entidadExterna = new EntidadExterna();
				EstadoExpediente estadoExpediente = new EstadoExpediente();
				List<ExpedienteDocPersona> listaExpedienteDocPersona = new ArrayList<ExpedienteDocPersona>();
				NumeracionDocumento numeracionDocumento = new NumeracionDocumento();
				
				estadoExpediente.setCodEstadoExpediente(Constants.EstadoExpediente.ESTADO_REGISTRADO);
				
				expediente.setAnularExpedientes(listaAnularExpediente);//LIST
				expediente.setCanDias(expedienteVO.getCanDias());
				expediente.setCodExpediente(expedienteVO.getCodExpediente());
				expediente.setCodUsuCreacion(expedienteVO.getCodUsuario());
				
				expediente.setDesAsunto(expedienteVO.getDesAsunto());
				expediente.setDescripcion(expedienteVO.getDescripcion());
				expediente.setDesDocumento(expedienteVO.getDesDocumento());
				expediente.setDesUtd(expedienteVO.getDesUtd());
				expediente.setDetalle(expedienteVO.getDetalle());
				expediente.setDocumentos(listaDocumento);//LIST
				expediente.setEntidadExterna(entidadExterna);//ENTITY
				expediente.setEstadoExpediente(estadoExpediente);//ENTITY
				expediente.setExpedienteDocPersonas(listaExpedienteDocPersona);//LIST
				
				System.out.println("ADD EXPEDIENTE");
				
//				expediente.setIndExpediente(expedienteVO.getIndExpediente());
				expediente.setInteresado(expedienteVO.getInteresado());
				expediente.setNumeracionDocumento(numeracionDocumento);//entity
				expediente.setNumFolios(expedienteVO.getNumFolios());
				
				if(expedienteVO.getCodCorrelativo() == null ){
					/// AGREGAR
					expediente.setFecCreacion(new Date());
					expedienteManager.add(expediente);
					
				}
				else{
					 /// EDITAR
					expediente.setCodUsuModificacion(expedienteVO.getCodUsuario());
					expediente.setFecModificacion(new Date());
					expedienteManager.update(expediente);
				}
				return "succes";
	}						
	
	
}
