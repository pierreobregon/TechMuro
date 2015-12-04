package pe.conadis.tradoc.service;

import java.util.List;

import pe.conadis.tradoc.entity.Contrato;
import pe.conadis.tradoc.entity.Notaria;
import pe.conadis.tradoc.entity.NotariaContrato;
import pe.conadis.tradoc.model.UploadedFile;

public interface NotariaManager extends Service<Notaria> {

	public List<Notaria> buscarNotaria(String nombre);
	
	public boolean eliminarNotaria(Notaria notaria);
	
	public Notaria getNotaria(Notaria notaria);
	
	public boolean agregarNotariaContrato(Notaria notaria, List<Contrato> contratoList);
	
	public boolean deleteNotariaContrato(List<NotariaContrato> contratoList);
	
	public String subirArchivoNotarias(UploadedFile uploadedFile, String usuario);

	public List<Notaria> getNotariasPorPlaza(Integer idplaza);

	public List<NotariaContrato> getNotariasContratosOrdenados(Integer idnotaria);
}
