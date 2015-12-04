package pe.conadis.tradoc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grupobbva.bc.per.tele.ldap.serializable.IILDPeUsuario;

import pe.conadis.tradoc.entity.beans.MenuSistema;
import pe.conadis.tradoc.entity.beans.Personal;
import pe.conadis.tradoc.entity.beans.Rol;
import pe.conadis.tradoc.entity.beans.UnidadOrganica;
import pe.conadis.tradoc.entity.beans.Usuario;
import pe.conadis.tradoc.service.UsuarioManager;
import pe.conadis.tradoc.util.Constants;

@Controller
public class LoginController {
	
	private static final Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private UsuarioManager usuarioManager;

	@RequestMapping(value = "/login/page.htm", method = RequestMethod.GET)
	public String cargaLogin(ModelMap map) {
		map.addAttribute("usuario", new Usuario());
		return "usuarios/login";
	}
	
	@RequestMapping(value = "/login/header.htm", method = RequestMethod.GET)
	public String cargaHeader(ModelMap map, HttpServletRequest request) {
		if(request.getSession().getAttribute("usuarioLogin")!=null){
		 Usuario usuario=(Usuario)request.getSession().getAttribute("usuarioLogin");
		 Personal personal=usuario.getPersonal();
		 UnidadOrganica uo=usuario.getUnidadOrganica();
		 //Rol rol=usuario.getRolUsuarios();
		 
			
		 if(personal!=null){
			String usuarioNombreCabecera    = personal.getNombres()+" "+personal.getApeParteno()+" "+personal.getApeMaterno();
			request.getSession().setAttribute("usuarioNombreCabecera", usuarioNombreCabecera);
			request.getSession().setAttribute("codUsuario", usuario.getCodUsuario());
		    }
		    if(uo!=null){
				request.getSession().setAttribute("UONombre", uo.getDesUo());
				request.getSession().setAttribute("UOCodigo", uo.getCodUo());
		    }
		}

		
		return "menu/header";
	}
	
	@RequestMapping(value = "/login/menu.htm", method = RequestMethod.GET)
	public String cargaMenu(ModelMap map) {
		
		return "menu/menu";
	}
	
	@RequestMapping(value = "/login/bienvenida.htm", method = RequestMethod.GET)
	public String cargaBienvenida(ModelMap map) {
		
		return "menu/bienvenida";
	}

	/*
	@RequestMapping(value = "/login/login.htm", method = RequestMethod.GET)
	public String verifyPass(@ModelAttribute(value = "usuario") Usuario usuario,
			BindingResult result, HttpServletRequest request) {

		try {
			//usuario = usuarioManager.verifyPass(usuario);
			
			request.getSession().setAttribute("usuario", usuario);
			logger.debug("Ingreso exitoso");
			
		} catch (Exception e) {
			
			logger.error("Error en login ->"+e);
		}

		if (usuario == null) {
			return "redirect:/";
		}

		return "menu/plantilla";
	}
	
	*/

	@RequestMapping(value = "/login/login.htm", method = RequestMethod.POST)
	public  @ResponseBody   String login2( HttpServletRequest request) {
		String strResult = Constants.SUCCESS;
		Usuario usuario = new Usuario();
		usuario.setCodUsuario(String.valueOf(request.getParameter("codUsuario")).toUpperCase());
		usuario.setPassword(String.valueOf(request.getParameter("password")).toUpperCase());
		//usuario.setCodUsuario("RBURNEO");
		//usuario.setPassword("RBURNEO");
		
		try {
		
			if (!usuario.getCodUsuario().trim().equals("") && 
					!usuario.getPassword().trim().equals("")){
				
				usuario = usuarioManager.verifyPass(usuario);
				request.getSession().setAttribute("usuarioLogin", usuario);
				if (usuario == null){
					strResult = "Credenciales incorrectas, intente nuevamente";
				}else{
					usuario=usuarioManager.finById(usuario.getIdUsuario());
				}
			}else{
				strResult = "Ingrese las Crendenciales";
			}
			System.out.println(usuario.getDesCorreo());
		} catch (Exception e) {
			logger.error(e);
		}
		if (strResult.equals(Constants.SUCCESS)){
			List<MenuSistema> lstMenuSistema =  new ArrayList<MenuSistema>();
			try {
				if (usuario.getCodUsuario()!=null){
					lstMenuSistema = usuarioManager.obtnerMenuPorUsuario(usuario.getCodUsuario());	
				}
				lstMenuSistema = this.excluirMenuRepetido(lstMenuSistema);
				request.getSession().setAttribute(Constants.__CURRENT_USER__, usuario);
				request.getSession().setAttribute(Constants.__MENU_SISTEMA__, lstMenuSistema);
			} catch (Exception e) {
				
				logger.error("Error en login ->"+e);
			}	
		}
		
		request.setAttribute("result", strResult);
		System.out.println(strResult);
		return strResult;
		//return "menu/plantilla";
	}

	@RequestMapping(value = "/login/index.htm", method = RequestMethod.POST)
	public String index(HttpServletRequest request) {
		return "menu/plantilla";
	}
	
	private List<MenuSistema> excluirMenuRepetido(
			List<MenuSistema> lstMenuSistema) {
		List<MenuSistema> lstResultado = new ArrayList<MenuSistema>();
		for (MenuSistema menuSistema : lstMenuSistema) {
			if(!lstResultado.contains(menuSistema)){
				lstResultado.add(menuSistema);
			}
		}
		return lstResultado;
	}

	@RequestMapping(value = "/login/logout.htm", method = RequestMethod.GET)
	public String salida(ModelMap map, HttpServletRequest request) {
		
		request.getSession().removeAttribute("usuario");
		
		return "redirect:/";
	}
}
