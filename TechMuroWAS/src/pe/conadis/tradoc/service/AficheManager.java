package pe.conadis.tradoc.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import pe.conadis.tradoc.entity.Afiche;
import pe.conadis.tradoc.model.UploadedFile;

public interface AficheManager extends Service<Afiche> {
	
	public void deleteAfi(Integer id) throws Exception;
	
	public List<Afiche> buscarAfiche(String criterio) throws Exception;
	
	public byte[] byId(Integer id) throws Exception;
	
	public List<Object> listarAll() throws Exception;
	
	public String validateMin(String ofic) throws Exception;
	
	public String validateMinAny(String oficinaMiniatura, String ofic, Afiche afiche , BindingResult result,UploadedFile uploadedFile) throws Exception;
	
	public String validateMinAnyElse(String oficinaMiniatura, String ofic, Afiche afiche , BindingResult result,UploadedFile uploadedFile) throws Exception;
	
	

}
