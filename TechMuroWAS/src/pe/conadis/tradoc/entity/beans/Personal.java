package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the PERSONAL database table.
 * 
 */
@Entity
@Table(name="PERSONAL")
@NamedQuery(name="Personal.findAll", query="SELECT p FROM Personal p")
public class Personal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_PERSONA")
	private long codPersona;

	@Column(name="APE_MATERNO")
	private String apeMaterno;

	@Column(name="APE_PARTENO")
	private String apeParteno;

	@Column(name="NOMBRES")
	private String nombres;

	@Column(name="NUM_DOCUMENTO")
	private BigDecimal numDocumento;

	@Column(name="TIP_DOCUMENTO")
	private String tipDocumento;

	//bi-directional many-to-one association to Derivar
	@OneToMany(mappedBy="personal")
	private List<Derivar> derivars;

	//bi-directional many-to-one association to NumeracionPersona
	@OneToMany(mappedBy="personal")
	private List<NumeracionPersona> numeracionPersonas;

	//bi-directional many-to-one association to UnidadOrganica
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COD_UO")
	private UnidadOrganica unidadOrganica;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="personal")
	private List<Usuario> usuarios;

	public Personal() {
	}

	public long getCodPersona() {
		return this.codPersona;
	}

	public void setCodPersona(long codPersona) {
		this.codPersona = codPersona;
	}

	public String getApeMaterno() {
		return this.apeMaterno;
	}

	public void setApeMaterno(String apeMaterno) {
		this.apeMaterno = apeMaterno;
	}

	public String getApeParteno() {
		return this.apeParteno;
	}

	public void setApeParteno(String apeParteno) {
		this.apeParteno = apeParteno;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public BigDecimal getNumDocumento() {
		return this.numDocumento;
	}

	public void setNumDocumento(BigDecimal numDocumento) {
		this.numDocumento = numDocumento;
	}

	public String getTipDocumento() {
		return this.tipDocumento;
	}

	public void setTipDocumento(String tipDocumento) {
		this.tipDocumento = tipDocumento;
	}

	public List<Derivar> getDerivars() {
		return this.derivars;
	}

	public void setDerivars(List<Derivar> derivars) {
		this.derivars = derivars;
	}

	public Derivar addDerivar(Derivar derivar) {
		getDerivars().add(derivar);
		derivar.setPersonal(this);

		return derivar;
	}

	public Derivar removeDerivar(Derivar derivar) {
		getDerivars().remove(derivar);
		derivar.setPersonal(null);

		return derivar;
	}

	public List<NumeracionPersona> getNumeracionPersonas() {
		return this.numeracionPersonas;
	}

	public void setNumeracionPersonas(List<NumeracionPersona> numeracionPersonas) {
		this.numeracionPersonas = numeracionPersonas;
	}

	public NumeracionPersona addNumeracionPersona(NumeracionPersona numeracionPersona) {
		getNumeracionPersonas().add(numeracionPersona);
		numeracionPersona.setPersonal(this);

		return numeracionPersona;
	}

	public NumeracionPersona removeNumeracionPersona(NumeracionPersona numeracionPersona) {
		getNumeracionPersonas().remove(numeracionPersona);
		numeracionPersona.setPersonal(null);

		return numeracionPersona;
	}

	public UnidadOrganica getUnidadOrganica() {
		return this.unidadOrganica;
	}

	public void setUnidadOrganica(UnidadOrganica unidadOrganica) {
		this.unidadOrganica = unidadOrganica;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setPersonal(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setPersonal(null);

		return usuario;
	}

}