package pe.conadis.tradoc.entity.beans;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class AccesoPK implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer codRol;	
	private Integer codOpcMenu;	
	private Integer codMenu;
	

	@Column(name="COD_ROL")
	public Integer getCodRol() {
		return codRol;
	}	
	public void setCodRol(Integer codRol) {
		this.codRol = codRol;
	}

	@Column(name="COD_OPC_MENU", insertable=false, updatable=false)
	public Integer getCodOpcMenu() {
		return codOpcMenu;
	}	
	public void setCodOpcMenu(Integer codOpcMenu) {
		this.codOpcMenu = codOpcMenu;
	}

	@Column(name="COD_MENU", insertable=false, updatable=false)
	public Integer getCodMenu() {
		return codMenu;
	}

	public void setCodMenu(Integer codMenu) {
		this.codMenu = codMenu;
	}
	
	


}
