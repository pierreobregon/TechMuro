package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Date;
import java.util.List;


/**
 * The persistent class for the DERIVAR database table.
 * 
 */
@Entity
@Table(name="DERIVAR")
@NamedQuery(name="Derivar.findAll", query="SELECT d FROM Derivar d")
public class Derivar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_DERIVACION")
	private Integer codDerivacion;

	//@Column(name="COD_ENTIDAD_DESTINO")
	//private Integer codEntidadDestino;
	
	@ManyToOne
	@JoinColumn(name="COD_ENTIDAD_DESTINO")
	private EntidadExterna entidadExternaDestino;	

	//@Column(name="COD_ENTIDAD_ORIGEN")
	//private Integer codEntidadOrigen;
	
	@ManyToOne
	@JoinColumn(name="COD_ENTIDAD_ORIGEN")
	private EntidadExterna entidadExternaOrigen;	

	//@Column(name="COD_UO_DESTINO")
	//private Integer codUoDestino;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COD_UO_DESTINO")
	private UnidadOrganica uoDestino;	

	//@Column(name="COD_UO_ENVIO_ENT_EXTERNA")
	//private Integer codUoEnvioEntExterna;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="COD_UO_ENVIO_ENT_EXTERNA")
	private UnidadOrganica uoEnvioEntExterna;	

//	@Column(name="COD_UO_ORIGEN")
//	private Integer codUoOrigen;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COD_UO_ORIGEN")
	private UnidadOrganica uoOrigen;
	
	
	@Column(name="COD_USU_CREACION")
	private String codUsuCreacion;

	@Column(name="COD_USU_MODIFICACION")
	private String codUsuModificacion;

	@Column(name="COD_USU_RECEPCION")
	private String codUsuRecepcion;


	@Column(name="FEC_CREACION")
	private Date fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;

	@Column(name="FEC_RECEPCION")
	private Date fecRecepcion;

	@Column(name="IND_ATENCION")
	private String indAtencion;

	@Column(name="IND_DER_PERSONA")
	private String indDerPersona;

	@Column(name="IND_DEVOLUCION")
	private String indDevolucion;

	@Column(name="IND_DOCUMENTO")
	private String indDocumento;

	@Column(name="IND_ENVIO_ENT_EXTERNA")
	private String indEnvioEntExterna;

	@Column(name="IND_RECEPCION")
	private String indRecepcion;

	@Column(name="IND_RES_ATENCION")
	private String indResAtencion;

	//bi-directional many-to-one association to Atender
	@OneToMany(fetch = FetchType.LAZY,mappedBy="derivar")
	private List<Atender> atenders;

	//bi-directional many-to-one association to DerivacionAccion
	@OneToMany(fetch = FetchType.LAZY,mappedBy="derivar")
	private List<DerivacionAccion> derivacionAccions;

	//bi-directional many-to-one association to Documento
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COD_DOCUMENTO")
	private Documento documento;

	//bi-directional many-to-one association to Personal
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COD_PERSONA_DESTINO")
	private Personal personal;

	//bi-directional many-to-one association to Devolucion
	@OneToMany(fetch = FetchType.LAZY,mappedBy="derivar")
	private List<Devolucion> devolucions;
	


	public Derivar() {
	}

	public Integer getCodDerivacion() {
		return this.codDerivacion;
	}

	public void setCodDerivacion(Integer codDerivacion) {
		this.codDerivacion = codDerivacion;
	}
/*
	public Integer getCodEntidadDestino() {
		return this.codEntidadDestino;
	}

	public void setCodEntidadDestino(Integer codEntidadDestino) {
		this.codEntidadDestino = codEntidadDestino;
	}

	public Integer getCodEntidadOrigen() {
		return this.codEntidadOrigen;
	}

	public void setCodEntidadOrigen(Integer codEntidadOrigen) {
		this.codEntidadOrigen = codEntidadOrigen;
	}

	public Integer getCodUoDestino() {
		return this.codUoDestino;
	}

	public void setCodUoDestino(Integer codUoDestino) {
		this.codUoDestino = codUoDestino;
	}
*/


//	public Integer getCodUoOrigen() {
//		return this.codUoOrigen;
//	}
//
//	public void setCodUoOrigen(Integer codUoOrigen) {
//		this.codUoOrigen = codUoOrigen;
//	}

	public String getCodUsuCreacion() {
		return this.codUsuCreacion;
	}



	public UnidadOrganica getUoEnvioEntExterna() {
		return uoEnvioEntExterna;
	}

	public void setUoEnvioEntExterna(UnidadOrganica uoEnvioEntExterna) {
		this.uoEnvioEntExterna = uoEnvioEntExterna;
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

	public String getCodUsuRecepcion() {
		return this.codUsuRecepcion;
	}

	public void setCodUsuRecepcion(String codUsuRecepcion) {
		this.codUsuRecepcion = codUsuRecepcion;
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

	public Date getFecRecepcion() {
		return this.fecRecepcion;
	}

	public void setFecRecepcion(Date fecRecepcion) {
		this.fecRecepcion = fecRecepcion;
	}

	public String getIndAtencion() {
		return this.indAtencion;
	}

	public void setIndAtencion(String indAtencion) {
		this.indAtencion = indAtencion;
	}

	public String getIndDerPersona() {
		return this.indDerPersona;
	}

	public void setIndDerPersona(String indDerPersona) {
		this.indDerPersona = indDerPersona;
	}

	public String getIndDevolucion() {
		return this.indDevolucion;
	}

	public void setIndDevolucion(String indDevolucion) {
		this.indDevolucion = indDevolucion;
	}

	public String getIndDocumento() {
		return this.indDocumento;
	}

	public void setIndDocumento(String indDocumento) {
		this.indDocumento = indDocumento;
	}

	public String getIndEnvioEntExterna() {
		return this.indEnvioEntExterna;
	}

	public void setIndEnvioEntExterna(String indEnvioEntExterna) {
		this.indEnvioEntExterna = indEnvioEntExterna;
	}

	public String getIndRecepcion() {
		return this.indRecepcion;
	}

	public void setIndRecepcion(String indRecepcion) {
		this.indRecepcion = indRecepcion;
	}

	public String getIndResAtencion() {
		return this.indResAtencion;
	}

	public void setIndResAtencion(String indResAtencion) {
		this.indResAtencion = indResAtencion;
	}

	public List<Atender> getAtenders() {
		return this.atenders;
	}

	public void setAtenders(List<Atender> atenders) {
		this.atenders = atenders;
	}

	public Atender addAtender(Atender atender) {
		getAtenders().add(atender);
		atender.setDerivar(this);

		return atender;
	}

	public Atender removeAtender(Atender atender) {
		getAtenders().remove(atender);
		atender.setDerivar(null);

		return atender;
	}

	public List<DerivacionAccion> getDerivacionAccions() {
		return this.derivacionAccions;
	}

	public void setDerivacionAccions(List<DerivacionAccion> derivacionAccions) {
		this.derivacionAccions = derivacionAccions;
	}

	public DerivacionAccion addDerivacionAccion(DerivacionAccion derivacionAccion) {
		getDerivacionAccions().add(derivacionAccion);
		derivacionAccion.setDerivar(this);

		return derivacionAccion;
	}

	public DerivacionAccion removeDerivacionAccion(DerivacionAccion derivacionAccion) {
		getDerivacionAccions().remove(derivacionAccion);
		derivacionAccion.setDerivar(null);

		return derivacionAccion;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Personal getPersonal() {
		return this.personal;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

	public List<Devolucion> getDevolucions() {
		return this.devolucions;
	}

	public void setDevolucions(List<Devolucion> devolucions) {
		this.devolucions = devolucions;
	}

	public Devolucion addDevolucion(Devolucion devolucion) {
		getDevolucions().add(devolucion);
		devolucion.setDerivar(this);

		return devolucion;
	}

	public Devolucion removeDevolucion(Devolucion devolucion) {
		getDevolucions().remove(devolucion);
		devolucion.setDerivar(null);

		return devolucion;
	}

	public EntidadExterna getEntidadExternaDestino() {
		return entidadExternaDestino;
	}

	public void setEntidadExternaDestino(EntidadExterna entidadExternaDestino) {
		this.entidadExternaDestino = entidadExternaDestino;
	}

	public EntidadExterna getEntidadExternaOrigen() {
		return entidadExternaOrigen;
	}

	public void setEntidadExternaOrigen(EntidadExterna entidadExternaOrigen) {
		this.entidadExternaOrigen = entidadExternaOrigen;
	}

	public UnidadOrganica getUoDestino() {
		return uoDestino;
	}

	public void setUoDestino(UnidadOrganica uoDestino) {
		this.uoDestino = uoDestino;
	}

	public UnidadOrganica getUoOrigen() {
		return uoOrigen;
	}

	public void setUoOrigen(UnidadOrganica uoOrigen) {
		this.uoOrigen = uoOrigen;
	}


	
}