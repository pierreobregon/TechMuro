package pe.conadis.tradoc.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pe.conadis.tradoc.entity.Afiche;
import pe.conadis.tradoc.entity.AficheOficina;
import pe.conadis.tradoc.entity.AficheOficinaId;
import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.entity.Oficina;
import pe.conadis.tradoc.entity.Privilegio;
import pe.conadis.tradoc.model.UploadedFile;
import pe.conadis.tradoc.service.AficheManager;
import pe.conadis.tradoc.service.AficheOficinaManager;
import pe.conadis.tradoc.service.LogMuroManager;
import pe.conadis.tradoc.service.OficinaManager;
import pe.conadis.tradoc.service.VariableManager;
import pe.conadis.tradoc.util.Constants;

import com.google.gson.Gson;

@Controller
public class AficheController {
	
	private static final Logger logger = Logger.getLogger(AficheController.class);
	
	@Autowired
	private AficheOficinaManager aficheOficinaManager;
	
	@Autowired
	private AficheManager aficheManager;
	
	@Autowired
	private OficinaManager oficinaManager;
	
	@Autowired
	private LogMuroManager logMuroManager;
	
	@Autowired
	private VariableManager variableManager;
	
	@RequestMapping(value = "/afiche.htm", method = RequestMethod.GET)
	public String listAfiches(ModelMap map) throws Exception {
		
		// Lista todos los afiches
		map.addAttribute("aficheList", aficheManager.getAll());
		List<AficheOficina>  afiOfi = aficheOficinaManager.getAO();
		map.addAttribute("lisAll", afiOfi);
		map.addAttribute("listaSize", afiOfi.size());
		return "afiches/afiches";
	}
	
	@RequestMapping(value = "/upAgregarAfiche.htm", method = RequestMethod.GET)
	public String upAgregarAfiche(ModelMap map) throws Exception {
		
		Afiche aficheVacio = new Afiche();
		
		aficheVacio.setFechacreacion(new Date());;
		map.addAttribute("afiche", aficheVacio);
		List<Oficina> oficinas = oficinaManager.getOficinasFilter();
		
		for (Oficina oficina: oficinas) {
			oficina.setAficheOficinas(null);
			oficina.setComunicadoOficinas(null);
			oficina.setOcurrenciaMuros(null);
			oficina.setPlaza(null);
		}
		
		logger.debug("lista de oficinas "+oficinas);
		map.addAttribute("oficinas", oficinas);
		map.addAttribute("cantidadOficnas", oficinas.size());
		return "afiches/afiches-agregar";
	}
	
	@RequestMapping(value = "/upAgregarAfiche/{idAfi}/{idOfi}.htm", method = RequestMethod.GET)
	public String upEditarAfiche(@PathVariable("idAfi") Integer idAfi,@PathVariable("idOfi") Integer idOfi,ModelMap map) throws Exception {
		
		List<Oficina> oficinas = oficinaManager.getAll();
		
		for (Oficina oficina: oficinas) {
			oficina.setAficheOficinas(null);
			oficina.setComunicadoOficinas(null);
			oficina.setOcurrenciaMuros(null);
			oficina.setPlaza(null);
		}
		
		map.addAttribute("oficinas", oficinas);
		map.addAttribute("afiche", aficheManager.finById(idAfi));
		map.addAttribute("aficheOficina", aficheOficinaManager.byIdAO(idAfi, idOfi));
		
		return "afiches/afiches-agregar";
	}
	
	@RequestMapping(value = "/listAfiche.htm", method = RequestMethod.GET)
	public @ResponseBody String buscaProducto(ModelMap map) throws Exception {
				
		List<Afiche> afiches = aficheManager.getAll();

		for (Afiche afiche: afiches) {
			afiche.setAficheOficinas(null);
		}

		Gson g = new Gson();
		logger.debug(g.toJson(afiches));
		return g.toJson(afiches);
	}
	
	@RequestMapping(value = "/listAll.htm", method = RequestMethod.GET)
	public @ResponseBody String listAll(ModelMap map) throws Exception {
		
		List<Object> afiches = aficheManager.listarAll();
		
		map.addAttribute("lisAll", afiches);
		return "";
	}
	
	@RequestMapping(value = "/min/{oficinas}.htm", method = RequestMethod.GET)
	public @ResponseBody String minMiniatura(@PathVariable("oficinas") String ofic,ModelMap map) throws Exception 
	{
		
		if(ofic.equals("null") || ofic.equals("")){
		}
		else{
			String[] a = ofic.split(",");
		
			for(String j:a){
				if(j.equals("multiselect-all")){
					
					logger.debug("todas!");
					Oficina s = oficinaManager.getOficinasVisor(0);
					if(s.getAficheOficinas().size() == 7 ){
						for(AficheOficina p: s.getAficheOficinas()){
							if(p.getAfiche().getMiniatura()=='S'){
								return "encontro";
							}
						}
					}
					break;
				}
				else{
					logger.debug("todas!23");
					Oficina s = oficinaManager.getOficinasVisor(Integer.parseInt(j));
					
					if(s.getAficheOficinas().size() == 7){
						for(AficheOficina p: s.getAficheOficinas()){
							if(p.getAfiche().getMiniatura()=='S'){
								return "encontro";
							}
						}
					
					}
				}
			}
		
			return "no";
		}
		
		return "no";
		
	}

	@RequestMapping(value = "/addAfi/{oficinaMiniatura}/{oficinas}.htm", method = RequestMethod.POST)
	public @ResponseBody String addAfiche(@PathVariable("oficinaMiniatura") String oficinaMiniatura, @PathVariable("oficinas") String ofic, @ModelAttribute(value="afiche") Afiche afiche , BindingResult result,UploadedFile uploadedFile, ModelMap map, HttpServletRequest request) throws Exception 
	{
		
		boolean todas = false;
		
		logger.debug("miniatura -- > "+oficinaMiniatura);
		
		String nombreArchivo = obtenerNombreArchivo();
		
		if(afiche.getMiniatura()== null){
	
			//return aficheManager.validateMinAny(oficinaMiniatura, ofic, afiche, result, uploadedFile);

				if(ofic.equals("null")){
					
				}else{
					
					if(afiche.getIdafiche() == null ){
						
						logger.debug("URL! --> "+uploadedFile);
						logger.debug("URL! 222--> "+uploadedFile.getFile());
						
						if(uploadedFile.getFile() == null){
							
							afiche.setDireccion("../afiche/video.jpg");
							afiche.setTipoafiche("y");
							
						}else{
								
							InputStream inputStream = null;
							OutputStream outputStream = null;
							MultipartFile file = uploadedFile.getFile();
							String fileName = file.getOriginalFilename();
							String extension = fileName.substring(fileName.length() - 3);							
							fileName = nombreArchivo + "." + extension;
							
							String fileName_t= nombreArchivo+"_t."+extension;
					
							try {
								inputStream = file.getInputStream();
				
								String contentType = file.getContentType();
								int direccionAfiche = contentType.contains("video") ? Constants.DIRECCIONVIDEOS : Constants.DIRECCIONIMGAFICHES;
								//File newFile = new File(variableManager.finById(direccionAfiche).getValor() + fileName);
								File newFolder =  new File(variableManager.finById(direccionAfiche).getValor());
								if (!newFolder.exists()) {
									//newFolder.mkdirs(); ;
									return "errorArchivo";
								}
																
								File newFile = new File(variableManager.finById(direccionAfiche).getValor() + fileName);
								if (!newFile.exists()) {
									boolean seCreoArchivo = newFile.createNewFile();
									if(!seCreoArchivo){
										return "errorArchivo";
									}
								}

								outputStream = new FileOutputStream(newFile);
								int read = 0;
								byte[] bytes = new byte[1024];
					
								while ((read = inputStream.read(bytes)) != -1) {
									outputStream.write(bytes, 0, read);
								}								

								
								if(!fileName.substring(fileName.length()-3).equalsIgnoreCase("mp4")){
									BufferedImage afi = (BufferedImage)ImageIO.read(file.getInputStream());
									Image imagenEscalada = afi.getScaledInstance(138,195 ,Image.SCALE_SMOOTH); //escala la imagen
									
									File newFile_t = new File(variableManager.finById(direccionAfiche).getValor() + fileName_t);
									if (!newFile_t.exists()) {
										boolean seCreoArchivo = newFile_t.createNewFile();
										if(!seCreoArchivo){
											return "errorArchivo";
										}
									}
									outputStream.close();
									outputStream = new FileOutputStream(newFile_t);
									
									afi = new BufferedImage(138, 195, BufferedImage.TYPE_INT_RGB);
									afi.getGraphics().drawImage(imagenEscalada,0,0,null,null);
									ImageIO.write(afi, "jpg", outputStream);
									outputStream.close();								
								}
								
							} catch (IOException e) {
								e.printStackTrace();
								return "errorArchivo";
							}
							
							InputStream inputStream2 = null;
							OutputStream outputStream2 = null;
							MultipartFile file2 = uploadedFile.getFile();
							String fileName2 = file2.getOriginalFilename();
							String extension2 = fileName2.substring(fileName2.length() - 3);							
							fileName = nombreArchivo + "." + extension2;
							
					
							try {
								inputStream2 = file.getInputStream();
								File newFile2 = new File(request.getServletContext().getRealPath("/") + "/afiche/" + fileName2);
								logger.debug("context  "+ request.getServletContext().getRealPath("/"));
								
								if (!newFile2.exists()) {
									boolean seCreoArchivo = newFile2.createNewFile();
									if(!seCreoArchivo){
										return "errorArchivo";
									}
								}
								outputStream2 = new FileOutputStream(newFile2);
								int read2 = 0;
								byte[] bytes2 = new byte[1024];
					
								while ((read2 = inputStream2.read(bytes2)) != -1) {
									outputStream2.write(bytes2, 0, read2);
								}
								
								//agregar codigo
								
								
								
								
							} catch (IOException e) {
								e.printStackTrace();
								return "errorArchivo";
							}
							
							if(fileName.substring(fileName.length()-3).equals("mp4")){
								afiche.setDireccion("../afiche/video.jpg");
								afiche.setVideo("../afiche/"+fileName);
								afiche.setTipoafiche("v");
							}else{
								afiche.setDireccion("../afiche/"+fileName);
								afiche.setTipoafiche("i");
							}
							
						}
							
						afiche.setEstado('A');
						afiche.setFechacreacion(new Date());
						//afiche.setFechaactualizacion(new Date());
						afiche.setMiniatura('S');
						
						String[] a = ofic.split(",");
						
						if((a.length-1)==oficinaManager.getOficinasActivas().size()){
							todas = true;
						}
						
						for(String j:a){
							if(j.equals("multiselect-all")&&todas){
								aficheManager.add(afiche);
								
								logMuroManager.add(new LogMuro(null, new Privilegio(1),
										(String) request.getSession().getAttribute("idusuario"),
										Constants.REPORTE_CREAR, new Date(), "", 
										afiche.getDescripcion(), afiche.getIdafiche(), "Afiche", "N"));
								
								AficheOficina t = new AficheOficina();
								t.setId(new AficheOficinaId(afiche.getIdafiche(), 0));
								aficheOficinaManager.add(t);
								
								break;
							}else{
								if(j.equals("multiselect-all"))continue;
								aficheManager.add(afiche);
								
								logMuroManager.add(new LogMuro(null, new Privilegio(1),
										(String) request.getSession().getAttribute("idusuario"),
										Constants.REPORTE_CREAR, new Date(), "", 
										afiche.getDescripcion(), afiche.getIdafiche(), "Afiche", "N"));
								
								AficheOficina t = new AficheOficina();
								t.setId(new AficheOficinaId(afiche.getIdafiche(), Integer.parseInt(j)));
								aficheOficinaManager.add(t);
								
							}
						}
					}
					else{

						if(aficheManager.validateMin(oficinaMiniatura) == "encontro"){
							
							Afiche u =aficheManager.finById(afiche.getIdafiche());
							
							if(!u.getDescripcion().equals(afiche.getDescripcion())){
								logMuroManager.add(new LogMuro(null, new Privilegio(1),
										(String) request.getSession().getAttribute("idusuario"),
										Constants.REPORTE_MODIFICAR, new Date(),u.getDescripcion(),
										afiche.getDescripcion(), 
										afiche.getIdafiche(), "Afiche.Descripcion", "N"));
							u.setDescripcion(afiche.getDescripcion());
							}
							
							
							if(afiche.getMiniatura()== null){
								
								u.setMiniatura('N');
								if(!u.getMiniatura().equals(afiche.getMiniatura())){
									logMuroManager.add(new LogMuro(null, new Privilegio(1),
											(String) request.getSession().getAttribute("idusuario"),
											Constants.REPORTE_MODIFICAR, new Date(), 
											u.getMiniatura() == null ?  null : u.getMiniatura().toString(), 
											afiche.getMiniatura() == null ?  null : afiche.getMiniatura().toString(), 
											afiche.getIdafiche(), "Afiche.Miniatura", "N"));
								}
							}else{
								if(!u.getMiniatura().equals(afiche.getMiniatura())){
									logMuroManager.add(new LogMuro(null, new Privilegio(1),
											(String) request.getSession().getAttribute("idusuario"),
											Constants.REPORTE_MODIFICAR, new Date(), u.getMiniatura().toString(), 
											afiche.getMiniatura().toString(), 
											afiche.getIdafiche(), "Afiche.Miniatura", "N"));
								u.setMiniatura(afiche.getMiniatura());
							}
							}
							
							u.setFechaactualizacion(new Date());
							aficheManager.update(u);
							
						}else{
							
							return "error";
						}
					}
				}
				
				List<AficheOficina>  afiOfi2 = aficheOficinaManager.getAO();
				ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
				
				for(AficheOficina a: afiOfi2){
					logger.debug("idAO -- >"+a.getId().getIdafiche());
					logger.debug("idAO -- >"+a.getId().getIdoficina());
					HashMap<String,String> mapa = new HashMap<String,String>();
					  mapa.put("idoficina",a.getOficina().getIdoficina().toString());
					  mapa.put("idafiche",a.getAfiche().getIdafiche().toString());
					  mapa.put("imagen","0");
					  mapa.put("oficina",a.getOficina().getCodigo());
					  mapa.put("descripcion",a.getAfiche().getDescripcion());
					  mapa.put("miniatura",a.getAfiche().getMiniatura().toString());
					  mapa.put("miniaturaIcon",a.getAfiche().getMiniatura().toString());
					  y.add(mapa);
				}
				
				Gson g = new Gson();
				logger.debug("funka - "+g.toJson(y));
				return g.toJson(y);
					
		}else{
			
			//return aficheManager.validateMinAnyElse(oficinaMiniatura, ofic, afiche, result, uploadedFile);
			if(ofic.equals("null")){
				}else{
					if(afiche.getIdafiche() == null){
								
							InputStream inputStream = null;
							OutputStream outputStream = null;
							MultipartFile file = uploadedFile.getFile();
							String fileName = file.getOriginalFilename();

							try {
								inputStream = file.getInputStream();

								String contentType = file.getContentType();
								int direccionAfiche = contentType.contains("video") ? Constants.DIRECCIONVIDEOS : Constants.DIRECCIONIMGAFICHES;
								File newFile = new File(variableManager.finById(direccionAfiche).getValor() + fileName);

								if (!newFile.exists()) {
									newFile.createNewFile();
								}
								outputStream = new FileOutputStream(newFile);
								int read = 0;
								byte[] bytes = new byte[1024];
								while ((read = inputStream.read(bytes)) != -1) {
									outputStream.write(bytes, 0, read);
								}
							} catch (IOException e) {
								e.printStackTrace();
								return "error";
							}
							
							InputStream inputStream2 = null;
							OutputStream outputStream2 = null;
							MultipartFile file2 = uploadedFile.getFile();
							String fileName2 = file2.getOriginalFilename();
					
							try {
								inputStream2 = file.getInputStream();
								File newFile2 = new File(request.getServletContext().getRealPath("/") + "/afiche/" + fileName2);
								if (!newFile2.exists()) {
									newFile2.createNewFile();
								}
								outputStream2 = new FileOutputStream(newFile2);
								int read2 = 0;
								byte[] bytes2 = new byte[1024];
					
								while ((read2 = inputStream2.read(bytes2)) != -1) {
									outputStream2.write(bytes2, 0, read2);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
								
							if(fileName.substring(fileName.length()-3).equals("mp4")){
								afiche.setDireccion("../afiche/video.jpg");
								afiche.setVideo("../afiche/"+fileName);
								afiche.setTipoafiche("v");
							}else{
								afiche.setDireccion("../afiche/"+fileName);
							}
								
							afiche.setEstado('A');
							afiche.setFechacreacion(new Date());
							afiche.setMiniatura('S');
							afiche.setTipoafiche("i");
							
							String[] a = ofic.split(",");
							
							
							if((a.length-1)==oficinaManager.getOficinasActivas().size()){
								todas = true;
							}
								for(String j:a){
									if(j.equals("multiselect-all")&&todas){
										aficheManager.add(afiche);
										logMuroManager.add(new LogMuro(null, new Privilegio(1),
												(String) request.getSession().getAttribute("idusuario"),
												Constants.REPORTE_CREAR, new Date(), "", 
												afiche.getDescripcion(), afiche.getIdafiche(), "Afiche", "N"));
										AficheOficina t = new AficheOficina();
										t.setId(new AficheOficinaId(afiche.getIdafiche(), 0));
										aficheOficinaManager.add(t);
										
										LogMuro log = new LogMuro();
										
										log.setOpcion("Editar");
										logMuroManager.add(log);
										
										break;
									}
									else{
										if(j.equals("multiselect-all"))continue;
										aficheManager.add(afiche);
										logMuroManager.add(new LogMuro(null, new Privilegio(1),
												(String) request.getSession().getAttribute("idusuario"),
												Constants.REPORTE_CREAR, new Date(), "", 
												afiche.getDescripcion(), afiche.getIdafiche(), "Afiche", "N"));
										AficheOficina t = new AficheOficina();
										t.setId(new AficheOficinaId(afiche.getIdafiche(), Integer.parseInt(j)));
										aficheOficinaManager.add(t);
										
										LogMuro log = new LogMuro();
										
										log.setOpcion("Editar");
										logMuroManager.add(log);
									}
						
								}
						
						}else{
							Afiche u =aficheManager.finById(afiche.getIdafiche());
							if(!u.getDescripcion().equals(afiche.getDescripcion())){
								logMuroManager.add(new LogMuro(null, new Privilegio(1),
										(String) request.getSession().getAttribute("idusuario"),
										Constants.REPORTE_MODIFICAR, new Date(), u.getDescripcion(), 
										afiche.getDescripcion(), afiche.getIdafiche(), "Afiche.Descripcion", "N"));
							u.setDescripcion(afiche.getDescripcion());
							}
							
							
							if(afiche.getMiniatura()== null){
								
								u.setMiniatura('N');
								if(!u.getMiniatura().equals(afiche.getMiniatura())){
									logMuroManager.add(new LogMuro(null, new Privilegio(1),
											(String) request.getSession().getAttribute("idusuario"),
											Constants.REPORTE_MODIFICAR, new Date(), "", 
											u.getMiniatura().toString(), afiche.getIdafiche(), "Afiche.Miniatura", "N"));
								}
							}else{
								if(!u.getMiniatura().equals(afiche.getMiniatura())){
									logMuroManager.add(new LogMuro(null, new Privilegio(1),
											(String) request.getSession().getAttribute("idusuario"),
											Constants.REPORTE_MODIFICAR, new Date(), u.getMiniatura().toString(), 
											afiche.getMiniatura().toString(), afiche.getIdafiche(), "Afiche.Miniatura", "N"));
								u.setMiniatura(afiche.getMiniatura());
							}
							}
							u.setFechaactualizacion(new Date());
							aficheManager.update(u);
						}
					}
					
					List<AficheOficina>  afiOfi2 = aficheOficinaManager.getAO();
					ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
						for(AficheOficina a: afiOfi2){
							logger.debug("idAO -- >"+a.getId().getIdafiche());
							logger.debug("idAO -- >"+a.getId().getIdoficina());
							HashMap<String,String> mapa = new HashMap<String,String>();
							  mapa.put("idoficina",a.getOficina().getIdoficina().toString());
							  mapa.put("idafiche",a.getAfiche().getIdafiche().toString());
							  mapa.put("imagen","0");
							  mapa.put("oficina",a.getOficina().getCodigo());
							  mapa.put("descripcion",a.getAfiche().getDescripcion());
							  mapa.put("miniatura",a.getAfiche().getMiniatura().toString());
							  mapa.put("miniaturaIcon",a.getAfiche().getMiniatura().toString());
							  y.add(mapa);
					}
					
						Gson g = new Gson();
						logger.debug("funka - "+g.toJson(y));
						return g.toJson(y);
				
		}
		
		
		
		
		
	}

	
	@RequestMapping(value = "/deleteAfi/{aficheId}/{oficinaId}.htm", method = RequestMethod.GET)
	public @ResponseBody String deleteEmplyee(@PathVariable("aficheId") Integer aficheId,
			@PathVariable("oficinaId") Integer oficinaId, HttpServletRequest request) throws Exception
	{
		
		
		if(aficheManager.validateMin(oficinaId.toString()) == "encontro"){
			
			Integer idAfiche = aficheOficinaManager.deleteAfi(aficheId, oficinaId);
			
			logMuroManager.add(new LogMuro(null, new Privilegio(1),
					(String) request.getSession().getAttribute("idusuario"),
					Constants.REPORTE_ELIMINAR, new Date(), aficheManager.finById(idAfiche).getDescripcion()+ " - " + oficinaId, 
					"", idAfiche, "Afiche", "N"));
			
			List<AficheOficina>  afiOfi2 = aficheOficinaManager.getAO();
			ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
				for(AficheOficina a: afiOfi2){
					logger.debug("idAO -- >"+a.getId().getIdafiche());
					logger.debug("idAO -- >"+a.getId().getIdoficina());
					HashMap<String,String> mapa = new HashMap<String,String>();
					  mapa.put("idoficina",a.getOficina().getIdoficina().toString());
					  mapa.put("idafiche",a.getAfiche().getIdafiche().toString());
					  mapa.put("imagen","0");
					  mapa.put("oficina",a.getOficina().getCodigo());
					  mapa.put("descripcion",a.getAfiche().getDescripcion());
					  mapa.put("miniatura",a.getAfiche().getMiniatura().toString());
					  mapa.put("miniaturaIcon",a.getAfiche().getMiniatura().toString());
					  y.add(mapa);
				}
			
				
				
				Gson g = new Gson();
				return g.toJson(y);
			
		}else{
			
			return "error";
		}
		

	}
	
	@RequestMapping(value = "/pick/{aficheId}.htm")
	@ResponseBody
	public byte[] imagen(@PathVariable Integer aficheId) throws Exception  {	
		return aficheManager.byId(aficheId);
	}

	
	
	@RequestMapping(value = "/buscarAfiche.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaProducto(ModelMap map, HttpServletRequest request) throws Exception {
		
		String criterio = request.getParameter("criterio");if(criterio==null)criterio="";
		
		List<AficheOficina>  afiOfi2 = aficheOficinaManager.buscarAfi("%"+criterio+"%");
		ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
			for(AficheOficina a: afiOfi2){
				logger.debug("idAO -- >"+a.getId().getIdafiche());
				logger.debug("idAO -- >"+a.getId().getIdoficina());
				HashMap<String,String> mapa = new HashMap<String,String>();
				  mapa.put("idoficina",a.getOficina().getIdoficina().toString());
				  mapa.put("idafiche",a.getAfiche().getIdafiche().toString());
				  mapa.put("imagen","0");
				  mapa.put("oficina",a.getOficina().getCodigo());
				  mapa.put("descripcion",a.getAfiche().getDescripcion());
				  mapa.put("miniatura",a.getAfiche().getMiniatura().toString());
				  mapa.put("miniaturaIcon",a.getAfiche().getMiniatura().toString());
				  y.add(mapa);
			}
		
			Gson g = new Gson();
			logger.debug("funka - "+g.toJson(y));
			return g.toJson(y);

	}
	
	public void setAficheManager(AficheManager aficheManager) {
		this.aficheManager = aficheManager;
	}
	
	
	private String obtenerNombreArchivo() {
		
		LocalDateTime fechaActual = LocalDateTime.now();
		
		Integer anho = fechaActual.getYear();
		Integer mes = fechaActual.getMonthOfYear();
		Integer dia = fechaActual.getDayOfMonth();
		Integer hora = fechaActual.getHourOfDay();
		Integer minutos = fechaActual.getMinuteOfHour();
		Integer segundos = fechaActual.getSecondOfMinute();

		
		String nombreArchivo = anho + 
									String.format("%02d", mes) + 
									String.format("%02d", dia) + "-" +
									String.format("%02d", hora) + 
									String.format("%02d", minutos) + 
									String.format("%02d", segundos);
		
		return nombreArchivo;
	}


}
