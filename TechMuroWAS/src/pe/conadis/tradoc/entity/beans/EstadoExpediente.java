package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ESTADO_EXPEDIENTE database table.
 * 
 */
@Entity
@Table(name="ESTADO_EXPEDIENTE")
@NamedQuery(name="EstadoExpediente.findAll", query="SELECT e FROM EstadoExpediente e")
public class EstadoExpediente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_ESTADO_EXPEDIENTE")
	private Integer codEstadoExpediente;

	@Column(name="DES_EST_EXPEDIENTE")
	private String desEstExpediente;

	//bi-directional many-to-one association to Expediente
	@OneToMany(mappedBy="estadoExpediente")
	private List<Expediente> expedientes;

	public EstadoExpediente() {
	}

	public Integer getCodEstadoExpediente() {
		return this.codEstadoExpediente;
	}

	public void setCodEstadoExpediente(Integer codEstadoExpediente) {
		this.codEstadoExpediente = codEstadoExpediente;
	}

	public String getDesEstExpediente() {
		return this.desEstExpediente;
	}

	public void setDesEstExpediente(String desEstExpediente) {
		this.desEstExpediente = desEstExpediente;
	}

	public List<Expediente> getExpedientes() {
		return this.expedientes;
	}

	public void setExpedientes(List<Expediente> expedientes) {
		this.expedientes = expedientes;
	}

	public Expediente addExpediente(Expediente expediente) {
		getExpedientes().add(expediente);
		expediente.setEstadoExpediente(this);

		return expediente;
	}

	public Expediente removeExpediente(Expediente expediente) {
		getExpedientes().remove(expediente);
		expediente.setEstadoExpediente(null);

		return expediente;
	}

}