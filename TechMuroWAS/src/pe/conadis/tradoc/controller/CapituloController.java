package pe.conadis.tradoc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.entity.Nota;
import pe.conadis.tradoc.entity.Privilegio;
import pe.conadis.tradoc.entity.Producto;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.service.CapituloManager;
import pe.conadis.tradoc.service.LogMuroManager;
import pe.conadis.tradoc.service.NotaManager;
import pe.conadis.tradoc.service.ProductoManager;
import pe.conadis.tradoc.service.VariableManager;
import pe.conadis.tradoc.util.Constants;

import com.google.gson.Gson;

@Controller
public class CapituloController {
	
	private static final Logger logger = Logger.getLogger(CapituloController.class);

	@Autowired
	private ProductoManager productoManager;
	
	@Autowired
	private CapituloManager capituloManager;
	
	@Autowired
	private VariableManager variableManager;
	
	@Autowired
	private NotaManager notaManager;

	@Autowired
	private LogMuroManager logMuroManager;
	

	@RequestMapping(value = "/tarifario/capitulo/list.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String cargaLista(ModelMap map, HttpServletRequest request) {
		try{
			String id = request.getParameter("id");if(id==null)id="0";
			
			if(id!="0"){
				Capitulo capitulo = new Capitulo();
				capitulo.setProducto(productoManager.finById(Integer.parseInt(id)));
				map.addAttribute("capitulo", capitulo);
				
				List<Capitulo> capitulos = capituloManager.buscarCapitulo(capitulo);
				
				for(Capitulo cap: capitulos){
					boolean band = false;
					boolean band2 = false;
					cap.setDescripcion("");
					
					
					// NS = Sin subcapitulo
					/*
					 *  Si algun subcapitulo es "Sin Subcapitulo", entonces se muestra el boton de rubros
					 *  caso contrario se muestra el boton de subcapitulos
					 */
					
					for(Subcapitulo subcap: cap.getSubcapitulos()){
						if(subcap.getDescripcion()!=null&&
								subcap.getDescripcion().equals("NS")&&
								subcap.getEstado().equals("A".charAt(0))){
							band = true;
						}else if(subcap.getEstado().equals("A".charAt(0))){
							band2 = true;
						}
					}
					
					if(band2){
						cap.setDescripcion("SC");
					}
					
					if(band){
						cap.setDescripcion("RU");
					}
				}
				map.addAttribute("capituloList",capitulos);
				map.addAttribute("productoList", productoManager.buscarProducto("%", capitulo.getProducto().getTipocliente()));
			}else{
				map.addAttribute("capitulo", new Capitulo());
			}
			
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
		}catch(Exception e){
			logger.error("Error cargaLista -> "+e);
		}
		
		return "tarifario/capitulo";
	}
	
	@RequestMapping(value = "/tarifario/capitulo/buscar.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaCapitulo(@ModelAttribute(value="capitulo") Capitulo capitulo, ModelMap map) {
		
		List<Capitulo> capitulos = capituloManager.buscarCapitulo(capitulo);
		
		for(Capitulo cap: capitulos){
			boolean band = false;
			boolean band2 = false;
			cap.setDescripcion("");
			for(Subcapitulo subcap: cap.getSubcapitulos()){
				if(subcap.getDescripcion()!=null&&
						subcap.getDescripcion().equals("NS")&&
						subcap.getEstado().equals("A".charAt(0))){
					band = true;
				}else if(subcap.getEstado().equals("A".charAt(0))){
					band2 = true;
				}
			}
			
			if(band2){
				cap.setDescripcion("SC");
			}
			
			if(band){
				cap.setDescripcion("RU");
			}
			
			cap.setSubcapitulos(null);
			cap.setNotaByIdnotainicial(null);
			cap.setNotaByIdnotafinal(null);
			cap.setProducto(null);
		}
		
		Gson g = new Gson();
		logger.debug(g.toJson(capitulos));
		return g.toJson(capitulos);
	}
	
	@RequestMapping(value = "/tarifario/capitulo/cargaForm.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaForm(@ModelAttribute(value="capitulo") Capitulo capitulo, ModelMap map, 
				HttpServletRequest request) {
		
		
	//	logger.debug("capitulo open form : " + capitulo);
		try {
			
			String id = request.getParameter("id");if(id==null)id="";
		//	logger.debug("capitulo open form id : >" + id + "<");
			if(StringUtils.isEmpty(id)){
				capitulo.setNombre("");
				capitulo.setFechacreacion(new Date());
		//		logger.debug("capitulo open form nuevo");
			}else{
				capitulo = capituloManager.finById(Integer.parseInt(id));
		//		logger.debug("capitulo open form edit");
			}
			
			List<Producto> listaProducto =  productoManager.buscarProducto("%", capitulo.getProducto().getTipocliente());
			map.addAttribute("productoList",listaProducto);
		//	logger.debug("capitulo open form producto cargado:  " + listaProducto.size());
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
		//	logger.debug("capitulo open form tipoCliente cargado");
			map.addAttribute("capitulo", capitulo);
			
		} catch (Exception e) {
			logger.error("Error cargaForm -> "+e);
		}	
		return "tarifario/capituloForm";
	}
	
	@RequestMapping(value = "/tarifario/capitulo/up.htm", method = RequestMethod.POST)
	public @ResponseBody String capituloUp(ModelMap map, @RequestParam("id") Integer idCapitulo, HttpServletRequest request) {
		
		String result = "";
		try {
			int posicionAnt = capituloManager.finById(idCapitulo).getOrden();
			result = capituloManager.up(idCapitulo)+"";
			int posicionNueva = capituloManager.finById(idCapitulo).getOrden();
			
			logMuroManager.add(new LogMuro(null, new Privilegio(4),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_MODIFICAR, new Date(), ""+posicionAnt, ""+posicionNueva, idCapitulo, "Capitulo.Orden", "N"));
			
		} catch (Exception e) {
			logger.error("Error Up Capitulo"+e);
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/tarifario/capitulo/down.htm", method = RequestMethod.POST)
	public @ResponseBody String capituloDown(ModelMap map, @RequestParam("id") Integer idCapitulo, HttpServletRequest request) {
		
		String result = "";
		try {
			int posicionAnt = capituloManager.finById(idCapitulo).getOrden();
			result = capituloManager.down(idCapitulo)+"";
			int posicionNueva = capituloManager.finById(idCapitulo).getOrden();
			
			logMuroManager.add(new LogMuro(null, new Privilegio(4),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_MODIFICAR, new Date(), ""+posicionAnt, ""+posicionNueva, idCapitulo, "Capitulo.Orden", "N"));
			
		} catch (Exception e) {
			logger.error("Error Down Capitulo"+e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/tarifario/capitulo/agregar.htm", method = RequestMethod.POST)
	public  String agregarCapitulo(@ModelAttribute(value="capitulo") Capitulo capitulo, HttpServletRequest request) {
		try{
		    String result="";
			if(capitulo.getIdcapitulo()==null){
				result = capituloManager.guardarCapitulo(capitulo);
				
				logMuroManager.add(new LogMuro(null, new Privilegio(4),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_CREAR, new Date(), "", capitulo.getNombre(), capitulo.getIdcapitulo(), "Capítulo", "N"));
				
			}else{
				
				String capituloAnt = capituloManager.finById(capitulo.getIdcapitulo()).getNombre();
				
				result = capituloManager.editarCapitulo(capitulo);
				
				logMuroManager.add(new LogMuro(null, new Privilegio(4),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_MODIFICAR, new Date(), capituloAnt, capitulo.getNombre(), capitulo.getIdcapitulo(), "Capítulo.Nombre", "N"));
				
			}
			
			request.setAttribute("idTest", "testtooo");
			request.setAttribute("result", result);
			return "result";
		}catch(Exception e){
			logger.error("Error agregar Capitulo ->"+e);
			request.setAttribute("result", "Error agregar Capitulo");
			return "result";
		}
	}
	
	@RequestMapping(value = "/tarifario/capitulo/eliminar.htm", method = RequestMethod.POST)
	public @ResponseBody String eliminarCapitulo(HttpServletRequest request) {
		String id = request.getParameter("id");if(id==null)id="0";
		
		
		String result =  "false";
		try {
			result =  ""+capituloManager.eliminarCapitulo(Integer.parseInt(id));
			
			Capitulo capitulo = capituloManager.finById(Integer.parseInt(id));
			
			logMuroManager.add(new LogMuro(null, new Privilegio(4),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_ELIMINAR, new Date(), capitulo.getNombre(), "", capitulo.getIdcapitulo(), "Capítulo.Nombre", "N"));
			
		} catch (Exception e) {
			logger.error("Error eliminar Producto ->"+e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/tarifario/capitulo/cargaNotas.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaNotas(ModelMap map, HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			
			Capitulo cap = capituloManager.finById(Integer.parseInt(id));
			
			if(cap.getNotaByIdnotainicial()!=null){
				map.addAttribute("notaSuperior", cap.getNotaByIdnotainicial());
			}else{
				map.addAttribute("notaSuperior", new Nota());
			}
			if(cap.getNotaByIdnotafinal()!=null){
				map.addAttribute("notaInferior", cap.getNotaByIdnotafinal());
			}else{
				map.addAttribute("notaInferior", new Nota());
			}
			
			request.setAttribute("idCapitulo", id);
		} catch (Exception e) {
			logger.error("Error cargaNotas -> "+e);
		}	
		return "tarifario/notasForm";
	}
	
	@RequestMapping(value = "/tarifario/capitulo/agregarNotaSuperior.htm", method = RequestMethod.POST)
	public String agregarNotaSuperior(@ModelAttribute(value="notaSuperior") Nota nota, ModelMap map, HttpServletRequest request) {
		
		try {
			if(nota.getIdnota()==null){
				nota.setEstado("A".charAt(0));
				nota.setFechacreacion(new Date());
				notaManager.add(nota);
				int idCapitulo = Integer.parseInt(request.getParameter("idCapitulo"));
				Capitulo cap = capituloManager.finById(idCapitulo);
				cap.setNotaByIdnotainicial(nota);
				capituloManager.update(cap);
			}else{
				Nota notaTemp = notaManager.finById(nota.getIdnota());
				notaTemp.setTitulo(nota.getTitulo());
				notaTemp.setDescripcion(nota.getDescripcion());
				notaManager.update(notaTemp);
			}
			map.addAttribute("result", "true");
		} catch (Exception e) {
			logger.error("Error al insertar nota");
			map.addAttribute("result", "false");
		}
		
		return "result";
	}
	
	@RequestMapping(value = "/tarifario/capitulo/agregarNotaInferior.htm", method = RequestMethod.POST)
	public String agregarNotaInferior(@ModelAttribute(value="notaInferior") Nota nota, HttpServletRequest request) {
		
		try {
			if(nota.getIdnota()==null){
				nota.setEstado("A".charAt(0));
				nota.setFechacreacion(new Date());
				notaManager.add(nota);
				int idCapitulo = Integer.parseInt(request.getParameter("idCapitulo"));
				Capitulo cap = capituloManager.finById(idCapitulo);
				cap.setNotaByIdnotafinal(nota);
				capituloManager.update(cap);
			}else{
				Nota notaTemp = notaManager.finById(nota.getIdnota());
				notaTemp.setTitulo(nota.getTitulo());
				notaTemp.setDescripcion(nota.getDescripcion());
				notaManager.update(notaTemp);
			}
			request.setAttribute("result", "true");
		} catch (Exception e) {
			logger.error("Error al insertar nota");
			request.setAttribute("result", "false");
		}
		
		return "result";
	}
	
	@RequestMapping(value = "/tarifario/capitulo/combo.htm", method = RequestMethod.POST)
	public @ResponseBody String cargaCombo(HttpServletRequest request) {
		
		String id = request.getParameter("id");if(id==null)id="";
		
		try {
			List<Capitulo> capitulos = capituloManager.findByProducto(Integer.parseInt(id));
			
			for(Capitulo cap:capitulos){
				cap.setNotaByIdnotafinal(null);
				cap.setNotaByIdnotainicial(null);
				cap.setProducto(null);
				cap.setSubcapitulos(null);
			}
			
			return new Gson().toJson(capitulos);
			
		} catch (Exception e) {
			logger.error("Error cargaCombo ->"+e);
			return "{}";
		}
	}
	
}
