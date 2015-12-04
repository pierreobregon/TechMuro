package pe.conadis.tradoc.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import pe.conadis.tradoc.entity.Aviso;
import pe.conadis.tradoc.service.AvisoManager;


@Controller
public class AvisoController {

	
   private static final Logger logger = Logger.getLogger(AvisoController.class);

	@Autowired
	private AvisoManager avisoManager;
	
	
	@RequestMapping(value = "/listAllAvi.htm", method = RequestMethod.GET)
	public String listAll(ModelMap map) throws Exception {
		
		List<Aviso> avisos = avisoManager.listarAll();
		
		map.addAttribute("lisAllavi", avisos);
		return "aviso/aviso";
	}
	
	public void setAvisoManager(AvisoManager avisoManager) {
		this.avisoManager = avisoManager;
	}
	
	@RequestMapping(value = "/buscarAviso.htm", method = RequestMethod.POST)
	public @ResponseBody String buscarAviso(ModelMap map, HttpServletRequest request) throws Exception {
		
		String criterio = request.getParameter("criterio");if(criterio==null)criterio="";
		
		
		List<Aviso>  avi = avisoManager.buscarAviso("%"+criterio+"%"); 
		
		ArrayList<HashMap<String,String>> x = new ArrayList<HashMap<String,String>>();
			for(Aviso a: avi){
				logger.debug("idaviso -- >"+a.getIdaviso());
				logger.debug("Titulo -- >"+a.getTitulo());
				
				HashMap<String,String> mapa = new HashMap<String,String>();
				mapa.put("idaviso",a.getIdaviso().toString());
				mapa.put("fecha", a.getFechacreacion().toString());
                mapa.put("titulo", a.getTitulo().toString());
				mapa.put("circular", a.getCircular().toString());
                mapa.put("descripcion", a.getDescripcion().toString());
				x.add(mapa);
			}
		
			Gson g = new Gson();
			logger.debug("Buscar Aviso - "+g.toJson(x));
			return g.toJson(x);

	}
	
	@RequestMapping(value = "/deleteAviso/{Id}.htm", method = RequestMethod.GET)
	public @ResponseBody String deleteAviso(@PathVariable("Id") Integer Id) throws Exception
	{		
		avisoManager.deleteAviso(Id);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		List<Aviso>  avi = avisoManager.getCO();
		
		ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
		
		for(Aviso a: avi){
			logger.debug("idaviso -- >"+a.getIdaviso());
			logger.debug("Titulo -- >"+a.getTitulo());

			HashMap<String,String> mapa = new HashMap<String,String>();
			mapa.put("idaviso",a.getIdaviso().toString());
			Date today = a.getFechacreacion();      
			String reportDate = df.format(today);
			mapa.put("fecha", reportDate);
            mapa.put("titulo", a.getTitulo().toString());
			mapa.put("circular", a.getCircular().toString());
            mapa.put("descripcion", a.getDescripcion().toString());
			y.add(mapa);
		}
	
		Gson g = new Gson();
		logger.debug("delete aviso - "+g.toJson(y));
		return g.toJson(y);

	}
	
	@RequestMapping(value = "/upEditarAviso/{idAviso}.htm", method = RequestMethod.GET)
	public String upEditarAviso(@PathVariable("idAviso") Integer idAviso,ModelMap map) throws Exception {
		
		map.addAttribute("aviso", avisoManager.finById(idAviso)); 
		return "aviso/aviso-agregar";
	}
	
	@RequestMapping(value = "/upAgregarAviso.htm", method = RequestMethod.GET)
	public String upAgregarAviso(ModelMap map) throws Exception {
		
		Aviso avisoVacio = new Aviso();
		avisoVacio.setFechacreacion(new Date()); 
		map.addAttribute("aviso", avisoVacio);
		return "aviso/aviso-agregar";
	}
	
	
	@RequestMapping(value = "/addAviso.htm", method = RequestMethod.POST)
	public @ResponseBody String addAviso(@ModelAttribute(value="aviso") Aviso aviso , HttpServletRequest request) throws Exception 
	{
		request.setCharacterEncoding("UTF-8");
		
		
		logger.debug("Descripcion -- > "+ aviso.getDescripcion());
		
					
			if(aviso.getIdaviso() == null ){	
				
				aviso.setEstado('A');
				aviso.setFechacreacion(new Date());
				avisoManager.add(aviso);	
			}
			else{
				logger.debug("desc --> "+ aviso.getDescripcion());
				Aviso a=avisoManager.finById(aviso.getIdaviso());
				a.setTitulo(aviso.getTitulo());
				a.setDescripcion(aviso.getDescripcion());
				a.setCircular(aviso.getCircular());
				a.setFechaactualizacion(new Date());
				avisoManager.update(a);
				
				logger.debug("aca"+a.getTitulo());

			}
												
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				
				List<Aviso>  avi = avisoManager.getCO();
				
				ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
				
				for(Aviso a: avi){
					logger.debug("idaviso -- >"+a.getIdaviso());
					logger.debug("Titulo Aviso -- >"+a.getTitulo());

					HashMap<String,String> mapa = new HashMap<String,String>();
					mapa.put("idaviso",a.getIdaviso().toString());
					Date today = a.getFechacreacion();      
					String reportDate = df.format(today);
					mapa.put("fecha", reportDate);
		            mapa.put("titulo", a.getTitulo().toString());
					mapa.put("circular", a.getCircular().toString());
		            mapa.put("descripcion", a.getDescripcion().toString());
					y.add(mapa);
					logger.debug("carga lista   -->"+aviso.getTitulo());
				}
				
				Gson g = new Gson();
				logger.debug("Actualizo o agrego aviso - "+g.toJson(y));
				return g.toJson(y);
		
	}
}
