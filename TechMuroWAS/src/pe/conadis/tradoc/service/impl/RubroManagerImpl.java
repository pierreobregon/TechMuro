package pe.conadis.tradoc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.CapituloDAO;
import pe.conadis.tradoc.dao.CategoriaDAO;
import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.RubroDAO;
import pe.conadis.tradoc.dao.SubCapituloDAO;
import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.service.RubroManager;
import pe.conadis.tradoc.service.SubCapituloManager;
import pe.conadis.tradoc.util.Constants;

@Service
public class RubroManagerImpl extends ServiceImpl<Rubro> implements RubroManager{

	private static final Logger logger = Logger.getLogger(RubroManagerImpl.class);
	
	@Autowired
    private RubroDAO rubroDAO;
	@Autowired
    private CapituloDAO capituloDAO;
	@Autowired
    private SubCapituloDAO subCapituloDAO;
	@Autowired
    private CategoriaDAO categoriaDAO;
	
	@Override
	protected Dao<Rubro> getDAO() {
		return rubroDAO;
	}
	
	@Transactional
	public List<Rubro> buscarRubro(Rubro rubro){
		
		List<Rubro> lista = rubroDAO.buscarRubro(rubro);
		
		for(Rubro r :lista){
			logger.debug("lista ->"+r.getCategorias().size());
		}
		return rubroDAO.buscarRubro(rubro);
	}
	
	@Transactional
	public String guardarRubro(Rubro rubro){
		try{
			
			if(rubroDAO.validaRubro(rubro)==null){
				
				if(rubro.getSubcapitulo().getCapitulo().getIdcapitulo()==0){
					Capitulo cap = new Capitulo();
					cap.setNombre(Constants.SINCAP);
					cap.setOrden(1);
					cap.setEstado("A".charAt(0));
					cap.setFechacreacion(new Date());
					cap.setDescripcion("NS");
					cap.setProducto(rubro.getSubcapitulo().getCapitulo().getProducto());
					capituloDAO.add(cap);
					rubro.getSubcapitulo().setCapitulo(cap);
				}
				
				if(rubro.getSubcapitulo().getIdsubcapitulo()==0){
					Subcapitulo subcap = new Subcapitulo();
					subcap.setNombre(Constants.SINSUBCAP);
					subcap.setOrden(1);
					subcap.setEstado("A".charAt(0));
					subcap.setFechacreacion(new Date());
					subcap.setDescripcion("NS");
					subcap.setCapitulo(rubro.getSubcapitulo().getCapitulo());
					subCapituloDAO.add(subcap);
					rubro.setSubcapitulo(subcap);
				}
				
				rubro.setEstado("A".charAt(0));
				rubro.setFechacreacion(new Date());
			//	Integer nroOrden = rubroDAO.getMaxOrder(rubro);
				//rubro.setOrden(-1);
				rubro.setOrden(rubroDAO.getMaxOrder(rubro));
				rubroDAO.add(rubro);
				return ""+rubro.getIdrubro()+"|"+rubro.getSubcapitulo().getIdsubcapitulo();
			}else{
				//return "Rubro ya est\u00e1 relacionado con este Sub-Cap\u00edtulo";
				return "rubError01";
			}
			
		}catch(Exception e){
			logger.error("Error al guardar Rubro -->" +e);
			//return "Error al Insertar Rubro";
			return "rubError02";
		}
	}
	
	@Override
	@Transactional
	public Rubro finById(Integer id){
		try{
			Rubro cap =  rubroDAO.findById(id);
			logger.debug("Aquí el TipoCliente ->"+cap.getSubcapitulo().getCapitulo().getProducto().getTipocliente());
			logger.debug("Aquí el TipoCliente ->"+cap.getSubcapitulo().getCapitulo().getProducto().getIdproducto());
			if(cap.getNota()!=null){
				logger.debug("Aquí las Notas ->"+cap.getNota().getTitulo());
			
				logger.debug("Aquí las Notas ->"+cap.getNota().getDescripcion());
			}
			return cap;
		}catch(Exception e){
			logger.error("Error al finById -->" +e);
			return null;
		}
	}
	
	@Transactional
	public String editarRubro(Rubro rubro){
		try{
			
			Rubro rubroAnterior = rubroDAO.findById(rubro.getIdrubro());
			
			boolean tieneElMismoNombreQueElAnterior = rubroAnterior.getNombre().toUpperCase().trim().equals(rubro.getNombre().toUpperCase().trim());
			
			if(tieneElMismoNombreQueElAnterior || rubroDAO.validaRubro(rubro)==null){
				Rubro capTemp = rubroDAO.findById(rubro.getIdrubro());
				capTemp.setNombre(rubro.getNombre());
				capTemp.setSubcapitulo(rubro.getSubcapitulo());
				capTemp.setFechaModificacion(new Date());
				//rubroDAO.update(capTemp);
				
				return ""+rubro.getIdrubro()+"|"+rubro.getSubcapitulo().getIdsubcapitulo();
			}else{
				//return "Rubro ya est\u00e1 relacionado con este Sub-Cap\u00edtulo";
				return "rubError01";
			}
		}catch(Exception e){
			logger.error("Error al editarRubro -->" +e);
			//return "Error al Editar Rubro";
			return "rubError03";
		}
	}
	
	@Transactional
	public String eliminarRubro(int id){
		try{
			Rubro cap = rubroDAO.findById(id);
			
			if(categoriaDAO.findByRubro(id).size()>0){
				//return "Rubro tiene categorias asociadas";
				return "rubError04";
			}else{
				cap.setEstado("I".charAt(0));
				
				Rubro sc = new Rubro();
				sc.setNombre("");
				sc.setSubcapitulo(cap.getSubcapitulo());
				
				List<Rubro> capitulos =rubroDAO.buscarRubro(sc);
				int i=1;
				for(Rubro capitulo : capitulos){
					capitulo.setOrden(i++);
				}
				
				return "true";
			}
		}catch(Exception e){
			logger.error("Error al Eliminar Rubro ->" + e);
			//return "Error al Eliminar eliminar Rubro";
			return "rubError05";
		}
	}
	
	@Transactional
	public boolean up(Integer id) {
		
		try {
			Rubro cap1  = rubroDAO.findById(id);
			if(cap1.getOrden()>1){
				Rubro cap2 = rubroDAO.findByOrden(cap1, -1);

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
			Rubro prod1  = rubroDAO.findById(id);
			if(prod1.getOrden() < rubroDAO.getMaxOrder(prod1)-1){
				Rubro prod2 = rubroDAO.findByOrden(prod1, +1);

				prod1.setOrden(prod1.getOrden()+1);
				prod2.setOrden(prod2.getOrden()-1);
			}
			return true;
		} catch (Exception ex) {
			logger.error("up " +ex.getLocalizedMessage());
			return false;
		}
	}
	
	@Transactional
	public List<Rubro> findBySubCapitulo(int id){
		return rubroDAO.findBySubCapitulo(id);
	}

	
}
