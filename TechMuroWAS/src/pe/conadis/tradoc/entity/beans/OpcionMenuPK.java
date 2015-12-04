package pe.conadis.tradoc.entity.beans;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the OPCION_MENU database table.
 * 
 */

public class OpcionMenuPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private Integer codOpcMenu;	
	private Integer codMenu;

	public OpcionMenuPK() {
	}
	
	@Column(name="COD_OPC_MENU")
	public Integer getCodOpcMenu() {
		return this.codOpcMenu;
	}
	public void setCodOpcMenu(Integer codOpcMenu) {
		this.codOpcMenu = codOpcMenu;
	}
	
	@Column(name="COD_MENU", insertable=false, updatable=false)
	public Integer getCodMenu() {
		return this.codMenu;
	}
	public void setCodMenu(Integer codMenu) {
		this.codMenu = codMenu;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OpcionMenuPK)) {
			return false;
		}
		OpcionMenuPK castOther = (OpcionMenuPK)other;
		return 
			(this.codOpcMenu == castOther.codOpcMenu)
			&& (this.codMenu == castOther.codMenu);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.codOpcMenu ^ (this.codOpcMenu >>> 32)));
		hash = hash * prime + ((int) (this.codMenu ^ (this.codMenu >>> 32)));
		
		return hash;
	}
}