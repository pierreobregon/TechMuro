package pe.conadis.tradoc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.RubroDAO;
import pe.conadis.tradoc.dao.SubCapituloDAO;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.service.SubCapituloManager;

@Service
public class SubCapituloManagerImpl extends ServiceImpl<Subcapitulo> implements SubCapituloManager {

	private static final Logger logger = Logger.getLogger(SubCapituloManagerImpl.class);
	
	@Autowired
    private SubCapituloDAO subCapituloDAO;
	@Autowired
    private RubroDAO rubroDAO;
	
	@Override
	protected Dao<Subcapitulo> getDAO() {
		return subCapituloDAO;
	}
	
	@Transactional
	public List<Subcapitulo> findByCapitulo(int id){
		return subCapituloDAO.findByCapitulo(id);
	}
	
	@Transactional
	public Subcapitulo findByDescripcion(Subcapitulo subCapitulo){
		
		Subcapitulo cap =  subCapituloDAO.findByDescripcion(subCapitulo);
		if(cap!=null){
			logger.debug("Aqu� el TipoCliente ->"+cap.getCapitulo().getProducto().getTipocliente());
			logger.debug("Aqu� el TipoCliente ->"+cap.getCapitulo().getProducto().getIdproducto());
			if(cap.getNota()!=null){
				logger.debug("Aquí las Notas ->"+cap.getNota().getTitulo());
				logger.debug("Aquí las Notas ->"+cap.getNota().getDescripcion());
			}
		}
		
		return cap;
	}
	
	@Transactional
	public List<Subcapitulo> buscarSubCapitulo(Subcapitulo subCapitulo){
		return subCapituloDAO.buscarSubCapitulo(subCapitulo);
	}
	
	@Transactional
	public String guardarSubCapitulo(Subcapitulo subCapitulo){
		try{
			
			if(subCapituloDAO.validaSubCapitulo(subCapitulo)==null){
				
				if(subCapituloDAO.validaSubCapituloSinSubCapitulo(subCapitulo)==null){
				subCapitulo.setEstado("A".charAt(0));
				subCapitulo.setFechacreacion(new Date());
				subCapitulo.setOrden(subCapituloDAO.getMaxOrder(subCapitulo));
				subCapituloDAO.add(subCapitulo);
				return "true";
			}else{
					//return "No se puede agregar Sub Cap\u00edtulos a este Cap\u00edtulo";
					return "subError01";
				}
				
				
			}else{
				//return "Sub-Cap\u00edtulo ya est\u00e1 relacionado con este Cap\u00edtulo";
				return "subError02";
			}
			
		}catch(Exception e){
			logger.error("Error al guardar Capitulo -->" +e);
			//return "Error al Insertar Sub-Capitulo";
			return "subError03";
		}
	}
	
	@Override
	@Transactional
	public Subcapitulo finById(Integer id){
		try{
			Subcapitulo cap =  subCapituloDAO.findById(id);
			logger.debug("Aquí el TipoCliente ->"+cap.getCapitulo().getProducto().getTipocliente());
			logger.debug("Aquí el TipoCliente ->"+cap.getCapitulo().getProducto().getIdproducto());
			if(cap.getNota()!=null){
				logger.debug("Aquí las Notas ->"+cap.getNota().getTitulo());
				logger.debug("Aquí las Notas ->"+cap.getNota().getDescripcion());
			}
			return cap;
		}catch(Exception e){
			logger.error("Error al buscar x id capitulo -->" +e);
			return null;
		}
	}
	
	@Transactional
	public String editarSubCapitulo(Subcapitulo subCapitulo){
		try{
			if(subCapituloDAO.validaSubCapitulo(subCapitulo)==null){
				
				if(subCapituloDAO.validaSubCapituloSinSubCapitulo(subCapitulo)==null){
				Subcapitulo capTemp = subCapituloDAO.findById(subCapitulo.getIdsubcapitulo());
				capTemp.setNombre(subCapitulo.getNombre());
				capTemp.setCapitulo(subCapitulo.getCapitulo());
				capTemp.setFechaModificacion(new Date());
				return "true";
			}else{
					//return "No se puede agregar Sub Cap\u00edtulos a este Cap\u00edtulo";
				return "subError01";
				}
				
			}else{
				//return "Sub-Cap\u00edtulo ya est\u00e1 relacionado con este Cap\u00edtulo";
				return "subError02";
			}
			
		}catch(Exception e){
			logger.error("Error al editarSubCapitulo -->" +e);
			//return "Error al Editar Sub-Capitulo";
			return "subError04";
		}
	}
	
	@Transactional
	public String eliminarSubCapitulo(int id){
		try{
			Subcapitulo cap = subCapituloDAO.findById(id);
			
			if(rubroDAO.findBySubCapitulo(id).size()>0){
				//return "Sub-capítulo tiene rubros asociados";
				return "subError05";
			}else{
				cap.setEstado("I".charAt(0));
				
				Subcapitulo sc = new Subcapitulo();
				sc.setNombre("");
				sc.setCapitulo(cap.getCapitulo());
				
				List<Subcapitulo> capitulos =subCapituloDAO.buscarSubCapitulo(sc);
				int i=1;
				for(Subcapitulo capitulo : capitulos){
					capitulo.setOrden(i++);
				}
				
				return "true";
			}
		}catch(Exception e){
			logger.error("Error al Eliminar Capítulo ->" + e);
			//return "Error al Eliminar eliminar SubCapitulo";
			return "subError06";
		}
	}
	
	@Transactional
	public boolean up(Integer id) {
		
		try {
			Subcapitulo cap1  = subCapituloDAO.findById(id);
			if(cap1.getOrden()>1){
				Subcapitulo cap2 = subCapituloDAO.findByOrden(cap1, -1);

				cap1.setOrden(cap1.getOrden()-1);
				cap2.setOrden(cap2.getOrden()+1);
			}
			return true;
		} catch (Exception ex) {
			logger.error("up " +ex.getLocalizedMessage());
			return false;
		}
	}
	
	@Transactional
	public boolean down(Integer id) {
		try {
			Subcapitulo prod1  = subCapituloDAO.findById(id);
			if(prod1.getOrden() < subCapituloDAO.getMaxOrder(prod1)-1){
				Subcapitulo prod2 = subCapituloDAO.findByOrden(prod1, +1);

				prod1.setOrden(prod1.getOrden()+1);
				prod2.setOrden(prod2.getOrden()-1);
			}
			return true;
		} catch (Exception ex) {
			logger.error("up " +ex.getLocalizedMessage());
			return false;
		}
	}
}
