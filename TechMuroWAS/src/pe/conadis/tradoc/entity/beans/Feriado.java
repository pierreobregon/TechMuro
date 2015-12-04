package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FERIADO database table.
 * 
 */
@Entity
@Table(name="FERIADO")
@NamedQuery(name="Feriado.findAll", query="SELECT f FROM Feriado f")
public class Feriado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_FERIADO")
	private Integer codFeriado;

	@Column(name="ANIO")
	private String anio;

	@Column(name="DES_FERIADO")
	private String desFeriado;

	@Column(name="FECHA")
	private String fecha;

	@Column(name="IND_ESTADO")
	private String indEstado;

	public Feriado() {
	}

	public Integer getCodFeriado() {
		return this.codFeriado;
	}

	public void setCodFeriado(Integer codFeriado) {
		this.codFeriado = codFeriado;
	}

	public String getAnio() {
		return this.anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getDesFeriado() {
		return this.desFeriado;
	}

	public void setDesFeriado(String desFeriado) {
		this.desFeriado = desFeriado;
	}

	public String getFecha() {
		return this.fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getIndEstado() {
		return this.indEstado;
	}

	public void setIndEstado(String indEstado) {
		this.indEstado = indEstado;
	}

}