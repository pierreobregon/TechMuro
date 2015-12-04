package pe.conadis.tradoc.entity.beans;
// default package
// Generated 12/11/2015 12:56:38 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RolUsuarioId generated by hbm2java
 */
@Embeddable
public class RolUsuarioId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer codRol;
	private Integer idUsuario;

	public RolUsuarioId() {
	}

	public RolUsuarioId(Integer codRol, Integer idUsuario) {
		this.codRol = codRol;
		this.idUsuario = idUsuario;
	}

	@Column(name = "COD_ROL", precision = 22, scale = 0)
	public Integer getCodRol() {
		return this.codRol;
	}

	public void setCodRol(Integer codRol) {
		this.codRol = codRol;
	}

	@Column(name = "ID_USUARIO", precision = 22, scale = 0)
	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RolUsuarioId))
			return false;
		RolUsuarioId castOther = (RolUsuarioId) other;

		return ((this.getCodRol() == castOther.getCodRol()) || (this
				.getCodRol() != null && castOther.getCodRol() != null && this
				.getCodRol().equals(castOther.getCodRol())))
				&& ((this.getIdUsuario() == castOther.getIdUsuario()) || (this
						.getIdUsuario() != null
						&& castOther.getIdUsuario() != null && this
						.getIdUsuario().equals(castOther.getIdUsuario())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCodRol() == null ? 0 : this.getCodRol().hashCode());
		result = 37 * result
				+ (getIdUsuario() == null ? 0 : this.getIdUsuario().hashCode());
		return result;
	}

}
