package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FLUJO_COMUNICACION_UO database table.
 * 
 */
@Entity
@Table(name="FLUJO_COMUNICACION_UO")
@NamedQuery(name="FlujoComunicacionUo.findAll", query="SELECT f FROM FlujoComunicacionUo f")
public class FlujoComunicacionUo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_FLU_COMUNICACION")
	private Integer codFluComunicacion;

	@Column(name="COD_UO_DESTINO")
	private Integer codUoDestino;

	@Column(name="COD_UO_ORIGEN")
	private Integer codUoOrigen;

	@Column(name="IND_ESTADO")
	private String indEstado;

	public FlujoComunicacionUo() {
	}

	public Integer getCodFluComunicacion() {
		return this.codFluComunicacion;
	}

	public void setCodFluComunicacion(Integer codFluComunicacion) {
		this.codFluComunicacion = codFluComunicacion;
	}

	public Integer getCodUoDestino() {
		return this.codUoDestino;
	}

	public void setCodUoDestino(Integer codUoDestino) {
		this.codUoDestino = codUoDestino;
	}

	public Integer getCodUoOrigen() {
		return this.codUoOrigen;
	}

	public void setCodUoOrigen(Integer codUoOrigen) {
		this.codUoOrigen = codUoOrigen;
	}

	public String getIndEstado() {
		return this.indEstado;
	}

	public void setIndEstado(String indEstado) {
		this.indEstado = indEstado;
	}

}