package pe.conadis.tradoc.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.conadis.tradoc.entity.Courier;
import pe.conadis.tradoc.entity.IncidenciaMensajeria;
import pe.conadis.tradoc.entity.beans.Documento;
import pe.conadis.tradoc.entity.beans.Usuario;
import pe.conadis.tradoc.service.MensajeriaManager;
import pe.conadis.tradoc.util.Constants;

import com.google.gson.Gson;

@Controller
public class MensajeriaController {

	private static final Logger logger = Logger.getLogger(MensajeriaController.class);
	
	@Autowired
	private MensajeriaManager mensajeriaManager;
	
	@RequestMapping(value = "/mensajeria/listDocumentos.htm", method = RequestMethod.GET)
	public String buscarDocumentos(ModelMap map){
		
		try {
			List<Documento> lstDocumento =  mensajeriaManager.obtenerDocumentosXEntidad(new Documento());
			for (Documento documento : lstDocumento) {
				documento.setIncidenciaMensajerias(null);
			}
			List<IncidenciaMensajeria> lstIncidenciaMensajerias = new ArrayList<IncidenciaMensajeria>();
			for (IncidenciaMensajeria incidMensj : lstIncidenciaMensajerias) {
				incidMensj.getDocumento().setIncidenciaMensajerias(null);
				incidMensj.getCourier().setIncidenciaMensajerias(null);
			}
			
			map.addAttribute("lstDocumento", new Gson().toJson(lstDocumento));
			map.addAttribute("lstIncidencias", new Gson().toJson(lstIncidenciaMensajerias));
			map.addAttribute("listaSize", lstDocumento.size());
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "mensajeria/documentoList";
	}
	
	@RequestMapping(value = "/mensajeria/listIncidencias.htm", method = RequestMethod.POST)
	public @ResponseBody String listIncidencias(ModelMap map, HttpServletRequest request) throws Exception {
		String strPrmCodDocumento = request.getParameter("prmCodDocumento")!=null?request.getParameter("prmCodDocumento"):"0"; 
		List<IncidenciaMensajeria> lstIncidenciaMensajerias = mensajeriaManager.obtenerIncidenciaMensajesXCodDocumento(Integer.parseInt(strPrmCodDocumento));
		for (IncidenciaMensajeria incidMensj : lstIncidenciaMensajerias) {
			incidMensj.getDocumento().setIncidenciaMensajerias(null);
			incidMensj.getCourier().setIncidenciaMensajerias(null);
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			incidMensj.setFecCreacionFormato(incidMensj.getFecCreacion()!=null?df.format(incidMensj.getFecCreacion()):null);
		}
		return new Gson().toJson(lstIncidenciaMensajerias);
	}
	
	@RequestMapping(value = "/mensajeria/cargaFormIncidencia.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public String cargaFormIncidencia(ModelMap map, HttpServletRequest request){
		try{
			Integer codIncidenciaSeleccionada = (request.getParameter("prmCodIncMensajeria")!=null?Integer.parseInt(request.getParameter("prmCodIncMensajeria")):null);
			Integer codDocumentoSeleccionada = (request.getParameter("prmCodDocumento")!=null?Integer.parseInt(request.getParameter("prmCodDocumento")):null);
			IncidenciaMensajeria incidenciaMensajeria = new IncidenciaMensajeria();
			Usuario usuario = (Usuario)request.getSession().getAttribute(Constants.__CURRENT_USER__);
			if(codIncidenciaSeleccionada!=null){
				incidenciaMensajeria = this.mensajeriaManager.finById(codIncidenciaSeleccionada);
			}else{
				Documento documento = mensajeriaManager.findById_documento(codDocumentoSeleccionada);
				documento.setIncidenciaMensajerias(null);
				Courier courier = new Courier();
				courier.setIncidenciaMensajerias(null);
				
				incidenciaMensajeria.setFecCreacion(new Date());
				incidenciaMensajeria.setDocumento(documento);
				incidenciaMensajeria.setCourier(courier);
				incidenciaMensajeria.setCodUsuCreacion(usuario.getCodUsuario());
			}
			
			List<Courier> lstCourier = mensajeriaManager.getAnything_Courier();
			for (Courier courier : lstCourier) {
				courier.setIncidenciaMensajerias(null);
			}
			map.addAttribute("incidenciaMensajeria", incidenciaMensajeria);
			map.addAttribute("lstCourier", lstCourier);
			
		}catch(Exception e){
			logger.error("Error lista notarias"+e) ;
		}
		return "mensajeria/incidenciaForm";
	}
	
	@RequestMapping(value = "/mensajeria/agregar.htm", method = RequestMethod.POST)
	public String agregarMensajeIncidencia(@ModelAttribute(value="incidenciaMensajeria") IncidenciaMensajeria incidenciaMensajeria, HttpServletRequest request){
		Usuario usuario = (Usuario) request.getSession().getAttribute(Constants.__CURRENT_USER__);
		try{
		    String result="true";
			if(incidenciaMensajeria.getCodIncMensajeria()==null){
				incidenciaMensajeria.setDocumento(mensajeriaManager.findById_documento(incidenciaMensajeria.getDocumento().getCodDocumento()));
				incidenciaMensajeria.setCourier(mensajeriaManager.findById_courier(incidenciaMensajeria.getCourier().getCodCourier()));
				incidenciaMensajeria.setCodUsuCreacion(usuario.getCodUsuario());
				incidenciaMensajeria.setCodUsuModificacion(usuario.getCodUsuario());
				incidenciaMensajeria.setFecCreacion(new Date());
				incidenciaMensajeria.setFecModificacion(new Date());
				mensajeriaManager.add(incidenciaMensajeria);
			}else{
				IncidenciaMensajeria objIncidOrig = mensajeriaManager.finById(incidenciaMensajeria.getCodIncMensajeria());
				incidenciaMensajeria.setFecCreacion(objIncidOrig.getFecCreacion());
				incidenciaMensajeria.setCodUsuCreacion(objIncidOrig.getCodUsuCreacion());
				incidenciaMensajeria.setFecModificacion(new Date());
				incidenciaMensajeria.setCodUsuModificacion(usuario.getCodUsuario());
				mensajeriaManager.update(incidenciaMensajeria);
			}
			request.setAttribute("result", result);
			return "result";
		}catch(Exception e){
			logger.error("Error agregar agregarRubro ->"+e);
			e.printStackTrace();
			request.setAttribute("result", "-1");
			return "result";
		}
	}
	
	
	@RequestMapping(value = "/mensajeria/modificarIncidencia.htm", method = RequestMethod.POST)
	public String modificarIncidencia(ModelMap map, HttpServletRequest request){
		
		return "mensajeria/incidenciaForm";
	}
	
	
	
}
