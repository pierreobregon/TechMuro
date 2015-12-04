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

import pe.conadis.tradoc.util.Constants;

/**
 * Subcapitulo generated by hbm2java
 */
@Entity
@Table(name = "X_SUBCAPITULO")
public class Subcapitulo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7516778140828476121L;
	
	private Integer idsubcapitulo;
	private Nota nota;
	private Capitulo capitulo;
	private String nombre;
	private String descripcion;
	private Date fechacreacion;
	private Character estado;
	private Integer orden;
	private List<Rubro> rubros = new ArrayList<Rubro>(0);
	private Date fechaModificacion;

	public Subcapitulo() {
	}
	
	public static Subcapitulo crearSinSubcapitulo(Capitulo capitulo){
		Subcapitulo subcap = new Subcapitulo();
		subcap.setIdsubcapitulo(0);
		subcap.setNombre(Constants.SINSUBCAP);
		subcap.setOrden(1);
		subcap.setCapitulo(capitulo);
		return subcap;
	}

	public Subcapitulo(Integer idsubcapitulo) {
		this.idsubcapitulo = idsubcapitulo;
	}

	public Subcapitulo(Integer idsubcapitulo, Nota nota, Capitulo capitulo,
			String nombre, String descripcion, Date fechacreacion,
			Character estado, Integer orden, List<Rubro> rubros) {
		this.idsubcapitulo = idsubcapitulo;
		this.nota = nota;
		this.capitulo = capitulo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechacreacion = fechacreacion;
		this.estado = estado;
		this.orden = orden;
		this.rubros = rubros;
	}

	@Id
	@Column(name = "IDSUBCAPITULO", unique = true, nullable = false, precision = 22, scale = 0)
	//@SequenceGenerator(name = "seq_subcapitulo", sequenceName = "SEQ_SUBCAPITULO", allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_subcapitulo")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getIdsubcapitulo() {
		return this.idsubcapitulo;
	}
	
	public void setIdsubcapitulo(Integer idsubcapitulo) {
		this.idsubcapitulo = idsubcapitulo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDNOTA")
	public Nota getNota() {
		return this.nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDCAPITULO")
	public Capitulo getCapitulo() {
		return this.capitulo;
	}

	public void setCapitulo(Capitulo capitulo) {
		this.capitulo = capitulo;
	}

	@Column(name = "NOMBRE", length = 200)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "DESCRIPCION", length = 200)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Temporal(TemporalType.TIMESTAMP)
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

	@Column(name = "ORDEN", precision = 22, scale = 0)
	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subcapitulo")
	public List<Rubro> getRubros() {
		return this.rubros;
	}

	public void setRubros(List<Rubro> rubros) {
		this.rubros = rubros;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHAMODIFICACION", length = 7)
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
}
