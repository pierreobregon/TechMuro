package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ACCION database table.
 * 
 */
@Entity
@Table(name="ACCION")
@NamedQuery(name="Accion.findAll", query="SELECT a FROM Accion a")
public class Accion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_ACCION")
	private Integer codAccion;

	@Column(name="DES_ACCION")
	private String desAccion;

	@Column(name="IND_ESTADO")
	private String indEstado;

	//bi-directional many-to-one association to DerivacionAccion
	@OneToMany(mappedBy="accion")
	private List<DerivacionAccion> derivacionAccions;

	public Accion() {
	}

	public Integer getCodAccion() {
		return this.codAccion;
	}

	public void setCodAccion(Integer codAccion) {
		this.codAccion = codAccion;
	}

	public String getDesAccion() {
		return this.desAccion;
	}

	public void setDesAccion(String desAccion) {
		this.desAccion = desAccion;
	}

	public String getIndEstado() {
		return this.indEstado;
	}

	public void setIndEstado(String indEstado) {
		this.indEstado = indEstado;
	}

	public List<DerivacionAccion> getDerivacionAccions() {
		return this.derivacionAccions;
	}

	public void setDerivacionAccions(List<DerivacionAccion> derivacionAccions) {
		this.derivacionAccions = derivacionAccions;
	}

	public DerivacionAccion addDerivacionAccion(DerivacionAccion derivacionAccion) {
		getDerivacionAccions().add(derivacionAccion);
		derivacionAccion.setAccion(this);

		return derivacionAccion;
	}

	public DerivacionAccion removeDerivacionAccion(DerivacionAccion derivacionAccion) {
		getDerivacionAccions().remove(derivacionAccion);
		derivacionAccion.setAccion(null);

		return derivacionAccion;
	}

}