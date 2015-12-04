package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Date;


/**
 * The persistent class for the INCIDENCIA_MENSAJERIA database table.
 * 
 */
@Entity
@Table(name="INCIDENCIA_MENSAJERIA")
@NamedQuery(name="IncidenciaMensajeria.findAll", query="SELECT i FROM IncidenciaMensajeria i")
public class IncidenciaMensajeria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_INC_MENSAJERIA")
	private Integer codIncMensajeria;

	@Column(name="COD_USU_CREACION")
	private String codUsuCreacion;

	@Column(name="COD_USU_MODIFICACION")
	private String codUsuModificacion;

	@Column(name="DES_NOM_MENSAJRO")
	private String desNomMensajro;

	@Column(name="DES_OBSERVACION")
	private String desObservacion;

	@Column(name="FEC_CREACION")
	private Date fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;

	@Column(name="IND_EST_MENSAJERIA")
	private String indEstMensajeria;

	//bi-directional many-to-one association to Courier	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_COURIER")
	private Courier courier;

	//bi-directional many-to-one association to Documento
	@ManyToOne
	@JoinColumn(name="COD_DOCUMENTO")
	private Documento documento;

	public IncidenciaMensajeria() {
	}

	public Integer getCodIncMensajeria() {
		return this.codIncMensajeria;
	}

	public void setCodIncMensajeria(Integer codIncMensajeria) {
		this.codIncMensajeria = codIncMensajeria;
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

	public String getDesNomMensajro() {
		return this.desNomMensajro;
	}

	public void setDesNomMensajro(String desNomMensajro) {
		this.desNomMensajro = desNomMensajro;
	}

	public String getDesObservacion() {
		return this.desObservacion;
	}

	public void setDesObservacion(String desObservacion) {
		this.desObservacion = desObservacion;
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

	public String getIndEstMensajeria() {
		return this.indEstMensajeria;
	}

	public void setIndEstMensajeria(String indEstMensajeria) {
		this.indEstMensajeria = indEstMensajeria;
	}

	public Courier getCourier() {
		return this.courier;
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

}