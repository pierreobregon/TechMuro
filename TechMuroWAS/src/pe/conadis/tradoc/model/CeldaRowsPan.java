package pe.conadis.tradoc.model;

public class CeldaRowsPan {

	
	Integer fila;
	Integer columna;
	
	
	
	public CeldaRowsPan(Integer fila, Integer columna) {
		super();
		this.columna = columna;
		this.fila = fila;
	}
	
	public Integer getFila() {
		return fila;
	}
	public void setFila(Integer fila) {
		this.fila = fila;
	}
	public Integer getColumna() {
		return columna;
	}
	public void setColumna(Integer columna) {
		this.columna = columna;
	}

	@Override
	public String toString() {
		return "CeldaRowsPan [fila=" + fila + ", columna=" + columna + "]";
	}
		
	
	
}
