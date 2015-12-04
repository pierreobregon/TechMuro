package pe.conadis.tradoc.dao.impl;

import org.springframework.stereotype.Repository;

import pe.conadis.tradoc.dao.PlazaDAO;
import pe.conadis.tradoc.entity.Plaza;

@Repository
public class PlazaDaoImpl extends AbstractDAO<Plaza> implements PlazaDAO{

	public Plaza findByNombre(String nombre){
		return (Plaza)this.getSessionFactory().getCurrentSession().
				createQuery("from Plaza where trim(upper(nombre)) =trim(upper(:nombre)) and estado = 'A'")  
		          .setParameter("nombre", nombre)
		          .uniqueResult();
	}
}
