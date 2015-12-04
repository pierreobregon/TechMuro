package pe.conadis.tradoc.dao;

import java.util.Date;
import java.util.List;

import pe.conadis.tradoc.entity.LogMuro;

public interface LogMuroDAO extends Dao<LogMuro> {

	public List<LogMuro> buscarLog(Date fechaIni, Date fechaFin);
}
