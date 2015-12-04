package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the UNIDAD_ORGANICA database table.
 * 
 */
@Entity
@Table(name="UNIDAD_ORGANICA")
@NamedQuery(name="UnidadOrganica.findAll", query="SELECT u FROM UnidadOrganica u")
public class UnidadOrganica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_UO")
	private Integer codUo;

	@Column(name="COD_UO_PADRE")
	private Integer codUoPadre;

	@Column(name="DES_SIGLA")
	private String desSigla;

	@Column(name="DES_UO")
	private String desUo;

	@Column(name="IND_EST_UO")
	private Integer indEstUo;

	@Column(name="IND_ESTADO")
	private String indEstado;

	//bi-directional many-to-one association to NumeracionDocumento
	@OneToMany(fetch = FetchType.LAZY,mappedBy="unidadOrganica")
	private List<NumeracionDocumento> numeracionDocumentos;

	//bi-directional many-to-one association to NumeracionPersona
	@OneToMany(fetch = FetchType.LAZY,mappedBy="unidadOrganica")
	private List<NumeracionPersona> numeracionPersonas;

	//bi-directional many-to-one association to Personal
	@OneToMany(fetch = FetchType.LAZY,mappedBy="unidadOrganica")
	private List<Personal> personals;

	//bi-directional many-to-one association to Usuario
	@OneToMany(fetch = FetchType.LAZY,mappedBy="unidadOrganica")
	private List<Usuario> usuarios;

	public UnidadOrganica() {
	}

	public long getCodUo() {
		return this.codUo;
	}

	public void setCodUo(Integer codUo) {
		this.codUo = codUo;
	}

	public Integer getCodUoPadre() {
		return this.codUoPadre;
	}

	public void setCodUoPadre(Integer codUoPadre) {
		this.codUoPadre = codUoPadre;
	}

	public String getDesSigla() {
		return this.desSigla;
	}

	public void setDesSigla(String desSigla) {
		this.desSigla = desSigla;
	}

	public String getDesUo() {
		return this.desUo;
	}

	public void setDesUo(String desUo) {
		this.desUo = desUo;
	}

	public Integer getIndEstUo() {
		return this.indEstUo;
	}

	public void setIndEstUo(Integer indEstUo) {
		this.indEstUo = indEstUo;
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
		numeracionDocumento.setUnidadOrganica(this);

		return numeracionDocumento;
	}

	public NumeracionDocumento removeNumeracionDocumento(NumeracionDocumento numeracionDocumento) {
		getNumeracionDocumentos().remove(numeracionDocumento);
		numeracionDocumento.setUnidadOrganica(null);

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
		numeracionPersona.setUnidadOrganica(this);

		return numeracionPersona;
	}

	public NumeracionPersona removeNumeracionPersona(NumeracionPersona numeracionPersona) {
		getNumeracionPersonas().remove(numeracionPersona);
		numeracionPersona.setUnidadOrganica(null);

		return numeracionPersona;
	}

	public List<Personal> getPersonals() {
		return this.personals;
	}

	public void setPersonals(List<Personal> personals) {
		this.personals = personals;
	}

	public Personal addPersonal(Personal personal) {
		getPersonals().add(personal);
		personal.setUnidadOrganica(this);

		return personal;
	}

	public Personal removePersonal(Personal personal) {
		getPersonals().remove(personal);
		personal.setUnidadOrganica(null);

		return personal;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setUnidadOrganica(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setUnidadOrganica(null);

		return usuario;
	}

}