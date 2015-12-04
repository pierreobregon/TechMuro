package pe.conadis.tradoc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.MenuSistemaDAO;
import pe.conadis.tradoc.dao.UsuarioDAO;
import pe.conadis.tradoc.entity.beans.MenuSistema;
import pe.conadis.tradoc.entity.beans.Usuario;
import pe.conadis.tradoc.exception.DAOException;
import pe.conadis.tradoc.service.UsuarioManager;

@Service
public class UsuarioManagerImpl extends ServiceImpl<Usuario> implements UsuarioManager {

	private static final Logger logger = Logger.getLogger(UsuarioManagerImpl.class);
	

	@Autowired
    private UsuarioDAO usuarioDAO;
	
	@Autowired
    private MenuSistemaDAO menuSistemaDAO;
	
	@Override
	protected Dao<Usuario> getDAO() {
		return usuarioDAO;
	}
	
	public Usuario verifyPass(Usuario usuario)  throws Exception{
		
		usuario = usuarioDAO.verifyPass(usuario);
		
		return usuario;
	}
	
	public List<MenuSistema> obtnerMenuPorUsuario(String strCodigoUsuario)
			throws Exception {
		return menuSistemaDAO.obtenerMenuSistema(strCodigoUsuario);
	}
	
	@Transactional
	public List<Usuario> buscarUsuario(Usuario usuario){
		
		List<Usuario> lista =  usuarioDAO.buscarUsuario(usuario);
		for(Usuario usu:lista){
			logger.debug("codUsuario ->"+usu.getCodUsuario());
		}
		return lista;
	}
	
	@Transactional
	//@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public String guardarUsuario(Usuario usuario){
		String result = "";
		try{
			usuario.setIndEstado("1");
			usuario.setFecCreacion(new Date());		
			
			if(usuarioDAO.validaUsuario(usuario)==null){				
				usuarioDAO.add(usuario);
					result = "true";				
			}else{
				//result = "Usuario ya existe";
				result = "usuError02";
			}
			
		}catch(Exception e){
			logger.error("Error al guardar Usuario -->" +e);
			//result = "Error al Insertar usuario";
			result = "usuError03";
		}
		
		return result;
	}

	
	@Transactional
	public String editarUsuario(Usuario usuario){
		try{
			if(usuarioDAO.validaUsuario(usuario)==null){
			
				Usuario usuTemp = usuarioDAO.findById(usuario.getIdUsuario());
				usuTemp.setCodUsuario(usuario.getCodUsuario());
				usuTemp.setPassword(usuario.getPassword());
				usuTemp.setDesCorreo(usuario.getDesCorreo());
				usuTemp.setFecModificacion(new Date());
				usuarioDAO.update(usuTemp);				
				return "true";
				
			}else{
				//return "Usuario no existe";
				return "usuError02";
			}
			
		}catch(Exception e){
			logger.error("Error al Editar Usuario -->" +e);
			//return "Error al Insertar usuario";
			return "usuError04";
		}
	}
	
	
	@Transactional
	public String eliminarUsuario(Integer id){
		try{
			Usuario usu = usuarioDAO.findById(id);
			
			//if(rolUsuarioDAO.findByUsuario(id).size()>0){
			if(1>0){
				//return "usuario tiene roles asociados";
				return "usuError06";
			}else{
				usu.setIndEstado("I");				
				usuarioDAO.update(usu);
				
				return "true";
			}
		}catch(Exception e){
			logger.error("Error al Eliminar usuario ->" + e);
			//return "Error al Eliminar Cap\u00edtulo";
			return "usuError05";
		}
	}
	@Transactional
	public List<Usuario> getAll() throws DAOException{
		List<Usuario> listUsuario=null;
		try{			
			 listUsuario=usuarioDAO.getAll();
		} catch (Exception e) {
			logger.error("Error al obtener all usuarios ->" + e);
		}
		return	listUsuario;		
	}
	@Transactional
	public Usuario findById(Integer id) throws Exception{
		Usuario usuario=new Usuario();
		return usuario=usuarioDAO.findById(id);		
	}
	


}
