package pe.conadis.tradoc.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.joda.time.Hours;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.conadis.tradoc.entity.Afiche;
import pe.conadis.tradoc.entity.AficheOficina;
import pe.conadis.tradoc.entity.Aviso;
import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.Comunicado;
import pe.conadis.tradoc.entity.ComunicadoOficina;
import pe.conadis.tradoc.entity.Notaria;
import pe.conadis.tradoc.entity.NotariaContrato;
import pe.conadis.tradoc.entity.Oficina;
import pe.conadis.tradoc.entity.Producto;
import pe.conadis.tradoc.service.AvisoManager;
import pe.conadis.tradoc.service.CapituloManager;
import pe.conadis.tradoc.service.ComunicadoManager;
import pe.conadis.tradoc.service.ComunicadoOficinaManager;
import pe.conadis.tradoc.service.ContratoManager;
import pe.conadis.tradoc.service.NotariaManager;
import pe.conadis.tradoc.service.OficinaManager;
import pe.conadis.tradoc.service.ProductoManager;
import pe.conadis.tradoc.service.VariableManager;
import pe.conadis.tradoc.util.Constants;

@Controller
public class VisorController {
	
	private static final Logger logger = Logger.getLogger(AficheController.class);
	
	@Autowired
	private OficinaManager oficinaManager;
	
	@Autowired
	private ContratoManager contratoManager;
	
	@Autowired
	private NotariaManager notariaManager;
	
	@Autowired
	private VariableManager variableManager;
	

	@Autowired
	private AvisoManager avisoManager;

	@Autowired
	private ComunicadoManager comunicadoManager;

	@Autowired
	private ComunicadoOficinaManager comunicadoOficinaManager;
	
	@Autowired
	private ProductoManager productoManager;
	
	@Autowired
	private CapituloManager capituloManager;
	
	
	
	@RequestMapping(value = "/visor/tarifario/{idProducto}.htm", method = RequestMethod.GET)
	public String visorTarifario(@PathVariable("idProducto") Integer idProducto, ModelMap map) throws Exception {
		
		Producto producto = productoManager.finById(idProducto);
		
		map.addAttribute("producto", producto);
		
		Capitulo cap = new Capitulo(producto);
		//List<Capitulo> capituloList = capituloManager.buscarCapituloVisor(cap);
		
		List<Capitulo> capituloList = capituloManager.obtenerTarifarioPorProducto(producto.getIdproducto());
		
		map.addAttribute("capituloList", capituloList);
		
		return "visor/tarifario";
	}
	

	@RequestMapping(value = "/visor_index/index.htm", method = RequestMethod.GET)
	public String index(ModelMap map) throws Exception {
		
		map.addAttribute("urlVisor", variableManager.finById(Constants.URL_VISOR).getValor());
		return "visor/ipredirect";
	}
	
	
	@RequestMapping(value = "/visor_index/{codigoOficina}.htm", method = RequestMethod.GET)
	public String visor(@PathVariable("codigoOficina") String codigoOficina,ModelMap map, HttpServletRequest request) throws Exception {
			
		Integer tiempoActualizacion = obtenerTiempoActualizacion();
		
		map.addAttribute("tiempoActualizacion", tiempoActualizacion);
		map.addAttribute("tiempoActualizacionPopUp", variableManager.finById(Constants.TIEMPOPOPUP).getValor());
		map.addAttribute("tiempoActualizacionPopUpVideo", variableManager.finById(Constants.TIEMPOVIDEO).getValor());
		
		map.addAttribute("avisos", avisoManager.listarAll());
		
		map.addAttribute("codigoOficina", codigoOficina);
		
		
		Map<String, String> rutasServidor = new HashMap<String, String>();
		
		String ipMultimedia = variableManager.finById(Constants.IP_MULTIMEDIA).getValor();
		String folderImagenes = variableManager.finById(Constants.DIRECCIONIMGAFICHES).getValor();
		String folderVideos = variableManager.finById(Constants.DIRECCIONVIDEOS).getValor();
		
		rutasServidor.put("ipMultimedia", ipMultimedia);
		rutasServidor.put("folderImagenes", folderImagenes);
		rutasServidor.put("folderVideos", folderVideos);
		
		int p = 0;
		
		int contAfiche = 0;
		
		Oficina o = oficinaManager.getOficinasByCodigoVisor(codigoOficina);

		Oficina todas = oficinaManager.getOficinasVisor(0);
		
		List<AficheOficina> lBigAfiche = new ArrayList<AficheOficina>();
		
		//setear direcciones miniatura
		for(AficheOficina i: todas.getAficheOficinas()){
				lBigAfiche.add(i);
				contAfiche++;
			if((8 - o.getAficheOficinas().size()) == contAfiche){
				break;
			}
			
		}
		
		
		// Asignar la direccion de la imagen y video segun el servidor definido en BD
		// ejemplo   /mnt/murtec_imagenes/   ->  http://118.180.34.142/murtec_imagenes
		
		Set<AficheOficina> afichesOficinaTodas =  todas.getAficheOficinas();
		actualizarURLServidorAfiche(afichesOficinaTodas, rutasServidor, request);
		
		Set<AficheOficina> afichesOficina =  o.getAficheOficinas();
		actualizarURLServidorAfiche(afichesOficina, rutasServidor, request);
		
		
		if((o.getAficheOficinas().size()+ todas.getAficheOficinas().size()) < 9){
			
			map.addAttribute("oficinaVisor", o.getAficheOficinas());
			
			map.addAttribute("todas", todas.getAficheOficinas());
		}else{
			if(o.getAficheOficinas().size() == 8){
				map.addAttribute("oficinaVisor", o.getAficheOficinas());
				
				map.addAttribute("todas", null);
			}else{
				map.addAttribute("oficinaVisor", o.getAficheOficinas());
				
				map.addAttribute("todas", lBigAfiche);
			}
		}
		

		
		Oficina r = oficinaManager.getOficinasByCodigoVisor(codigoOficina);
		
		List<AficheOficina> h = new ArrayList<AficheOficina>();
		
		for(AficheOficina i: r.getAficheOficinas()){
			
			i.getAfiche().getMiniatura();
			
			if(i.getAfiche().getMiniatura()=='S'){
				h.add(i);
			}
			
		}
		
		Integer numberMiniatura = h.size();

		
		
		Oficina miniaturaTodas = oficinaManager.getOficinasVisor(0);
		
		List<AficheOficina> z = new ArrayList<AficheOficina>();
		
		List<AficheOficina> l = new ArrayList<AficheOficina>();
		
		for(AficheOficina i: miniaturaTodas.getAficheOficinas()){
			
			i.getAfiche().getMiniatura();
			
			if(i.getAfiche().getMiniatura()=='S'){
				z.add(i);
			}
			
		}
		
		Integer numberMiniaturaTodas = z.size();
		
		for(AficheOficina i: miniaturaTodas.getAficheOficinas()){
			
			i.getAfiche().getMiniatura();
			
			if(i.getAfiche().getMiniatura()=='S'){
				l.add(i);
				p++;
			}
			
			if((8 - numberMiniatura) == p){
				break;
			}
			
		}
		
		actualizarURLServidorAfiche(h, rutasServidor, request);
		actualizarURLServidorAfiche(z, rutasServidor, request);
		actualizarURLServidorAfiche(l, rutasServidor, request);
		
		
		if((numberMiniatura + numberMiniaturaTodas) < 9){
			
			map.addAttribute("miniatura", h);
			
			map.addAttribute("miniaturaTodas", z);
			
		}else{
			if (numberMiniatura == 8) {
				
				map.addAttribute("miniatura", h);
				
				map.addAttribute("miniaturaTodas", null);
			}else{
				map.addAttribute("miniatura", h);
				
				map.addAttribute("miniaturaTodas", l);
			}
		}
		
		
		List<ComunicadoOficina> comunicados = comunicadoOficinaManager.buscarComunicadoTodas(codigoOficina);
		
		actualizarURLServidorComunicado(comunicados, rutasServidor);
		
		map.addAttribute("comunicados", comunicados);
		
		map.addAttribute("tt", variableManager.finById(Constants.TASASTARIFAS).getValor());
		
		map.addAttribute("pn", variableManager.finById(Constants.TIPOCLIENTE1).getValor());
		
		map.addAttribute("pj", variableManager.finById(Constants.TIPOCLIENTE2).getValor());
		
		map.addAttribute("no", variableManager.finById(Constants.NOTARIOS).getValor());
		
		map.addAttribute("co", variableManager.finById(Constants.COMUNICADOS).getValor());
		
		map.addAttribute("cmc", variableManager.finById(Constants.CANALESCERCANOS).getValor());
		
		
		map.addAttribute("productoListPN", productoManager.buscarProductoOrden("PN"));
		
		map.addAttribute("productoListPJ", productoManager.buscarProductoOrden("PJ"));
		
		return "visor/index";
	}
	
	

	


	@RequestMapping(value = "/visor/big/{idAfiche}.htm", method = RequestMethod.GET)
	public String bigVisor(@PathVariable("idAfiche") Integer idAfiche, ModelMap map) throws Exception {
		
		map.addAttribute("idAfiche", idAfiche);
		
		logger.debug(" Big Visor!");
		
		return "visor/BigImagen";
	}
	
	@RequestMapping(value = "/visor/afiche/{codigoOficina}.htm", method = RequestMethod.GET)
	public String verAfiches(@PathVariable("codigoOficina") String codigoOficina, ModelMap map) throws Exception {
		
		int p = 0;
		Oficina o = oficinaManager.getOficinasByCodigoVisor(codigoOficina);
		
		Oficina r = oficinaManager.getOficinasByCodigoVisor(codigoOficina);
		
		List<AficheOficina> h = new ArrayList<AficheOficina>();
		
		
		
		for(AficheOficina i: r.getAficheOficinas()){
			
			i.getAfiche().getMiniatura();
			
			if(i.getAfiche().getMiniatura()=='S'){
				h.add(i);
			}
			
		}
		
		Integer numberMiniatura = h.size();

		Oficina todas = oficinaManager.getOficinasVisor(0);
		
		Oficina miniaturaTodas = oficinaManager.getOficinasVisor(0);
		
		List<AficheOficina> z = new ArrayList<AficheOficina>();
		
		List<AficheOficina> l = new ArrayList<AficheOficina>();
		
		for(AficheOficina i: miniaturaTodas.getAficheOficinas()){
			
			i.getAfiche().getMiniatura();
			
			if(i.getAfiche().getMiniatura()=='S'){
				z.add(i);
			}
			
		}
		
		Integer numberMiniaturaTodas = z.size();
		
		for(AficheOficina i: miniaturaTodas.getAficheOficinas()){
			
			i.getAfiche().getMiniatura();
			
			if(i.getAfiche().getMiniatura()=='S'){
				l.add(i);
				p++;
			}
			
			if((8 - numberMiniatura) == p){
				break;
			}
			
		}
		
		if((numberMiniatura + numberMiniaturaTodas) < 9){
			
			map.addAttribute("miniatura", h);
			
			map.addAttribute("miniaturaTodas", z);
			
		}else{
			if (numberMiniatura == 8) {
				
				map.addAttribute("miniatura", h);
				
				map.addAttribute("miniaturaTodas", null);
			}else{
				map.addAttribute("miniatura", h);
				
				map.addAttribute("miniaturaTodas", l);
			}
		}
		
		map.addAttribute("oficinaVisor", o);
		
		map.addAttribute("todas", todas);
		
//		map.addAttribute("miniatura", h);
//		
//		map.addAttribute("miniaturaTodas", z);
//		
//		map.addAttribute("numberMiniatura", numberMiniatura);
//		
//		map.addAttribute("numberMiniaturaTodas", numberMiniaturaTodas);
		
		o.getAficheOficinas();
		
		return "visor/afiches";
	}
	
	@RequestMapping(value = "/visor/notarias/{codigoOficina}.htm", method = RequestMethod.GET)
	public String notarias(@PathVariable("codigoOficina") String codigoOficina, ModelMap map) throws Exception {
		
		map.addAttribute("codigoOficina", codigoOficina);
		
	//	Notaria not = notariaManager.getNotaria(new Notaria(Integer.parseInt("1716"), ""));
		
		List<NotariaContrato> contratos = contratoManager.findByCodigoOficina(codigoOficina);
		
		
		List<Oficina> oficinas = oficinaManager.findByCodigo(codigoOficina);;
		Oficina oficina = oficinas.size() == 0 ? null : oficinas.get(0);
		
		List<Notaria> notariasPorPlaza = notariaManager.getNotariasPorPlaza(oficina.getPlaza().getIdplaza());
		
		//Notaria not = notariaManager.getNotaria(new Notaria(Integer.parseInt("1716"), ""));
		
		//List<Notaria> notarias = notariaManager.getNotariaByPlaza
		List<NotariaContrato> notariasContratos = new ArrayList<NotariaContrato>();
		
		for (Notaria notariaPorPlaza : notariasPorPlaza) {			
			List<NotariaContrato> notariasContratosTemp = notariaManager.getNotariasContratosOrdenados(notariaPorPlaza.getIdnotaria());
						
			notariasContratos.addAll(notariasContratosTemp);
		}
		
				
		map.addAttribute("listaContrato",notariasContratos);
		
		return "visor/listaNotaria";
	}
	

	
	@RequestMapping(value = "/visor/canales/{codigoOficina}.htm", method = RequestMethod.GET)
	public String verCanales(@PathVariable("codigoOficina") String id, ModelMap map) throws Exception {
		
		String codigoOfi = String.format("%04d", new Integer(id));
		
		String urlCanales = variableManager.finById(Constants.URL_CANALES).getValor();
		map.addAttribute("url", urlCanales + codigoOfi);
		
		System.out.println("url canales : " + urlCanales + codigoOfi);
		
		logger.debug("Canales!");
		
		return "visor/canales";
	}
	
	
	
	@RequestMapping(value = "/visor/notaria/{id}.htm", method = RequestMethod.GET)
	public String verNotaria(@PathVariable("id") String id, ModelMap map) throws Exception {
		
		map.addAttribute("notaria", notariaManager.finById(Integer.parseInt(id)));
		
		logger.debug("Visor!");
		
		return "visor/notaria";
	}
	
	@RequestMapping(value = "/visor/comunicado/{id}.htm", method = RequestMethod.GET)
	public String verComunicado(@PathVariable("id") String id, ModelMap map) throws Exception {
		
		map.addAttribute("comunicado", comunicadoManager.finById(Integer.parseInt(id)));
		
		logger.debug("Visor!");
		
		
		return "visor/comunicado";
	}
	
	@RequestMapping(value = "/visor/comunicadoImagen/{id}.htm", method = RequestMethod.GET)
	public String verComunicadoImagen(@PathVariable("id") String id, ModelMap map) throws Exception {
		
		map.addAttribute("idComunicado", Integer.parseInt(id));
		
		logger.debug("Visor!");
		
		return "visor/comunicadoImagen";
	}
	
	@RequestMapping(value = "/visor/avisos.htm", method = RequestMethod.GET)
	public String avisos(ModelMap map) throws Exception {
		
		
		List<Aviso> avisos = avisoManager.listarAll();
		
		map.addAttribute("listaAvisos",avisos);
		
		return "visor/listaAviso";
	}
	
	
	private void actualizarURLServidorAfiche(Collection<AficheOficina> afichesOficina, Map<String,String> rutas, HttpServletRequest request) throws Exception{

		String ipMultimedia = rutas.get("ipMultimedia");
		String folderImagenes = rutas.get("folderImagenes");
		String folderVideos = rutas.get("folderVideos"); 
				
		for (AficheOficina aficheOficina : afichesOficina) {
			Afiche afiche = aficheOficina.getAfiche();
			
			
			String direccionAfiche = afiche.esAficheImagen() ? afiche.getDireccion() : afiche.getVideo();
			
			
			int ultimaOcurrenciaSlash = direccionAfiche.lastIndexOf("/");
			
			String nombreArchivo = direccionAfiche.substring(ultimaOcurrenciaSlash + 1);
			
			String extensionArchivo = nombreArchivo.substring(nombreArchivo.length()-4);
			
			String nombreArchivoMiniatura = nombreArchivo.substring(0,nombreArchivo.length()-4)+"_t"+extensionArchivo;
			logger.info("nombre archivo : " +  nombreArchivo); 
			logger.info("nombre archivo miniatura : "+nombreArchivoMiniatura);
			
			
			// Se asume que la ruta de imagenes tiene dos back slash, se establecio que el nombre es 
			String nombreFolder = afiche.esAficheImagen() ? folderImagenes.substring(folderImagenes.indexOf("/",2)) : 
				
			folderVideos.substring(folderVideos.indexOf("/",2));
			
			logger.info("nombre folder : " +  nombreFolder); 
			
			
			String nuevaDireccionAfiche = "http://" + ipMultimedia + nombreFolder + nombreArchivo;
			String nuevaDireccionAficheMiniatura = "http://" + ipMultimedia + nombreFolder + nombreArchivoMiniatura;

			//String nuevaDireccionAficheMiniatura = "file:///D:" + nombreFolder + nombreArchivoMiniatura;
			
			logger.info("direccion afiche : " + nuevaDireccionAfiche);
			
			
			if(afiche.getTipoafiche().equals("i")){
				afiche.setDireccion(nuevaDireccionAfiche);
				afiche.setDireccionMiniatura(nuevaDireccionAficheMiniatura);
			}else if(afiche.getTipoafiche().equals("v")){ // es video
				//afiche.setDireccion(request.getServletContext().getRealPath("/")+"afiche/video.jpg");
				afiche.setDireccion("../afiche/video.jpg");
				afiche.setVideo(nuevaDireccionAfiche);
			}else{
				afiche.setDireccion("../afiche/video.jpg");
				afiche.setVideo(afiche.getVideo());
			}
			
		}
		
	}
	
	
	private void actualizarURLServidorComunicado(List<ComunicadoOficina> comunicados,  Map<String,String> rutas) {
				
		String ipMultimedia = rutas.get("ipMultimedia");
		String folderImagenes = rutas.get("folderImagenes");
				
		for (ComunicadoOficina comunicadoOficina : comunicados) {
			Comunicado comunicado = comunicadoOficina.getComunicado();
			
			if(!comunicado.esComunicadoImagen()) continue;
			
			String direccionComunicado = comunicado.getUrl();
			int ultimaOcurrenciaSlash = direccionComunicado.lastIndexOf("/");
			
			String nombreArchivo = direccionComunicado.substring(ultimaOcurrenciaSlash + 1);
			logger.info("nombre archivo : " +  nombreArchivo); 
						
			String nombreFolder = folderImagenes.substring(folderImagenes.indexOf("/",2)) ;
			
			logger.info("nombre folder : " +  nombreFolder); 
						
			String nuevaDireccionComunicado = "http://" + ipMultimedia + nombreFolder + nombreArchivo;
			
			logger.info("direccion comunicado : "  + nuevaDireccionComunicado);
			comunicado.setUrl(nuevaDireccionComunicado);
										
		}		
	}
	
	public static String getClientIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
	
	
	
	@RequestMapping(value = "/oficina_visor/{ip}.htm", method = RequestMethod.GET)
	public @ResponseBody String obtenerCodigoOficina(@PathVariable("ip") String ip, 
			HttpServletRequest request) throws Exception{
		
		String codigoOficina  = oficinaManager.obtenerCodigoOficinaPorIP(ip);
		
		// Si no se encuentra en la tabla de oficinas, se va a buscar la
		// oficina por defecto en la tabla de Variables Generales.
		
		if (codigoOficina == null) {
			codigoOficina = variableManager.finById(Constants.CODIGO_OFICINA_PRINCIPAL).getValor();
		}
		
		
		return codigoOficina;
	}
	
	private Integer obtenerTiempoActualizacion() throws Exception {
	
		String[] horaMinutosActualizacion = variableManager.finById(Constants.HORA_ACTUALIZACION_VISOR).getValor().split(":");

		String horaActualizacion = horaMinutosActualizacion[0];
		String minutosActualizacion = horaMinutosActualizacion[1];
	
		LocalTime horaVisor = new LocalTime(new Integer(horaActualizacion),new Integer(minutosActualizacion));
		LocalTime horaActual = LocalTime.now();
	
		
		Seconds segundosDiff =  Seconds.secondsBetween(horaActual, horaVisor);
		Integer segundos = segundosDiff.getSeconds();
		Integer maxCantidadSeg = 24 * 60 * 60;
	
		if(segundosDiff.isLessThan(Seconds.ZERO)){
			return maxCantidadSeg + segundos;
		}
		
		return segundos; 

	}
		
}
