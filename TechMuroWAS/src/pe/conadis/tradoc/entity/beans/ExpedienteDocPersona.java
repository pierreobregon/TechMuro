package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the EXPEDIENTE_DOC_PERSONA database table.
 * 
 */
@Entity
@Table(name="EXPEDIENTE_DOC_PERSONA")
@NamedQuery(name="ExpedienteDocPersona.findAll", query="SELECT e FROM ExpedienteDocPersona e")
public class ExpedienteDocPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_EXP_DOC_PERSONA")
	private Integer codExpDocPersona;

	@Column(name="COD_USU_CREACION")
	private String codUsuCreacion;

	@Column(name="COD_USU_MODIFICACION")
	private String codUsuModificacion;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	//bi-directional many-to-one association to DocumentoPersona
	@ManyToOne
	@JoinColumn(name="COD_DOC_PERSONA")
	private DocumentoPersona documentoPersona;

	//bi-directional many-to-one association to Expediente
	@ManyToOne
	@JoinColumn(name="COD_EXPEDIENTE")
	private Expediente expediente;

	public ExpedienteDocPersona() {
	}

	public Integer getCodExpDocPersona() {
		return this.codExpDocPersona;
	}

	public void setCodExpDocPersona(Integer codExpDocPersona) {
		this.codExpDocPersona = codExpDocPersona;
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

	public DocumentoPersona getDocumentoPersona() {
		return this.documentoPersona;
	}

	public void setDocumentoPersona(DocumentoPersona documentoPersona) {
		this.documentoPersona = documentoPersona;
	}

	public Expediente getExpediente() {
		return this.expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

}