package pe.conadis.tradoc.service;

import java.util.Date;
import java.util.List;

import pe.conadis.tradoc.entity.LogMuro;

public interface LogMuroManager extends Service<LogMuro>{

	public List<LogMuro> buscarLog(Date fechaIni, Date fechaFin);
}
