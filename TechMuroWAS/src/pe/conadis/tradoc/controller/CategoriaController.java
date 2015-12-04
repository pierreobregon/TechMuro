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

import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.entity.Privilegio;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.service.CapituloManager;
import pe.conadis.tradoc.service.CategoriaManager;
import pe.conadis.tradoc.service.ColumnaManager;
import pe.conadis.tradoc.service.LogMuroManager;
import pe.conadis.tradoc.service.NotaManager;
import pe.conadis.tradoc.service.ProductoManager;
import pe.conadis.tradoc.service.RubroManager;
import pe.conadis.tradoc.service.SubCapituloManager;
import pe.conadis.tradoc.service.VariableManager;
import pe.conadis.tradoc.util.Constants;

@Controller
public class CategoriaController {
	
	private static final Logger logger = Logger.getLogger(CategoriaController.class);
	
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
	private RubroManager rubroManager;
	@Autowired
	private ColumnaManager columnaManager;
	@Autowired
	private CategoriaManager categoriaManager;
	@Autowired
	private LogMuroManager logMuroManager;
	
	@RequestMapping(value = "/tarifario/categoria/list.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String cargaLista(ModelMap map, HttpServletRequest request) {
		try{
			String id = request.getParameter("id");if(id==null)id="0";
			
			if(id!="0"){
				
				Categoria categoria = new Categoria();
				Rubro rubro = rubroManager.finById(Integer.parseInt(id));
				map.addAttribute("categoria", categoria);
				rubro.setNombre("");
				categoria.setRubro(rubro);
				map.addAttribute("categoriaList", categoriaManager.buscarCategoria(categoria));
				map.addAttribute("rubroList", rubroManager.buscarRubro(rubro));
				categoria.getRubro().getSubcapitulo().setNombre("");
				map.addAttribute("subCapituloList", subCapituloManager.buscarSubCapitulo(rubro.getSubcapitulo()));
				map.addAttribute("capituloList", capituloManager.findByProducto(rubro.getSubcapitulo().getCapitulo().getProducto().getIdproducto()));
				map.addAttribute("productoList", productoManager.buscarProducto("%", rubro.getSubcapitulo().getCapitulo().getProducto().getTipocliente()));
				
			}else{
				map.addAttribute("categoria", new Categoria());
			}
			
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
		}catch(Exception e){
			logger.error("Error cargaLista categoria->"+e);
		}
		
		return "tarifario/categoria";
	}
	
	@RequestMapping(value = "/tarifario/categoria/cargaForm.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaForm(@ModelAttribute(value="categoria") Categoria categoria, ModelMap map, 
				HttpServletRequest request) {
		
		try {
			
			String id = request.getParameter("id");if(id==null)id="";
			if(StringUtils.isEmpty(id)){
				categoria.setNombre("");
				categoria.setFechacreacion(new Date());
				
				if(categoria.getRubro().getSubcapitulo().getCapitulo().getProducto().getTipocliente()!=null){
					map.addAttribute("productoList", productoManager.buscarProducto("%", categoria.getRubro().getSubcapitulo().getCapitulo().getProducto().getTipocliente()));
				}
				
				if(categoria.getRubro().getSubcapitulo().getCapitulo().getProducto().getIdproducto()!=null){
					map.addAttribute("capituloList", capituloManager.findByProducto(categoria.getRubro().getSubcapitulo().getCapitulo().getProducto().getIdproducto()));
				}
				
				if(categoria.getRubro().getSubcapitulo().getCapitulo().getIdcapitulo()!=null){
					map.addAttribute("subCapituloList", subCapituloManager.findByCapitulo(categoria.getRubro().getSubcapitulo().getCapitulo().getIdcapitulo()));
				}
				if(categoria.getRubro().getSubcapitulo().getIdsubcapitulo()!=null){
					map.addAttribute("rubroList", rubroManager.findBySubCapitulo(categoria.getRubro().getSubcapitulo().getIdsubcapitulo()));
				}
			}else{
				categoria = categoriaManager.finById(Integer.parseInt(id));
				map.addAttribute("productoList", productoManager.buscarProducto("%", categoria.getRubro().getSubcapitulo().getCapitulo().getProducto().getTipocliente()));
				map.addAttribute("capituloList", capituloManager.findByProducto(categoria.getRubro().getSubcapitulo().getCapitulo().getProducto().getIdproducto()));
				map.addAttribute("subCapituloList", subCapituloManager.findByCapitulo(categoria.getRubro().getSubcapitulo().getCapitulo().getIdcapitulo()));
				map.addAttribute("rubroList", rubroManager.findBySubCapitulo(categoria.getRubro().getSubcapitulo().getIdsubcapitulo()));
				
			}
			
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
			map.addAttribute("categoria", categoria);
			
		} catch (Exception e) {
			logger.error("Error cargaForm categoriaForm -> "+e);
		}	
		return "tarifario/categoriaForm";
	}
	
	@RequestMapping(value = "/tarifario/categoria/agregar.htm", method = RequestMethod.POST)
	public String agregarCategoria(@ModelAttribute(value="categoria") Categoria categoria, HttpServletRequest request) {
		try{
		    String result="";
			if(categoria.getIdcategoria()==null){
				result = categoriaManager.guardarCategoria(categoria);
				
				logMuroManager.add(new LogMuro(null, new Privilegio(7),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_CREAR, new Date(), "", categoria.getNombre(), categoria.getIdcategoria(), "Categoría", "N"));
				
			}else{
				
				Categoria cat = categoriaManager.finById(categoria.getIdcategoria());
				String nombreAnt = cat.getNombre();
				String denominacionAnt = cat.getDenominacion();
				int rubroAnt =  cat.getRubro().getIdrubro();
				
				result = categoriaManager.editarCategoria(categoria);
				
				if(!categoria.getNombre().equals(nombreAnt)){
					logMuroManager.add(new LogMuro(null, new Privilegio(7),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), nombreAnt, 
							categoria.getNombre(), categoria.getIdcategoria(), "Categoría.Nombre", "N"));
					
				}
				if(!categoria.getDenominacion().equals(denominacionAnt)){
					logMuroManager.add(new LogMuro(null, new Privilegio(7),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), denominacionAnt, 
							categoria.getDenominacion(), categoria.getIdcategoria(), "Categoría.Denominación", "N"));
				}
				if(!categoria.getRubro().getIdrubro().equals(rubroAnt)){
					logMuroManager.add(new LogMuro(null, new Privilegio(7),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), ""+rubroAnt, 
							""+categoria.getRubro().getIdrubro(), categoria.getIdcategoria(), "Categoría.Rubro", "N"));
				}
			}
			
			request.setAttribute("result", result);
			return "result";
		}catch(Exception e){
			logger.error("Error agregar agregarCategoria ->"+e);
			request.setAttribute("result", "Error al agregar categoria");
			return "result";
		}
	}
	
	@RequestMapping(value = "/tarifario/categoria/buscar.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaCategoria(@ModelAttribute(value="categoria") Categoria categoria, ModelMap map) {
		
		List<Categoria> categorias = categoriaManager.buscarCategoria(categoria);
		
		for(Categoria cap: categorias){
			cap.setRubro(null);
			cap.setTransaccions(null);
		}
		
		Gson g = new Gson();
		logger.debug(g.toJson(categorias));
		return g.toJson(categorias);
	}
	
	@RequestMapping(value = "/tarifario/categoria/eliminar.htm", method = RequestMethod.POST)
	public @ResponseBody String eliminarCategoria(HttpServletRequest request) {
		String id = request.getParameter("id");if(id==null)id="0";
		
		String result =  "false";
		try {
			result =  ""+categoriaManager.eliminarCategoria(Integer.parseInt(id));
			
			Categoria categoria = categoriaManager.finById(Integer.parseInt(id));
			
			logMuroManager.add(new LogMuro(null, new Privilegio(7),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_ELIMINAR, new Date(), categoria.getNombre(), "", categoria.getIdcategoria(), "Categoría", "N"));
			
		} catch (Exception e) {
			logger.error("Error eliminar Sub Capitulo ->"+e);
		}
		
		return result;
	}


	@RequestMapping(value = "/tarifario/categoria/up.htm", method = RequestMethod.POST)
	public @ResponseBody String categoriaUp(ModelMap map, @RequestParam("id") Integer idCategoria, HttpServletRequest request) {
		String result = "";
		try {
			int posicionAnt = categoriaManager.finById(idCategoria).getOrden();
			result = categoriaManager.up(idCategoria)+"";
			int posicionNueva = categoriaManager.finById(idCategoria).getOrden();
			
			logMuroManager.add(new LogMuro(null, new Privilegio(7),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_MODIFICAR, new Date(), ""+posicionAnt, ""+posicionNueva, idCategoria, "Categoria.Orden", "N"));
		
		} catch (Exception e) {
			logger.error("Error Up Categoría"+e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/tarifario/categoria/down.htm", method = RequestMethod.POST)
	public @ResponseBody String categoriaDown(ModelMap map, @RequestParam("id") Integer idCategoria, HttpServletRequest request) {
		String result = "";
		try {
			int posicionAnt = categoriaManager.finById(idCategoria).getOrden();
			result = categoriaManager.down(idCategoria)+"";
			int posicionNueva = categoriaManager.finById(idCategoria).getOrden();
			
			logMuroManager.add(new LogMuro(null, new Privilegio(7),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_MODIFICAR, new Date(), ""+posicionAnt, ""+posicionNueva, idCategoria, "Categoria.Orden", "N"));
			
		} catch (Exception e) {
			logger.error("Error Down Rubro"+e);
		}
		
		return result;
	}
//	
//	@RequestMapping(value = "/cargaColumnasRubro.htm", method = {RequestMethod.POST, RequestMethod.GET})
//	public String cargaColumnas(ModelMap map, HttpServletRequest request) {
//		try {
//			String id = request.getParameter("id");
//			
//			if(id!=null){
//				Rubro rubro = rubroManager.finById(Integer.parseInt(id));
//				
//				map.addAttribute("columnaList", columnaManager.findByRubro(new Columna(0,rubro,"","","","","","")));
//				map.addAttribute("idRubro", id);
//			}
//			
//		} catch (Exception e) {
//			logger.error("Error cargaColumnas -> "+e);
//		}
//		return "tarifario/rubroColEdit";
//	}
//	
//	@RequestMapping(value = "/tarifario/rubro/addColumnas.htm", method = {RequestMethod.POST, RequestMethod.GET})
//	public @ResponseBody String addColumnas(ModelMap map, HttpServletRequest request) {
//		try {
//			Integer idRubro = null;
//			String idR = request.getParameter("idRubro");
//			if(idR!=null&&!idR.equals("undefined")&&!idR.equals("")){
//				idRubro = Integer.parseInt(idR);
//			}
//			
//			Integer idColumna = null;
//			String idC = request.getParameter("idColumna");
//			if(idC!=null&&!idC.equals("undefined")&&!idC.equals("")){
//				idColumna = Integer.parseInt(idC);
//			}
//			
//			String posicionX = request.getParameter("posicionX");
//			String posicionY = request.getParameter("posicionY");
//			String width = request.getParameter("width");
//			
//			String titulo = !request.getParameter("titulo").equals("undefined") ? request
//					.getParameter("titulo") : "";
//			String colspan = !request.getParameter("colspan").equals(
//					"undefined") ? request.getParameter("colspan") : "1";
//			String rowspan = !request.getParameter("rowspan").equals(
//					"undefined") ? request.getParameter("rowspan") : "1";
//
//			Columna columna = new Columna(idColumna, new Rubro(idRubro), width,
//					titulo, posicionX, posicionY, colspan, rowspan);
//
//			columnaManager.add(columna);
//			
//			return columna.getIdcolumna()+"";
//		} catch (Exception e) {
//			logger.error("Error addColumnas -> "+e);
//		}
//		return "false";
//	}
//	
//	@RequestMapping(value = "/tarifario/rubro/deleteByRubro.htm", method = {RequestMethod.POST, RequestMethod.GET})
//	public @ResponseBody String deleteByRubro(ModelMap map, HttpServletRequest request) {
//		try {
//			String idRubro = request.getParameter("idRubro");
//			Columna col = new Columna();
//			col.setRubro(new Rubro(Integer.parseInt(idRubro)));
//			return columnaManager.deleteByRubro(col)+"";
//			
//		} catch (Exception e) {
//			logger.error("Error deleteByRubro -> "+e);
//			return "false";
//		}
//	}
	
	@RequestMapping(value = "/tarifario/categoria/combo.htm", method = RequestMethod.POST)
	public @ResponseBody String cargaCombo(HttpServletRequest request) {
		
		String id = request.getParameter("id");if(id==null)id="";
		
		try {
			List<Categoria> categorias = categoriaManager.findByRubro(Integer.parseInt(id));
			
			for(Categoria cap:categorias){
				cap.setRubro(null);
				cap.setTransaccions(null);
			}
			
			return new Gson().toJson(categorias);
			
		} catch (Exception e) {
			logger.error("Error cargaCombo ->"+e);
			return "{}";
		}
	}
	
	
}
