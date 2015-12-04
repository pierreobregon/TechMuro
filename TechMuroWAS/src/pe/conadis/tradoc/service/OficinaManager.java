package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.Oficina;
import pe.conadis.tradoc.entity.beans.Accion;
import pe.conadis.tradoc.model.UploadedFile;

public interface OficinaManager extends Service<Oficina>{
	
	public List<Oficina> getOficinasFilter() throws Exception;
	public List<Oficina> getOficinasActivas() throws Exception;
	
	public Oficina getOficinasVisor(Integer id) throws Exception;
	
	public Oficina getOficinasByCodigoVisor(String criterio) throws Exception;
	
	
	public List<Oficina> buscarOficina(String criterio) throws Exception;
	
	public void deleteOficina(Integer id) throws Exception;
	
	public String subirArchivoOficinas(UploadedFile uploadedFile, String usuario);

	
	public List<Oficina> findByCodigo(String codigo);

	public String obtenerCodigoOficinaPorIP(String ip);

	public List<Oficina> getOficinasPorPlaza(Integer idplaza);
}
