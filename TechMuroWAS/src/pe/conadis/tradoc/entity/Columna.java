package pe.conadis.tradoc.entity;

// Generated Feb 27, 2014 5:00:05 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Columna generated by hbm2java
 */
@Entity
@Table(name = "X_COLUMNA")
public class Columna implements java.io.Serializable, Comparable<Columna> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2364838832161928714L;
	
	private Integer idcolumna;
	private Rubro rubro;
	private String width;
	private String titulo;
	private String posicionx;
	private String posiciony;
	private String colspan;
	private String rowspan;

	public Columna() {
	}

	public Columna(Integer idcolumna) {
		this.idcolumna = idcolumna;
	}

	public Columna(Integer idcolumna, Rubro rubro, String width,
			String titulo, String posicionx, String posiciony, String colspan,
			String rowspan) {
		this.idcolumna = idcolumna;
		this.rubro = rubro;
		this.width = width;
		this.titulo = titulo;
		this.posicionx = posicionx;
		this.posiciony = posiciony;
		this.colspan = colspan;
		this.rowspan = rowspan;
	}

	@Id
	@Column(name = "IDCOLUMNA", unique = true, nullable = false, precision = 22, scale = 0)
	//@SequenceGenerator(name = "seq_columna", sequenceName = "SEQ_COLUMNA", allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_columna")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getIdcolumna() {
		return this.idcolumna;
	}

	public void setIdcolumna(Integer idcolumna) {
		this.idcolumna = idcolumna;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDRUBRO")
	public Rubro getRubro() {
		return this.rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	@Column(name = "WIDTH", length = 10)
	public String getWidth() {
		return this.width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	@Column(name = "TITULO", length = 500)
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Column(name = "POSICIONX", length = 20)
	public String getPosicionx() {
		return this.posicionx;
	}

	public void setPosicionx(String posicionx) {
		this.posicionx = posicionx;
	}

	@Column(name = "POSICIONY", length = 20)
	public String getPosiciony() {
		return this.posiciony;
	}

	public void setPosiciony(String posiciony) {
		this.posiciony = posiciony;
	}

	@Column(name = "COLSPAN", length = 20)
	public String getColspan() {
		return this.colspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	@Column(name = "ROWSPAN", length = 20)
	public String getRowspan() {
		return this.rowspan;
	}

	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}

	@Override
	public int compareTo(Columna o) {
		int resultado = this.getPosicionx().compareTo(o.getPosicionx());
		if(resultado == 0) return this.getPosiciony().compareTo(o.getPosiciony());
		return resultado;
	}

}
