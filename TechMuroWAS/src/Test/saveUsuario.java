package Test;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;





import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pe.conadis.tradoc.entity.beans.Personal;
import pe.conadis.tradoc.entity.beans.Usuario;
import pe.conadis.tradoc.service.UsuarioManager;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:/src/Test/dispatcher-servlet.xml" })
@ContextConfiguration(locations={"file:src/Test/dispatcher-servlet.xml"})
public class saveUsuario {
	
	@Autowired
	private UsuarioManager usuarioManager;

	@Test
	public void saveUsuariotest() {
		try {
			Usuario usuario =new Usuario();
			Usuario usuario2 =new Usuario();
			Usuario usuario3 =new Usuario();
			Usuario usuario4 =new Usuario();
			usuario.setIdUsuario(1);
			usuario.setCodUsuario("JMATEO");
			usuario.setCodUsuCreacion("JMATEO");
			usuario.setPassword("JMATEO");
			
			usuario3 =usuarioManager.findById(new Integer(1));
			System.out.println("usuario3 ::codusuario: "+usuario3.getCodUsuario() + "correo: "+usuario3.getDesCorreo());
			
			List<Usuario> usuarios =new ArrayList<Usuario>();
			
			usuarios=usuarioManager.getAll();
			
			for (Usuario usu:usuarios){
				System.out.println("usuarios:::codusuario: "+usu.getCodUsuario() + "correo: "+usu.getDesCorreo());
			}
			
			List<Usuario> listaUsuario =new ArrayList<Usuario>();
			Personal opersonal=new Personal();
			opersonal.setCodPersona(2L);
			opersonal.setNombres("Juan");
			usuario.setPersonal(opersonal);
			listaUsuario=usuarioManager.buscarUsuario(usuario);
			
			for (Usuario usu:usuarios){
				System.out.println("listaUsuario::codusuario: "+usu.getCodUsuario() + "correo: "+usu.getDesCorreo());
			}
			
			
			//usuario2  = usuarioManager.verifyPass(usuario);
			//System.out.println(usuario2);
			//usuario2.setIdUsuario(0);
			
			usuario2.setCodUsuario("JMATE10dsd1");			
			usuario2.setPassword("JMATE10dds");
			usuario2.setDesCorreo("JMATE10sdddd");
			
		 System.out.println("retorno de guardar usuario"+usuarioManager.guardarUsuario(usuario2)); 
		 System.out.println(usuario2.getIdUsuario());
		 usuario4 =usuarioManager.findById(1);
		 
			usuario4.setCodUsuario("JMATE10yeteye");			
			usuario4.setPassword("JMATE10xxxxxxxxsaaSs");
			usuario4.setDesCorreo("JMATE10xx");
			
			 usuarioManager.editarUsuario(usuario4); 
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		
	}

}
