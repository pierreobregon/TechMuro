package pe.conadis.tradoc.controller;

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

import com.google.gson.Gson;

import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.entity.Nota;
import pe.conadis.tradoc.entity.Privilegio;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.service.CapituloManager;
import pe.conadis.tradoc.service.LogMuroManager;
import pe.conadis.tradoc.service.NotaManager;
import pe.conadis.tradoc.service.ProductoManager;
import pe.conadis.tradoc.service.SubCapituloManager;
import pe.conadis.tradoc.service.VariableManager;
import pe.conadis.tradoc.util.Constants;

@Controller
public class SubCapituloController {
	
	private static final Logger logger = Logger.getLogger(SubCapituloController.class);
	
	@Autowired
	private SubCapituloManager subCapituloManager;
	@Autowired
	private VariableManager variableManager;
	@Autowired
	private CapituloManager capituloManager;
	@Autowired
	private ProductoManager productoManager;
	@Autowired
	private NotaManager notaManager;
	@Autowired
	private LogMuroManager logMuroManager;
	
	
	@RequestMapping(value = "/tarifario/subcapitulo/list.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String cargaLista(ModelMap map, HttpServletRequest request) {
		try{
			String id = request.getParameter("id");if(id==null)id="0";
			
			if(id!="0"){
				Subcapitulo subCapitulo = new Subcapitulo();
				Capitulo cap = capituloManager.finById(Integer.parseInt(id));
				subCapitulo.setCapitulo(cap);
				map.addAttribute("subCapitulo", subCapitulo);
				map.addAttribute("subCapituloList", subCapituloManager.buscarSubCapitulo(subCapitulo));
				map.addAttribute("capituloList", capituloManager.findByProducto(cap.getProducto().getIdproducto()));
				map.addAttribute("productoList", productoManager.buscarProducto("%", cap.getProducto().getTipocliente()));
			}else{
				map.addAttribute("subCapitulo", new Subcapitulo());
			}
			
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
		}catch(Exception e){
			logger.error("Error cargaLista  subcapitulo-> "+e);
		}
		
		return "tarifario/subCapitulo";
	}
	
	@RequestMapping(value = "/tarifario/subcapitulo/cargaForm.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaForm(@ModelAttribute(value="subCapitulo") Subcapitulo subCapitulo, ModelMap map, 
				HttpServletRequest request) {
		
		try {
			
			String id = request.getParameter("id");if(id==null)id="";
			if(StringUtils.isEmpty(id)){
				subCapitulo.setNombre("");
				subCapitulo.setFechacreacion(new Date());
				
				if(subCapitulo.getCapitulo().getProducto().getTipocliente()!=null){
					map.addAttribute("productoList", productoManager.buscarProducto("%", subCapitulo.getCapitulo().getProducto().getTipocliente()));
				}
				
				if(subCapitulo.getCapitulo().getProducto().getIdproducto()!=null){
					map.addAttribute("capituloList", capituloManager.findByProducto(subCapitulo.getCapitulo().getProducto().getIdproducto()));
				}
			}else{
				subCapitulo = subCapituloManager.finById(Integer.parseInt(id));
				map.addAttribute("productoList", productoManager.buscarProducto("%", subCapitulo.getCapitulo().getProducto().getTipocliente()));
				map.addAttribute("capituloList", capituloManager.findByProducto(subCapitulo.getCapitulo().getProducto().getIdproducto()));
			}
			
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
			map.addAttribute("subCapitulo", subCapitulo);
			
		} catch (Exception e) {
			logger.error("Error cargaForm subCapituloForm -> "+e);
		}	
		return "tarifario/subCapituloForm";
	}
	
	@RequestMapping(value = "/tarifario/subcapitulo/agregar.htm", method = RequestMethod.POST)
	public String agregarSubCapitulo(@ModelAttribute(value="capitulo") Subcapitulo subcapitulo, HttpServletRequest request) {
		try{
		    String result="";
			if(subcapitulo.getIdsubcapitulo()==null){
				result = subCapituloManager.guardarSubCapitulo(subcapitulo);
				
				logMuroManager.add(new LogMuro(null, new Privilegio(5),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_CREAR, new Date(), "", subcapitulo.getNombre(), subcapitulo.getIdsubcapitulo(), "Sub Capítulo", "N"));
				
			}else{
				String subCapituloAnt = subCapituloManager.finById(subcapitulo.getIdsubcapitulo()).getNombre();
				result = subCapituloManager.editarSubCapitulo(subcapitulo);
				
				logMuroManager.add(new LogMuro(null, new Privilegio(5),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_MODIFICAR, new Date(), subCapituloAnt, subcapitulo.getNombre(), subcapitulo.getIdsubcapitulo(), "Sub Capítulo.Nombre", "N"));
				
			}
			
			request.setAttribute("result", result);
			return "result";
		}catch(Exception e){
			logger.error("Error agregar agregarSubCapitulo ->"+e);
			request.setAttribute("result", "Error agregar SubCapitulo");
			return "result";
		}
	}
	
	@RequestMapping(value = "/tarifario/subcapitulo/buscar.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaSubCapitulo(@ModelAttribute(value="subCapitulo") Subcapitulo subCapitulo, ModelMap map) {
		
		List<Subcapitulo> subCapitulos = subCapituloManager.buscarSubCapitulo(subCapitulo);
		
		for(Subcapitulo cap: subCapitulos){
			cap.setCapitulo(null);
			cap.setNota(null);
			cap.setRubros(null);		
		}
		
		Gson g = new Gson();
		logger.debug(g.toJson(subCapitulos));
		return g.toJson(subCapitulos);
	}
	
	@RequestMapping(value = "/tarifario/subcapitulo/eliminar.htm", method = RequestMethod.POST)
	public @ResponseBody String eliminarSubCapitulo(HttpServletRequest request) {
		String id = request.getParameter("id");if(id==null)id="0";
		
		String result =  "false";
		try {
			result =  ""+subCapituloManager.eliminarSubCapitulo(Integer.parseInt(id));
			
			Subcapitulo subcapitulo = subCapituloManager.finById(Integer.parseInt(id));
			
			logMuroManager.add(new LogMuro(null, new Privilegio(5),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_ELIMINAR, new Date(), subcapitulo.getNombre(), "", subcapitulo.getIdsubcapitulo(), "Sub Capítulo", "N"));
			
		} catch (Exception e) {
			logger.error("Error eliminar Sub Capitulo ->"+e);
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/tarifario/subcapitulo/cargaNota.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaNotas(ModelMap map, HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			
			Subcapitulo cap = subCapituloManager.finById(Integer.parseInt(id));
			
			if(cap.getNota()!=null&&cap.getNota().getTitulo()!=null){
				map.addAttribute("nota", cap.getNota());
			}else{
				map.addAttribute("nota", new Nota());
			}
			map.addAttribute("opcion", "subcapitulo");
			request.setAttribute("id", id);
		} catch (Exception e) {
			logger.error("Error cargaNotas -> "+e);
		}	
		return "tarifario/notaForm";
	}
	
	@RequestMapping(value = "/tarifario/subcapitulo/agregarNota.htm", method = RequestMethod.POST)
	public String agregarNota(@ModelAttribute(value="nota") Nota nota, ModelMap map, HttpServletRequest request) {
		
		try {
			if(nota.getIdnota()==null){
				nota.setEstado("A".charAt(0));
				nota.setFechacreacion(new Date());
				notaManager.add(nota);
				int idSubCapitulo = Integer.parseInt(request.getParameter("id"));
				Subcapitulo cap = subCapituloManager.finById(idSubCapitulo);
				cap.setNota(nota);
				subCapituloManager.update(cap);
				
				
				logMuroManager.add(new LogMuro(null, new Privilegio(5),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_MODIFICAR, new Date(), "", nota.getTitulo(), cap.getIdsubcapitulo(), "Sub Capitulo.Nota", "N"));
				
				
			}else{
				Nota notaTemp = notaManager.finById(nota.getIdnota());
				
				if(!notaTemp.getTitulo().equals(nota.getTitulo())){
					logMuroManager.add(new LogMuro(null, new Privilegio(5),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), notaTemp.getTitulo(), nota.getTitulo(), nota.getIdnota(), "Sub Capitulo.Nota.Título", "N"));
					
				notaTemp.setTitulo(nota.getTitulo());
				}
				
				if(!notaTemp.getDescripcion().equals(nota.getDescripcion())){
					logMuroManager.add(new LogMuro(null, new Privilegio(5),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), notaTemp.getDescripcion(), nota.getDescripcion(), nota.getIdnota(), "Sub Capitulo.Nota.Descripcion", "N"));
					
				notaTemp.setDescripcion(nota.getDescripcion());
				}
				
				
				notaManager.update(notaTemp);
				
				
				
			}
			map.addAttribute("result", "true");
		} catch (Exception e) {
			logger.error("Error al insertar nota");
			map.addAttribute("result", "false");
		}
		
		return "result";
	}
	
	@RequestMapping(value = "/tarifario/subcapitulo/up.htm", method = RequestMethod.POST)
	public @ResponseBody String subCapituloUp(ModelMap map, @RequestParam("id") Integer idCapitulo, HttpServletRequest request) {
		
		String result = "";
		try {
			int posicionAnt = subCapituloManager.finById(idCapitulo).getOrden();
			result = subCapituloManager.up(idCapitulo)+"";
			int posicionNueva = subCapituloManager.finById(idCapitulo).getOrden();
			
			logMuroManager.add(new LogMuro(null, new Privilegio(5),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_MODIFICAR, new Date(), ""+posicionAnt, ""+posicionNueva, idCapitulo, "Sub Capitulo.Orden", "N"));
			
		} catch (Exception e) {
			logger.error("Error Up Sub Capitulo"+e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/tarifario/subcapitulo/down.htm", method = RequestMethod.POST)
	public @ResponseBody String subCapituloDown(ModelMap map, @RequestParam("id") Integer idCapitulo, HttpServletRequest request) {
		String result = "";
		try {
			int posicionAnt = subCapituloManager.finById(idCapitulo).getOrden();
			result = subCapituloManager.down(idCapitulo)+"";
			int posicionNueva = subCapituloManager.finById(idCapitulo).getOrden();
			
			logMuroManager.add(new LogMuro(null, new Privilegio(5),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_MODIFICAR, new Date(), ""+posicionAnt, ""+posicionNueva, idCapitulo, "Sub Capitulo.Orden", "N"));
			
		} catch (Exception e) {
			logger.error("Error Down Sub Capitulo"+e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/tarifario/subcapitulo/combo.htm", method = RequestMethod.POST)
	public @ResponseBody String cargaCombo(HttpServletRequest request) {
		
		String id = request.getParameter("id");if(id==null)id="";
		
		try {
			List<Subcapitulo> capitulos = subCapituloManager.findByCapitulo(Integer.parseInt(id));
			
			for(Subcapitulo cap:capitulos){
				cap.setNota(null);
				cap.setCapitulo(null);
				cap.setRubros(null);
			}
			
			return new Gson().toJson(capitulos);
			
		} catch (Exception e) {
			logger.error("Error cargaCombo ->"+e);
			return "{}";
		}
	}
}
