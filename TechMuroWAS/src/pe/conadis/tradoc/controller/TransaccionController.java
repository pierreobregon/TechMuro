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

import com.google.gson.Gson;

import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Columna;
import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.entity.Privilegio;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;
import pe.conadis.tradoc.service.CapituloManager;
import pe.conadis.tradoc.service.CategoriaManager;
import pe.conadis.tradoc.service.ColumnaManager;
import pe.conadis.tradoc.service.LogMuroManager;
import pe.conadis.tradoc.service.NotaManager;
import pe.conadis.tradoc.service.ProductoManager;
import pe.conadis.tradoc.service.RubroManager;
import pe.conadis.tradoc.service.SubCapituloManager;
import pe.conadis.tradoc.service.TransaccionDetalleManager;
import pe.conadis.tradoc.service.TransaccionManager;
import pe.conadis.tradoc.service.VariableManager;
import pe.conadis.tradoc.util.Constants;

@Controller
public class TransaccionController {
	
	private static final Logger logger = Logger.getLogger(TransaccionController.class);
	
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
	private TransaccionManager transaccionManager;
	@Autowired
	private TransaccionDetalleManager transaccionDetalleManager;
	@Autowired
	private LogMuroManager logMuroManager;
	
	@RequestMapping(value = "/tarifario/transaccion/list.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String cargaLista(ModelMap map, HttpServletRequest request) {
		try{
			String id = request.getParameter("id");if(id==null)id="0";
			
			if(id!="0"){
				
				String ct = request.getParameter("ct");if(id==null)id="";
				Transaccion transaccion = new Transaccion();
				
				if(ct!=null&&ct.equals("NS")){
					
					Rubro rubro = rubroManager.finById(Integer.parseInt(id));
					Categoria cat = new Categoria();
					cat.setDescripcion("NS");
					cat.setNombre(Constants.SINCAT);
					cat.setIdcategoria(0);
					cat.setRubro(rubro);
					
					transaccion.setCategoria(cat);
										
					cat.getRubro().setNombre("");
					map.addAttribute("rubroList", rubroManager.buscarRubro(cat.getRubro()));
					cat.getRubro().getSubcapitulo().setNombre("");
					map.addAttribute("subCapituloList", subCapituloManager.buscarSubCapitulo(cat.getRubro().getSubcapitulo()));
					map.addAttribute("capituloList", capituloManager.findByProducto(cat.getRubro().getSubcapitulo().getCapitulo().getProducto().getIdproducto()));
					map.addAttribute("productoList", productoManager.buscarProducto("%", cat.getRubro().getSubcapitulo().getCapitulo().getProducto().getTipocliente()));
					
					
					Categoria categoria = categoriaManager.findByDescripcion(cat);
					
					if(categoria != null){
						transaccion.setCategoria(new Categoria(categoria.getIdcategoria()));
						List<Transaccion> transacciones = transaccionManager.buscarTransaccion(transaccion);
						map.addAttribute("transaccionList", transacciones);
						
						// Se obtiene la categoria para devolver un objeto transaccion para los combos
						Categoria categ = categoriaManager.finById(categoria.getIdcategoria());
						transaccion.setCategoria(categ);
						map.addAttribute("transaccion", transaccion);
						
						// Agrega Sin Categoria al combo.
						List<Categoria> l = new ArrayList<Categoria>();
						l.add(categ);
						map.addAttribute("categoriaList", l);
						
					}else{
						
						transaccion.setCategoria(cat);
						map.addAttribute("transaccion", transaccion);
						
						// Agrega Sin Categoria al combo.
						List<Categoria> l = new ArrayList<Categoria>();
						l.add(cat);
						map.addAttribute("categoriaList", l);
					}
					
					
					
				
				}else{
					
					Categoria categoria = categoriaManager.finById(Integer.parseInt(id));
					transaccion.setCategoria(categoria);
					map.addAttribute("transaccion", transaccion);
					map.addAttribute("transaccionList", transaccionManager.buscarTransaccion(transaccion));
					categoria.setNombre("");
					map.addAttribute("categoriaList", categoriaManager.buscarCategoria(categoria));
					categoria.getRubro().setNombre("");
					map.addAttribute("rubroList", rubroManager.buscarRubro(categoria.getRubro()));
					categoria.getRubro().getSubcapitulo().setNombre("");
					map.addAttribute("subCapituloList", subCapituloManager.buscarSubCapitulo(categoria.getRubro().getSubcapitulo()));
					map.addAttribute("capituloList", capituloManager.findByProducto(categoria.getRubro().getSubcapitulo().getCapitulo().getProducto().getIdproducto()));
					map.addAttribute("productoList", productoManager.buscarProducto("%", categoria.getRubro().getSubcapitulo().getCapitulo().getProducto().getTipocliente()));
					
				}
			}else{
				map.addAttribute("transaccion", new Transaccion());
			}
			
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
		}catch(Exception e){
			logger.error("Error cargaLista transaccion->"+e);
		}
		
		return "tarifario/transaccion";
	}
	
	@RequestMapping(value = "/tarifario/transaccion/cargaForm.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaForm(@ModelAttribute(value="transaccion") Transaccion transaccion, ModelMap map, 
				HttpServletRequest request) {
		
		try {
			
			String id = request.getParameter("id");if(id==null)id="";
			if(StringUtils.isEmpty(id)){
				transaccion.setNombre("");
				transaccion.setFechacreacion(new Date());
				
				if(transaccion.getCategoria().getRubro().getSubcapitulo().getCapitulo().getProducto().getTipocliente()!=null){
					map.addAttribute("productoList", productoManager.buscarProducto("%", transaccion.getCategoria().getRubro().getSubcapitulo().getCapitulo().getProducto().getTipocliente()));
				}
				
				if(transaccion.getCategoria().getRubro().getSubcapitulo().getCapitulo().getProducto().getIdproducto()!=null){
					map.addAttribute("capituloList", capituloManager.findByProducto(transaccion.getCategoria().getRubro().getSubcapitulo().getCapitulo().getProducto().getIdproducto()));
				}
				
				if(transaccion.getCategoria().getRubro().getSubcapitulo().getCapitulo().getIdcapitulo()!=null){
					map.addAttribute("subCapituloList", subCapituloManager.findByCapitulo(transaccion.getCategoria().getRubro().getSubcapitulo().getCapitulo().getIdcapitulo()));
				}
				if(transaccion.getCategoria().getRubro().getSubcapitulo().getIdsubcapitulo()!=null){
					map.addAttribute("rubroList", rubroManager.findBySubCapitulo(transaccion.getCategoria().getRubro().getSubcapitulo().getIdsubcapitulo()));
				}
				if(transaccion.getCategoria().getRubro().getIdrubro()!=null){
					
					if(transaccion.getCategoria().getIdcategoria()==0){
						List<Categoria> l = new ArrayList<Categoria>();
						transaccion.getCategoria().setNombre(Constants.SINCAT);
						l.add(transaccion.getCategoria());
						map.addAttribute("categoriaList", l);
						
					}else{
						map.addAttribute("categoriaList", categoriaManager.findByRubro(transaccion.getCategoria().getRubro().getIdrubro()));
					}				
				}
		
			}else{
				
				transaccion = transaccionManager.finById(Integer.parseInt(id));
				map.addAttribute("productoList", productoManager.buscarProducto("%", transaccion.getCategoria().getRubro().getSubcapitulo().getCapitulo().getProducto().getTipocliente()));
				map.addAttribute("capituloList", capituloManager.findByProducto(transaccion.getCategoria().getRubro().getSubcapitulo().getCapitulo().getProducto().getIdproducto()));
				map.addAttribute("subCapituloList", subCapituloManager.findByCapitulo(transaccion.getCategoria().getRubro().getSubcapitulo().getCapitulo().getIdcapitulo()));
				map.addAttribute("rubroList", rubroManager.findBySubCapitulo(transaccion.getCategoria().getRubro().getSubcapitulo().getIdsubcapitulo()));
				map.addAttribute("categoriaList", categoriaManager.findByRubro(transaccion.getCategoria().getRubro().getIdrubro()));
			}
			
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
			map.addAttribute("transaccion", transaccion);
			
		} catch (Exception e) {
			logger.error("Error cargaForm transaccionForm -> "+e);
		}	
		return "tarifario/transaccionForm";
	}
	
	@RequestMapping(value = "/tarifario/transaccion/agregar.htm", method = RequestMethod.POST)
	public String agregarTransaccion(@ModelAttribute(value="transaccion") Transaccion transaccion, HttpServletRequest request) {
		try{
		    String result="";
		    
		    // si se crea la transaccion (nuevo)
			if(transaccion.getIdtransaccion()==null){
				result = transaccionManager.guardarTransaccion(transaccion);
				
				logMuroManager.add(new LogMuro(null, new Privilegio(8),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_CREAR, new Date(), "", transaccion.getNombre(), transaccion.getIdtransaccion(), "Transaccion", "N"));
				
			}else{ // en caso de modificacion (update)
				Transaccion tran = transaccionManager.finById(transaccion.getIdtransaccion());
				
				String nombreAnt = tran.getNombre();
				int categoriaAnt = tran.getCategoria().getIdcategoria();
				
				result = transaccionManager.editarTransaccion(transaccion);
				
				transaccionDetalleManager.actualizarTransaccionEnDetalle(transaccion);
				
				if(!transaccion.getNombre().equals(nombreAnt)){
					logMuroManager.add(new LogMuro(null, new Privilegio(8),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), nombreAnt, transaccion.getNombre(), 
							transaccion.getIdtransaccion(), "Transaccion.Nombre", "N"));
				}
				
				if(!transaccion.getCategoria().getIdcategoria().equals(categoriaAnt)){
					logMuroManager.add(new LogMuro(null, new Privilegio(8),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), ""+categoriaAnt, 
							""+transaccion.getCategoria().getIdcategoria(), 
							transaccion.getIdtransaccion(), "Transaccion.Categoría", "N"));
				}
			}
			
			request.setAttribute("result", result);
			return "result";
		}catch(Exception e){
			logger.error("Error agregar agregarTransaccion ->"+e);
			request.setAttribute("result", "Error al agregar transaccion");
			return "result";
		}
	}
	
	@RequestMapping(value = "/tarifario/transaccion/buscar.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaTransaccion(@ModelAttribute(value="transaccion") Transaccion transaccion, ModelMap map) {
		
		List<Transaccion> transacciones = transaccionManager.buscarTransaccion(transaccion);
		
		if(transacciones!=null)
		for(Transaccion cap: transacciones){
			cap.setCategoria(null);
			cap.setTransaccionDetalles(null);
		}
		
		Gson g = new Gson();
		logger.debug(g.toJson(transacciones));
		return g.toJson(transacciones);
	}
	
	@RequestMapping(value = "/tarifario/transaccion/eliminar.htm", method = RequestMethod.POST)
	public @ResponseBody String eliminarTransaccion(HttpServletRequest request) {
		String id = request.getParameter("id");if(id==null)id="0";
		
		return ""+transaccionManager.eliminarTransaccion(Integer.parseInt(id));
	}


	@RequestMapping(value = "/tarifario/transaccion/up.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String transaccionUp(ModelMap map, @RequestParam("id") Integer idTransaccion, HttpServletRequest request) {
		String result = "";
		try {
			int posicionAnt = transaccionManager.finById(idTransaccion).getOrden();
			result = transaccionManager.up(idTransaccion)+"";
			int posicionNueva = transaccionManager.finById(idTransaccion).getOrden();
			
			logMuroManager.add(new LogMuro(null, new Privilegio(8),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_MODIFICAR, new Date(), ""+posicionAnt, ""+posicionNueva, 
					idTransaccion, "Transaccion.Orden", "N"));
		
		} catch (Exception e) {
			logger.error("Error Up transaccion"+e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/tarifario/transaccion/down.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String transaccionDown(ModelMap map, @RequestParam("id") Integer idTransaccion, HttpServletRequest request) {
		String result = "";
		try {
			int posicionAnt = transaccionManager.finById(idTransaccion).getOrden();
			result = transaccionManager.down(idTransaccion)+"";
			int posicionNueva = transaccionManager.finById(idTransaccion).getOrden();
			
			logMuroManager.add(new LogMuro(null, new Privilegio(8),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_MODIFICAR, new Date(), ""+posicionAnt, ""+posicionNueva, 
					idTransaccion, "Transaccion.Orden", "N"));
			
		} catch (Exception e) {
			logger.error("Error Down transaccion"+e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/cargaDetalleTransaccion.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaDetalle(ModelMap map, HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			List<List<TransaccionDetalle>> listaFilas = new ArrayList<List<TransaccionDetalle>>();
			if(id!=null){
				Transaccion transaccion = transaccionManager.finById(Integer.parseInt(id));
				
				List<TransaccionDetalle> listaDetalle = transaccionManager.findDetalle(transaccion); 
				List<Columna> listaColumna = columnaManager.findByRubro(new Columna(null,transaccion.getCategoria().getRubro(), "", "", "", "", "", ""));
				
				map.addAttribute("listaColumna", listaColumna);
				
				if(listaDetalle.size()==0){
					
					listaDetalle = new ArrayList<TransaccionDetalle>();
					int posX = 0;
					int posY = 0;
					int posD = 0;
					for(Columna col : listaColumna){
						
						if(col.getPosicionx().equals("0")){
							
							
							if(Integer.parseInt(col.getColspan())>1){
								
								for(int i=0;i<Integer.parseInt(col.getColspan());i++){
									
									Columna colTemp = columnaManager.findByPosicion(new Columna(null, transaccion.getCategoria().getRubro(), "", "", "1", posD+"", "", ""));
									TransaccionDetalle detalle = new TransaccionDetalle();
									detalle.setTransaccion(transaccion);
									detalle.setPosicionx(posX);
									detalle.setPosiciony(posY);
									detalle.setRowspan(1);
									detalle.setColspan(1);
									//detalle.setWidth(Double.parseDouble(colTemp.getWidth())+"");
									listaDetalle.add(detalle);
									posY++;
									posD++;
								}
								
							}else{
								TransaccionDetalle detalle = new TransaccionDetalle();
								detalle.setTransaccion(transaccion);
								detalle.setPosicionx(posX);
								detalle.setPosiciony(posY);
								detalle.setRowspan(1);
								detalle.setColspan(1);
								//detalle.setWidth(Integer.parseInt(col.getWidth()));
								listaDetalle.add(detalle);
								posY++;
							}
						}else{
							break;
						}
						
					}
				}else{
					int max_rows = 0;
					for(TransaccionDetalle tx_det : listaDetalle){
						if(tx_det.getPosicionx()>max_rows){
							max_rows = tx_det.getPosicionx();
						}
					}
					

					for(int i = 1; i <= max_rows; i++){
						List<TransaccionDetalle> lista_tx_det = new ArrayList<TransaccionDetalle>();
						for(TransaccionDetalle tx_det : listaDetalle){
							if(tx_det.getPosicionx()==i){
								lista_tx_det.add(tx_det);
				}
						}
						listaFilas.add(lista_tx_det);
					}
					
				
					
				}
				map.addAttribute("listaFilas",listaFilas);
				map.addAttribute("listaDetalle", listaDetalle);
				map.addAttribute("transaccion", transaccion);
			}
			
		} catch (Exception e) {
			logger.error("Error cargaDetalle -> "+e);
		}
		return "tarifario/transaccionDetalle";
	}
	
	@RequestMapping(value = "tarifario/transaccion/addDetalle.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String addDetalle(ModelMap map, HttpServletRequest request) {
		try {
			Integer idTransaccion = null;
			String idR = request.getParameter("idTransaccion");
			if(idR!=null&&!idR.equals("undefined")&&!idR.equals("")){
				idTransaccion = Integer.parseInt(idR);
			}
			
			Integer idDetalle = null;
			String idC = request.getParameter("idDetalle");
			if(idC!=null&&!idC.equals("undefined")&&!idC.equals("")){
				idDetalle = Integer.parseInt(idC);
			}
			
			String posicionX = request.getParameter("posicionX");
			String posicionY = request.getParameter("posicionY");
			
			String contenido = request.getParameter("contenido")==null?"":(request.getParameter("contenido").equals("undefined")?"":request.getParameter("contenido").toString());	
			String colspan = request.getParameter("colspan")==null?"1":(request.getParameter("colspan").equals("undefined")?"1":request.getParameter("colspan").toString());
			String rowspan = request.getParameter("rowspan")==null?"1":(request.getParameter("rowspan").equals("undefined")?"1":request.getParameter("rowspan").toString());
			
			if(idDetalle==null){
				TransaccionDetalle tr =  new TransaccionDetalle(
						null,
						new Transaccion(idTransaccion), 
						Integer.parseInt(posicionY), 
						Integer.parseInt(posicionX),
						Integer.parseInt(colspan), 
						Integer.parseInt(rowspan),
						contenido, 
						new Date(), 
						"A".charAt(0)
				);
								
				transaccionDetalleManager.add(tr);
				logMuroManager.add(new LogMuro(null, new Privilegio(8),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_MODIFICAR, new Date(), "", tr.getContenido(), 
						idTransaccion, "Transaccion.Detalle", "N"));
			}else{
				TransaccionDetalle tr =  transaccionDetalleManager.finById(idDetalle);
				
				if(!(tr.getContenido()==null?"":tr.getContenido()).equals(contenido)){
					
					logMuroManager.add(new LogMuro(null, new Privilegio(8),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), tr.getContenido(), 
							contenido, idTransaccion, "Transaccion.Detalle", "N"));
					
				tr.setContenido(contenido);
				}
				
				if(!(""+tr.getColspan()).equals(colspan)){
					
					logMuroManager.add(new LogMuro(null, new Privilegio(8),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), ""+tr.getColspan(), 
							colspan, idTransaccion, "Transaccion.Detalle", "N"));
					
				tr.setColspan(Integer.parseInt(colspan));
				}
				
				if(!(""+tr.getRowspan()).equals(rowspan)){
					
					logMuroManager.add(new LogMuro(null, new Privilegio(8),
							(String) request.getSession().getAttribute("idusuario"),
							Constants.REPORTE_MODIFICAR, new Date(), ""+tr.getRowspan(), 
							rowspan, idTransaccion, "Transaccion.Detalle", "N"));
					
				tr.setRowspan(Integer.parseInt(rowspan));
			}
			
			
				tr.setEstado("A".charAt(0));
				transaccionDetalleManager.update(tr);
				
			}
						
			return "true";
		} catch (Exception e) {
			logger.error("Error addDetalle -> "+e);
		}
		return "false";
	}
	
	@RequestMapping(value = "/tarifario/transaccion/deleteByTransaccion.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String deleteByTransaccion(ModelMap map, HttpServletRequest request) {
		try {
			String idTransaccion = request.getParameter("idTransaccion");
			
			return ""+transaccionManager.deleteByTransaccion(transaccionManager.finById(Integer.parseInt(idTransaccion)));
			
		} catch (Exception e) {
			logger.error("Error deleteByRubro -> "+e);
			return "false";
		}
	}
	
	@RequestMapping(value = "/tarifario/transaccion/openDetalleForm.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String openDetalleForm(ModelMap map, HttpServletRequest request) {
		try {
			String idTransaccion = request.getParameter("idTransaccion");
			
			map.addAttribute("idTransaccion", idTransaccion);
			
			return "tarifario/transaccionDetalleForm";
		} catch (Exception e) {
			logger.error("Error openDetalleForm -> "+e);
			return "";
		}
	}
	
}
