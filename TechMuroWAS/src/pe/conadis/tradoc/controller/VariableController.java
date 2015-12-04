package pe.conadis.tradoc.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.entity.Oficina;
import pe.conadis.tradoc.entity.Privilegio;
import pe.conadis.tradoc.entity.VariablesGenerales;
import pe.conadis.tradoc.service.LogMuroManager;
import pe.conadis.tradoc.service.NotariaManager;
import pe.conadis.tradoc.service.OficinaManager;
import pe.conadis.tradoc.service.PlazaManager;
import pe.conadis.tradoc.service.VariableManager;
import pe.conadis.tradoc.util.Constants;



@Controller
public class VariableController {
	
	private static final Logger logger = Logger.getLogger(VariableController.class);
	
	@Autowired
	private VariableManager variableManager;
	
	@Autowired
	private NotariaManager notariaManager;
	
	@Autowired
	private PlazaManager plazaManager;
	
	@Autowired
	private LogMuroManager logMuroManager;
	
	@Autowired
	private OficinaManager oficinaManager;
	
	
	
	@RequestMapping(value = "/variables.htm", method = RequestMethod.GET)
	public String cargaVariables(ModelMap map, HttpServletRequest request) throws Exception 
	{
		map.addAttribute("ta", variableManager.finById(Constants.TIEMPOACTUALIZACION).getValor());
		
		map.addAttribute("tpu", variableManager.finById(Constants.TIEMPOPOPUP).getValor());
		
		//map.addAttribute("da", variableManager.finById(Constants.DIRECCIONAFICHES).getValor());
		map.addAttribute("dia", variableManager.finById(Constants.DIRECCIONIMGAFICHES).getValor());
		
		map.addAttribute("dic", variableManager.finById(Constants.DIRECCIONIMGCOMUNICADOS).getValor());
		
		map.addAttribute("dv", variableManager.finById(Constants.DIRECCIONVIDEOS).getValor());
		
		map.addAttribute("tdv", variableManager.finById(Constants.TIEMPOVIDEO).getValor());
		
		map.addAttribute("mpoa", variableManager.finById(Constants.PRODUCTOSALFABETICAMENTE).getValor());
				
		String codigoOficina = variableManager.finById(Constants.CODIGO_OFICINA_PRINCIPAL).getValor();
				
		map.addAttribute("cop", codigoOficina );
		
		map.addAttribute("urv", variableManager.finById(Constants.URL_VISOR).getValor());
		map.addAttribute("urc", variableManager.finById(Constants.URL_CANALES).getValor());
		
		
		String horaActualizacion  = variableManager.finById(Constants.HORA_ACTUALIZACION_VISOR).getValor();
		
	    String[] tiempoActualizacion = horaActualizacion.split(":");
		map.addAttribute("haa", tiempoActualizacion[0]);
		map.addAttribute("maa", tiempoActualizacion[1]);
		
	
		
		logger.debug("Variables --> ");
		
		
		Oficina oficinaPorDefecto = oficinaManager.getOficinasByCodigoVisor(codigoOficina);
		map.addAttribute("oficina", oficinaPorDefecto );
		
		
		
		// ---  oficinas ----
		
	 List<Oficina> oficinas = oficinaManager.getOficinasFilter();
		
		for (Oficina oficina: oficinas) {
			oficina.setAficheOficinas(null);
			oficina.setComunicadoOficinas(null);
			oficina.setOcurrenciaMuros(null);
			oficina.setPlaza(null);
		}
		
		logger.debug("lista de oficinas "+oficinas);
		map.addAttribute("oficinas", oficinas);
		
		
		//  ---------------------
		
		return "variables/variables";
	}

	@RequestMapping(value = "/etiquetas.htm", method = RequestMethod.GET)
	public String cargaEtiquetas(ModelMap map, HttpServletRequest request) throws Exception 
	{
		map.addAttribute("tt", variableManager.finById(Constants.TASASTARIFAS).getValor());
		
		map.addAttribute("pn", variableManager.finById(Constants.TIPOCLIENTE1).getValor());
		
		map.addAttribute("pj", variableManager.finById(Constants.TIPOCLIENTE2).getValor());
		
		map.addAttribute("no", variableManager.finById(Constants.NOTARIOS).getValor());
		
		map.addAttribute("co", variableManager.finById(Constants.COMUNICADOS).getValor());
		
		map.addAttribute("cmc", variableManager.finById(Constants.CANALESCERCANOS).getValor());
		
		return "variables/etiquetas";
	}
	
	@RequestMapping(value = "/actualizarVariables.htm", method = RequestMethod.POST)
	public @ResponseBody String actualizarVariables(ModelMap map, HttpServletRequest request) throws Exception {
		
		logger.debug("datos --> " +request.getParameter("ta"));
		
//		String ta = request.getParameter("ta");
//		VariablesGenerales taTemp = variableManager.finById(Constants.TIEMPOACTUALIZACION);
//		logger.debug(ta.trim());
//		if(ta==null || ta.trim().equals("")){
//			return "error";
//		}else{
//			if(!taTemp.getValor().equals(ta)){
//				LogMuro l = new LogMuro();
//				l.setCampo("Tiempo de Auto Actualización");
//				l.setFecha(new Date());
//				l.setPrivilegio(new Privilegio(13));
//				l.setId(taTemp.getIdvariable());
//				l.setOpcion(Constants.REPORTE_MODIFICAR);
//				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
//				l.setValoranterior(taTemp.getValor());
//				l.setValornuevo(ta);
//			
//				logMuroManager.add(l);
//			taTemp.setValor(ta);
//				variableManager.update(taTemp);
//			}
//			
//		}
		
		String tpu = request.getParameter("tpu");
		VariablesGenerales tpuTemp = variableManager.finById(Constants.TIEMPOPOPUP);
		
		if(tpu==null || tpu.trim().equals("")){
			return "error";
		}else{
			
			if(!tpuTemp.getValor().equals(tpu)){
				LogMuro l = new LogMuro();
				l.setCampo("Tiempo de espera de Pop Up de Imagen");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(13));
				l.setId(tpuTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(tpuTemp.getValor());
				l.setValornuevo(tpu);
				
				logMuroManager.add(l);
			tpuTemp.setValor(tpu);
				variableManager.update(tpuTemp);
			}
			
		}
		
		String dia = request.getParameter("dia");
		VariablesGenerales diaTemp = variableManager.finById(Constants.DIRECCIONIMGAFICHES);
		
		if(dia==null || dia.trim().equals("")){
			return "error";
		}else{
			
			if(!diaTemp.getValor().equals(dia)){
				LogMuro l = new LogMuro();
				l.setCampo("Ruta de imágenes para Afiches");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(13));
				l.setId(diaTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(diaTemp.getValor());
				l.setValornuevo(dia);
				
				logMuroManager.add(l);
			diaTemp.setValor(dia);
				variableManager.update(diaTemp);
			}
			
		}
		
		String dic = request.getParameter("dic");
		VariablesGenerales dicTemp = variableManager.finById(Constants.DIRECCIONIMGCOMUNICADOS);
		
		if(dic==null || dic.trim().equals("")){
			return "error";
		}else{
			
			if(!dicTemp.getValor().equals(dic)){
				LogMuro l = new LogMuro();
				l.setCampo("Ruta de imágenes para Comunicados");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(13));
				l.setId(dicTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(dicTemp.getValor());
				l.setValornuevo(dic);
				
				logMuroManager.add(l);
			dicTemp.setValor(dic);
				variableManager.update(dicTemp);
			}
			
		}
		
		
		String dv = request.getParameter("dv");
		VariablesGenerales dvTemp = variableManager.finById(Constants.DIRECCIONVIDEOS);
		
		if(dv==null || dv.trim().equals("")){
			return "error";
		}else{
			
			if(!dvTemp.getValor().equals(dv)){
				LogMuro l = new LogMuro();
				l.setCampo("Ruta de Videos");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(13));
				l.setId(dvTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(dvTemp.getValor());
				l.setValornuevo(dv);
				
				logMuroManager.add(l);
			dvTemp.setValor(dv);
				variableManager.update(dvTemp);
			}
			
		}
		
		String tdv = request.getParameter("tdv");
		VariablesGenerales tdvTemp = variableManager.finById(Constants.TIEMPOVIDEO);
		
		if(tdv==null || tdv.trim().equals("")){
			return "error";
		}else{
			if(!tdvTemp.getValor().equals(tdv)){
				LogMuro l = new LogMuro();
				l.setCampo("Tiempo de espera de Pop Up de Video");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(13));
				l.setId(tdvTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(tdvTemp.getValor());
				l.setValornuevo(tdv);
			
				logMuroManager.add(l);
			tdvTemp.setValor(tdv);
				variableManager.update(tdvTemp);
			}
		}
		
		String mpoa = request.getParameter("mpoa");
		VariablesGenerales mpoaTemp = variableManager.finById(Constants.PRODUCTOSALFABETICAMENTE);
		
		if(mpoa==null || mpoa.trim().equals("")){
			//mpoaTemp.setValor("no");
			mpoa = "no";
			if(!mpoaTemp.getValor().equals(mpoa)){
				LogMuro l = new LogMuro();
				l.setCampo("Mostrar Productos en orden alfabético");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(13));
				l.setId(mpoaTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(mpoaTemp.getValor());
				l.setValornuevo(mpoa);
				
				logMuroManager.add(l);
				mpoaTemp.setValor(mpoa);
				variableManager.update(mpoaTemp);
			}
			
		}else{
			if(!mpoaTemp.getValor().equals(mpoa)){
				LogMuro l = new LogMuro();
				l.setCampo("Mostrar Productos en orden alfabético");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(13));
				l.setId(mpoaTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(mpoaTemp.getValor());
				l.setValornuevo(mpoa);
			
				logMuroManager.add(l);
				mpoaTemp.setValor(mpoa);
		variableManager.update(mpoaTemp);
		}
		}
		
		
		
		String iop = request.getParameter("cop");
		VariablesGenerales copTemp = variableManager.finById(Constants.CODIGO_OFICINA_PRINCIPAL);
		
		if(iop==null || iop.trim().equals("")){
			return "error";
		}else{
			Oficina oficina = oficinaManager.finById(new Integer(iop));
			String cop = oficina.getCodigo();
			
			if(!copTemp.getValor().equals(cop)){
				LogMuro l = new LogMuro();
				l.setCampo("Código de oficina por defecto");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(13));
				l.setId(copTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(copTemp.getValor());
				l.setValornuevo(cop);
				
				logMuroManager.add(l);
				copTemp.setValor(cop);
				variableManager.update(copTemp);
			}
			
		}
		
		
		// URL VISOR
		
		String urvParam = request.getParameter("urv");
		VariablesGenerales urvTemp = variableManager.finById(Constants.URL_VISOR);
		
		if(urvParam==null || urvParam.trim().equals("")){
			return "error";
		}else{
			if(!urvTemp.getValor().equals(urvParam)){
				LogMuro l = new LogMuro();
				l.setCampo("URL del Visor");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(13));
				l.setId(urvTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(urvTemp.getValor());
				l.setValornuevo(urvParam);
			
				logMuroManager.add(l);
				urvTemp.setValor(urvParam.trim());
				variableManager.update(urvTemp);
			}
		}

		
		
		
		// Hora de actualización del visor
		
				String haaParam = request.getParameter("horaActualizacion");
				String maaParam = request.getParameter("minutoActualizacion");
				String tiempoParam = haaParam + ":" + maaParam;
				VariablesGenerales haaTemp = variableManager.finById(Constants.HORA_ACTUALIZACION_VISOR);
				
				if(tiempoParam==null || tiempoParam.trim().equals("")){
					return "error";
				}else{
					if(!haaTemp.getValor().equals(tiempoParam)){
						LogMuro l = new LogMuro();
						l.setCampo("URL del Visor");
						l.setFecha(new Date());
						l.setPrivilegio(new Privilegio(13));
						l.setId(haaTemp.getIdvariable());
						l.setOpcion(Constants.REPORTE_MODIFICAR);
						l.setUsuario((String)request.getSession().getAttribute("idusuario"));
						l.setValoranterior(haaTemp.getValor());
						l.setValornuevo(tiempoParam);
					
						logMuroManager.add(l);
						haaTemp.setValor(tiempoParam.trim());
						variableManager.update(haaTemp);
					}
				}
				
				
				
				
			

		
		
		
		
	// URL CANALES MAS CERCANOS
		
		String urcParam = request.getParameter("urc");
		VariablesGenerales urcTemp = variableManager.finById(Constants.URL_CANALES);
		
		if(urcParam==null || urcParam.trim().equals("")){
			return "error";
		}else{
			if(!urcTemp.getValor().equals(urcParam)){
				LogMuro l = new LogMuro();
				l.setCampo("URL del Visor");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(13));
				l.setId(urcTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(urcTemp.getValor());
				l.setValornuevo(urcParam);
			
				logMuroManager.add(l);
				urcTemp.setValor(urcParam.trim());
				variableManager.update(urcTemp);
			}
		}

		
		
		return "go";

	}
	
	@RequestMapping(value = "/actualizarEtiquetas.htm", method = RequestMethod.POST)
	public @ResponseBody String actualizarEtiquetas(ModelMap map, HttpServletRequest request) throws Exception {
				
		String tt = request.getParameter("tt");
		VariablesGenerales ttTemp = variableManager.finById(Constants.TASASTARIFAS);
		
		if(tt==null || tt.trim().equals("")){
			return "error";
		}else{
			
			if(!ttTemp.getValor().equals(tt)){
				LogMuro l = new LogMuro();
				l.setCampo("Tasas y Tarifas");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(14));
				l.setId(ttTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(ttTemp.getValor());
				l.setValornuevo(tt);
				
				logMuroManager.add(l);
			ttTemp.setValor(tt);
				variableManager.update(ttTemp);
			}
			
		}
		
		String pn = request.getParameter("pn");
		VariablesGenerales pnTemp = variableManager.finById(Constants.TIPOCLIENTE1);
		
		if(pn==null || pn.trim().equals("")){
			return "error";
		}else{
			
			if(!pnTemp.getValor().equals(pn)){
				LogMuro l = new LogMuro();
				l.setCampo("Tipo Cliente 1");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(14));
				l.setId(pnTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(pnTemp.getValor());
				l.setValornuevo(pn);
				
				logMuroManager.add(l);
			pnTemp.setValor(pn);
				variableManager.update(pnTemp);
			}
			
		}
		
		String pj = request.getParameter("pj");
		VariablesGenerales pjTemp = variableManager.finById(Constants.TIPOCLIENTE2);
		
		if(pj==null || pj.trim().equals("")){
			return "error";
		}else{
			
			if(!pjTemp.getValor().equals(pj)){
				LogMuro l = new LogMuro();
				l.setCampo("Tipo Cliente 2");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(14));
				l.setId(pjTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(pjTemp.getValor());
				l.setValornuevo(pj);
				
				logMuroManager.add(l);
			pjTemp.setValor(pj);
				variableManager.update(pjTemp);
			}
			
		}
		
		String no = request.getParameter("no");
		VariablesGenerales noTemp = variableManager.finById(Constants.NOTARIOS);
		
		if(no==null || no.trim().equals("")){
			return "error";
		}else{
			
			if(!noTemp.getValor().equals(no)){
				LogMuro l = new LogMuro();
				l.setCampo("Lista de Notarías");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(14));
				l.setId(noTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(noTemp.getValor());
				l.setValornuevo(no);
				
				logMuroManager.add(l);
			noTemp.setValor(no);
				variableManager.update(noTemp);
			}
			
		}
		
		String co = request.getParameter("co");
		VariablesGenerales coTemp = variableManager.finById(Constants.COMUNICADOS);
		
		if(co==null || co.trim().equals("")){
			return "error";
		}else{
			
			if(!coTemp.getValor().equals(co)){
				LogMuro l = new LogMuro();
				l.setCampo("Comunicados");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(14));
				l.setId(coTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(coTemp.getValor());
				l.setValornuevo(co);
			
				logMuroManager.add(l);
			coTemp.setValor(co);
				variableManager.update(coTemp);
			}
		}
		
		String cmc = request.getParameter("cmc");
		VariablesGenerales cmcTemp = variableManager.finById(Constants.CANALESCERCANOS);
		
		if(cmc==null || cmc.trim().equals("")){
			return "error";
		}else{
			
			if(!cmcTemp.getValor().equals(cmc)){
				LogMuro l = new LogMuro();
				l.setCampo("Canales mas cercanos");
				l.setFecha(new Date());
				l.setPrivilegio(new Privilegio(14));
				l.setId(cmcTemp.getIdvariable());
				l.setOpcion(Constants.REPORTE_MODIFICAR);
				l.setUsuario((String)request.getSession().getAttribute("idusuario"));
				l.setValoranterior(cmcTemp.getValor());
				l.setValornuevo(cmc);
			
				logMuroManager.add(l);
			cmcTemp.setValor(cmc);
				variableManager.update(cmcTemp);
		}
			
		}
				
		return "go";

	}
	
}
