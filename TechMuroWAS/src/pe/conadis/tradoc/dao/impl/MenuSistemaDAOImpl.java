package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.MenuSistemaDAO;
import pe.conadis.tradoc.entity.beans.MenuSistema;

@Repository
public class MenuSistemaDAOImpl extends AbstractDAO<MenuSistema> implements MenuSistemaDAO{
	private static final Logger logger = Logger.getLogger(MenuSistemaDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Transactional
	public List<MenuSistema> obtenerMenuSistema(String strCodigoUsuario)
			throws Exception {
		

		
		List<MenuSistema> lstMenuSistema = null ;
				try{
					lstMenuSistema = this.getSessionFactory().getCurrentSession().createQuery("from MenuSistema m"
				+ "		inner join fetch m.opcionMenus om "
				+ "		inner join fetch om.accesos ac "
				+ "		inner join fetch ac.rol ro "
				+ "		inner join fetch ro.rolUsuarios ru "
				+ "		inner join fetch ru.usuario us "
				+ " where us.codUsuario =:codUsuario")  
          .setParameter("codUsuario", strCodigoUsuario)
          .list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return lstMenuSistema;
	}

}
