package pe.conadis.tradoc.controller;

import java.text.DecimalFormat;
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

import com.google.gson.Gson;
import com.mysql.jdbc.Util;

import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Columna;
import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.entity.Nota;
import pe.conadis.tradoc.entity.Privilegio;
import pe.conadis.tradoc.entity.Producto;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.service.CapituloManager;
import pe.conadis.tradoc.service.ColumnaManager;
import pe.conadis.tradoc.service.LogMuroManager;
import pe.conadis.tradoc.service.NotaManager;
import pe.conadis.tradoc.service.ProductoManager;
import pe.conadis.tradoc.service.RubroManager;
import pe.conadis.tradoc.service.SubCapituloManager;
import pe.conadis.tradoc.service.VariableManager;
import pe.conadis.tradoc.util.Constants;

@Controller
public class RubroController {
	
	private static final Logger logger = Logger.getLogger(RubroController.class);
	
	@Autowired
	private ProductoManager productoManager;
	@Autowired
	private CapituloManager capituloManager;
	@Autowired
	private SubCapituloManager subCapituloManager;
	@Autowired
	private VariableManager variableManager;
	@Autowired
	private NotaManager notaManager;
	@Autowired
	private RubroManager rubroManager;
	@Autowired
	private ColumnaManager columnaManager;
	@Autowired
	private LogMuroManager logMuroManager;
	
//	@RequestMapping(value = "/tarifario/rubro/list.htm/", method = {RequestMethod.GET, RequestMethod.POST})
//	public String cargaListaDesdeSubcapitulo(ModelMap map, HttpServletRequest request) {
//		
//		return null;
//	}
	
	/*
	 * Carga la lista de rubros:
	 * Desde Producto: id = id del capitulo / rubro = 'P' 
	 * Desde Capitulos: id = id del capitulo / rubro = 'C'
	 * Desde subcapitulo : id  = id del sucapitulo
	 * 
	 */
	@RequestMapping(value = "/tarifario/rubro/list.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String cargaLista(ModelMap map, HttpServletRequest request) {
		
		
		
		try{
			
			String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
			
			String r = request.getParameter("rubro") ;
	
			if(id!="0"){
				Rubro rubro = new Rubro();
				
				// Si rubro no es nulo, viene de Capitulo o Producto 
				if(r != null){
					
					// Si viene por Productos
					if(r.equals("P")){
						Producto producto = productoManager.finById(Integer.parseInt(id));
						Capitulo cap = new Capitulo();
						cap.setDescripcion("NS");
						cap.setProducto(producto);
						
						// Se busca un capitulo (por id del producto y descripcion del capitulo)
						cap = capituloManager.findByDescripcion(cap);
						
						if(cap == null){
							cap = Capitulo.crearSinCapitulo(producto);
						}
						
						List<Capitulo> capituloList = new ArrayList<Capitulo>();
						capituloList.add(cap);
					
						
						map.addAttribute("capituloList", capituloList);

							
						Subcapitulo subcap = new Subcapitulo();
						subcap.setDescripcion("NS");
						subcap.setCapitulo(cap);
					
						// Se busca un subcapitulo (por id del capitulo y descripcion del subcapitulo)
						subcap = subCapituloManager.findByDescripcion(subcap);
					
						// si no encuentra se crea un subcapitulo "sin subcapitulo"
						if(subcap==null){
							subcap = Subcapitulo.crearSinSubcapitulo(cap);
						}
					
						rubro.setSubcapitulo(subcap);
					
						map.addAttribute("rubro", rubro);
						List<Subcapitulo> subCapituloList = new ArrayList<Subcapitulo>();
						subCapituloList.add(subcap);
					
						map.addAttribute("rubroList", obtenerRubrosConDescripcion(rubro));
						map.addAttribute("subCapituloList", subCapituloList);
						
//						List<Producto> productos =  productoManager.buscarProducto("%", producto.getTipocliente());
//						for (Producto producto2 : productos) {
//							producto2.setCapitulos(null);
//						}
						map.addAttribute("productoList", productoManager.buscarProducto("%", producto.getTipocliente()));

				
					}
					
					
					// Si viene por Capitulos
					if(r.equals("C")){
						
						Capitulo cap = capituloManager.finById(Integer.parseInt(id));
						
						Subcapitulo subcap = new Subcapitulo();
						subcap.setDescripcion("NS");
						subcap.setCapitulo(cap);
					
						// Se busca un subcapitulo (por id del capitulo y descripcion del subcapitulo)
						subcap = subCapituloManager.findByDescripcion(subcap);
					
						// si no encuentra se crea un subcapitulo "sin subcapitulo"
						if(subcap==null){
							subcap = Subcapitulo.crearSinSubcapitulo(cap);
						}
					
						rubro.setSubcapitulo(subcap);
					
						map.addAttribute("rubro", rubro);
						
						List<Subcapitulo> l = new ArrayList<Subcapitulo>();
						l.add(subcap);
					
						map.addAttribute("rubroList", obtenerRubrosConDescripcion(rubro));
						map.addAttribute("subCapituloList", l);
						map.addAttribute("capituloList", capituloManager.findByProducto(cap.getProducto().getIdproducto()));
						map.addAttribute("productoList", productoManager.buscarProducto("%", cap.getProducto().getTipocliente()));
				}
					
					
					
					// Si viene de subcapitulo
				}else{
					
					Subcapitulo subcap = subCapituloManager.finById(Integer.parseInt(id));
					rubro.setSubcapitulo(subcap);
					map.addAttribute("rubro", rubro);
					subcap.setNombre("");
					
					List<Rubro> rubros  =obtenerRubrosConDescripcion(rubro);
								
					map.addAttribute("rubroList", rubros);
								
					map.addAttribute("subCapituloList", subCapituloManager.buscarSubCapitulo(subcap));
					map.addAttribute("capituloList", capituloManager.findByProducto(subcap.getCapitulo().getProducto().getIdproducto()));
					map.addAttribute("productoList", productoManager.buscarProducto("%", subcap.getCapitulo().getProducto().getTipocliente()));
				}
				
			}else{
				map.addAttribute("rubro", new Rubro());
			}
			
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
		}catch(Exception e){
			logger.error("Error cargaLista  rubro-> "+e);
		}
		
		return "tarifario/rubro";
	}
	
	@RequestMapping(value = "/tarifario/rubro/cargaForm.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaForm(@ModelAttribute(value="rubro") Rubro rubro, ModelMap map, 
				HttpServletRequest request) {
		
		try {
			
			Producto producto = rubro.getSubcapitulo().getCapitulo().getProducto();
			Capitulo capitulo =  rubro.getSubcapitulo().getCapitulo();
			Subcapitulo subcapitulo = rubro.getSubcapitulo();
			
			String id = request.getParameter("id");if(id==null)id="";
			if(StringUtils.isEmpty(id)){
				rubro.setNombre("");
				rubro.setFechacreacion(new Date());
				
				
				
				if(producto.getTipocliente()!=null){
					map.addAttribute("productoList", productoManager.buscarProducto("%",producto.getTipocliente()));
				}
				
				if(producto.getIdproducto()!=null){
					
					if(capitulo.getIdcapitulo() == 0){
						List<Capitulo> l = new ArrayList<Capitulo>();
						capitulo.setNombre(Constants.SINCAP);
						l.add(capitulo);
						map.addAttribute("capituloList", l);
					}else{
						map.addAttribute("capituloList", capituloManager.findByProducto(producto.getIdproducto()));

					}
				}
				if(capitulo.getIdcapitulo()!=null){
					
					if(subcapitulo.getIdsubcapitulo()==0){
						List<Subcapitulo> l = new ArrayList<Subcapitulo>();
						subcapitulo.setNombre(Constants.SINSUBCAP);
						l.add(subcapitulo);
						map.addAttribute("subCapituloList", l);
					}else{
						map.addAttribute("subCapituloList", subCapituloManager.findByCapitulo(capitulo.getIdcapitulo()));
					}
					
				}
			}else{
				rubro = rubroManager.finById(Integer.parseInt(id));
				map.addAttribute("productoList", productoManager.buscarProducto("%", producto.getTipocliente()));
				map.addAttribute("capituloList", capituloManager.findByProducto(producto.getIdproducto()));
				map.addAttribute("subCapituloList", subCapituloManager.findByCapitulo(capitulo.getIdcapitulo()));
			}
			
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
			map.addAttribute("rubro", rubro);
			
		} catch (Exception e) {
			logger.error("Error cargaForm subCapituloForm -> "+e);
		}	
		return "tarifario/rubroForm";
	}
	
	@RequestMapping(value = "/tarifario/rubro/agregar.htm", method = RequestMethod.POST)
	public String agregarRubro(@ModelAttribute(value="rubro") Rubro rubro, HttpServletRequest request) {
		try{
		    String result="";
			if(rubro.getIdrubro()==null){
				
//				if(rubro.getSubcapitulo().getIdsubcapitulo() == 0){
//					rubro.getSubcapitulo().setIdsubcapitulo(null);
//				}
//				
//				if(rubro.getSubcapitulo().getCapitulo().getIdcapitulo() == 0){
//					rubro.getSubcapitulo().getCapitulo().setIdcapitulo(null);
//				}
			
				result = rubroManager.guardarRubro(rubro);

				logMuroManager.add(new LogMuro(null, new Privilegio(6),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_CREAR, new Date(), "", rubro.getNombre(), rubro.getIdrubro(), "Rubro", "N"));
				
			}else{
				
				Rubro r = rubroManager.finById(rubro.getIdrubro());
				
				String nombre = r.getNombre();
				int idSubCapitulo = r.getSubcapitulo().getIdsubcapitulo();
				
				result = rubroManager.editarRubro(rubro);
				
				if(!rubro.getNombre().equals(nombre)){
					logMuroManager.add(new LogMuro(null, new Privilegio(6),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), nombre, rubro.getNombre(), 
							rubro.getIdrubro(), "Rubro.Nombre", "N"));
				}
				
				if(!rubro.getSubcapitulo().getIdsubcapitulo().equals(idSubCapitulo)){
					logMuroManager.add(new LogMuro(null, new Privilegio(6),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), ""+idSubCapitulo, 
							""+rubro.getSubcapitulo().getIdsubcapitulo(), rubro.getIdrubro(), "Rubro.Sub Capítulo", "N"));
				}
			}
			
			request.setAttribute("result", result);
			return "result";
		}catch(Exception e){
			logger.error("Error agregar agregarRubro ->"+e);
			request.setAttribute("result", "-1");
			return "result";
		}
	}
	
	@RequestMapping(value = "/tarifario/rubro/buscar.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaRubro(@ModelAttribute(value="rubro") Rubro rubro, ModelMap map) {
		
		List<Rubro> rubros = obtenerRubrosConDescripcion(rubro);
		Gson g = new Gson();
		logger.debug(g.toJson(rubros));
		return g.toJson(rubros);
	}
	
	@RequestMapping(value = "/tarifario/rubro/eliminar.htm", method = RequestMethod.POST)
	public @ResponseBody String eliminarRubro(HttpServletRequest request) {
		String id = request.getParameter("id");if(id==null)id="0";
		
		String result =  "false";
		try {
			result =  ""+rubroManager.eliminarRubro(Integer.parseInt(id));
			
			Rubro rubro = rubroManager.finById(Integer.parseInt(id));
			
			logMuroManager.add(new LogMuro(null, new Privilegio(6),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_ELIMINAR, new Date(), rubro.getNombre(), "", rubro.getIdrubro(), "Rubro", "N"));
			
		} catch (Exception e) {
			logger.error("Error eliminar Sub Capitulo ->"+e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/tarifario/rubro/cargaNota.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaNotas(ModelMap map, HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			
			Rubro cap = rubroManager.finById(Integer.parseInt(id));
			
			if(cap.getNota()!=null&&cap.getNota().getTitulo()!=null){
				map.addAttribute("nota", cap.getNota());
			}else{
				map.addAttribute("nota", new Nota());
			}
			map.addAttribute("opcion", "rubro");
			request.setAttribute("id", id);
		} catch (Exception e) {
			logger.error("Error cargaNotas -> "+e);
		}
		return "tarifario/notaForm";
	}
	
	@RequestMapping(value = "/tarifario/rubro/agregarNota.htm", method = RequestMethod.POST)
	public String agregarNota(@ModelAttribute(value="nota") Nota nota, ModelMap map, HttpServletRequest request) {
		
		try {
			if(nota.getIdnota()==null){
				nota.setEstado("A".charAt(0));
				nota.setFechacreacion(new Date());
				notaManager.add(nota);
				int idRubro = Integer.parseInt(request.getParameter("id"));
				Rubro cap = rubroManager.finById(idRubro);
				cap.setNota(nota);
				
				rubroManager.update(cap);
				
				logMuroManager.add(new LogMuro(null, new Privilegio(6),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_MODIFICAR, new Date(), "", nota.getTitulo(), cap.getIdrubro(), "Rubro.Nota", "N"));
				
				
			}else{
				Nota notaTemp = notaManager.finById(nota.getIdnota());
				
				if(!notaTemp.getTitulo().equals(nota.getTitulo())){
					logMuroManager.add(new LogMuro(null, new Privilegio(6),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), notaTemp.getTitulo(), nota.getTitulo(), nota.getIdnota(), "Rubro.Nota.Titulo", "N"));
					
				notaTemp.setTitulo(nota.getTitulo());
				}
				
				if(!notaTemp.getDescripcion().equals(nota.getDescripcion())){
					logMuroManager.add(new LogMuro(null, new Privilegio(6),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), notaTemp.getDescripcion(), nota.getDescripcion(), nota.getIdnota(), "Rubro.Nota.Descripcion", "N"));
					
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
	
	@RequestMapping(value = "/tarifario/rubro/up.htm", method = RequestMethod.POST)
	public @ResponseBody String rubroUp(ModelMap map, @RequestParam("id") Integer idRubro, HttpServletRequest request) {
		
		String result = "";
		try {
			int posicionAnt = rubroManager.finById(idRubro).getOrden();
			result = rubroManager.up(idRubro)+"";
			int posicionNueva = rubroManager.finById(idRubro).getOrden();
			
			logMuroManager.add(new LogMuro(null, new Privilegio(6),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_MODIFICAR, new Date(), ""+posicionAnt, ""+posicionNueva, idRubro, "Rubro.Orden", "N"));
			
		} catch (Exception e) {
			logger.error("Error Up Rubro"+e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/tarifario/rubro/down.htm", method = RequestMethod.POST)
	public @ResponseBody String rubroDown(ModelMap map, @RequestParam("id") Integer idRubro, HttpServletRequest request) {
		String result = "";
		try {
			int posicionAnt = rubroManager.finById(idRubro).getOrden();
			result = rubroManager.down(idRubro)+"";
			int posicionNueva = rubroManager.finById(idRubro).getOrden();
			
			logMuroManager.add(new LogMuro(null, new Privilegio(6),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_MODIFICAR, new Date(), ""+posicionAnt, ""+posicionNueva, idRubro, "Rubro.Orden", "N"));
			
		} catch (Exception e) {
			logger.error("Error Down Rubro"+e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/cargaColumnasRubro.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaColumnas(ModelMap map, HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			
			if(id!=null){
				Rubro rubro = rubroManager.finById(Integer.parseInt(id));
				
				map.addAttribute("columnaList", columnaManager.findByRubro(new Columna(0,rubro,"","","","","","")));
				map.addAttribute("idRubro", id);
			}
			
		} catch (Exception e) {
			logger.error("Error cargaColumnas -> "+e);
		}
		return "tarifario/rubroColEdit";
	}
	
	@RequestMapping(value = "/tarifario/rubro/addColumnas.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String addColumnas(ModelMap map, HttpServletRequest request) {
		try {
			Integer idRubro = null;
			String idR = request.getParameter("idRubro");
			Rubro rubro = rubroManager.finById(Integer.parseInt(idR));
			
			if(idR!=null&&!idR.equals("undefined")&&!idR.equals("")){
				idRubro = Integer.parseInt(idR);
			}
			
			Integer idColumna = null;
			String idC = request.getParameter("idColumna");
			if(idC!=null&&!idC.equals("undefined")&&!idC.equals("")){
				idColumna = Integer.parseInt(idC);
			}
			
			String posicionX = request.getParameter("posicionX");
			String posicionY = request.getParameter("posicionY");
			String width = request.getParameter("width");
			
			String titulo = !request.getParameter("titulo").equals("undefined") ? request
					.getParameter("titulo") : "";
			String colspan = !request.getParameter("colspan").equals(
					"undefined") ? request.getParameter("colspan") : "1";
			String rowspan = !request.getParameter("rowspan").equals(
					"undefined") ? request.getParameter("rowspan") : "1";

			titulo = 	posicionY.equals("0") ? rubro.getNombre() : titulo;	
			Columna columna = new Columna(idColumna, rubro, new DecimalFormat("0").format(Double.parseDouble(width)),
					titulo, posicionX, posicionY, colspan, rowspan);

			columnaManager.add(columna);
			
			List<Columna> colsDel = (List<Columna>)request.getSession().getAttribute("colsDel");
			boolean ed = true;
			for(Columna col:colsDel){
				if(col.getPosicionx().equals(columna.getPosicionx())&&
						col.getPosiciony().equals(columna.getPosiciony())){
					ed=false;
					if(!col.getTitulo().equals(columna.getTitulo())){
						logMuroManager.add(new LogMuro(null, new Privilegio(6),
								(String) request.getSession().getAttribute("idusuario"),
								Constants.REPORTE_MODIFICAR, new Date(), col.getTitulo(), columna.getTitulo(), 
								idRubro, "Rubro.Columna.Título", "N"));
					}
					
					if(!col.getWidth().equals(columna.getWidth())){
						logMuroManager.add(new LogMuro(null, new Privilegio(6),
								(String) request.getSession().getAttribute("idusuario"),
								Constants.REPORTE_MODIFICAR, new Date(), col.getWidth(), columna.getWidth(), 
								idRubro, "Rubro.Columna.Tamaño", "N"));
					}
					if(!col.getColspan().equals(columna.getColspan())){
						logMuroManager.add(new LogMuro(null, new Privilegio(6),
								(String) request.getSession().getAttribute("idusuario"),
								Constants.REPORTE_MODIFICAR, new Date(), col.getColspan(), columna.getColspan(), 
								idRubro, "Rubro.Columna.Colspan", "N"));
					}
					if(!col.getRowspan().equals(columna.getRowspan())){
						logMuroManager.add(new LogMuro(null, new Privilegio(6),
								(String) request.getSession().getAttribute("idusuario"),
								Constants.REPORTE_MODIFICAR, new Date(), col.getRowspan(), columna.getRowspan(), 
								idRubro, "Rubro.Columna.Rowspan", "N"));
					}
				}
				
			}
			
			if(ed&&colsDel.size()>0){
				logMuroManager.add(new LogMuro(null, new Privilegio(6),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_MODIFICAR, new Date(), "", columna.getTitulo(), 
						idRubro, "Rubro.Columna.Título", "N"));
			}
			
			return columna.getIdcolumna()+"";
		} catch (Exception e) {
			logger.error("Error addColumnas -> "+e);
		}
		return "false";
	}
	
	@RequestMapping(value = "/tarifario/rubro/deleteByRubro.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String deleteByRubro(ModelMap map, HttpServletRequest request) {
		try {
			String idRubro = request.getParameter("idRubro");
			Columna col = new Columna();
			col.setRubro(new Rubro(Integer.parseInt(idRubro)));
			
			request.getSession().setAttribute("colsDel", columnaManager.findByRubro(col));
			
			return columnaManager.deleteByRubro(col)+"";
			
		} catch (Exception e) {
			logger.error("Error deleteByRubro -> "+e);
			return "false";
		}
	}
	
	@RequestMapping(value = "/tarifario/rubro/combo.htm", method = RequestMethod.POST)
	public @ResponseBody String cargaCombo(HttpServletRequest request) {
		
		String id = request.getParameter("id");if(id==null)id="";
		
		try {
			List<Rubro> rubro = rubroManager.findBySubCapitulo(Integer.parseInt(id));
			
			for(Rubro cap:rubro){
				cap.setCategorias(null);
				cap.setColumnas(null);
				cap.setSubcapitulo(null);
				cap.setNota(null);
			}
			
			return new Gson().toJson(rubro);
			
		} catch (Exception e) {
			logger.error("Error cargaCombo ->"+e);
			return "{}";
		}
	}
	
	private List<Rubro> obtenerRubrosConDescripcion(Rubro rubro){
		
		List<Rubro> rubros = rubroManager.buscarRubro(rubro);
		for(Rubro cap: rubros){
			boolean band = false;
			boolean band2 = false;
			cap.setDescripcion("");
			for(Categoria cate: cap.getCategorias()){
				if(cate.getDescripcion()!=null&&
						cate.getDescripcion().equals("NS")&&
						cate.getEstado().equals("A".charAt(0))){
							band = true;
				}else if(cate.getEstado().equals("A".charAt(0))){
					band2 = true;
				}
			}
			if(band2){
				cap.setDescripcion("CA");
			}
			if(band){
				cap.setDescripcion("TR");
			}
			cap.setCategorias(null);
			cap.setColumnas(null);
			cap.setNota(null);
			cap.setSubcapitulo(null);
		}
		
		return rubros; 
		
	}
	
}
