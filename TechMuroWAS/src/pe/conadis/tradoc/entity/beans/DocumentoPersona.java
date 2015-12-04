package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;


/**
 * The persistent class for the DOCUMENTO_PERSONA database table.
 * 
 */
@Entity
@Table(name="DOCUMENTO_PERSONA")
@NamedQuery(name="DocumentoPersona.findAll", query="SELECT d FROM DocumentoPersona d")
public class DocumentoPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_DOC_PERSONA")
	private Integer codDocPersona;

	@Column(name="COD_USU_CREACION")
	private String codUsuCreacion;

	@Column(name="COD_USU_MODIFICACION")
	private String codUsuModificacion;

	@Column(name="DES_DOCUMENTO")
	private String desDocumento;

	@Column(name="FEC_CREACION")
	private Date fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;

	@Column(name="ind_est_doc")
	private String indEstDoc;

	//bi-directional many-to-one association to NumeracionPersona
	@ManyToOne
	@JoinColumn(name="COD_CORRELATIVO_PER")
	private NumeracionPersona numeracionPersona;

	//bi-directional many-to-one association to ExpedienteDocPersona
	@OneToMany(mappedBy="documentoPersona")
	private List<ExpedienteDocPersona> expedienteDocPersonas;

	public DocumentoPersona() {
	}

	public Integer getCodDocPersona() {
		return this.codDocPersona;
	}

	public void setCodDocPersona(Integer codDocPersona) {
		this.codDocPersona = codDocPersona;
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

	public String getDesDocumento() {
		return this.desDocumento;
	}

	public void setDesDocumento(String desDocumento) {
		this.desDocumento = desDocumento;
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

	public String getIndEstDoc() {
		return this.indEstDoc;
	}

	public void setIndEstDoc(String indEstDoc) {
		this.indEstDoc = indEstDoc;
	}

	public NumeracionPersona getNumeracionPersona() {
		return this.numeracionPersona;
	}

	public void setNumeracionPersona(NumeracionPersona numeracionPersona) {
		this.numeracionPersona = numeracionPersona;
	}

	public List<ExpedienteDocPersona> getExpedienteDocPersonas() {
		return this.expedienteDocPersonas;
	}

	public void setExpedienteDocPersonas(List<ExpedienteDocPersona> expedienteDocPersonas) {
		this.expedienteDocPersonas = expedienteDocPersonas;
	}

	public ExpedienteDocPersona addExpedienteDocPersona(ExpedienteDocPersona expedienteDocPersona) {
		getExpedienteDocPersonas().add(expedienteDocPersona);
		expedienteDocPersona.setDocumentoPersona(this);

		return expedienteDocPersona;
	}

	public ExpedienteDocPersona removeExpedienteDocPersona(ExpedienteDocPersona expedienteDocPersona) {
		getExpedienteDocPersonas().remove(expedienteDocPersona);
		expedienteDocPersona.setDocumentoPersona(null);

		return expedienteDocPersona;
	}

}