package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;


/**
 * The persistent class for the DEVOLUCION database table.
 * 
 */
@Entity
@Table(name="DEVOLUCION")
@NamedQuery(name="Devolucion.findAll", query="SELECT d FROM Devolucion d")
public class Devolucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_DEVOLUCION")
	private Integer codDevolucion;

	@Column(name="COD_USU_CREACION")
	private String codUsuCreacion;

	@Column(name="COD_USU_DEVOLUCION")
	private String codUsuDevolucion;

	@Column(name="COD_USU_MODIFICACION")
	private String codUsuModificacion;

	@Column(name="DES_DEVOLUCION")
	private String desDevolucion;

	@Column(name="FEC_CREACION")
	private Date fecCreacion;

	@Column(name="FEC_DEVOLUCION")
	private Date fecDevolucion;

	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;

	//bi-directional many-to-one association to Derivar
	@ManyToOne
	@JoinColumn(name="COD_DERIVACION")
	private Derivar derivar;

	public Devolucion() {
	}

	public Integer getCodDevolucion() {
		return this.codDevolucion;
	}

	public void setCodDevolucion(Integer codDevolucion) {
		this.codDevolucion = codDevolucion;
	}

	public String getCodUsuCreacion() {
		return this.codUsuCreacion;
	}

	public void setCodUsuCreacion(String codUsuCreacion) {
		this.codUsuCreacion = codUsuCreacion;
	}

	public String getCodUsuDevolucion() {
		return this.codUsuDevolucion;
	}

	public void setCodUsuDevolucion(String codUsuDevolucion) {
		this.codUsuDevolucion = codUsuDevolucion;
	}

	public String getCodUsuModificacion() {
		return this.codUsuModificacion;
	}

	public void setCodUsuModificacion(String codUsuModificacion) {
		this.codUsuModificacion = codUsuModificacion;
	}

	public String getDesDevolucion() {
		return this.desDevolucion;
	}

	public void setDesDevolucion(String desDevolucion) {
		this.desDevolucion = desDevolucion;
	}

	public Date getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Date fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Date getFecDevolucion() {
		return this.fecDevolucion;
	}

	public void setFecDevolucion(Date fecDevolucion) {
		this.fecDevolucion = fecDevolucion;
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