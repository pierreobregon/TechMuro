package pe.conadis.tradoc.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.conadis.tradoc.dao.ContratoDAO;
import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.LogMuroDAO;
import pe.conadis.tradoc.dao.NotariaContratoDAO;
import pe.conadis.tradoc.dao.NotariaDAO;
import pe.conadis.tradoc.dao.PlazaDAO;
import pe.conadis.tradoc.entity.Contrato;
import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.entity.Notaria;
import pe.conadis.tradoc.entity.NotariaContrato;
import pe.conadis.tradoc.entity.NotariaContratoId;
import pe.conadis.tradoc.entity.Plaza;
import pe.conadis.tradoc.entity.Privilegio;
import pe.conadis.tradoc.model.UploadedFile;
import pe.conadis.tradoc.service.NotariaManager;
import pe.conadis.tradoc.util.Constants;

@Service
public class NotariaManagerImpl extends ServiceImpl<Notaria> implements NotariaManager {

	private static final Logger logger = Logger.getLogger(NotariaManagerImpl.class);
	
	@Autowired
    private NotariaDAO  notariaDAO;	
	@Autowired
    private NotariaContratoDAO   notariaContratoDAO;	
	@Autowired
    private ContratoDAO   contratoDAO;	
	@Autowired
    private PlazaDAO   plazaDAO;
	@Autowired
    private LogMuroDAO logMuroDAO;
	
	@Override
	protected Dao<Notaria> getDAO() {
		return notariaDAO;
	}
	
	public List<Notaria> buscarNotaria(String nombre){
		
		return notariaDAO.buscarNotaria(nombre);
	}

	
	@Override
	@Transactional
	public boolean eliminarNotaria(Notaria notaria) {
		try{
			notaria = notariaDAO.findById(notaria.getIdnotaria());
		
			notaria.setEstado("I".charAt(0));
			
			notariaDAO.update(notaria);
			
		}catch(Exception ex){
			logger.error("eliminarNotaria -->"+ex);
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional
	public Notaria getNotaria(Notaria notaria) {
		try{
			notaria = notariaDAO.findById(notaria.getIdnotaria());
			notaria.getPlaza().getNombre();
			notaria.getPlaza().setNotarias(null);
			notaria.getPlaza().setOficinas(null);
			notaria.getNotariaContratos().size();
			List<NotariaContrato> lista = notaria.getNotariaContratos();
			
			for(NotariaContrato nc:lista){
				logger.debug(nc.getContrato().getIdcontrato());
				logger.debug(nc.getContrato().getDescripcion());
				logger.debug(nc.getContrato().getGastos().replace("\"", "\\\""));
				logger.debug(nc.getNotaria().getNombre().replace("\"", "\\\""));
			}
			
		}catch(Exception ex){
			logger.error("eliminarNotaria -->"+ex);
			return notaria;
		}
		return notaria;
	}

	@Override
	@Transactional
	public boolean agregarNotariaContrato(Notaria notaria, List<Contrato> contratoList) {
		try{
			for(Contrato contrato:contratoList){
				if(contrato.getIdcontrato()==0){
					contrato.setIdcontrato(null);
					contrato.setEstado("A".charAt(0));
					contrato.setFechacreacion(new Date());
					contratoDAO.add(contrato);
				}else{
					Contrato contratoTemp = contratoDAO.findById(contrato.getIdcontrato());
					if(!contratoTemp.getDescripcion().trim().equals(contrato.getDescripcion().trim())||
							!contratoTemp.getGastos().trim().equals(contrato.getGastos().trim())){
						contrato.setIdcontrato(null);
						contrato.setEstado("A".charAt(0));
						contrato.setFechacreacion(new Date());
						contratoDAO.add(contrato);
					}
				}
				notariaContratoDAO.add(new NotariaContrato(new NotariaContratoId(notaria.getIdnotaria(), contrato.getIdcontrato())));
			}
			return true;
		}catch(Exception ex){
			logger.error("agregarNotariaContrato -->"+ex);
			return false;
		}
	}
	
	@Override
	@Transactional
	public boolean deleteNotariaContrato(List<NotariaContrato> contratoList) {
		try{
			
			for(NotariaContrato ncTemp: contratoList){
				notariaContratoDAO.deleteFisico(ncTemp);
			}
			return true;
		}catch(Exception ex){
			logger.error("agregarNotariaContrato -->"+ex);
			return false;
		}
	}

	@Override
	@Transactional
	public String subirArchivoNotarias(UploadedFile uploadedFile, String usuario) {

		InputStream inputStream;
		String fileName = uploadedFile.getFile().getOriginalFilename();
		String msje ="";
		try {
			inputStream = uploadedFile.getFile().getInputStream();
			
			Workbook wb = null;
            
			if(fileName.toLowerCase().endsWith("xlsx")){
                wb = new XSSFWorkbook(inputStream);
            }else if(fileName.toLowerCase().endsWith("xls")){
                wb = new HSSFWorkbook(inputStream);
            }
			
			Notaria notariaAnterior = new Notaria();
			int contadorRegistrosInsertados = 0;
			int nroFilaError = 0;
			String contratoAnterior = "";
			
			
			for (int k = 0; k < wb.getNumberOfSheets(); k++) {
				Sheet sheet = wb.getSheetAt(k);
				int rows = sheet.getPhysicalNumberOfRows();
 				for (int r = 1; r < rows; r++) {
					Row row = sheet.getRow(r);
					if (row == null) {
						continue;
					}
					
					
					// 0 - Nombre Notaria
					// 1 - Direccion Notaria
					// 2 - Telefono
					// 3 - Pagina Web
					// 4 - Email
					// 5 - Nombre Plaza
					// 6 - Descripcion (contrato)
					// 7 - Gastos (contrato)
					
					
					String nombreNotaria = getCellValue(row,0);
					String direccion = getCellValue(row,1);
					String telefono = getCellValue(row,2);
					String paginaWeb = getCellValue(row,3);
					String email = getCellValue(row,4);
					String nombrePlaza = getCellValue(row,5);
					String descripcion = getCellValue(row,6);
					String gastos = getCellValue(row,7);
					
					
					if((getCellValue(row,0) == null || getCellValue(row,0).trim().equals("")) && 
							(getCellValue(row,1) == null || getCellValue(row,1).trim().equals("")) && 
							(getCellValue(row,2) == null || getCellValue(row,2).trim().equals("")) && 
							(getCellValue(row,3) == null || getCellValue(row,3).trim().equals("")) && 
							(getCellValue(row,4) == null || getCellValue(row,4).trim().equals("")) && 
							(getCellValue(row,5) == null || getCellValue(row,4).trim().equals("")) && 
							(getCellValue(row,6) == null || getCellValue(row,4).trim().equals("")) && 
							(getCellValue(row,7) == null || getCellValue(row,4).trim().equals("")) 
	
							){
						break;
					}
						
					
					
					
					boolean notariaExistente = false;
					
				
					
					// si la notaria, la direccion, la plaza, el contrato o el gasto es nulo
					if(!validaObligatorios(nombreNotaria,direccion,nombrePlaza,descripcion,gastos)){
						
						// algun campo obligatorio no esta completo
						// se almacena el valor de nro de fila
						nroFilaError = row.getRowNum() + 1;	
						break;
															
					}else{
						
						//Se busca si existe la plaza, si no existe se detiene la carga
						Plaza plazaTemp = plazaDAO.findByNombre(nombrePlaza);		
						if(plazaTemp==null){
							nroFilaError = row.getRowNum() + 1;
							break;
						}	

						
						// Compara con el anterior por nombre y plaza,
						if(notariaAnterior.getNombre()!=null &&
								notariaAnterior.getNombre().trim().toUpperCase().equals(nombreNotaria.trim().toUpperCase())&&
								notariaAnterior.getPlaza().getNombre().trim().toUpperCase().equals(nombrePlaza.trim().toUpperCase())){
							
							// Si existe, ya no se crea la notaria
							
							
						}else{ // si el nombre o la plaza del anterior es diferente 
							
							
														
							
							// busca la notaria si existe , si no se encuentra, la crea
							
							Notaria notaria = notariaDAO.getNotariaPorNombreYPlazaId(nombreNotaria, plazaTemp.getIdplaza());
						//	boolean existeNotaria = notariaDAO.buscarNotariaEquals(nombreNotaria).size()>0;
							
							
							if(notaria == null){
							
								// Se crea la notaria y se asocia con la plaza
								Notaria notariaTemp = new Notaria();
								notariaTemp.setNombre(nombreNotaria);
						notariaTemp.setPlaza(plazaTemp);
								notariaTemp.setDireccion(direccion);
								notariaTemp.setTelefono1(telefono);
								notariaTemp.setPaginaweb(paginaWeb);
								notariaTemp.setEmail(email);
						notariaTemp.setEstado("A".charAt(0));
						notariaTemp.setFechacreacion(new Date());
						notariaDAO.add(notariaTemp);
								
						logMuroDAO.add(new LogMuro(null, new Privilegio(10),
								usuario, Constants.REPORTE_CREAR, new Date(), "", 
								notariaTemp.getNombre(), notariaTemp.getIdnotaria(), "Notaría", "S"));	

								notariaAnterior = notariaTemp;
					}
					
					

						
						}
							
						// busca el contrato si existe, si no se encuentra, la crea
					Contrato contrato = contratoDAO.findByNombreGastos(descripcion,gastos);
					if(contrato==null){
						contrato = new Contrato();
						contrato.setDescripcion(descripcion);
						contrato.setGastos(gastos);
						contrato.setEstado("A".charAt(0));
						contrato.setFechacreacion(new Date());						
						contratoDAO.add(contrato);
					}
						
						
						Notaria notaria = notariaDAO.getNotariaPorNombreYPlazaId(nombreNotaria, plazaTemp.getIdplaza());
						
						List notarioContratoList = notariaContratoDAO.getNotariasContratos(notaria.getIdnotaria(),  contrato.getIdcontrato());
						
						boolean existeNotariaContrato = notarioContratoList.size() != 0;
						
						if(!existeNotariaContrato){
							notariaContratoDAO.add(new NotariaContrato(new NotariaContratoId(notaria.getIdnotaria(), contrato.getIdcontrato())));
							contadorRegistrosInsertados++;
						}
						
						
					}
				}
 				
 				
			}
			
			if(contadorRegistrosInsertados == 0){
				msje += "No se insertaron registros";
			}else{
				msje += "Se procesaron correctamente: " + contadorRegistrosInsertados + " registro(s).";
			}
			 
			
			if(nroFilaError != 0){
				msje += "\nError detectado en la fila " + nroFilaError + ".\nProceso de carga masiva detenido.";
			}
			
			return msje;
		} catch (Exception e) { 
			logger.error("Error subiendo archivo " +e);
			e.printStackTrace();
			return "";
		}
	}
	
	public String getCellValue(Row row, int posicion){
		
		Cell cell = row.getCell(posicion);
		
		if(cell == null) return "";
		
		String value = null;

		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				value = "" + cell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_STRING:
				value = "" + cell.getStringCellValue();
				break;
			default:
				value="";
				break;
		}
		
		return value;
	}
	
	public boolean validaObligatorios(String nombre, String direccion, String plaza, String contrato, String pago){
		
		if(nombre==null||nombre.trim().equals("")){
			return false;
		}
		if(direccion==null||direccion.trim().equals("")){
			return false;
		}
		if(plaza==null||plaza.trim().equals("")){
			return false;
		}
		if(contrato==null||contrato.trim().equals("")){
			return false;
		}
		if(pago==null||pago.trim().equals("")){
			return false;
		}
		
		return true;
	}
	
	@Override
	public List<Notaria> getNotariasPorPlaza(Integer idplaza) {
		// TODO Auto-generated method stub
		return notariaDAO.getNotariasPorPlaza(idplaza);
	}
		
	@Override
	public List<NotariaContrato> getNotariasContratosOrdenados(Integer idnotaria) {		
		return notariaContratoDAO.getNotariasContratosOrdenados(idnotaria);

	}
		
}