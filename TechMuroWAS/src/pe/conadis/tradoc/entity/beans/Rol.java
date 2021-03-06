package pe.conadis.tradoc.entity.beans;
// default package
// Generated 12/11/2015 03:26:12 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Rol generated by hbm2java
 */
@Entity
@Table(name = "ROL")
public class Rol implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer codRol;
	private String desRol;
	private String indEstado;
	private Set<RolUsuario> rolUsuarios = new HashSet<RolUsuario>(0);
	private Set<Acceso> accesos = new HashSet<Acceso>(0);

	public Rol() {
	}

	public Rol(Integer codRol) {
		this.codRol = codRol;
	}

	public Rol(Integer codRol, String desRol, String indEstado,
			Set<RolUsuario> rolUsuarios, Set<Acceso> accesos) {
		this.codRol = codRol;
		this.desRol = desRol;
		this.indEstado = indEstado;
		this.rolUsuarios = rolUsuarios;
		this.accesos = accesos;
	}

	@Id
	@Column(name = "COD_ROL", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getCodRol() {
		return this.codRol;
	}

	public void setCodRol(Integer codRol) {
		this.codRol = codRol;
	}

	@Column(name = "DES_ROL", length = 100)
	public String getDesRol() {
		return this.desRol;
	}

	public void setDesRol(String desRol) {
		this.desRol = desRol;
	}

	@Column(name = "IND_ESTADO", length = 1)
	public String getIndEstado() {
		return this.indEstado;
	}

	public void setIndEstado(String indEstado) {
		this.indEstado = indEstado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rol")
	public Set<RolUsuario> getRolUsuarios() {
		return this.rolUsuarios;
	}

	public void setRolUsuarios(Set<RolUsuario> rolUsuarios) {
		this.rolUsuarios = rolUsuarios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rol")
	public Set<Acceso> getAccesos() {
		return this.accesos;
	}

	public void setAccesos(Set<Acceso> accesos) {
		this.accesos = accesos;
	}

}
