package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the EXPEDIENTE database table.
 * 
 */
@Entity
@Table(name="EXPEDIENTE")
@NamedQuery(name="Expediente.findAll", query="SELECT e FROM Expediente e")
public class Expediente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_EXPEDIENTE")
	private Integer codExpediente;

	@Column(name="CAN_DIAS")
	private Integer canDias;

	@Column(name="COD_USU_CREACION")
	private String codUsuCreacion;

	@Column(name="COD_USU_MODIFICACION")
	private String codUsuModificacion;

	@Column(name="DES_ASUNTO")
	private String desAsunto;

	@Column(name="DES_DOCUMENTO")
	private String desDocumento;

	@Column(name="DES_UTD")
	private String desUtd;

	@Column(name="DESCRIPCION")
	private String descripcion;

	@Column(name="DETALLE")
	private String detalle;

	@Column(name="FEC_CREACION")
	private Date fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;


	@Column(name="INTERESADO")
	private String interesado;

	@Column(name="NUM_FOLIOS")
	private Integer numFolios;

	//bi-directional many-to-one association to AnularExpediente
	@OneToMany(mappedBy="expediente")
	private List<AnularExpediente> anularExpedientes;

	//bi-directional many-to-one association to Documento
	@OneToMany(mappedBy="expediente")
	private List<Documento> documentos;

	//bi-directional many-to-one association to EntidadExterna
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COD_ENTIDAD")
	private EntidadExterna entidadExterna;

	//bi-directional many-to-one association to EstadoExpediente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COD_ESTADO_EXPEDIENTE")
	private EstadoExpediente estadoExpediente;

	//bi-directional many-to-one association to NumeracionDocumento
	@ManyToOne
	@JoinColumn(name="COD_CORRELATIVO")
	private NumeracionDocumento numeracionDocumento;

	//bi-directional many-to-one association to ExpedienteDocPersona
	@OneToMany(mappedBy="expediente")
	private List<ExpedienteDocPersona> expedienteDocPersonas;

	public Expediente() {
	}

	public Integer getCodExpediente() {
		return this.codExpediente;
	}

	public void setCodExpediente(Integer codExpediente) {
		this.codExpediente = codExpediente;
	}

	public Integer getCanDias() {
		return this.canDias;
	}

	public void setCanDias(Integer canDias) {
		this.canDias = canDias;
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

	public String getDesAsunto() {
		return this.desAsunto;
	}

	public void setDesAsunto(String desAsunto) {
		this.desAsunto = desAsunto;
	}

	public String getDesDocumento() {
		return this.desDocumento;
	}

	public void setDesDocumento(String desDocumento) {
		this.desDocumento = desDocumento;
	}

	public String getDesUtd() {
		return this.desUtd;
	}

	public void setDesUtd(String desUtd) {
		this.desUtd = desUtd;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
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


	public String getInteresado() {
		return this.interesado;
	}

	public void setInteresado(String interesado) {
		this.interesado = interesado;
	}

	public Integer getNumFolios() {
		return this.numFolios;
	}

	public void setNumFolios(Integer numFolios) {
		this.numFolios = numFolios;
	}

	public List<AnularExpediente> getAnularExpedientes() {
		return this.anularExpedientes;
	}

	public void setAnularExpedientes(List<AnularExpediente> anularExpedientes) {
		this.anularExpedientes = anularExpedientes;
	}

	public AnularExpediente addAnularExpediente(AnularExpediente anularExpediente) {
		getAnularExpedientes().add(anularExpediente);
		anularExpediente.setExpediente(this);

		return anularExpediente;
	}

	public AnularExpediente removeAnularExpediente(AnularExpediente anularExpediente) {
		getAnularExpedientes().remove(anularExpediente);
		anularExpediente.setExpediente(null);

		return anularExpediente;
	}

	public List<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Documento addDocumento(Documento documento) {
		getDocumentos().add(documento);
		documento.setExpediente(this);

		return documento;
	}

	public Documento removeDocumento(Documento documento) {
		getDocumentos().remove(documento);
		documento.setExpediente(null);

		return documento;
	}

	public EntidadExterna getEntidadExterna() {
		return this.entidadExterna;
	}

	public void setEntidadExterna(EntidadExterna entidadExterna) {
		this.entidadExterna = entidadExterna;
	}

	public EstadoExpediente getEstadoExpediente() {
		return this.estadoExpediente;
	}

	public void setEstadoExpediente(EstadoExpediente estadoExpediente) {
		this.estadoExpediente = estadoExpediente;
	}

	public NumeracionDocumento getNumeracionDocumento() {
		return this.numeracionDocumento;
	}

	public void setNumeracionDocumento(NumeracionDocumento numeracionDocumento) {
		this.numeracionDocumento = numeracionDocumento;
	}

	public List<ExpedienteDocPersona> getExpedienteDocPersonas() {
		return this.expedienteDocPersonas;
	}

	public void setExpedienteDocPersonas(List<ExpedienteDocPersona> expedienteDocPersonas) {
		this.expedienteDocPersonas = expedienteDocPersonas;
	}

	public ExpedienteDocPersona addExpedienteDocPersona(ExpedienteDocPersona expedienteDocPersona) {
		getExpedienteDocPersonas().add(expedienteDocPersona);
		expedienteDocPersona.setExpediente(this);

		return expedienteDocPersona;
	}

	public ExpedienteDocPersona removeExpedienteDocPersona(ExpedienteDocPersona expedienteDocPersona) {
		getExpedienteDocPersonas().remove(expedienteDocPersona);
		expedienteDocPersona.setExpediente(null);

		return expedienteDocPersona;
	}

}