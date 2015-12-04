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
import pe.conadis.tradoc.dao.TransaccionDetalleDAO;
import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Columna;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;
import pe.conadis.tradoc.service.CategoriaManager;
import pe.conadis.tradoc.service.SubCapituloManager;
import pe.conadis.tradoc.service.TransaccionManager;
import pe.conadis.tradoc.util.Constants;

@Service
public class TransaccionManagerImpl extends ServiceImpl<Transaccion> implements TransaccionManager {

	private static final Logger logger = Logger.getLogger(TransaccionManagerImpl.class);
	
	@Autowired
    private TransaccionDAO transaccionDAO;
	@Autowired
    private CategoriaDAO categoriaDAO;
	@Autowired
    private TransaccionDetalleDAO transaccionDetalleDAO;
	
	@Override
	protected Dao<Transaccion> getDAO() {
		return transaccionDAO;
	}
	
	@Transactional
	public List<Transaccion> findByCategoria(int id){
		return transaccionDAO.findByCategoria(id);
	}
	
	
	@Transactional
	public List<Transaccion> buscarTransaccion(Transaccion transaccion){
		return transaccionDAO.buscarTransaccion(transaccion);
	}
	
	@Transactional
	public String guardarTransaccion(Transaccion transaccion){
		try{
			
			if(transaccionDAO.validaTransaccion(transaccion)==null){
				if(transaccion.getCategoria().getIdcategoria()==0){
					Categoria cat = new Categoria();
					cat.setNombre(Constants.SINCAT);
					cat.setOrden(1);
					cat.setEstado("A".charAt(0));
					cat.setFechacreacion(new Date());
					cat.setDescripcion("NS");
					cat.setRubro(transaccion.getCategoria().getRubro());
					categoriaDAO.add(cat);
					transaccion.setCategoria(cat);
					
				}
				
				transaccion.setEstado("A".charAt(0));
				transaccion.setFechacreacion(new Date());
				transaccion.setOrden(transaccionDAO.getMaxOrder(transaccion));
				transaccionDAO.add(transaccion);
				return ""+transaccion.getIdtransaccion()+"|"+transaccion.getCategoria().getIdcategoria();
			}else{
				//return "Transacci\u00f3n ya est\u00e1 relacionada con este Rubro o Categor\u00eda";
				return "traError01";
			}
			
		}catch(Exception e){
			logger.error("Error al guardarTransaccion -->" +e);
			//return "Error al Insertar Transacci\u00f3n";
			return "traError02";
		}
	}
	
	@Override
	@Transactional
	public Transaccion finById(Integer id){
		try{
			Transaccion cap =  transaccionDAO.findById(id);
			logger.debug("Aquí el TipoCliente ->"+cap.getCategoria().getRubro().getSubcapitulo().getCapitulo().getProducto().getTipocliente());
			logger.debug("Aquí el TipoCliente ->"+cap.getCategoria().getRubro().getSubcapitulo().getCapitulo().getProducto().getIdproducto());
			
			return cap;
		}catch(Exception e){
			logger.error("Error al finById-->" +e);
			return null;
		}
	}
	
	@Transactional
	public String editarTransaccion(Transaccion transaccion){
		try{
			if(transaccionDAO.validaTransaccion(transaccion)==null){
				Transaccion capTemp = transaccionDAO.findById(transaccion.getIdtransaccion());
				capTemp.setNombre(transaccion.getNombre());
				capTemp.setCategoria(transaccion.getCategoria());
				capTemp.setFechaModificacion(new Date());
				
				return ""+transaccion.getIdtransaccion()+"|"+transaccion.getCategoria().getIdcategoria();
			}else{
				//return "Transacci\u00f3n ya est\u00e1 relacionada con este Rubro o Categor\u00eda";
				return "traError01";
			}
			
		}catch(Exception e){
			logger.error("Error al editarTransaccion -->" +e);
			//return "Error al editar transaccion";
			return "traError03";
		}
	}
	
	@Transactional
	public String eliminarTransaccion(int id){
		try{
			Transaccion cap = transaccionDAO.findById(id);
			cap.setEstado("I".charAt(0));
			
			Transaccion sc = new Transaccion();
			sc.setNombre("");
			sc.setCategoria(cap.getCategoria());
			
			List<Transaccion> capitulos =transaccionDAO.buscarTransaccion(sc);
			int i=1;
			for(Transaccion capitulo : capitulos){
				capitulo.setOrden(i++);
			}
			
			return "true";
		}catch(Exception e){
			logger.error("Error al eliminarTransaccion ->" + e);
			//return "Error al Eliminar Transacci\u00f3n";
			return "traError04";
		}
	}
	
	@Transactional
	public boolean up(Integer id) {
		
		try {
			Transaccion cap1  = transaccionDAO.findById(id);
			if(cap1.getOrden()>1){
				Transaccion cap2 = transaccionDAO.findByOrden(cap1, -1);

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
			Transaccion prod1  = transaccionDAO.findById(id);
			logger.debug(prod1.getCategoria().getIdcategoria());
			if(prod1.getOrden() < transaccionDAO.getMaxOrder(prod1)-1){
				Transaccion prod2 = transaccionDAO.findByOrden(prod1, +1);

				prod1.setOrden(prod1.getOrden()+1);
				prod2.setOrden(prod2.getOrden()-1);
			}
			return true;
		} catch (Exception ex) {
			logger.error("up " +ex.getLocalizedMessage());
			return false;
		}
	}

	public List<TransaccionDetalle> findDetalle(Transaccion transaccion){
		
		return transaccionDAO.findDetalle(transaccion);
	}
	
	public TransaccionDetalle findByPosicion(TransaccionDetalle transaccion){
		
		return transaccionDAO.findByPosicion(transaccion);
	}
	
	@Transactional
	public boolean deleteByTransaccion(Transaccion transaccion){
		
		List<TransaccionDetalle> lista = transaccionDAO.findDetalle(transaccion);
		
		for(TransaccionDetalle tran: lista){
			try {
				
				//transaccionDetalleDAO.delete(tran.getIddetalle());
				tran.setEstado("I".charAt(0));
				
			} catch (Exception e) {
				logger.error("Error eliminar deleteByTransaccion "+e);
				return false;
			}
		}

		return true;
	}
	
}
