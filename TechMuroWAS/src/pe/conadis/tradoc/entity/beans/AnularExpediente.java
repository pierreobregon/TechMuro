package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;


/**
 * The persistent class for the ANULAR_EXPEDIENTE database table.
 * 
 */
@Entity
@Table(name="ANULAR_EXPEDIENTE")
@NamedQuery(name="AnularExpediente.findAll", query="SELECT a FROM AnularExpediente a")
public class AnularExpediente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_ANU_EXPEDIENTE")
	private Integer codAnuExpediente;

	@Column(name="COD_EST_EXP_ANT_ANU")
	private String codEstExpAntAnu;

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

	//bi-directional many-to-one association to AnularDocumento
	@OneToMany(mappedBy="anularExpediente")
	private List<AnularDocumento> anularDocumentos;

	//bi-directional many-to-one association to Expediente
	@ManyToOne
	@JoinColumn(name="COD_EXPEDIENTE")
	private Expediente expediente;

	//bi-directional many-to-one association to ReactivarExpediente
	@OneToMany(mappedBy="anularExpediente")
	private List<ReactivarExpediente> reactivarExpedientes;

	public AnularExpediente() {
	}

	public Integer getCodAnuExpediente() {
		return this.codAnuExpediente;
	}

	public void setCodAnuExpediente(Integer codAnuExpediente) {
		this.codAnuExpediente = codAnuExpediente;
	}

	public String getCodEstExpAntAnu() {
		return this.codEstExpAntAnu;
	}

	public void setCodEstExpAntAnu(String codEstExpAntAnu) {
		this.codEstExpAntAnu = codEstExpAntAnu;
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

	public List<AnularDocumento> getAnularDocumentos() {
		return this.anularDocumentos;
	}

	public void setAnularDocumentos(List<AnularDocumento> anularDocumentos) {
		this.anularDocumentos = anularDocumentos;
	}

	public AnularDocumento addAnularDocumento(AnularDocumento anularDocumento) {
		getAnularDocumentos().add(anularDocumento);
		anularDocumento.setAnularExpediente(this);

		return anularDocumento;
	}

	public AnularDocumento removeAnularDocumento(AnularDocumento anularDocumento) {
		getAnularDocumentos().remove(anularDocumento);
		anularDocumento.setAnularExpediente(null);

		return anularDocumento;
	}

	public Expediente getExpediente() {
		return this.expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public List<ReactivarExpediente> getReactivarExpedientes() {
		return this.reactivarExpedientes;
	}

	public void setReactivarExpedientes(List<ReactivarExpediente> reactivarExpedientes) {
		this.reactivarExpedientes = reactivarExpedientes;
	}

	public ReactivarExpediente addReactivarExpediente(ReactivarExpediente reactivarExpediente) {
		getReactivarExpedientes().add(reactivarExpediente);
		reactivarExpediente.setAnularExpediente(this);

		return reactivarExpediente;
	}

	public ReactivarExpediente removeReactivarExpediente(ReactivarExpediente reactivarExpediente) {
		getReactivarExpedientes().remove(reactivarExpediente);
		reactivarExpediente.setAnularExpediente(null);

		return reactivarExpediente;
	}

}