package pe.conadis.tradoc.dao;

import java.util.List;

import pe.conadis.tradoc.entity.beans.MenuSistema;

public interface MenuSistemaDAO {
	public List<MenuSistema> obtenerMenuSistema(String strCodigoUsuario) throws Exception;
}
