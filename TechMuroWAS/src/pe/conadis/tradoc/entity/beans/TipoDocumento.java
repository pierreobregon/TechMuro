package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TIPO_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="TIPO_DOCUMENTO")
@NamedQuery(name="TipoDocumento.findAll", query="SELECT t FROM TipoDocumento t")
public class TipoDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_TIP_DOCUMENTO")
	private Integer codTipDocumento;

	@Column(name="DES_TIP_DOCUMENTO")
	private String desTipDocumento;

	@Column(name="IND_DOCUMENTO")
	private String indDocumento;

	@Column(name="IND_ESTADO")
	private String indEstado;

	//bi-directional many-to-one association to NumeracionDocumento
	@OneToMany(mappedBy="tipoDocumento")
	private List<NumeracionDocumento> numeracionDocumentos;

	//bi-directional many-to-one association to NumeracionPersona
	@OneToMany(mappedBy="tipoDocumento")
	private List<NumeracionPersona> numeracionPersonas;

	public TipoDocumento() {
	}

	public Integer getCodTipDocumento() {
		return this.codTipDocumento;
	}

	public void setCodTipDocumento(Integer codTipDocumento) {
		this.codTipDocumento = codTipDocumento;
	}

	public String getDesTipDocumento() {
		return this.desTipDocumento;
	}

	public void setDesTipDocumento(String desTipDocumento) {
		this.desTipDocumento = desTipDocumento;
	}

	public String getIndDocumento() {
		return this.indDocumento;
	}

	public void setIndDocumento(String indDocumento) {
		this.indDocumento = indDocumento;
	}

	public String getIndEstado() {
		return this.indEstado;
	}

	public void setIndEstado(String indEstado) {
		this.indEstado = indEstado;
	}

	public List<NumeracionDocumento> getNumeracionDocumentos() {
		return this.numeracionDocumentos;
	}

	public void setNumeracionDocumentos(List<NumeracionDocumento> numeracionDocumentos) {
		this.numeracionDocumentos = numeracionDocumentos;
	}

	public NumeracionDocumento addNumeracionDocumento(NumeracionDocumento numeracionDocumento) {
		getNumeracionDocumentos().add(numeracionDocumento);
		numeracionDocumento.setTipoDocumento(this);

		return numeracionDocumento;
	}

	public NumeracionDocumento removeNumeracionDocumento(NumeracionDocumento numeracionDocumento) {
		getNumeracionDocumentos().remove(numeracionDocumento);
		numeracionDocumento.setTipoDocumento(null);

		return numeracionDocumento;
	}

	public List<NumeracionPersona> getNumeracionPersonas() {
		return this.numeracionPersonas;
	}

	public void setNumeracionPersonas(List<NumeracionPersona> numeracionPersonas) {
		this.numeracionPersonas = numeracionPersonas;
	}

	public NumeracionPersona addNumeracionPersona(NumeracionPersona numeracionPersona) {
		getNumeracionPersonas().add(numeracionPersona);
		numeracionPersona.setTipoDocumento(this);

		return numeracionPersona;
	}

	public NumeracionPersona removeNumeracionPersona(NumeracionPersona numeracionPersona) {
		getNumeracionPersonas().remove(numeracionPersona);
		numeracionPersona.setTipoDocumento(null);

		return numeracionPersona;
	}

}