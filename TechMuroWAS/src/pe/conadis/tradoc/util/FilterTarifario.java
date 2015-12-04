package pe.conadis.tradoc.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Columna;
import pe.conadis.tradoc.entity.Nota;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;

public class FilterTarifario {

	
	public static List<Subcapitulo> filterSubCapitulo(List<Subcapitulo> subcapitulos, final Integer idCapitulo){
		Collection<Subcapitulo> filterSubCapitulos = Collections2.filter(subcapitulos, new Predicate<Subcapitulo>() {

			@Override
			public boolean apply(Subcapitulo subcapitulo) {
				return subcapitulo.getCapitulo().getIdcapitulo().equals(idCapitulo);
			}
		});
		return new ArrayList<Subcapitulo>(filterSubCapitulos);
	}
	
	
	public static List<Rubro> filterRubro(List<Rubro> rubros, final Integer idSubCapitulo){
		Collection<Rubro> filterRubros = Collections2.filter(rubros, new Predicate<Rubro>() {

			@Override
			public boolean apply(Rubro rubro) {
				return rubro.getSubcapitulo().getIdsubcapitulo().equals(idSubCapitulo);
			}
		});
		return new ArrayList<Rubro>(filterRubros);
	}
	
	
	public static List<Columna> filterColumnaRubro(List<Columna> columnas, final Integer idRubro){
		Collection<Columna> filterColumnas = Collections2.filter(columnas, new Predicate<Columna>() {

			@Override
			public boolean apply(Columna columna) {
				return columna.getRubro().getIdrubro().equals(idRubro);
			}
		});
		return new ArrayList<Columna>(filterColumnas);
	}
	
	public static List<Categoria> filterCategoria(List<Categoria> categorias, final Integer idRubro){
		Collection<Categoria> filterCategorias = Collections2.filter(categorias, new Predicate<Categoria>() {

			@Override
			public boolean apply(Categoria rubro) {
				return rubro.getRubro().getIdrubro().equals(idRubro);
			}
		});
		return new ArrayList<Categoria>(filterCategorias);
	}
	
	
	public static List<Transaccion> filterTransaccion(List<Transaccion> transacciones, final Integer idCategoria){
		Collection<Transaccion> filterTransacciones = Collections2.filter(transacciones, new Predicate<Transaccion>() {

			@Override
			public boolean apply(Transaccion rubro) {
				return rubro.getCategoria().getIdcategoria().equals(idCategoria);
			}
		});
		return new ArrayList<Transaccion>(filterTransacciones);
	}
	
	public static List<TransaccionDetalle> filterTransaccionDetalles(List<TransaccionDetalle> transaccionesDetalle, final Integer idTransaccion){
		Collection<TransaccionDetalle> filterTransaccionDetalle = Collections2.filter(transaccionesDetalle, new Predicate<TransaccionDetalle>() {

			@Override
			public boolean apply(TransaccionDetalle detalle) {
				return detalle.getTransaccion().getIdtransaccion().equals(idTransaccion);
			}
		});
		return new ArrayList<TransaccionDetalle>(filterTransaccionDetalle);
	}
	
	
	public static Map<Integer, Nota> getNotasMap(List<Nota> notas){
		
	//	Map<Integer, List<Nota>> notasMap =  Maps.uniqueIndex(notas, new Function
		Map<Integer, Nota> notasMap = Maps.uniqueIndex(notas, new Function<Nota, Integer>() {

			@Override
			public Integer apply(Nota nota) {
				return nota.getIdnota();
			}
		});
		
		return notasMap;
	}
	
	
}
