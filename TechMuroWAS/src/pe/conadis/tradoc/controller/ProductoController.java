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

import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.entity.Privilegio;
import pe.conadis.tradoc.entity.Producto;
import pe.conadis.tradoc.service.LogMuroManager;
import pe.conadis.tradoc.service.ProductoManager;
import pe.conadis.tradoc.service.VariableManager;
import pe.conadis.tradoc.util.Constants;

import com.google.gson.Gson;


@Controller
public class ProductoController {
	
	private static final Logger logger = Logger.getLogger(ProductoController.class);

	@Autowired
	private ProductoManager productoManager;
	
	@Autowired
	private VariableManager variableManager;

	@Autowired
	private LogMuroManager logMuroManager;
	

	@RequestMapping(value = "/tarifario/producto/list.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String cargaLista(ModelMap map) {
		
		map.addAttribute("producto", new Producto());
		map.addAttribute("tipoCliente", variableManager.tipoClienteList());
		
		return "tarifario/producto";
	}
	
	@RequestMapping(value = "/tarifario/producto/buscar.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaProducto(@ModelAttribute(value="producto") Producto producto, ModelMap map, HttpServletRequest request) {
		
		String tipoCliente = producto.getTipocliente();if(tipoCliente==null)tipoCliente="";
		String criterio = producto.getNombre();if(criterio==null)criterio="";
		
		
		List<Producto> productos = productoManager.buscarProductoConCapitulos(criterio+"%", tipoCliente+"%"); 
		
		// Validacion para mostrar el ojo de capitulos y el ojo de rubros
		
		for (Producto prod : productos) {
			List<Capitulo> capitulos = prod.getCapitulos();
			
			// Si no tiene capitulos se ven los 2 ojos
			if(capitulos == null || capitulos.size() == 0){
				prod.setDescripcion("CAP,RU");
			}
			// Si tiene un ojo y es "Sin capitulo" solo se muestra el ojo de rubros
			else if(capitulos.size() == 1 && ((Capitulo)capitulos.get(0)).getNombre().equals(Constants.SINCAP)){
				prod.setDescripcion("RU");
			}
			// Caso contrario, muestra el ojo de capitulos
			else{
				prod.setDescripcion("CAP");
			}
			
			// al final se quitan los capitulos para enviar via JSON
			prod.setCapitulos(null);
		}
		
		return new Gson().toJson(productos);
	}
	
	@RequestMapping(value = "/tarifario/producto/combo.htm", method = RequestMethod.POST)
	public @ResponseBody String cargaCombo(HttpServletRequest request) {
		
		String tipoCliente = request.getParameter("tipoCliente");if(tipoCliente==null)tipoCliente="";
		
		return new Gson().toJson(productoManager.buscarProducto("%", tipoCliente));
	}
	
	@RequestMapping(value = "/tarifario/producto/cargaForm.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaForm(@ModelAttribute(value="producto") Producto prod, ModelMap map, HttpServletRequest request) {
		
		try {
			
			String id = request.getParameter("id");if(id==null)id="";
			Producto producto;
			if(StringUtils.isEmpty(id)){
				producto = new Producto();
				producto.setFechacreacion(new Date());
			}else{
				producto = productoManager.finById(Integer.parseInt(id));
			}
			
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
			map.addAttribute("producto", producto);
		} catch (Exception e) {
			logger.error("Error cargaForm -> "+e);
		}	
		return "tarifario/productoForm";
	}
	
	@RequestMapping(value = "/tarifario/producto/up.htm", method = RequestMethod.POST)
	public @ResponseBody String productoUp(ModelMap map, @RequestParam("id") Integer idProducto, HttpServletRequest request) {
		
		String result = "";
		try {
			int posicionAnt = productoManager.finById(idProducto).getOrden();
			result = productoManager.up(idProducto)+"";
			int posicionNueva = productoManager.finById(idProducto).getOrden();
			
			logMuroManager.add(new LogMuro(null, new Privilegio(3),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_MODIFICAR, new Date(), ""+posicionAnt, ""+posicionNueva, idProducto, "Producto.Orden", "N"));
			
		} catch (Exception e) {
			logger.error("Error Up Producto"+e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/tarifario/producto/down.htm", method = RequestMethod.POST)
	public @ResponseBody String productoDown(ModelMap map, @RequestParam("id") Integer idProducto, HttpServletRequest request) {
		
		String result = "";
		try {
			int posicionAnt = productoManager.finById(idProducto).getOrden();
			result = productoManager.down(idProducto)+"";
			int posicionNueva = productoManager.finById(idProducto).getOrden();
			
			logMuroManager.add(new LogMuro(null, new Privilegio(3),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_MODIFICAR, new Date(), ""+posicionAnt, ""+posicionNueva, idProducto, "Producto.Orden", "N"));
			
		} catch (Exception e) {
			logger.error("Error Down Producto"+e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/tarifario/producto/agregar.htm", method = RequestMethod.POST)
	public String agregarProducto(@ModelAttribute(value="producto") Producto producto, HttpServletRequest request) {
		try{
		    String result="";
			if(producto.getIdproducto()==null){
				result =  productoManager.guardarProducto(producto);
				
				logMuroManager.add(new LogMuro(null, new Privilegio(3),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_CREAR, new Date(), "", producto.getNombre(), producto.getIdproducto(), "Producto", "N"));
				
				
			}else{
				String productoAnt = productoManager.finById(producto.getIdproducto()).getNombre();
				
				result = productoManager.editarProducto(producto);
				
				logMuroManager.add(new LogMuro(null, new Privilegio(3),
						(String) request.getSession().getAttribute("idusuario"),
						Constants.REPORTE_MODIFICAR, new Date(), productoAnt, producto.getNombre(), producto.getIdproducto(), "Producto.Nombre", "N"));
				
			}
			
			request.setAttribute("result", result);
			return "result";
		}catch(Exception e){
			logger.error("Error agregar Producto ->"+e);
			request.setAttribute("result", "false");
			return "result";
		}
	}
	
	@RequestMapping(value = "/tarifario/producto/eliminar.htm", method = RequestMethod.POST)
	public @ResponseBody String eliminarNotaria(HttpServletRequest request) {
		String id = request.getParameter("id");if(id==null)id="0";
		
		String result =  "false";
		try {
			result =  ""+productoManager.eliminarProducto(Integer.parseInt(id));
			
			Producto producto = productoManager.finById(Integer.parseInt(id));
			
			logMuroManager.add(new LogMuro(null, new Privilegio(3),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_ELIMINAR, new Date(), producto.getNombre(), "", producto.getIdproducto(), "Producto", "N"));
			
		} catch (Exception e) {
			logger.error("Error eliminar Producto ->"+e);
		}
		
		return result;
	}
	
}
