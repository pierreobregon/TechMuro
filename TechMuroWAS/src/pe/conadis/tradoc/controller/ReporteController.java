package pe.conadis.tradoc.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;




import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Producto;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.service.CapituloManager;
import pe.conadis.tradoc.service.LogMuroManager;
import pe.conadis.tradoc.service.ProductoManager;
import pe.conadis.tradoc.service.RubroManager;
import pe.conadis.tradoc.service.SubCapituloManager;
import pe.conadis.tradoc.service.VariableManager;


@Controller
public class ReporteController {

	private static final Logger logger = Logger.getLogger(ReporteController.class);
	
	@Autowired
	private VariableManager variableManager;
	
	@Autowired
	private ProductoManager productoManager;
	
	@Autowired
	private CapituloManager capituloManager;
	
	@Autowired
	private SubCapituloManager subCapituloManager;
	
	@Autowired
	private LogMuroManager logMuroManager;
	
	@Autowired
	private RubroManager rubroManager;
	
	
	@RequestMapping(value = "/reporte.htm", method = RequestMethod.GET)
	public String reportes(ModelMap map) throws Exception {
		map.addAttribute("tipoCliente", variableManager.tipoClienteList());
		return "reportes/reportes";
	}
		
	@RequestMapping(value = "/reportes/reportes/tarifarioCompleto.htm", method = RequestMethod.GET)
	public  ModelAndView  reporteTarifarioXLSCompleto(@RequestParam("id") String codigoTipoCliente, 
			@RequestParam("formato") String formato,
			HttpServletRequest request,  HttpServletResponse response) {	
		
		Map<String, List<Producto>> tarifarios = new HashMap<String, List<Producto>>();
		
		//Si no es ambos
		if(!codigoTipoCliente.equals("AM")){
			List<Producto> productos =  obtenerProductosPorTipoCliente(codigoTipoCliente);
			tarifarios.put(codigoTipoCliente,productos);
		}else{
			List<Producto> productosPN = obtenerProductosPorTipoCliente("PN");
			List<Producto> productosPJ = obtenerProductosPorTipoCliente("PJ");
			tarifarios.put("PN",productosPN);
			tarifarios.put("PJ",productosPJ);
		}
	
		
		
		if(formato.equals("xls")){
		return new ModelAndView("reporteTarifarioCompletoXLS", "tarifarios", tarifarios);
		}else{
			return new ModelAndView("reporteTarifarioCompletoPDF", "tarifarios", tarifarios);
		}
		
	}

	

	@RequestMapping(value = "/tarifario/reportes/productoCapituloForm.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaReporteProductoCapituloForm(@RequestParam("tipo") String tipoArchivo, ModelMap map, 
				HttpServletRequest request) {
		
		Subcapitulo subca = new Subcapitulo();
		subca.setDescripcion(tipoArchivo);
		map.addAttribute("formato", tipoArchivo);	
		map.addAttribute("subCapitulo", subca);	
		map.addAttribute("tipoCliente", variableManager.tipoClienteList());
				
		return "reportes/reporteProductoForm";
				
	}
	
	@RequestMapping(value = "/tarifario/reportes/logPDF.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaReporteLogPDF(HttpServletRequest request, HttpServletResponse response) {
		
		String fechaini = request.getParameter("fechaini");
		String fechafin = request.getParameter("fechafin");
		try{
			Date fechaIni = new SimpleDateFormat("yyyyMMdd").parse(fechaini);
			Date fechaFin = new SimpleDateFormat("yyyyMMdd").parse(fechafin);
			
			request.setAttribute("listaLog", logMuroManager.buscarLog(fechaIni, fechaFin));
			request.setAttribute("fechaini", fechaIni);
			request.setAttribute("fechafin", fechaFin);
		}catch(Exception e){
			System.out.println(e);
		}
		return "reportes/logPdf";		
	}
	
	@RequestMapping(value = "/tarifario/reportes/logXLS.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaReporteLogXLS(HttpServletRequest request, HttpServletResponse response) {
		
		String fechaini = request.getParameter("fechaini");
		String fechafin = request.getParameter("fechafin");
		try{
			Date fechaIni = new SimpleDateFormat("yyyyMMdd").parse(fechaini);
			Date fechaFin = new SimpleDateFormat("yyyyMMdd").parse(fechafin);
			
			request.setAttribute("listaLog", logMuroManager.buscarLog(fechaIni, fechaFin));
			request.setAttribute("fechaini", fechaIni);
			request.setAttribute("fechafin", fechaFin);
		}catch(Exception e){
			System.out.println(e);
		}
		return "reportes/logXls";		
	}
	

	@RequestMapping(value = "/reporte/reportes/generarReporte.htm", method = RequestMethod.POST)
	public ModelAndView generarReporteProductoCapitulo(@ModelAttribute(value="subCapitulo") Subcapitulo subcapitulo, HttpServletRequest request) throws Exception {
		
		String formato =subcapitulo.getDescripcion();
		Capitulo capitulo = subcapitulo.getCapitulo();
				
		// Si el usuario selecciono un capitulo 
		if(capitulo.getIdcapitulo() != null){
			
			// Se toma el metodo que utiliza el reporte del tarifario comppleto
			List<Capitulo> capitulos = capituloManager.buscarCapituloVisor(capitulo);
				for (Capitulo capituloReporte : capitulos) {
					if(capitulo.equals(capituloReporte)){
			
						Producto productoReporte = productoManager.finById(capitulo.getProducto().getIdproducto());
						
						//Se le asigna el producto para poder colocar el tipo de persona como nombre de la hoja.
						capituloReporte.setProducto(productoReporte);
						
						if(formato.equals("xls")){
						return new ModelAndView("reporteCapituloXLS", "capitulo", capituloReporte);
						}else{
							return new ModelAndView("reporteCapituloPDF", "capitulo", capituloReporte);
						}
						
					}
				}
			
		
		}else{
			Producto prod = capitulo.getProducto();
			Producto productoReporte = productoManager.finById(prod.getIdproducto());
			Capitulo capFiltro = new Capitulo(productoReporte);
			
			// Se toma el metodo que utiliza el tarifario
			List<Capitulo> capituloList = capituloManager.buscarCapituloVisor(capFiltro);
			productoReporte.setCapitulos(capituloList);
			
			if(formato.equals("xls")){
			return new ModelAndView("reporteProductoXLS", "producto", productoReporte);
			}else{
				return new ModelAndView("reporteProductoPDF", "producto", productoReporte);
			}
			
		}
		
		
		System.out.println("test");
		return null;
		
	}
	
	
	private List<Producto> obtenerProductosPorTipoCliente(String tipoCliente){
		List<Producto> productos = productoManager.getProductosPorTipoCliente(tipoCliente);
		
		for (Producto producto : productos) {
			Capitulo cap = new Capitulo(producto);
			List<Capitulo> capitulos = capituloManager.buscarCapituloVisor(cap);
			producto.setCapitulos(capitulos);
		}
		
		return productos;
		
	}
	

}
