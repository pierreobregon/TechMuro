package pe.conadis.tradoc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.controller.ProductoController;
import pe.conadis.tradoc.dao.CapituloDAO;
import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.ProductoDAO;
import pe.conadis.tradoc.dao.VariableDAO;
import pe.conadis.tradoc.entity.Producto;
import pe.conadis.tradoc.service.ProductoManager;
import pe.conadis.tradoc.util.Constants;

@Service
public class ProductoManagerImpl extends ServiceImpl<Producto> implements ProductoManager {

	private static final Logger logger = Logger.getLogger(ProductoManagerImpl.class);
	
	@Autowired
    private ProductoDAO productoDAO;
	@Autowired
    private CapituloDAO capituloDAO;
	@Autowired
    private VariableDAO variableDAO;
	
	@Override
	protected Dao<Producto> getDAO() {
		return productoDAO;
	}
	
	public List<Producto> buscarProducto(String criterio, String tipoCliente){
		return productoDAO.buscarProducto(criterio, tipoCliente);
	}
	
	@Transactional
	public List<Producto> buscarProductoOrden(String tipoCliente){
		String orden = "no";
		try{
			 orden = variableDAO.findById(Constants.PRODUCTOSALFABETICAMENTE).getValor();
			 System.out.println(orden+"-----------------------------------");
		}catch(Exception e){
			logger.error("Error buscarProductoOrden "+e);
		}
		
		return productoDAO.buscarProductoOrden(tipoCliente, orden);
	}
	
	@Transactional
	public List<Producto> buscarProductoConCapitulos(String criterio, String tipoCliente){
		return productoDAO.buscarProducto(criterio, tipoCliente, true);
	}
	
	@Transactional
	public boolean up(Integer id) {
		try {
			Producto prod1  = productoDAO.findById(id);
			if(prod1.getOrden()>1){
				Producto prod2 = productoDAO.findByOrden(prod1, -1);
				prod1.setOrden(prod1.getOrden()-1);
				prod2.setOrden(prod2.getOrden()+1);
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
			Producto prod1  = productoDAO.findById(id);
			if(prod1.getOrden() < productoDAO.getMaxOrder(prod1.getTipocliente())-1){
				Producto prod2 = productoDAO.findByOrden(prod1, +1);
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
	public String guardarProducto(Producto producto){
		try{
			producto.setEstado("A".charAt(0));
			producto.setFechacreacion(new Date());
			producto.setOrden(productoDAO.getMaxOrder(producto.getTipocliente()));
			
			if(productoDAO.validaNombre(producto)==null){
				productoDAO.add(producto);
				return "true";
			}else{
				return "Producto ya existe para este Tipo de Cliente";
			}
			
			
		}catch(Exception e){
			logger.error("Error al guardar Producto -->" +e);
			return "Error al Insertar producto";
		}
	}
	
	@Transactional
	public String editarProducto(Producto producto){
		try{
			Producto productoTemp = productoDAO.findById(producto.getIdproducto());
			if(productoDAO.validaNombre(producto)==null){
				productoTemp.setNombre(producto.getNombre());
				productoTemp.setTipocliente(producto.getTipocliente());
				
				productoTemp.setFechaModificacion(new Date());
				return "true";
			}else{
				return "Producto ya existe para este Tipo de Cliente";
			}
			
		}catch(Exception e){
			logger.error("Error al guardar Producto -->" +e);
			return "Error al Insertar producto";
		}
	}
	
	@Transactional
	public String eliminarProducto(int id){
		try{
			Producto prod = productoDAO.findById(id);
			
			if(capituloDAO.findByProducto(id).size()>0){
				//return "Producto tiene cap\u00edtulos asociados";
				return "proError01";
			}else{
				prod.setEstado("I".charAt(0));
				
				List<Producto> productos = productoDAO.buscarProducto("%", prod.getTipocliente());
				int i=1;
				for(Producto producto : productos){
					producto.setOrden(i++);
				}
				
				return ""+true;
			}
		}catch(Exception e){
			logger.debug("Error eliminarProducto "+e);
			return ""+false;
		}
	}


	public List<Producto> getProductosPorTipoCliente(String codigoTipoCliente) {
		return productoDAO.getProductosPorTipoCliente(codigoTipoCliente);
	}


	
}
