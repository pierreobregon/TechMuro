package pe.conadis.tradoc.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.LogMuroDAO;
import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.entity.Notaria;

@Repository
public class LogMuroDaoImpl extends AbstractDAO<LogMuro> implements LogMuroDAO{

	@SuppressWarnings("unchecked")
	@Transactional
	public List<LogMuro> buscarLog(Date fechaIni, Date fechaFin) {

		try {
			Calendar calFechaFin = Calendar.getInstance();
			calFechaFin.setTime(fechaFin);
			calFechaFin.add(Calendar.DATE,1);
			List<LogMuro> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from LogMuro l "
							+ "where l.fecha between :fechaini and :fechafin order by l.fecha desc")
					.setDate("fechaini", fechaIni)
					.setDate("fechafin", calFechaFin.getTime())
					.list();
			
			return result;
		} catch (Exception ex) {
			return null;
		}
	}
}
