package pe.conadis.tradoc.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;




import pe.conadis.tradoc.dao.CapituloDAO;
import pe.conadis.tradoc.dao.CategoriaDAO;
import pe.conadis.tradoc.dao.ColumnaDAO;
import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.NotaDAO;
import pe.conadis.tradoc.dao.RubroDAO;
import pe.conadis.tradoc.dao.SubCapituloDAO;
import pe.conadis.tradoc.dao.TransaccionDAO;
import pe.conadis.tradoc.dao.TransaccionDetalleDAO;
import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Columna;
import pe.conadis.tradoc.entity.Nota;
import pe.conadis.tradoc.entity.Producto;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;
import pe.conadis.tradoc.service.CapituloManager;
import pe.conadis.tradoc.util.Constants;
import pe.conadis.tradoc.util.FilterTarifario;

@Service
public class CapituloManagerImpl extends ServiceImpl<Capitulo> implements CapituloManager {

	private static final Logger logger = Logger.getLogger(CapituloManagerImpl.class);
	
	@Autowired
    private CapituloDAO capituloDAO;
	
	@Autowired
    private SubCapituloDAO subCapituloDAO;
	
	@Autowired
    private RubroDAO rubroDAO;
	
	@Autowired
    private ColumnaDAO columnaDAO;
	
	@Autowired
    private CategoriaDAO categoriaDAO;
	
	@Autowired
    private TransaccionDAO transaccionDAO;
	
	@Autowired
    private TransaccionDetalleDAO transaccionDetalleDAO;
	
	@Autowired
    private NotaDAO notaDAO;
	
	
	
	@Override
	protected Dao<Capitulo> getDAO() {
		return capituloDAO;
	}
	@Transactional
	public List<Capitulo> findByProducto(int id){
		
		return capituloDAO.findByProducto(id);
	}
	
	@Transactional
	public List<Capitulo> buscarCapitulo(Capitulo capitulo){
		
		List<Capitulo> lista =  capituloDAO.buscarCapitulo(capitulo);
		for(Capitulo cap:lista){
			logger.debug("size subcapitulos ->"+cap.getSubcapitulos().size());
		}
		return lista;
	}
	
	@Transactional
	public List<Capitulo> buscarCapituloVisor(Capitulo capitulo){
		
		List<Capitulo> listaCapitulo =  capituloDAO.buscarCapitulo(capitulo);
		
		for(Capitulo cap:listaCapitulo){
			if(cap.getNotaByIdnotainicial()!=null){
				cap.getNotaByIdnotainicial().getTitulo();
				cap.getNotaByIdnotainicial().getDescripcion();
			}
			
			if(cap.getNotaByIdnotafinal()!=null){
				cap.getNotaByIdnotafinal().getTitulo();
				cap.getNotaByIdnotafinal().getDescripcion();
			}
			
			Subcapitulo subCapitulo = new Subcapitulo();
			subCapitulo.setNombre("");
			subCapitulo.setCapitulo(cap);
			
			List<Subcapitulo> listaSubCapitulo =  subCapituloDAO.buscarSubCapitulo(subCapitulo);
			
			for(Subcapitulo subcap : listaSubCapitulo){
				if(subcap.getNota()!=null){
					subcap.getNota().getTitulo();
					subcap.getNota().getDescripcion();
				}
				
				Rubro rubro = new Rubro();
				rubro.setNombre("");
				rubro.setSubcapitulo(subcap);
				List<Rubro> listaRubro = rubroDAO.buscarRubro(rubro);
				
				for(Rubro rubros : listaRubro){
					Columna columna = new Columna();
					columna.setRubro(rubros);
					List<Columna> listaColumna = columnaDAO.findByRubro(columna);
					
					if(rubros.getNota()!=null){
						rubros.getNota().getTitulo();
						rubros.getNota().getDescripcion();
					}
					
					
					Categoria categoria = new Categoria();
					categoria.setRubro(rubros);
					
					List<Categoria> listaCategoria = categoriaDAO.buscarCategoria(categoria);
					
					for(Categoria cat:listaCategoria){
						
						List<Transaccion> listaTransaccion = transaccionDAO.buscarTransaccion(new Transaccion(0, new Categoria(cat.getIdcategoria())));
						
						for(Transaccion tran : listaTransaccion){
							List<TransaccionDetalle> listaDetalle = transaccionDAO.findDetalle(tran);
							tran.setTransaccionDetalles(listaDetalle);
						}
						
						cat.setTransaccions(listaTransaccion);
						
					}
					
					rubros.setCategorias(listaCategoria);
					rubros.setColumnas(listaColumna);
				}
				subcap.setRubros(listaRubro);
			}
			cap.setSubcapitulos(listaSubCapitulo);
		}
		return listaCapitulo;
	}
	
	@Transactional
	public String guardarCapitulo(Capitulo capitulo){
		String result = "";
		try{
			capitulo.setEstado("A".charAt(0));
			capitulo.setFechacreacion(new Date());
			capitulo.setOrden(capituloDAO.getMaxOrder(capitulo));
			
			if(capituloDAO.validaCapitulo(capitulo)==null){
				if(capituloDAO.validaSinCapitulo(capitulo)==null){
				capituloDAO.add(capitulo);
					result = "true";
				}else{
					//result = "No se puede agregar mas Cap\u00edtulos a este Producto";
					result = "capError01";
				}
			}else{
				//result = "Cap\u00edtulo ya est\u00e1 relacionado con este Producto";
				result = "capError02";
			}
			
		}catch(Exception e){
			logger.error("Error al guardar Capitulo -->" +e);
			//result = "Error al Insertar Capitulo";
			result = "capError03";
		}
		
		return result;
	}
	
	@Override
	@Transactional
	public Capitulo finById(Integer id){
		try{
			Capitulo cap =  capituloDAO.findById(id);
			logger.debug("Aquí el producto ->"+cap.getProducto().getTipocliente());
			
			if(cap.getNotaByIdnotainicial()!=null){
				logger.debug("Aquí las Notas ->"+cap.getNotaByIdnotainicial().getTitulo());
				logger.debug("Aquí las Notas ->"+cap.getNotaByIdnotainicial().getDescripcion());
			}
			
			if(cap.getNotaByIdnotafinal()!=null){
				logger.debug("Aquí las Notas ->"+cap.getNotaByIdnotafinal().getTitulo());
				logger.debug("Aquí las Notas ->"+cap.getNotaByIdnotafinal().getDescripcion());
			}
			
			return cap;
		}catch(Exception e){
			logger.error("Error al buscar x id capitulo -->" +e);
			return null;
		}
	}
	
	@Transactional
	public String editarCapitulo(Capitulo capitulo){
		try{
			if(capituloDAO.validaCapitulo(capitulo)==null){
			
				Capitulo capTemp = capituloDAO.findById(capitulo.getIdcapitulo());
				capTemp.setNombre(capitulo.getNombre());
				capTemp.setProducto(capitulo.getProducto());
				capTemp.setFechaModificacion(new Date());
				return "true";
				
			}else{
				//return "Cap\u00edtulo ya est\u00e1 relacionado con este Producto";
				return "capError02";
			}
			
		}catch(Exception e){
			logger.error("Error al Editar Capitulo -->" +e);
			//return "Error al Insertar Capitulo";
			return "capError04";
		}
	}
	
	@Transactional
	public String eliminarCapitulo(int id){
		try{
			Capitulo cap = capituloDAO.findById(id);
			
			if(subCapituloDAO.findByCapitulo(id).size()>0){
				//return "Cap\u00edtulo tiene sub-cap\u00edtulos asociados";
				return "capError06";
			}else{
				cap.setEstado("I".charAt(0));
				
				List<Capitulo> capitulos = capituloDAO.buscarCapitulo(cap);
				int i=1;
				for(Capitulo capitulo : capitulos){
					capitulo.setOrden(i++);
				}
				
				return "true";
			}
		}catch(Exception e){
			logger.error("Error al Eliminar Capítulo ->" + e);
			//return "Error al Eliminar Cap\u00edtulo";
			return "capError05";
		}
	}
	
	@Transactional
	public boolean up(Integer id) {
		
		try {
			Capitulo cap1  = capituloDAO.findById(id);
			if(cap1.getOrden()>1){
				Capitulo cap2 = capituloDAO.findByOrden(cap1, -1);

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
			Capitulo prod1  = capituloDAO.findById(id);
			if(prod1.getOrden() < capituloDAO.getMaxOrder(prod1)-1){
				Capitulo prod2 = capituloDAO.findByOrden(prod1, +1);

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
	public Capitulo findByDescripcion(Capitulo cap) {
		return capituloDAO.findByDescripcion(cap);
	}
	@Override
	public List<Capitulo> obtenerTarifarioPorProducto(Integer idproducto) {
		
		List<Capitulo> capitulos = capituloDAO.getCapitulosPorProductoId(idproducto);
		System.out.println("cantidad de capitulos : " + capitulos.size());
	
		
		
		List<Subcapitulo> subcapitulos = subCapituloDAO.getSubCapitulosPorProductoId(idproducto);
		System.out.println("cantidad de subcapitulos : " + subcapitulos.size());
		
		
		List<Nota> notas = notaDAO.getNotasPorProductoId(idproducto);
		System.out.println("cantidad de notas : " + notas.size());
		
		
//		Subcapitulo sub = subcapitulos.get(0);
//		System.out.println("id cap: " + sub.getCapitulo().getIdcapitulo());
		
		List<Rubro> rubros = rubroDAO.getRubrosPorProductoId(idproducto);
		System.out.println("cantidad de rubros : " + rubros.size());
		
		List<Columna> columnasRubro = columnaDAO.getColumnasRubroPorProductoId(idproducto);
		System.out.println("cantidad de columnasRubro : " + columnasRubro.size());
		
		
		List<Categoria> categorias = categoriaDAO.getCategoriasPorProductoId(idproducto);
		System.out.println("cantidad de categorias : " + categorias.size());
		
		List<Transaccion> transacciones = transaccionDAO.getTransaccionesPorProductoId(idproducto);
		System.out.println("cantidad de transacciones : " + transacciones.size());
		
		List<TransaccionDetalle> detallesTransaccion = transaccionDetalleDAO.getTransaccioneDetallePorProductoId(idproducto);
		System.out.println("cantidad de transacciones detalle : " + detallesTransaccion.size());
		
		
		Map<Integer, Nota> notasMap = FilterTarifario.getNotasMap(notas);
		
		
		
		///------------------  Filtrado  ---------------------------------------------------
		
		for (Capitulo capitulo : capitulos) {
			List<Subcapitulo> subCapitulosFilter = FilterTarifario.filterSubCapitulo(subcapitulos, capitulo.getIdcapitulo());
			capitulo.setSubcapitulos(subCapitulosFilter);
			
			if(capitulo.getNotaByIdnotainicial() != null){
				Integer notaInicialIdCap= capitulo.getNotaByIdnotainicial().getIdnota();
				capitulo.setNotaByIdnotainicial(notasMap.get(notaInicialIdCap));
			}
			
			if(capitulo.getNotaByIdnotafinal() != null){
				Integer notaFinalIdCap= capitulo.getNotaByIdnotafinal().getIdnota();
				capitulo.setNotaByIdnotafinal(notasMap.get(notaFinalIdCap));
			}
			
			
			
			
			for (Subcapitulo subcapitulo : subCapitulosFilter) {
				
				if(subcapitulo.getNota() != null){
					Integer notaIdSubcapitulo = subcapitulo.getNota().getIdnota();
					subcapitulo.setNota(notasMap.get(notaIdSubcapitulo));
				}else{
					Nota notaSubcapitulo  = new Nota();
					subcapitulo.setNota(notaSubcapitulo);
				}
				
							
				List<Rubro> rubrosFilter = FilterTarifario.filterRubro(rubros, subcapitulo.getIdsubcapitulo());
				subcapitulo.setRubros(rubrosFilter);
				
				for (Rubro rubro : rubrosFilter) {
					
					if(rubro.getNota() != null){
						Integer notaIdRubro = rubro.getNota().getIdnota();
						rubro.setNota(notasMap.get(notaIdRubro));
					}else{
						Nota notaRubro = new Nota();
						rubro.setNota(notaRubro);
					}
					
					List<Columna> columnasFilter = FilterTarifario.filterColumnaRubro(columnasRubro,  rubro.getIdrubro());
					Collections.sort(columnasFilter);
					rubro.setColumnas(columnasFilter);
					
					List<Categoria> categoriasFilter = FilterTarifario.filterCategoria(categorias, rubro.getIdrubro());
					rubro.setCategorias(categoriasFilter);
					
					for (Categoria categoria : categoriasFilter) {
						List<Transaccion> transaccionesFilter = FilterTarifario.filterTransaccion(transacciones, categoria.getIdcategoria());
						categoria.setTransaccions(transaccionesFilter);
						
						for (Transaccion transaccion : transaccionesFilter) {
							List<TransaccionDetalle> transaccionesDetalleFilter = FilterTarifario.filterTransaccionDetalles(detallesTransaccion, transaccion.getIdtransaccion());
							Collections.sort(transaccionesDetalleFilter);
							transaccion.setTransaccionDetalles(transaccionesDetalleFilter);
						
						}
						
					}
				}
			}
		}
		

		
		
		return capitulos;
	}
	
	
}
