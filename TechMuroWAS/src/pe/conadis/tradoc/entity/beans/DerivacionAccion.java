package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;


/**
 * The persistent class for the DERIVACION_ACCION database table.
 * 
 */
@Entity
@Table(name="DERIVACION_ACCION")
@NamedQuery(name="DerivacionAccion.findAll", query="SELECT d FROM DerivacionAccion d")
public class DerivacionAccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_DER_ACCION")
	private Integer codDerAccion;

	@Column(name="COD_USU_CREACION")
	private String codUsuCreacion;

	@Column(name="COD_USU_MODIFICACION")
	private String codUsuModificacion;

	@Column(name="FEC_CREACION")
	private Date fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;

	//bi-directional many-to-one association to Accion
	@ManyToOne
	@JoinColumn(name="COD_ACCION")
	private Accion accion;

	//bi-directional many-to-one association to Derivar
	@ManyToOne
	@JoinColumn(name="COD_DERIVACION")
	private Derivar derivar;

	public DerivacionAccion() {
	}

	public Integer getCodDerAccion() {
		return this.codDerAccion;
	}

	public void setCodDerAccion(Integer codDerAccion) {
		this.codDerAccion = codDerAccion;
	}

	public String getCodUsuCreacion() {
		return this.codUsuCreacion;
	}

	public void setCodUsuCreacion(String codUsuCreacion) {
		this.codUsuCreacion = codUsuCreacion;
	}

	public String getCodUsuModificacion() {
		return this.codUsuModificacion;
	}

	public void setCodUsuModificacion(String codUsuModificacion) {
		this.codUsuModificacion = codUsuModificacion;
	}

	public Date getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Date fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Date getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Date fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public Accion getAccion() {
		return this.accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}

	public Derivar getDerivar() {
		return this.derivar;
	}

	public void setDerivar(Derivar derivar) {
		this.derivar = derivar;
	}

}