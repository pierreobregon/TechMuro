package pe.conadis.tradoc.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.UsuarioDAO;
import pe.conadis.tradoc.entity.beans.Acceso;
import pe.conadis.tradoc.entity.beans.OpcionMenu;
import pe.conadis.tradoc.entity.beans.RolUsuario;
import pe.conadis.tradoc.entity.beans.Usuario;

@Repository
public class UsuarioDaoImpl extends AbstractDAO<Usuario> implements UsuarioDAO {
	private static final Logger logger = Logger.getLogger(UsuarioDaoImpl.class);
	
	
	@Transactional
	public Usuario verifyPass(Usuario usuario) {
		usuario = (Usuario) this.getSessionFactory().getCurrentSession()
				.createQuery("from Usuario "
						+ " where codUsuario =:codUsuario "
						+ " and password =:password")
				.setParameter("codUsuario", usuario.getCodUsuario())
				.setParameter("password", usuario.getPassword())
				.uniqueResult();
		return usuario;
	}
	@Transactional
	public List<OpcionMenu> obtenerOpcionMenu(Usuario usuario) throws Exception {
		List<OpcionMenu> lstOpcionMenu = new ArrayList<OpcionMenu>();
		usuario = (Usuario) this
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from Usuario u" + " left join fetch u.rolUsuarios ru "
								+ " left join fetch ru.rol rol "
								+ " left join fetch rol.accesos ac "
								+ " left join fetch ac.opcionMenu om "
								+ " left join fetch om.menuSistema ms "

								+ "where u.codUsuario =:codUsuario")
				.setParameter("codUsuario", usuario.getCodUsuario())
				.uniqueResult();
		if (usuario != null && usuario.getRolUsuarios() != null) {
			Iterator<RolUsuario> itRolUsuario = usuario.getRolUsuarios()
					.iterator();
			while (itRolUsuario.hasNext()) {
				RolUsuario rolUsuario = itRolUsuario.next();
				if (rolUsuario.getRol() != null
						&& rolUsuario.getRol().getAccesos() != null) {
					Iterator<Acceso> itAccesos = rolUsuario.getRol()
							.getAccesos().iterator();
					while (itAccesos.hasNext()) {
						Acceso acceso = itAccesos.next();
						if (acceso.getOpcionMenu() != null)
							lstOpcionMenu.add(acceso.getOpcionMenu());
					}
				}
			}
		}
		return lstOpcionMenu;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Usuario> buscarUsuario(Usuario usuario) {

		try {
			List<Usuario> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Usuario "
							+ "where personal.codPersona = :codPersona "
							+ "and upper(personal.nombres) like upper('%'||:nombre||'%') and indEstado = '1' ")
					.setParameter("nombre", usuario.getPersonal().getNombres())
					.setParameter("codPersona", usuario.getPersonal().getCodPersona()).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error buscar Opcion Menu ->" +ex);
			return null;
		}
	}
	
	
	@Transactional
	public Usuario validaUsuario(Usuario usuario){
		
		return (Usuario)this.getSessionFactory().getCurrentSession().
				createQuery("from Usuario where codUsuario = :codUsuario "
							+ "and indEstado = '1'")  
		          .setParameter("codUsuario", usuario.getCodUsuario())		          
		          .uniqueResult();
}
	

	
}

