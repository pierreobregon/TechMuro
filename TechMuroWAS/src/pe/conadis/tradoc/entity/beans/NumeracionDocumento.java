package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Date;
import java.util.List;


/**
 * The persistent class for the NUMERACION_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="NUMERACION_DOCUMENTO")
@NamedQuery(name="NumeracionDocumento.findAll", query="SELECT n FROM NumeracionDocumento n")
public class NumeracionDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_CORRELATIVO")
	private Integer codCorrelativo;

	@Column(name="ANIO_DOCUMENTO")
	private String anioDocumento;

	@Column(name="COD_USU_ANULACION")
	private String codUsuAnulacion;

	@Column(name="COD_USU_CREACION")
	private String codUsuCreacion;

	@Column(name="COD_USU_MODIFICACION")
	private String codUsuModificacion;

	@Column(name="COD_USU_RECTIVACION")
	private String codUsuRectivacion;

	@Column(name="DES_ANULACION")
	private String desAnulacion;

	@Column(name="DES_REACTIVACION")
	private String desReactivacion;

	@Column(name="FEC_ANULACION")
	private Date fecAnulacion;

	@Column(name="FEC_CREACION")
	private Date fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;

	@Column(name="FEC_REACTIVACION")
	private Date fecReactivacion;

	@Column(name="IND_ESTADO")
	private String indEstado;

	@Column(name="NUM_DOCUMENTO")
	private Integer numDocumento;

	@Column(name="IND_EXPEDIENTE")
	private String indExpediente;

	public String getIndExpediente() {
		return indExpediente;
	}

	public void setIndExpediente(String indExpediente) {
		this.indExpediente = indExpediente;
	}

	//bi-directional many-to-one association to Documento
	@OneToMany(mappedBy="numeracionDocumento")
	private List<Documento> documentos;

	//bi-directional many-to-one association to Expediente
	@OneToMany(mappedBy="numeracionDocumento")
	private List<Expediente> expedientes;

	//bi-directional many-to-one association to TipoDocumento
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COD_TIP_DOCUMENTO")
	private TipoDocumento tipoDocumento;

	//bi-directional many-to-one association to UnidadOrganica
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COD_UO")
	private UnidadOrganica unidadOrganica;

	public NumeracionDocumento() {
	}

	public Integer getCodCorrelativo() {
		return this.codCorrelativo;
	}

	public void setCodCorrelativo(Integer codCorrelativo) {
		this.codCorrelativo = codCorrelativo;
	}

	public String getAnioDocumento() {
		return this.anioDocumento;
	}

	public void setAnioDocumento(String anioDocumento) {
		this.anioDocumento = anioDocumento;
	}

	public String getCodUsuAnulacion() {
		return this.codUsuAnulacion;
	}

	public void setCodUsuAnulacion(String codUsuAnulacion) {
		this.codUsuAnulacion = codUsuAnulacion;
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

	public String getCodUsuRectivacion() {
		return this.codUsuRectivacion;
	}

	public void setCodUsuRectivacion(String codUsuRectivacion) {
		this.codUsuRectivacion = codUsuRectivacion;
	}

	public String getDesAnulacion() {
		return this.desAnulacion;
	}

	public void setDesAnulacion(String desAnulacion) {
		this.desAnulacion = desAnulacion;
	}

	public String getDesReactivacion() {
		return this.desReactivacion;
	}

	public void setDesReactivacion(String desReactivacion) {
		this.desReactivacion = desReactivacion;
	}

	public Date getFecAnulacion() {
		return this.fecAnulacion;
	}

	public void setFecAnulacion(Date fecAnulacion) {
		this.fecAnulacion = fecAnulacion;
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

	public Date getFecReactivacion() {
		return this.fecReactivacion;
	}

	public void setFecReactivacion(Date fecReactivacion) {
		this.fecReactivacion = fecReactivacion;
	}

	public String getIndEstado() {
		return this.indEstado;
	}

	public void setIndEstado(String indEstado) {
		this.indEstado = indEstado;
	}

	public Integer getNumDocumento() {
		return this.numDocumento;
	}

	public void setNumDocumento(Integer numDocumento) {
		this.numDocumento = numDocumento;
	}

	public List<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Documento addDocumento(Documento documento) {
		getDocumentos().add(documento);
		documento.setNumeracionDocumento(this);

		return documento;
	}

	public Documento removeDocumento(Documento documento) {
		getDocumentos().remove(documento);
		documento.setNumeracionDocumento(null);

		return documento;
	}

	public List<Expediente> getExpedientes() {
		return this.expedientes;
	}

	public void setExpedientes(List<Expediente> expedientes) {
		this.expedientes = expedientes;
	}

	public Expediente addExpediente(Expediente expediente) {
		getExpedientes().add(expediente);
		expediente.setNumeracionDocumento(this);

		return expediente;
	}

	public Expediente removeExpediente(Expediente expediente) {
		getExpedientes().remove(expediente);
		expediente.setNumeracionDocumento(null);

		return expediente;
	}

	public TipoDocumento getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public UnidadOrganica getUnidadOrganica() {
		return this.unidadOrganica;
	}

	public void setUnidadOrganica(UnidadOrganica unidadOrganica) {
		this.unidadOrganica = unidadOrganica;
	}

}