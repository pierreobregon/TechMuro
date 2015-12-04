package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ESTADOS_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="ESTADOS_DOCUMENTO")
@NamedQuery(name="EstadosDocumento.findAll", query="SELECT e FROM EstadosDocumento e")
public class EstadosDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_EST_DOCUMENTO")
	private Integer codEstDocumento;

	@Column(name="DES_EST_DOCUMENTO")
	private String desEstDocumento;

	//bi-directional many-to-one association to Documento
	@OneToMany(mappedBy="estadosDocumento")
	private List<Documento> documentos;

	public EstadosDocumento() {
	}

	public Integer getCodEstDocumento() {
		return this.codEstDocumento;
	}

	public void setCodEstDocumento(Integer codEstDocumento) {
		this.codEstDocumento = codEstDocumento;
	}

	public String getDesEstDocumento() {
		return this.desEstDocumento;
	}

	public void setDesEstDocumento(String desEstDocumento) {
		this.desEstDocumento = desEstDocumento;
	}

	public List<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Documento addDocumento(Documento documento) {
		getDocumentos().add(documento);
		documento.setEstadosDocumento(this);

		return documento;
	}

	public Documento removeDocumento(Documento documento) {
		getDocumentos().remove(documento);
		documento.setEstadosDocumento(null);

		return documento;
	}

}