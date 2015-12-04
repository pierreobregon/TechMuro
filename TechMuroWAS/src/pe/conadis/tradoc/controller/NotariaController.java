package pe.conadis.tradoc.controller;

import java.lang.reflect.Type;
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

import pe.conadis.tradoc.entity.Contrato;
import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.entity.Notaria;
import pe.conadis.tradoc.entity.NotariaContrato;
import pe.conadis.tradoc.entity.Privilegio;
import pe.conadis.tradoc.model.UploadedFile;
import pe.conadis.tradoc.service.ContratoManager;
import pe.conadis.tradoc.service.LogMuroManager;
import pe.conadis.tradoc.service.NotariaManager;
import pe.conadis.tradoc.service.PlazaManager;
import pe.conadis.tradoc.util.Constants;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
public class NotariaController {
	
	private static final Logger logger = Logger.getLogger(NotariaController.class);

	@Autowired
	private NotariaManager notariaManager;
	@Autowired
	private PlazaManager plazaManager;
	@Autowired
	private ContratoManager contratoManager;
	@Autowired
	private LogMuroManager logMuroManager;

	@RequestMapping(value = "/notaria/list.htm", method = RequestMethod.GET)
	public String cargaLista(ModelMap map) {
		List<Notaria> lista= new ArrayList<Notaria>();
		try{
			lista = notariaManager.buscarNotaria("%");
			for(Notaria notaria:lista){
				notaria.setNotariaContratos(null);
				notaria.getPlaza().setNotarias(null);
				notaria.getPlaza().setOficinas(null);
			}
			
			map.addAttribute("notariaList", new Gson().toJson(lista));
			map.addAttribute("listaSize", lista.size());
			map.addAttribute("uploadedFile", new UploadedFile());
			
		}catch(Exception e){
			logger.error("Error lista notarias"+e) ;
		}	
		
		return "notaria/notariaList";
	}
	
	@RequestMapping(value = "/notaria/buscar.htm", method = RequestMethod.POST)
	public @ResponseBody String buscarNotaria(HttpServletRequest request) {
		
		String criterio = request.getParameter("criterio");if(criterio==null)criterio="";
		
		List<Notaria> lista= new ArrayList<Notaria>();
		try{
			lista = notariaManager.buscarNotaria("%"+criterio+"%");
			for(Notaria notaria:lista){
				notaria.setNotariaContratos(null);
				notaria.getPlaza().setNotarias(null);
				notaria.getPlaza().setOficinas(null);
			}
		}catch(Exception e){
			logger.error("Error lista notarias"+e) ;
		}
		
		return new Gson().toJson(lista);
	}
	
	@RequestMapping(value = "/notaria/eliminar.htm", method = RequestMethod.POST)
	public @ResponseBody String eliminarNotaria(HttpServletRequest request) {
		String id = request.getParameter("id");if(id==null)id="0";
		
		try{
			logMuroManager.add(new LogMuro(null, new Privilegio(10),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_ELIMINAR, new Date(), notariaManager.finById(Integer.parseInt(id)).getNombre(), "", Integer.parseInt(id), "Notaría", "N"));
			
		}catch(Exception e){
			logger.error("exception log"+e);
		}
		
		
		return ""+notariaManager.eliminarNotaria(new Notaria(Integer.parseInt(id),""));
	}
	
	@RequestMapping(value = "/notaria/agregar.htm", method = RequestMethod.POST)
	public String agregarNotaria(@ModelAttribute(value="notaria") Notaria notaria, HttpServletRequest request) {
		try{
			List<NotariaContrato> listaTemp = new ArrayList<NotariaContrato>();
			String contratoList = request.getParameter("contratoList");if(contratoList==null)contratoList="";
			
		    final Type tipoListaContratos = new TypeToken<List<Contrato>>(){}.getType();
		    final List<Contrato> contratos = new Gson().fromJson(contratoList, tipoListaContratos);
		    
			if(notaria.getIdnotaria()==null){
				notaria.setEstado("A".charAt(0));
				notaria.setFechacreacion(new Date());
				
				notariaManager.add(notaria);
				
				logMuroManager.add(new LogMuro(null, new Privilegio(10),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_CREAR, new Date(), "", notaria
								.getNombre(), notaria.getIdnotaria(), "Notaría", "N"));
			}else{
				Notaria notariaTemp = notariaManager.getNotaria(new Notaria(notaria.getIdnotaria(),""));
				notaria.setEstado(notariaTemp.getEstado());
				notaria.setFechacreacion(notariaTemp.getFechacreacion());
				notaria.setFechaactualizacion(new Date());
				
				if(!notaria.getNombre().equals(notariaTemp.getNombre())){
					logMuroManager.add(new LogMuro(null, new Privilegio(10),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), notariaTemp.getNombre(), notaria
									.getNombre(), notaria.getIdnotaria(), "Notaria.Nombre", "N"));
				}
				if(!notaria.getDireccion().equals(notariaTemp.getDireccion())){
					logMuroManager.add(new LogMuro(null, new Privilegio(10),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), notariaTemp.getDireccion(), notaria
									.getDireccion(), notaria.getIdnotaria(), "Notaria.Dirección", "N"));
				}
				if(!notaria.getTelefono1().equals(notariaTemp.getTelefono1())){
					logMuroManager.add(new LogMuro(null, new Privilegio(10),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), notariaTemp.getTelefono1(), notaria
									.getTelefono1(), notaria.getIdnotaria(), "Notaria.Teléfono", "N"));
				}
				if(!notaria.getPaginaweb().equals(notariaTemp.getPaginaweb())){
					logMuroManager.add(new LogMuro(null, new Privilegio(10),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), notariaTemp.getPaginaweb(), notaria
									.getPaginaweb(), notaria.getIdnotaria(), "Notaria.Página Web", "N"));
				}
				if(!notaria.getEmail().equals(notariaTemp.getEmail())){
					logMuroManager.add(new LogMuro(null, new Privilegio(10),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), notariaTemp.getEmail(), notaria
									.getEmail(), notaria.getIdnotaria(), "Notaria.Email", "N"));
				}
				if(!notaria.getPlaza().getIdplaza().equals(notariaTemp.getPlaza().getIdplaza())){
					logMuroManager.add(new LogMuro(null, new Privilegio(10),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), notariaTemp.getPlaza().getNombre(), 
							plazaManager.finById(notaria.getPlaza().getIdplaza()).getNombre(), 
							notaria.getIdnotaria(), "Notaria.Plaza", "N"));
				}
				
				notariaManager.update(notaria);
				
				listaTemp = notaria.getNotariaContratos();
				
				notariaManager.deleteNotariaContrato(notariaTemp.getNotariaContratos());
			}
			notariaManager.agregarNotariaContrato(notaria, contratos);
			
			request.setAttribute("result", "true");
			return "result";
		}catch(Exception e){
			logger.error("Error agregar notaria ->"+e);
			request.setAttribute("result", "false");
			return "result";
		}
	}
	
	@RequestMapping(value = "/notaria/cargaForm.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public String cargaForm(ModelMap map) {
		
		try{
			Notaria n = new Notaria();
			n.setFechacreacion(new Date());
			map.addAttribute("notaria", n);
			map.addAttribute("plazaList", plazaManager.getAll());
			
		}catch(Exception e){
			logger.error("Error lista notarias"+e) ;
		}
		
		return "notaria/notariaForm";
	}
	
	@RequestMapping(value = "/notaria/cargaEditarForm.htm", method = RequestMethod.POST)
	public String cargaEditarForm(ModelMap map, HttpServletRequest request) {
		
		try{
			String id = request.getParameter("id");if(id==null)id="0";
			
			map.addAttribute("notaria", notariaManager.getNotaria(new Notaria(Integer.parseInt(id), "")));
			map.addAttribute("plazaList", plazaManager.getAll());
			
		}catch(Exception e){
			logger.error("Error cargaEditarForm"+e) ;
		}
		
		return "notaria/notariaForm";
	}
	
	@RequestMapping(value = "/notaria/cargaDetalle.htm", method = RequestMethod.POST)
	public String cargaDetalle(ModelMap map, HttpServletRequest request) {
		
		String idNotaria=request.getParameter("idNotaria");if(idNotaria==null)idNotaria="";
		
		try{
			
			
			Notaria notaria = notariaManager.getNotaria(new Notaria(Integer.parseInt(idNotaria), ""));			
			List<NotariaContrato> notariasContratosTemp = notariaManager.getNotariasContratosOrdenados(new Integer(idNotaria));
			notaria.setNotariaContratos(notariasContratosTemp);
			map.addAttribute("notaria",notaria );
			
			
		}catch(Exception e){
			logger.error("Error cargaDetalle"+e) ;
		}
		
		return "notaria/notariaDetalle";
	}
	
	@RequestMapping(value = "/notaria/cargaContratoForm.htm", method = RequestMethod.POST)
	public String cargaContratoForm(ModelMap map, HttpServletRequest request) {
		
		try{
			String idContrato = request.getParameter("id");if(idContrato==null)idContrato="0";
			
			map.addAttribute("contratoList", contratoManager.contratoList());
			
			if(idContrato.equals("0") ){
				map.addAttribute("notariaContrato", new Contrato(0));
			}else{
				map.addAttribute("notariaContrato", contratoManager.finById(Integer.parseInt(idContrato)));
			}
						
		}catch(Exception e){
			logger.error("Error cargaDetalle"+e) ;
		}
		
		return "notaria/notariaContratoForm";
	}
	
	@RequestMapping(value = "/notaria/subirArchivo.htm", method = RequestMethod.POST)
	public String subirArchivo(UploadedFile uploadedFile, ModelMap map, HttpServletRequest request) throws Exception{		
		map.addAttribute("result", notariaManager.subirArchivoNotarias(uploadedFile, (String)request.getSession().getAttribute("idusuario")));
		return "result";

	}
	
}
