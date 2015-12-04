package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.beans.MenuSistema;
import pe.conadis.tradoc.entity.beans.Usuario;

public interface UsuarioManager extends Service<Usuario> {

	public Usuario verifyPass(Usuario usuario) throws Exception;
	
	public List<MenuSistema> obtnerMenuPorUsuario(String strCodigoUsuario)
			throws Exception;
	public Usuario findById(Integer id) throws Exception;
	public List<Usuario> getAll() throws Exception;
	public List<Usuario> buscarUsuario(Usuario usuario);
	public String guardarUsuario(Usuario usuario);
	public String editarUsuario(Usuario usuario);
	public String eliminarUsuario(Integer id);	
	

	
}
