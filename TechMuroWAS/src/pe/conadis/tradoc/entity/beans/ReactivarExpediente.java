package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;


/**
 * The persistent class for the REACTIVAR_EXPEDIENTE database table.
 * 
 */
@Entity
@Table(name="REACTIVAR_EXPEDIENTE")
@NamedQuery(name="ReactivarExpediente.findAll", query="SELECT r FROM ReactivarExpediente r")
public class ReactivarExpediente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_REA_EXPEDIENTE")
	private Integer codReaExpediente;

	@Column(name="COD_USU_CREACION")
	private String codUsuCreacion;

	@Column(name="COD_USU_MODIFICACION")
	private String codUsuModificacion;

	@Column(name="DES_SUSTENTO")
	private String desSustento;

	@Column(name="FEC_CREACION")
	private Date fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;

	//bi-directional many-to-one association to AnularExpediente
	@ManyToOne
	@JoinColumn(name="COD_ANU_EXPEDIENTE")
	private AnularExpediente anularExpediente;

	public ReactivarExpediente() {
	}

	public Integer getCodReaExpediente() {
		return this.codReaExpediente;
	}

	public void setCodReaExpediente(Integer codReaExpediente) {
		this.codReaExpediente = codReaExpediente;
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

	public String getDesSustento() {
		return this.desSustento;
	}

	public void setDesSustento(String desSustento) {
		this.desSustento = desSustento;
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

	public AnularExpediente getAnularExpediente() {
		return this.anularExpediente;
	}

	public void setAnularExpediente(AnularExpediente anularExpediente) {
		this.anularExpediente = anularExpediente;
	}

}