package pe.conadis.tradoc.entity;

// Generated Feb 27, 2014 5:00:05 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * AficheOficinaId generated by hbm2java
 */
@Embeddable
public class AficheOficinaId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3764362943249742036L;
	
	private Integer idafiche;
	private Integer idoficina;
	

	public AficheOficinaId() {
	}

	public AficheOficinaId(Integer idafiche, Integer idoficina) {
		this.idafiche = idafiche;
		this.idoficina = idoficina;
	}

	@Column(name = "IDAFICHE", precision = 22, scale = 0)
	public Integer getIdafiche() {
		return this.idafiche;
	}

	public void setIdafiche(Integer idafiche) {
		this.idafiche = idafiche;
	}

	@Column(name = "IDOFICINA", precision = 22, scale = 0)
	public Integer getIdoficina() {
		return this.idoficina;
	}

	public void setIdoficina(Integer idoficina) {
		this.idoficina = idoficina;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AficheOficinaId))
			return false;
		AficheOficinaId castOther = (AficheOficinaId) other;

		return ((this.getIdafiche() == castOther.getIdafiche()) || (this
				.getIdafiche() != null && castOther.getIdafiche() != null && this
				.getIdafiche().equals(castOther.getIdafiche())))
				&& ((this.getIdoficina() == castOther.getIdoficina()) || (this
						.getIdoficina() != null
						&& castOther.getIdoficina() != null && this
						.getIdoficina().equals(castOther.getIdoficina())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIdafiche() == null ? 0 : this.getIdafiche().hashCode());
		result = 37 * result
				+ (getIdoficina() == null ? 0 : this.getIdoficina().hashCode());
		return result;
	}

}