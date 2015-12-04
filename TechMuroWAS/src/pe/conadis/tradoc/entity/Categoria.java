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
 * Categoria generated by hbm2java
 */
@Entity
@Table(name = "X_CATEGORIA")
public class Categoria implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8549010380606599536L;
	
	private Integer idcategoria;
	private Rubro rubro;
	private String nombre;
	private String denominacion;
	private Date fechacreacion;
	private Character estado;
	private List<Transaccion> transaccions = new ArrayList<Transaccion>(0);
	private Date fechaModificacion;
	private Integer orden;

	private String descripcion;
	
	public Categoria() {
	}

	public Categoria(Integer idcategoria) {
		this.idcategoria = idcategoria;
	}

	public Categoria(Integer idcategoria, Rubro rubro, String nombre,
			String denominacion, Date fechacreacion, Character estado,
			List<Transaccion> transaccions, Date fechaModificacion) {
		this.idcategoria = idcategoria;
		this.rubro = rubro;
		this.nombre = nombre;
		this.denominacion = denominacion;
		this.fechacreacion = fechacreacion;
		this.estado = estado;
		this.transaccions = transaccions;
		this.fechaModificacion = fechaModificacion;
	}

	@Id
	@Column(name = "IDCATEGORIA", unique = true, nullable = false, precision = 22, scale = 0)
	//@SequenceGenerator(name = "seq_categoria", sequenceName = "SEQ_CATEGORIA", allocationSize = 1)
   // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categoria")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getIdcategoria() {
		return this.idcategoria;
	}

	public void setIdcategoria(Integer idcategoria) {
		this.idcategoria = idcategoria;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDRUBRO")
	public Rubro getRubro() {
		return this.rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	@Column(name = "NOMBRE", length = 200)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "DENOMINACION", length = 500)
	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria")
	public List<Transaccion> getTransaccions() {
		return this.transaccions;
	}

	public void setTransaccions(List<Transaccion> transaccions) {
		this.transaccions = transaccions;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHAMODIFICACION", length = 7)
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@Column(name = "ORDEN", precision = 22, scale = 0)
	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	
	@Column(name = "DESCRIPCION", length = 20)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}