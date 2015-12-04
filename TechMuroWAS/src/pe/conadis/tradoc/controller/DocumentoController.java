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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.Oficina;
import pe.conadis.tradoc.entity.Producto;
import pe.conadis.tradoc.entity.beans.Derivar;
import pe.conadis.tradoc.entity.beans.Documento;
import pe.conadis.tradoc.service.DerivarManager;
import pe.conadis.tradoc.service.DocumentoManager;
import pe.conadis.tradoc.service.OficinaManager;
import pe.conadis.tradoc.service.TipoDocumentoManager;
import pe.conadis.tradoc.util.Constants;
import pe.conadis.tradoc.util.Utilitario;


@Controller
public class DocumentoController {

	private static final Logger logger = Logger.getLogger(DocumentoController.class);
	
	@Autowired
	private OficinaManager oficinaManager;
	
	@Autowired
	private TipoDocumentoManager tipoDocumentoManager;
	
	@Autowired
	private DerivarManager derivarManager;	
	
	
	@RequestMapping(value = "/mesa_parte/derivarDocumentoBuscarExt.htm", method = RequestMethod.GET)
	public String cargaDerivarDocumentoBuscarExt(ModelMap map) {
		
		try{
			map.addAttribute("tipoDocumentoLista", tipoDocumentoManager.listaTipoDocumento());
			map.addAttribute("documento", new Documento());
			
			

			
		}catch(Exception e){
			logger.error("Error cargando lista de derivacion de documentos al experior"+e) ;
		}	
		
		return "mesa_parte/derivarDocumentoBuscarExt";
	}
	
	@RequestMapping(value = "/mesa_parte/listaDocumentoDerivar.htm", method = RequestMethod.POST)
	public @ResponseBody String listaDocumentoDerivar(@ModelAttribute(value="documento") Documento documento, ModelMap map, HttpServletRequest request) {
		
		try{
			
			List <Derivar> listaDerivar=derivarManager.listarDocumentosDerivarExterno(documento);
			
			ArrayList<HashMap<String,String>> listaMapa = new ArrayList<HashMap<String,String>>();
				for(Derivar derivar: listaDerivar){
					HashMap<String,String> mapa = new HashMap<String,String>();
					  mapa.put("codDocumento",derivar.getDocumento().getCodDocumento().toString());
					  mapa.put("nroExpediente",Utilitario.obtenerNumeroExpediente(derivar.getDocumento().getExpediente()));
					  mapa.put("tipoDocumento",Utilitario.obtenerTipoDocumento(derivar.getDocumento()));
					  mapa.put("nroDocumento",Utilitario.obtenerNumeroDocumento(derivar.getDocumento()));
					  mapa.put("enviadoPor",derivar.getDocumento().getExpediente().getEntidadExterna().getDesEntidad());
					  listaMapa.add(mapa);
				}
			
				Gson g = new Gson();
				return g.toJson(listaMapa);
				
			
		}catch(Exception e){
			logger.error("Error cargando lista de derivacion de documentos al exterior"+e) ;
			e.printStackTrace();
		}	
		return null;		
		
		/*
		
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
		*/
		//return new Gson().toJson(productos);
	}
		
	
	
	
	@RequestMapping(value = "/mesa_partes/derivarForm.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaDerivarForm(@ModelAttribute(value="documento") Documento documento, ModelMap map, HttpServletRequest request) {
		
		try {
			
			String id = request.getParameter("id");if(id==null)id="";
			Producto producto;
			if(StringUtils.isEmpty(id)){
				producto = new Producto();
				producto.setFechacreacion(new Date());
			}else{
				//producto = productoManager.finById(Integer.parseInt(id));
			}
			
			//map.addAttribute("tipoCliente", variableManager.tipoClienteList());
			//map.addAttribute("producto", producto);
		} catch (Exception e) {
			logger.error("Error cargaForm -> "+e);
		}	
		return "tarifario/productoForm";
	}
	
	
	
	
	
	@RequestMapping(value = "/mesa_parte/openDerivarDocumentoForm.htm", method = RequestMethod.GET)
	public String cargaDerivar(ModelMap map) {
		
		try{			
			
			List<Oficina> oficinas = oficinaManager.getOficinasActivas();
			
	
			logger.debug("lista de oficinas "+oficinas);
			map.addAttribute("oficinas", oficinas);
			map.addAttribute("cantidadOficnas", oficinas.size());
			
			
		}catch(Exception e){
			logger.error("Error"+e) ;
		}		
		return "mesa_parte/derivarDocumento";
	}
}
