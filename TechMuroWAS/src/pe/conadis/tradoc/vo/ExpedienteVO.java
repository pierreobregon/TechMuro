package pe.conadis.tradoc.vo;

import java.util.Date;



public class ExpedienteVO {
	
	//EXPEDIENTE
	private Integer codCorrelativo;
	private Integer codExpediente; //PK
	private Integer canDias;
	private String codUsuario;
	private String desAsunto;	
	private String desDocumento;
	private String desUtd;
	private String descripcion;
	private String detalle;
	private Date fecCreacion;
	private String interesado;
	private Integer numFolios;
	private String indExpediente;//PREGUNTAR
	private Integer codEntidad;

	//NUMERACION DOCUMENTO
		
	private String anioDocumento;	
	private String indEstado;
	private Integer numDocumento;
	
	
	//METODOS
	
	
	public Integer getCanDias() {
		return canDias;
	}
	public Integer getCodCorrelativo() {
		return codCorrelativo;
	}
	public void setCodCorrelativo(Integer codCorrelativo) {
		this.codCorrelativo = codCorrelativo;
	}
	public Integer getCodEntidad() {
		return codEntidad;
	}
	public void setCodEntidad(Integer codEntidad) {
		this.codEntidad = codEntidad;
	}
	public Integer getCodExpediente() {
		return codExpediente;
	}
	public void setCodExpediente(Integer codExpediente) {
		this.codExpediente = codExpediente;
	}
	public void setCanDias(Integer canDias) {
		this.canDias = canDias;
	}
	public String getCodUsuario() {
		return codUsuario;
	}
	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}
	public String getDesAsunto() {
		return desAsunto;
	}
	public void setDesAsunto(String desAsunto) {
		this.desAsunto = desAsunto;
	}
	public String getDesDocumento() {
		return desDocumento;
	}
	public void setDesDocumento(String desDocumento) {
		this.desDocumento = desDocumento;
	}
	public String getDesUtd() {
		return desUtd;
	}
	public void setDesUtd(String desUtd) {
		this.desUtd = desUtd;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public Date getFecCreacion() {
		return fecCreacion;
	}
	public void setFecCreacion(Date fecCreacion) {
		this.fecCreacion = fecCreacion;
	}
	public String getInteresado() {
		return interesado;
	}
	public void setInteresado(String interesado) {
		this.interesado = interesado;
	}
	public Integer getNumFolios() {
		return numFolios;
	}
	public void setNumFolios(Integer numFolios) {
		this.numFolios = numFolios;
	}
	public String getIndExpediente() {
		return indExpediente;
	}
	public void setIndExpediente(String indExpediente) {
		this.indExpediente = indExpediente;
	}
	public String getAnioDocumento() {
		return anioDocumento;
	}
	public void setAnioDocumento(String anioDocumento) {
		this.anioDocumento = anioDocumento;
	}
	public String getIndEstado() {
		return indEstado;
	}
	public void setIndEstado(String indEstado) {
		this.indEstado = indEstado;
	}
	public Integer getNumDocumento() {
		return numDocumento;
	}
	public void setNumDocumento(Integer numDocumento) {
		this.numDocumento = numDocumento;
	}
	
	
	
	
	
	

}
