package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;


/**
 * The persistent class for the ANULAR_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="ANULAR_DOCUMENTO")
@NamedQuery(name="AnularDocumento.findAll", query="SELECT a FROM AnularDocumento a")
public class AnularDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_ANU_DOC")
	private Integer codAnuDoc;

	@Column(name="COD_EST_DOC_ANT_ANU")
	private String codEstDocAntAnu;

	@Column(name="COD_USU_CREACION")
	private String codUsuCreacion;

	@Column(name="COD_USU_MODIFICACION")
	private String codUsuModificacion;

	@Column(name="DES_ANULACION")
	private String desAnulacion;

	@Column(name="FEC_CREACION")
	private Date fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;

	@Column(name="IND_ANU_DOCUMENTO")
	private String indAnuDocumento;

	//bi-directional many-to-one association to AnularExpediente
	@ManyToOne
	@JoinColumn(name="COD_ANU_EXPEDIENTE")
	private AnularExpediente anularExpediente;

	//bi-directional many-to-one association to Documento
	@ManyToOne
	@JoinColumn(name="COD_DOCUMENTO")
	private Documento documento;

	//bi-directional many-to-one association to ReactivarDocumento
	@OneToMany(mappedBy="anularDocumento")
	private List<ReactivarDocumento> reactivarDocumentos;

	public AnularDocumento() {
	}

	public Integer getCodAnuDoc() {
		return this.codAnuDoc;
	}

	public void setCodAnuDoc(Integer codAnuDoc) {
		this.codAnuDoc = codAnuDoc;
	}

	public String getCodEstDocAntAnu() {
		return this.codEstDocAntAnu;
	}

	public void setCodEstDocAntAnu(String codEstDocAntAnu) {
		this.codEstDocAntAnu = codEstDocAntAnu;
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

	public String getDesAnulacion() {
		return this.desAnulacion;
	}

	public void setDesAnulacion(String desAnulacion) {
		this.desAnulacion = desAnulacion;
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

	public String getIndAnuDocumento() {
		return this.indAnuDocumento;
	}

	public void setIndAnuDocumento(String indAnuDocumento) {
		this.indAnuDocumento = indAnuDocumento;
	}

	public AnularExpediente getAnularExpediente() {
		return this.anularExpediente;
	}

	public void setAnularExpediente(AnularExpediente anularExpediente) {
		this.anularExpediente = anularExpediente;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public List<ReactivarDocumento> getReactivarDocumentos() {
		return this.reactivarDocumentos;
	}

	public void setReactivarDocumentos(List<ReactivarDocumento> reactivarDocumentos) {
		this.reactivarDocumentos = reactivarDocumentos;
	}

	public ReactivarDocumento addReactivarDocumento(ReactivarDocumento reactivarDocumento) {
		getReactivarDocumentos().add(reactivarDocumento);
		reactivarDocumento.setAnularDocumento(this);

		return reactivarDocumento;
	}

	public ReactivarDocumento removeReactivarDocumento(ReactivarDocumento reactivarDocumento) {
		getReactivarDocumentos().remove(reactivarDocumento);
		reactivarDocumento.setAnularDocumento(null);

		return reactivarDocumento;
	}

}