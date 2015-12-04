package pe.conadis.tradoc.entity;

// Generated Feb 27, 2014 5:00:05 PM by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Aviso generated by hbm2java
 */
@Entity
@Table(name = "X_AVISO")
public class Aviso implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1088034380020176287L;
	
	private Integer idaviso;
	private String titulo;
	private String circular;
	private String descripcion;
	private Date fechacreacion;
	private Character estado;
	private Date fechaactualizacion;
	
	public Aviso() {
	}

	public Aviso(Integer idaviso) {
		this.idaviso = idaviso;
	}

	public Aviso(Integer idaviso, String titulo, String circular,
			 Date fechacreacion, Character estado,Date fechaactualizacion, String descripcion) {
		this.idaviso = idaviso;
		this.titulo = titulo;
		this.circular = circular;
		this.descripcion = descripcion;
		this.fechacreacion = fechacreacion;
		this.estado = estado;
		this.fechaactualizacion = fechaactualizacion;
	}

	@Id
	@Column(name = "IDAVISO", unique = true, nullable = false, precision = 22, scale = 0)
	//@SequenceGenerator(name = "seq_avi", sequenceName = "SEQ_AVISO", allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_avi")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getIdaviso() {
		return this.idaviso;
	}

	public void setIdaviso(Integer idaviso) {
		this.idaviso = idaviso;
	}

	@Column(name = "TITULO", length = 200)
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Column(name = "CIRCULAR", length = 400)
	public String getCircular() {
		return this.circular;
	}

	public void setCircular(String circular) {
		this.circular = circular;
	}

	@Column(name = "DESCRIPCION", length = 4000)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHAACTUALIZACION", length = 7)
	public Date getFechaactualizacion() {
		return this.fechaactualizacion;
	}

	public void setFechaactualizacion(Date fechaactualizacion) {
		this.fechaactualizacion = fechaactualizacion;
	}

}
