package pe.conadis.tradoc.util;

import javax.servlet.http.HttpServletRequest;

import com.grupobbva.bc.per.tele.ldap.conexion.Conexion;
import com.grupobbva.bc.per.tele.ldap.serializable.IILDPeUsuario;
import com.grupobbva.bc.per.tele.seguridad.ServiciosSeguridadBbva;

//import com.grupobbva.bc.per.tele.ldap.conexion.Conexion;
//import com.grupobbva.bc.per.tele.ldap.serializable.IILDPeUsuario;


public class LdapUtil {

	
	public static IILDPeUsuario getUsuarioBeanLdap(HttpServletRequest request){
		
//		IILDPeUsuario usuario = new IILDPeUsuario();
//		usuario.setUID("zzzzz");
//		usuario.setNombre("Christian");
//		usuario.setApellido1("Gonzales");
//		usuario.setApellido2("Komiya");
//		return usuario;
		
		ServiciosSeguridadBbva seguridadBbva;
		seguridadBbva = new ServiciosSeguridadBbva(request);
		seguridadBbva.obtener_ID();
		String registro = seguridadBbva.getUsuario();
		Conexion conexion = new Conexion();
		return conexion.recuperarUsuario(registro);

	}

	
	
}
