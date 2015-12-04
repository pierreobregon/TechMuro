package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Date;
import java.util.List;


/**
 * The persistent class for the DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="DOCUMENTO")
@NamedQuery(name="Documento.findAll", query="SELECT d FROM Documento d")
public class Documento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_DOCUMENTO")
	private Integer codDocumento;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="COD_TIP_DOCUMENTO")
	private TipoDocumento tipoDocumento;

	@Column(name="COD_USU_CREACION")
	private String codUsuCreacion;

	@Column(name="COD_USU_MODIFICACION")
	private String codUsuModificacion;

	@Column(name="DES_NUM_DOCUMENTO")
	private String desNumDocumento;

	@Column(name="FEC_CREACION")
	private Date fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;

	@Column(name="IND_DOC_EXTERNO")
	private String indDocExterno;

	//bi-directional many-to-one association to AnularDocumento
	@OneToMany(mappedBy="documento")
	private List<AnularDocumento> anularDocumentos;

	//bi-directional many-to-one association to Derivar
	@OneToMany(mappedBy="documento")
	private List<Derivar> derivars;

	//bi-directional many-to-one association to ControlTipoTramite
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COD_TIPO_TRAMITE")
	private ControlTipoTramite controlTipoTramite;

	//bi-directional many-to-one association to EstadosDocumento
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COD_EST_DOCUMENTO")
	private EstadosDocumento estadosDocumento;

	//bi-directional many-to-one association to Expediente
	@ManyToOne
	@JoinColumn(name="COD_EXPEDIENTE")
	private Expediente expediente;

	//bi-directional many-to-one association to NumeracionDocumento
	@ManyToOne
	@JoinColumn(name="COD_CORRELATIVO")
	private NumeracionDocumento numeracionDocumento;

	//bi-directional many-to-one association to IncidenciaMensajeria
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "documento")
	private List<IncidenciaMensajeria> incidenciaMensajerias;

	public Documento() {
	}

	public Integer getCodDocumento() {
		return this.codDocumento;
	}

	public void setCodDocumento(Integer codDocumento) {
		this.codDocumento = codDocumento;
	}





	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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

	public String getDesNumDocumento() {
		return this.desNumDocumento;
	}

	public void setDesNumDocumento(String desNumDocumento) {
		this.desNumDocumento = desNumDocumento;
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

	public String getIndDocExterno() {
		return this.indDocExterno;
	}

	public void setIndDocExterno(String indDocExterno) {
		this.indDocExterno = indDocExterno;
	}

	public List<AnularDocumento> getAnularDocumentos() {
		return this.anularDocumentos;
	}

	public void setAnularDocumentos(List<AnularDocumento> anularDocumentos) {
		this.anularDocumentos = anularDocumentos;
	}

	public AnularDocumento addAnularDocumento(AnularDocumento anularDocumento) {
		getAnularDocumentos().add(anularDocumento);
		anularDocumento.setDocumento(this);

		return anularDocumento;
	}

	public AnularDocumento removeAnularDocumento(AnularDocumento anularDocumento) {
		getAnularDocumentos().remove(anularDocumento);
		anularDocumento.setDocumento(null);

		return anularDocumento;
	}

	public List<Derivar> getDerivars() {
		return this.derivars;
	}

	public void setDerivars(List<Derivar> derivars) {
		this.derivars = derivars;
	}

	public Derivar addDerivar(Derivar derivar) {
		getDerivars().add(derivar);
		derivar.setDocumento(this);

		return derivar;
	}

	public Derivar removeDerivar(Derivar derivar) {
		getDerivars().remove(derivar);
		derivar.setDocumento(null);

		return derivar;
	}

	public ControlTipoTramite getControlTipoTramite() {
		return this.controlTipoTramite;
	}

	public void setControlTipoTramite(ControlTipoTramite controlTipoTramite) {
		this.controlTipoTramite = controlTipoTramite;
	}

	public EstadosDocumento getEstadosDocumento() {
		return this.estadosDocumento;
	}

	public void setEstadosDocumento(EstadosDocumento estadosDocumento) {
		this.estadosDocumento = estadosDocumento;
	}

	public Expediente getExpediente() {
		return this.expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public NumeracionDocumento getNumeracionDocumento() {
		return this.numeracionDocumento;
	}

	public void setNumeracionDocumento(NumeracionDocumento numeracionDocumento) {
		this.numeracionDocumento = numeracionDocumento;
	}

	public List<IncidenciaMensajeria> getIncidenciaMensajerias() {
		return this.incidenciaMensajerias;
	}

	public void setIncidenciaMensajerias(List<IncidenciaMensajeria> incidenciaMensajerias) {
		this.incidenciaMensajerias = incidenciaMensajerias;
	}

	public IncidenciaMensajeria addIncidenciaMensajeria(IncidenciaMensajeria incidenciaMensajeria) {
		getIncidenciaMensajerias().add(incidenciaMensajeria);
		incidenciaMensajeria.setDocumento(this);

		return incidenciaMensajeria;
	}

	public IncidenciaMensajeria removeIncidenciaMensajeria(IncidenciaMensajeria incidenciaMensajeria) {
		getIncidenciaMensajerias().remove(incidenciaMensajeria);
		incidenciaMensajeria.setDocumento(null);

		return incidenciaMensajeria;
	}

}