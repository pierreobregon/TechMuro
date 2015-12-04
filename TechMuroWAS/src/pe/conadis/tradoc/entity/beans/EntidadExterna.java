package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ENTIDAD_EXTERNA database table.
 * 
 */
@Entity
@Table(name="ENTIDAD_EXTERNA")
@NamedQuery(name="EntidadExterna.findAll", query="SELECT e FROM EntidadExterna e")
public class EntidadExterna implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_ENTIDAD")
	private Integer codEntidad;

	@Column(name="DES_ENTIDAD")
	private String desEntidad;

	@Column(name="IND_ESTADO")
	private String indEstado;

	//bi-directional many-to-one association to Expediente
	@OneToMany(mappedBy="entidadExterna")
	private List<Expediente> expedientes;

	public EntidadExterna() {
	}

	public Integer getCodEntidad() {
		return this.codEntidad;
	}

	public void setCodEntidad(Integer codEntidad) {
		this.codEntidad = codEntidad;
	}

	public String getDesEntidad() {
		return this.desEntidad;
	}

	public void setDesEntidad(String desEntidad) {
		this.desEntidad = desEntidad;
	}

	public String getIndEstado() {
		return this.indEstado;
	}

	public void setIndEstado(String indEstado) {
		this.indEstado = indEstado;
	}

	public List<Expediente> getExpedientes() {
		return this.expedientes;
	}

	public void setExpedientes(List<Expediente> expedientes) {
		this.expedientes = expedientes;
	}

	public Expediente addExpediente(Expediente expediente) {
		getExpedientes().add(expediente);
		expediente.setEntidadExterna(this);

		return expediente;
	}

	public Expediente removeExpediente(Expediente expediente) {
		getExpedientes().remove(expediente);
		expediente.setEntidadExterna(null);

		return expediente;
	}

}