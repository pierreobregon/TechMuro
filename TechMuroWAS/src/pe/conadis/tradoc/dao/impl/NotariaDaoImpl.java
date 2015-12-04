package pe.conadis.tradoc.dao.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.NotariaDAO;
import pe.conadis.tradoc.entity.Notaria;

public class NotariaDaoImpl extends AbstractDAO<Notaria> implements NotariaDAO   {
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Notaria> buscarNotaria(String nombre) {

		try {

			List<Notaria> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Notaria "
							+ "where upper(nombre) like upper(:nombre) and estado = 'A'")
					.setParameter("nombre", nombre)
					.list();
			
			return result;
		} catch (Exception ex) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Notaria> buscarNotariaEquals(String nombre) {

		try {

			List<Notaria> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Notaria "
							+ "where upper(nombre) = upper(:nombre) and estado = 'A'")
					.setParameter("nombre", nombre)
					.list();
			
			return result;
		} catch (Exception ex) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Notaria getNotariaPorNombreYPlazaId(String nombreNotaria,
			Integer idplaza) {


		try {

			List<Notaria> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Notaria "
							+ "where upper(nombre) = upper(:nombre) and plaza.idplaza = :idPlaza  and estado = 'A'")
					.setParameter("nombre", nombreNotaria)
					.setParameter("idPlaza", idplaza)
					.list();
			
			return result.size() == 0 ? null : (Notaria)result.get(0);
		} catch (Exception ex) {
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Notaria> getNotariasPorPlaza(Integer idplaza) {
		try {

			List<Notaria> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Notaria "
							+ "where plaza.idplaza = :idplaza and estado = 'A' order by nombre")
					.setParameter("idplaza", idplaza)
					.list();
			
			return result;
		} catch (Exception ex) {
			return null;
		}
	}

//	@SuppressWarnings("unchecked")
//	public List<Notaria> buscarNotariaEquals(String nombre, String nombrePlaza,
//			String descripcion) {
//		try {
//
//			List<Notaria> result = this
//					.getSessionFactory()
//					.getCurrentSession()
//					.createQuery("select n from Notaria n, NotariaContrato nc, Contrato c  "
//							+ "where upper(n.nombre) = upper(:nombre) and n.estado = 'A' and n.plaza.nombre = :nombrePlaza and "
//							+ " nc.notaria.idnotaria = n.idnotaria and "
//							+ " c.idcontrato = nc.contrato.idcontrato and c.descripcion = :descripcion	")
//					.setParameter("nombre", nombre)
//					.setParameter("descripcion", descripcion)
//					.setParameter("nombrePlaza", nombrePlaza)
//					.list();
//			
//			return result;
//		} catch (Exception ex) {
	
//			return null;
//		}
//	}
	
}
