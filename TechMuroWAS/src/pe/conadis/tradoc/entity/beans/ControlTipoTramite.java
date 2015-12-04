package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CONTROL_TIPO_TRAMITE database table.
 * 
 */
@Entity
@Table(name="CONTROL_TIPO_TRAMITE")
@NamedQuery(name="ControlTipoTramite.findAll", query="SELECT c FROM ControlTipoTramite c")
public class ControlTipoTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_TIPO_TRAMITE")
	private Integer codTipoTramite;

	@Column(name="DES_TIPO_TRAMITE")
	private String desTipoTramite;

	@Column(name="DIAS_TRAMITE")
	private Integer diasTramite;

	//bi-directional many-to-one association to Documento
	@OneToMany(mappedBy="controlTipoTramite")
	private List<Documento> documentos;

	public ControlTipoTramite() {
	}

	public Integer getCodTipoTramite() {
		return this.codTipoTramite;
	}

	public void setCodTipoTramite(Integer codTipoTramite) {
		this.codTipoTramite = codTipoTramite;
	}

	public String getDesTipoTramite() {
		return this.desTipoTramite;
	}

	public void setDesTipoTramite(String desTipoTramite) {
		this.desTipoTramite = desTipoTramite;
	}

	public Integer getDiasTramite() {
		return this.diasTramite;
	}

	public void setDiasTramite(Integer diasTramite) {
		this.diasTramite = diasTramite;
	}

	public List<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Documento addDocumento(Documento documento) {
		getDocumentos().add(documento);
		documento.setControlTipoTramite(this);

		return documento;
	}

	public Documento removeDocumento(Documento documento) {
		getDocumentos().remove(documento);
		documento.setControlTipoTramite(null);

		return documento;
	}

}