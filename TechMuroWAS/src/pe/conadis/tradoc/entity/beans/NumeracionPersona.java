package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the NUMERACION_PERSONA database table.
 * 
 */
@Entity
@Table(name="NUMERACION_PERSONA")
@NamedQuery(name="NumeracionPersona.findAll", query="SELECT n FROM NumeracionPersona n")
public class NumeracionPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_CORRELATIVO_PER")
	private Integer codCorrelativoPer;

	@Column(name="ANIO_DOCUMENTO")
	private String anioDocumento;

	@Column(name="COD_USU_CREACION")
	private String codUsuCreacion;

	@Column(name="COD_USU_MODIFICACION")
	private String codUsuModificacion;

	@Column(name="DESCRIPCION")
	private String descripcion;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	@Column(name="IND_ESTADO")
	private String indEstado;

	@Column(name="NUM_DOCUMENTO")
	private Integer numDocumento;

	//bi-directional many-to-one association to DocumentoPersona
	@OneToMany(mappedBy="numeracionPersona")
	private List<DocumentoPersona> documentoPersonas;

	//bi-directional many-to-one association to Personal
	@ManyToOne
	@JoinColumn(name="COD_PERSONA")
	private Personal personal;

	//bi-directional many-to-one association to TipoDocumento
	@ManyToOne
	@JoinColumn(name="COD_TIP_DOCUMENTO")
	private TipoDocumento tipoDocumento;

	//bi-directional many-to-one association to UnidadOrganica
	@ManyToOne
	@JoinColumn(name="COD_UO")
	private UnidadOrganica unidadOrganica;

	public NumeracionPersona() {
	}

	public Integer getCodCorrelativoPer() {
		return this.codCorrelativoPer;
	}

	public void setCodCorrelativoPer(Integer codCorrelativoPer) {
		this.codCorrelativoPer = codCorrelativoPer;
	}

	public String getAnioDocumento() {
		return this.anioDocumento;
	}

	public void setAnioDocumento(String anioDocumento) {
		this.anioDocumento = anioDocumento;
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

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Timestamp getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
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

	public List<DocumentoPersona> getDocumentoPersonas() {
		return this.documentoPersonas;
	}

	public void setDocumentoPersonas(List<DocumentoPersona> documentoPersonas) {
		this.documentoPersonas = documentoPersonas;
	}

	public DocumentoPersona addDocumentoPersona(DocumentoPersona documentoPersona) {
		getDocumentoPersonas().add(documentoPersona);
		documentoPersona.setNumeracionPersona(this);

		return documentoPersona;
	}

	public DocumentoPersona removeDocumentoPersona(DocumentoPersona documentoPersona) {
		getDocumentoPersonas().remove(documentoPersona);
		documentoPersona.setNumeracionPersona(null);

		return documentoPersona;
	}

	public Personal getPersonal() {
		return this.personal;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
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