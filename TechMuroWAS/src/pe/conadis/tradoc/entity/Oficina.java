package pe.conadis.tradoc.entity;

// Generated Feb 27, 2014 5:00:05 PM by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Oficina generated by hbm2java
 */
@Entity
@Table(name = "X_OFICINA")
public class Oficina implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7120677908185031852L;
	
	private Integer idoficina;
	private Plaza plaza;
	private String nombre;
	private Date fechacreacion;
	private Character estado;
	private String codigo;
	private Date fechaactualizacion;
	private String ip;
	private Set<AficheOficina> aficheOficinas = new HashSet<AficheOficina>(0);
	private Set<OcurrenciaMuro> ocurrenciaMuros = new HashSet<OcurrenciaMuro>(0);
	private List<ComunicadoOficina> comunicadoOficinas = new ArrayList<ComunicadoOficina>(
			0);

	public Oficina() {
	}

	public Oficina(Integer idoficina) {
		this.idoficina = idoficina;
	}

	public Oficina(Integer idoficina, Plaza plaza, String nombre,
			String descripcion, String direccion, String telefono,
			Date fechacreacion, Character estado, String codigo,Date fechaactualizacion,
			Set<AficheOficina> aficheOficinas,
			Set<OcurrenciaMuro> ocurrenciaMuros,
			List<ComunicadoOficina> comunicadoOficinas) {
		this.idoficina = idoficina;
		this.plaza = plaza;
		this.nombre = nombre;
		this.fechacreacion = fechacreacion;
		this.estado = estado;
		this.codigo = codigo;
		this.fechaactualizacion = fechaactualizacion;
		this.aficheOficinas = aficheOficinas;
		this.ocurrenciaMuros = ocurrenciaMuros;
		this.comunicadoOficinas = comunicadoOficinas;
	}

	@Id
	@Column(name = "IDOFICINA", unique = true, nullable = false, precision = 22, scale = 0)
	//@SequenceGenerator(name = "seq_oficina", sequenceName = "SEQ_OFICINA", allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_oficina")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getIdoficina() {
		return this.idoficina;
	}

	public void setIdoficina(Integer idoficina) {
		this.idoficina = idoficina;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDPLAZA")
	public Plaza getPlaza() {
		return this.plaza;
	}

	public void setPlaza(Plaza plaza) {
		this.plaza = plaza;
	}

	@Column(name = "NOMBRE", length = 60)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHACREACION", length = 7)
	public Date getFechacreacion() {
		return this.fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	@Column(name = "ESTADO", length = 1)
	public Character getEstado() {
		return this.estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	@Column(name = "CODIGO", length = 20)
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "oficina")
	public Set<AficheOficina> getAficheOficinas() {
		return this.aficheOficinas;
	}

	public void setAficheOficinas(Set<AficheOficina> aficheOficinas) {
		this.aficheOficinas = aficheOficinas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "oficina")
	public Set<OcurrenciaMuro> getOcurrenciaMuros() {
		return this.ocurrenciaMuros;
	}

	public void setOcurrenciaMuros(Set<OcurrenciaMuro> ocurrenciaMuros) {
		this.ocurrenciaMuros = ocurrenciaMuros;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHAACTUALIZACION", length = 7)
	public Date getFechaactualizacion() {
		return this.fechaactualizacion;
	}

	public void setFechaactualizacion(Date fechaactualizacion) {
		this.fechaactualizacion = fechaactualizacion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "oficina")
	public List<ComunicadoOficina> getComunicadoOficinas() {
		return this.comunicadoOficinas;
	}

	public void setComunicadoOficinas(List<ComunicadoOficina> comunicadoOficinas) {
		this.comunicadoOficinas = comunicadoOficinas;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	

}
