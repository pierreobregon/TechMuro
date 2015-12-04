package pe.conadis.tradoc.entity;

// Generated Feb 27, 2014 5:00:05 PM by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TransaccionDetalle generated by hbm2java
 */
@Entity
@Table(name = "X_TRANSACCION_DETALLE")
public class TransaccionDetalle implements java.io.Serializable, Comparable<TransaccionDetalle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1732986379212737894L;
	
	private Integer iddetalle;
	private Transaccion transaccion;
	private Integer posicionx;
	private Integer posiciony;
	private Integer colspan;
	private Integer rowspan;
	private String contenido;
	private Date fechacreacion;
	private Character estado;
	private Date fechaModificacion;
	
	private Integer width;
	
	public TransaccionDetalle() {
	}

	public TransaccionDetalle(Integer iddetalle) {
		this.iddetalle = iddetalle;
	}

	public TransaccionDetalle(Integer iddetalle, Transaccion transaccion,
			Integer posicionx, Integer posiciony, Integer colspan,
			Integer rowspan, String contenido, Date fechacreacion,
			Character estado) {
		this.iddetalle = iddetalle;
		this.transaccion = transaccion;
		this.posicionx = posicionx;
		this.posiciony = posiciony;
		this.colspan = colspan;
		this.rowspan = rowspan;
		this.contenido = contenido;
		this.fechacreacion = fechacreacion;
		this.estado = estado;
	}

	@Id
	@Column(name = "IDDETALLE", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getIddetalle() {
		return this.iddetalle;
	}

	public void setIddetalle(Integer iddetalle) {
		this.iddetalle = iddetalle;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDTRANSACCION")
	public Transaccion getTransaccion() {
		return this.transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

	@Column(name = "POSICIONX", precision = 22, scale = 0)
	public Integer getPosicionx() {
		return this.posicionx;
	}

	public void setPosicionx(Integer posicionx) {
		this.posicionx = posicionx;
	}

	@Column(name = "POSICIONY", precision = 22, scale = 0)
	public Integer getPosiciony() {
		return this.posiciony;
	}

	public void setPosiciony(Integer posiciony) {
		this.posiciony = posiciony;
	}

	@Column(name = "COLSPAN", precision = 22, scale = 0)
	public Integer getColspan() {
		return this.colspan;
	}

	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}

	@Column(name = "ROWSPAN", precision = 22, scale = 0)
	public Integer getRowspan() {
		return this.rowspan;
	}

	public void setRowspan(Integer rowspan) {
		this.rowspan = rowspan;
	}

	@Column(name = "CONTENIDO", length = 4000)
	public String getContenido() {
		return this.contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
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
	@Column(name = "FECHAMODIFICACION", length = 7)
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@Column(name = "WIDTH", precision = 22, scale = 0)
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Override
	public int compareTo(TransaccionDetalle o) {
		int resultado = this.getPosicionx().compareTo(o.getPosicionx());
		if(resultado == 0) return this.getPosiciony().compareTo(o.getPosiciony());
		return resultado;
	}

	
	
	
}
