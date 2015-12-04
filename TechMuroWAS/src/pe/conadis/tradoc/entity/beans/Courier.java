package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the COURIER database table.
 * 
 */
@Entity
@Table(name="COURIER")
@NamedQuery(name="Courier.findAll", query="SELECT c FROM Courier c")
public class Courier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_COURIER")
	private Integer codCourier;

	@Column(name="DES_COURIER")
	private String desCourier;

	@Column(name="INS_ESTADO")
	private String insEstado;

	//bi-directional many-to-one association to IncidenciaMensajeria
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "courier")
	private List<IncidenciaMensajeria> incidenciaMensajerias;

	public Courier() {
	}

	public Integer getCodCourier() {
		return this.codCourier;
	}

	public void setCodCourier(Integer codCourier) {
		this.codCourier = codCourier;
	}

	public String getDesCourier() {
		return this.desCourier;
	}

	public void setDesCourier(String desCourier) {
		this.desCourier = desCourier;
	}

	public String getInsEstado() {
		return this.insEstado;
	}

	public void setInsEstado(String insEstado) {
		this.insEstado = insEstado;
	}

	public List<IncidenciaMensajeria> getIncidenciaMensajerias() {
		return this.incidenciaMensajerias;
	}

	public void setIncidenciaMensajerias(List<IncidenciaMensajeria> incidenciaMensajerias) {
		this.incidenciaMensajerias = incidenciaMensajerias;
	}

	public IncidenciaMensajeria addIncidenciaMensajeria(IncidenciaMensajeria incidenciaMensajeria) {
		getIncidenciaMensajerias().add(incidenciaMensajeria);
		incidenciaMensajeria.setCourier(this);

		return incidenciaMensajeria;
	}

	public IncidenciaMensajeria removeIncidenciaMensajeria(IncidenciaMensajeria incidenciaMensajeria) {
		getIncidenciaMensajerias().remove(incidenciaMensajeria);
		incidenciaMensajeria.setCourier(null);

		return incidenciaMensajeria;
	}

}