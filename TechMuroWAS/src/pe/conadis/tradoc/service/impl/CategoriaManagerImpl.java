package pe.conadis.tradoc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.CategoriaDAO;
import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.SubCapituloDAO;
import pe.conadis.tradoc.dao.TransaccionDAO;
import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.service.CategoriaManager;
import pe.conadis.tradoc.service.SubCapituloManager;

@Service
public class CategoriaManagerImpl extends ServiceImpl<Categoria> implements CategoriaManager {

	private static final Logger logger = Logger.getLogger(CategoriaManagerImpl.class);
	
	@Autowired
    private CategoriaDAO categoriaDAO;
	@Autowired
    private TransaccionDAO transaccionDAO;
	
	@Override
	protected Dao<Categoria> getDAO() {
		return categoriaDAO;
	}
	
	@Transactional
	public List<Categoria> findByRubro(int id){
		return categoriaDAO.findByRubro(id);
	}
	
	
	@Transactional
	public List<Categoria> buscarCategoria(Categoria categoria){
		return categoriaDAO.buscarCategoria(categoria);
	}
	
	@Transactional
	public String guardarCategoria(Categoria categoria){
		try{
			
			if(categoriaDAO.validaCategoria(categoria)==null){
				
				if(categoriaDAO.validaCategoriaSinCategoria(categoria)==null){
				categoria.setEstado("A".charAt(0));
				categoria.setFechacreacion(new Date());
				categoria.setOrden(categoriaDAO.getMaxOrder(categoria));
				categoriaDAO.add(categoria);
				return "true";
			}else{
					//return "No se puede agregar m\u00e1s Categor\u00edas a este Rubro";
					return "catError01";
				}
				
			}else{
				//return "Categoría ya está relacionado con este Rubro";
				return "catError02";
			}
			
		}catch(Exception e){
			logger.error("Error al guardarCategoria -->" +e);
			//return "Error al Insertar Categor\u00eda";
			return "catError03";
		}
	}
	
	@Override
	@Transactional
	public Categoria finById(Integer id){
		try{
			Categoria cap =  categoriaDAO.findById(id);
			logger.debug("Aquí el TipoCliente ->"+cap.getRubro().getSubcapitulo().getCapitulo().getProducto().getTipocliente());
			logger.debug("Aquí el TipoCliente ->"+cap.getRubro().getSubcapitulo().getCapitulo().getProducto().getIdproducto());
			
			return cap;
		}catch(Exception e){
			logger.error("Error al finById-->" +e);
			return null;
		}
	}
	
	@Transactional
	public String editarCategoria(Categoria categoria){
		try{
			if(categoriaDAO.validaCategoria(categoria)==null){
				
				if(categoriaDAO.validaCategoriaSinCategoria(categoria)==null){
				Categoria capTemp = categoriaDAO.findById(categoria.getIdcategoria());
				capTemp.setNombre(categoria.getNombre());
				capTemp.setDenominacion(categoria.getDenominacion()); 
				capTemp.setRubro(categoria.getRubro());
				capTemp.setFechaModificacion(new Date());
				return "true";
			}else{
				//return "No se puede agregar m\u00e1s Categor\u00edas a este Rubro";
				return "catError01";
				}
				
			}else{
				//return "Categor\u00eda ya est\u00e1 relacionado con este Rubro";
				return "catError02";
			}
			
		}catch(Exception e){
			logger.error("Error al editarCategoria -->" +e);
			//return "Error al Editar Categor\u00edaa";
			return "catError03";
		}
	}
	
	@Transactional
	public String eliminarCategoria(int id){
		try{
			Categoria cap = categoriaDAO.findById(id);
			
			if(transaccionDAO.findByCategoria(cap.getIdcategoria()).size()>0){
				//return "Categor\u00eda tiene transacciones asociadas";
				return "catError04";
			}else{
				cap.setEstado("I".charAt(0));
				
				Categoria sc = new Categoria();
				sc.setNombre("");
				sc.setRubro(cap.getRubro());
				
				List<Categoria> capitulos =categoriaDAO.buscarCategoria(sc);
				int i=1;
				for(Categoria capitulo : capitulos){
					capitulo.setOrden(i++);
				}
				return "true";
			}
			
		}catch(Exception e){
			logger.error("Error al eliminarCategoria ->" + e);
			return "Error al Eliminar Categoria";
		}
	}
	
	@Transactional
	public boolean up(Integer id) {
		
		try {
			Categoria cap1  = categoriaDAO.findById(id);
			if(cap1.getOrden()>1){
				Categoria cap2 = categoriaDAO.findByOrden(cap1, -1);

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
			Categoria prod1  = categoriaDAO.findById(id);
			logger.debug(prod1.getRubro().getIdrubro());
			if(prod1.getOrden() < categoriaDAO.getMaxOrder(prod1)-1){
				Categoria prod2 = categoriaDAO.findByOrden(prod1, +1);

				prod1.setOrden(prod1.getOrden()+1);
				prod2.setOrden(prod2.getOrden()-1);
			}
			return true;
		} catch (Exception ex) {
			logger.error("up " +ex.getLocalizedMessage());
			return false;
		}
	}

	@Override
	public Categoria findByDescripcion(Categoria cat) {
		return categoriaDAO.findByDescripcion(cat);
	}

	
}
