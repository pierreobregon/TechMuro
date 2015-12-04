package pe.conadis.tradoc.controller;

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

import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.entity.Oficina;
import pe.conadis.tradoc.entity.Privilegio;
import pe.conadis.tradoc.model.UploadedFile;
import pe.conadis.tradoc.service.AficheManager;
import pe.conadis.tradoc.service.AficheOficinaManager;
import pe.conadis.tradoc.service.ComunicadoOficinaManager;
import pe.conadis.tradoc.service.LogMuroManager;
import pe.conadis.tradoc.service.NotariaManager;
import pe.conadis.tradoc.service.OficinaManager;
import pe.conadis.tradoc.service.PlazaManager;
import pe.conadis.tradoc.service.VariableManager;
import pe.conadis.tradoc.util.Constants;

import com.google.gson.Gson;

@Controller
public class OficinaController {
	
	private static final Logger logger = Logger.getLogger(OficinaController.class);
	
	@Autowired
	private OficinaManager oficinaManager;	
	
	@Autowired
	private NotariaManager notariaManager;	
	
	@Autowired
	private PlazaManager plazaManager;
	
	@Autowired
	private LogMuroManager logMuroManager;
	
	@Autowired
	private AficheOficinaManager aficheOficinaManager;
	
	@Autowired
	private ComunicadoOficinaManager comunicadoOficinaManager;

	@Autowired
	private VariableManager variableManager;

	
	
	@RequestMapping(value = "/oficinas.htm", method = RequestMethod.GET)
	public String cargaOficinas(ModelMap map){
		
		try {
			List<Oficina> oficinas = oficinaManager.getOficinasActivas();
			for (Oficina oficina: oficinas) {
				oficina.setAficheOficinas(null);
				oficina.setComunicadoOficinas(null);
				oficina.setOcurrenciaMuros(null);
			}
			logger.debug("oficinitas "+ oficinas);
			map.addAttribute("oficinas", oficinas);
			map.addAttribute("uploadedFile", new UploadedFile());
			map.addAttribute("listaSize", oficinas.size());
			return "oficinas/oficinas";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "oficinas/oficinas";
	}
	
	@RequestMapping(value = "/buscarOficina.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaOficina(ModelMap map, HttpServletRequest request) throws Exception {
		
		String criterio = request.getParameter("criterio");if(criterio==null)criterio="";
		
		List<Oficina>  afiOfi2 = oficinaManager.buscarOficina("%"+criterio+"%");
		ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
			for(Oficina a: afiOfi2){

				HashMap<String,String> mapa = new HashMap<String,String>();
				  mapa.put("idplaza",a.getPlaza().getIdplaza().toString());
				  mapa.put("idoficina",a.getIdoficina().toString());
				  mapa.put("codigo",a.getCodigo());
				  mapa.put("nombre",a.getNombre());
				  mapa.put("plaza",a.getPlaza().getNombre());
				  y.add(mapa);
			}
		
			Gson g = new Gson();
			return g.toJson(y);

	}
	
	@RequestMapping(value = "/deleteOficina/{oficinaId}.htm", method = RequestMethod.GET)
	public @ResponseBody String deleteComunicado(@PathVariable("oficinaId") Integer oficinaId, 
			HttpServletRequest request) throws Exception{
		
		oficinaManager.deleteOficina(oficinaId);
			
		logMuroManager.add(new LogMuro(null, new Privilegio(15),
				(String) request.getSession().getAttribute("idusuario"),
				Constants.REPORTE_ELIMINAR, new Date(), oficinaManager.finById(oficinaId).getNombre(), 
				"", oficinaId, "Oficina", "N"));
		
		List<Oficina>  afiOfi2 = oficinaManager.getOficinasActivas();
		ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
			for(Oficina a: afiOfi2){

				HashMap<String,String> mapa = new HashMap<String,String>();
				  mapa.put("idplaza",a.getPlaza().getIdplaza().toString());
				  mapa.put("idoficina",a.getIdoficina().toString());
				  mapa.put("codigo",a.getCodigo());
				  mapa.put("nombre",a.getNombre());
				  mapa.put("plaza",a.getPlaza().getNombre());
				  y.add(mapa);
			}
		
			Gson g = new Gson();
			return g.toJson(y);

	}
	
	

	@RequestMapping(value = "/upAgregarOficina.htm", method = RequestMethod.GET)
	public String upAgregarComunicado(ModelMap map) throws Exception {
		
		Oficina oficina = new Oficina();
		
		oficina.setFechacreacion(new Date()); 
		map.addAttribute("oficina", oficina);
		
		
		map.addAttribute("plazaList", plazaManager.getAll());
		map.addAttribute("tipo", "agregar");
		return "oficinas/oficinas-agregar";
	}
	
	@RequestMapping(value = "/upEditarOficina/{idOfi}.htm", method = RequestMethod.GET)
	public String upEditarComunicado(@PathVariable("idOfi") Integer idOfi,ModelMap map) throws Exception {
		
		map.addAttribute("plazaList", plazaManager.getAll());
		
		map.addAttribute("oficina", oficinaManager.finById(idOfi));
		map.addAttribute("tipo", "editar");
		
		return "oficinas/oficinas-agregar";
	}
	
	@RequestMapping(value = "/addOficina.htm", method = RequestMethod.POST)
	public @ResponseBody String addAfiche(@ModelAttribute(value="oficina") Oficina oficina , HttpServletRequest request) throws Exception 
	{

					if(oficina.getIdoficina() == null ){
						/// AGREGAR
						
						oficina.setCodigo(("0000"+oficina.getCodigo().trim()).substring(oficina.getCodigo().trim().length()));
						
						if(oficinaManager.findByCodigo(oficina.getCodigo()).size()>0){
							return "false";
						}
						
						oficina.setEstado('A');
						oficina.setFechacreacion(new Date());
						//oficina.setFechaactualizacion(new Date());
						oficinaManager.add(oficina);
						
						
						logMuroManager.add(new LogMuro(null, new Privilegio(15),
								(String) request.getSession().getAttribute("idusuario"),
								Constants.REPORTE_CREAR, new Date(),"", oficina.getNombre(), oficina.getIdoficina(), "Oficina", "N"));
						
						
					}
					else{
						 /// EDITAR
						
//							if(oficinaManager.findByCodigo(oficina.getCodigo()).size()>0){
//								return "false";
//							}
						
							Oficina u =oficinaManager.finById(oficina.getIdoficina());
							
							if(!u.getCodigo().equals(oficina.getCodigo())){
								logMuroManager.add(new LogMuro(null, new Privilegio(15),
										(String) request.getSession().getAttribute("idusuario"),
										Constants.REPORTE_MODIFICAR, new Date(),u.getCodigo(), 
										oficina.getCodigo(), oficina.getIdoficina(), "Oficina.Codigo", "N"));
								
							u.setCodigo(oficina.getCodigo());
								
							}
							
							if(!u.getNombre().equals(oficina.getNombre())){
								logMuroManager.add(new LogMuro(null, new Privilegio(15),
										(String) request.getSession().getAttribute("idusuario"),
										Constants.REPORTE_MODIFICAR, new Date(),u.getNombre(), 
										oficina.getNombre(), oficina.getIdoficina(), "Oficina.Nombre", "N"));
								
							u.setNombre(oficina.getNombre());
							}
							
							if(!u.getPlaza().getIdplaza().equals(oficina.getPlaza().getIdplaza())){
								logMuroManager.add(new LogMuro(null, new Privilegio(15),
										(String) request.getSession().getAttribute("idusuario"),
										Constants.REPORTE_MODIFICAR, new Date(),""+u.getPlaza().getIdplaza(), 
										""+oficina.getPlaza().getIdplaza(), oficina.getIdoficina(), "Oficina.Plaza", "N"));
							u.setPlaza(oficina.getPlaza());
							}
							
							u.setFechaactualizacion(new Date());
							u.setIp(oficina.getIp());
							oficinaManager.update(u);
					}
				
				
					List<Oficina>  afiOfi2 = oficinaManager.getOficinasActivas();
					ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
						for(Oficina a: afiOfi2){

							HashMap<String,String> mapa = new HashMap<String,String>();
							  mapa.put("idplaza",a.getPlaza().getIdplaza().toString());
							  mapa.put("idoficina",a.getIdoficina().toString());
							  mapa.put("codigo",a.getCodigo());
							  mapa.put("nombre",a.getNombre());
							  mapa.put("plaza",a.getPlaza().getNombre());
							  y.add(mapa);
						}
					
						Gson g = new Gson();
						return g.toJson(y);
	
	}
	
	@RequestMapping(value = "/oficina/subirArchivo.htm", method = RequestMethod.POST)
	public String subirArchivo(UploadedFile uploadedFile, ModelMap map, HttpServletRequest request) throws Exception{
		
		map.addAttribute("result", oficinaManager.subirArchivoOficinas
				(uploadedFile, (String)request.getSession().getAttribute("idusuario")));
		
		return "result";

	}
	
	
	@RequestMapping(value = "/validaEliminacionOficina/{oficinaId}.htm", method = RequestMethod.GET)
	public @ResponseBody String validaEliminacionOficina(@PathVariable("oficinaId") Integer oficinaId, 
			HttpServletRequest request) throws Exception{
		
		String estado = "false";
		
		Oficina oficina = oficinaManager.finById(oficinaId);
		
		boolean tieneVariableGeneral = oficinaTieneVariableGeneral(oficina.getCodigo());
		if(tieneVariableGeneral) return estado;
		
		boolean tieneAfiches = aficheOficinaManager.oficinaTieneAfiches(oficinaId);
		if(tieneAfiches) return estado;

		
		boolean tieneComunicados = comunicadoOficinaManager.oficinaTieneComunicados(oficinaId);
		if(tieneComunicados) return estado;
		
		return "true";

	}
	
	private boolean oficinaTieneVariableGeneral(String codigo) {
		try {
			String codigoOficina = variableManager.finById(Constants.CODIGO_OFICINA_PRINCIPAL).getValor();
			return codigo.equals(codigoOficina);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
}
