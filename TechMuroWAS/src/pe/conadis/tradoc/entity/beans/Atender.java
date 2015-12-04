package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;


/**
 * The persistent class for the ATENDER database table.
 * 
 */
@Entity
@Table(name="ATENDER")
@NamedQuery(name="Atender.findAll", query="SELECT a FROM Atender a")
public class Atender implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_ATENDER")
	private Integer codAtender;

	@Column(name="COD_USU_ATENCION")
	private String codUsuAtencion;

	@Column(name="COD_USU_CREACION")
	private String codUsuCreacion;

	@Column(name="COD_USU_MODIFICACION")
	private String codUsuModificacion;

	@Column(name="DES_ATENCION")
	private String desAtencion;

	@Column(name="FEC_ATENCION")
	private Date fecAtencion;

	@Column(name="FEC_CREACION")
	private Date fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;

	//bi-directional many-to-one association to Derivar
	@ManyToOne
	@JoinColumn(name="COD_DERIVACION")
	private Derivar derivar;

	public Atender() {
	}

	public Integer getCodAtender() {
		return this.codAtender;
	}

	public void setCodAtender(Integer codAtender) {
		this.codAtender = codAtender;
	}

	public String getCodUsuAtencion() {
		return this.codUsuAtencion;
	}

	public void setCodUsuAtencion(String codUsuAtencion) {
		this.codUsuAtencion = codUsuAtencion;
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

	public String getDesAtencion() {
		return this.desAtencion;
	}

	public void setDesAtencion(String desAtencion) {
		this.desAtencion = desAtencion;
	}

	public Date getFecAtencion() {
		return this.fecAtencion;
	}

	public void setFecAtencion(Date fecAtencion) {
		this.fecAtencion = fecAtencion;
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

	public Derivar getDerivar() {
		return this.derivar;
	}

	public void setDerivar(Derivar derivar) {
		this.derivar = derivar;
	}

}