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

import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.LogMuroDAO;
import pe.conadis.tradoc.dao.OficinaDAO;
import pe.conadis.tradoc.dao.PlazaDAO;
import pe.conadis.tradoc.entity.LogMuro;
import pe.conadis.tradoc.entity.Oficina;
import pe.conadis.tradoc.entity.Plaza;
import pe.conadis.tradoc.entity.Privilegio;
import pe.conadis.tradoc.entity.beans.Accion;
import pe.conadis.tradoc.model.UploadedFile;
import pe.conadis.tradoc.service.OficinaManager;
import pe.conadis.tradoc.util.Constants;

@Service
public class OficinaManagerImpl extends ServiceImpl<Oficina> implements OficinaManager {
	
	private static final Logger logger = Logger.getLogger(OficinaManagerImpl.class);
	
	@Autowired
	private OficinaDAO oficinaDAO;
	
	@Autowired
	private PlazaDAO plazaDAO;
	
	@Autowired
	private LogMuroDAO logMuroDAO;
	
	@Override
	protected Dao<Oficina> getDAO() {
		return oficinaDAO;
	}
	
	@Override
	@Transactional
	public List<Oficina> getOficinasFilter() throws Exception {

		return oficinaDAO.getOficinasFilter();
	}
	
	@Override
	@Transactional
	public List<Oficina> getOficinasActivas() throws Exception {

		return oficinaDAO.getOficinasActivas();
	}	

	@Override
	@Transactional
	public Oficina getOficinasVisor(Integer id) throws Exception {
		return oficinaDAO.getOficinasVisor(id);
	}

	@Override
	@Transactional
	public Oficina getOficinasByCodigoVisor(String criterio) throws Exception {
		
		String codigo = criterio.replaceFirst("^0+(?!$)", "");
		return oficinaDAO.getOficinasByCodigoVisor(codigo);
	}

	@Override
	@Transactional
	public List<Oficina> findByCodigo(String codigo){
		
		return oficinaDAO.findByCodigo(codigo);
	}


	@Override
	@Transactional
	public List<Oficina> buscarOficina(String criterio) throws Exception {
		return oficinaDAO.buscarOficina(criterio);
	}
	
	@Override
	@Transactional
	public void deleteOficina(Integer id) throws Exception {
		Oficina oficina = oficinaDAO.findById(id);
		
		oficina.setEstado('I');
		
		oficinaDAO.update(oficina);
	}

	@Override
	@Transactional
	public String subirArchivoOficinas(UploadedFile uploadedFile, String usuario) {
		
		InputStream inputStream;
		String fileName = uploadedFile.getFile().getOriginalFilename();
		String result = "";
		int countRowInsert=0;
		int errorRow=0;
		try {
			inputStream = uploadedFile.getFile().getInputStream();
			
			Workbook wb = null;
            
			if(fileName.toLowerCase().endsWith("xlsx")){
                wb = new XSSFWorkbook(inputStream);
            }else if(fileName.toLowerCase().endsWith("xls")){
                wb = new HSSFWorkbook(inputStream);
            }
			
			Oficina oficinaAnterior = new Oficina();
			
			for (int k = 0; k < wb.getNumberOfSheets(); k++) {
				Sheet sheet = wb.getSheetAt(k);

				// getPhysicalNumberOfRows devuelve la cantidad de filas (inclusive los que no estan y fueron tocados)
				int rows = sheet.getPhysicalNumberOfRows();
 				for (int r = 1; r < rows; r++) {
					Row row = sheet.getRow(r);
					if (row == null) {
						continue;
					}
					
					String codigoOficina = getCellValue(row,0);
					String nombreOficina = getCellValue(row,1);
					String nombrePlaza = getCellValue(row,2);
					String ip = getCellValue(row,4);
					
					if((getCellValue(row,0) == null || getCellValue(row,0).trim().equals("")) && 
							(getCellValue(row,1) == null || getCellValue(row,1).trim().equals("")) && 
							(getCellValue(row,2) == null || getCellValue(row,2).trim().equals("")) && 
							(getCellValue(row,3) == null || getCellValue(row,3).trim().equals("")) && 
							(getCellValue(row,4) == null || getCellValue(row,4).trim().equals("")) 
							){
						break;
					}
						
					
					
					if(validaObligatorios(codigoOficina, nombreOficina, nombrePlaza)){
					if(oficinaDAO.findByNombre(nombreOficina).size() != 0||oficinaDAO.findByCodigo(codigoOficina).size() != 0){
						
					}else{
					
						if(oficinaAnterior.getNombre()!=null&&
								oficinaAnterior.getNombre()
								
								.trim().toUpperCase().equals(nombreOficina.trim().toUpperCase())&&
							oficinaAnterior.getPlaza().getNombre().trim().toUpperCase().equals(nombrePlaza.trim().toUpperCase())){
							
						}else{
							Oficina oficinaTemp = new Oficina();
								codigoOficina = String.format("%04d", new Integer(codigoOficina));
							oficinaTemp.setNombre(nombreOficina);
							Plaza plazaTemp = plazaDAO.findByNombre(nombrePlaza);		
							if(plazaTemp==null){
								plazaTemp = new Plaza();
								plazaTemp.setNombre(getCellValue(row,2));
								plazaTemp.setEstado("A".charAt(0));
								plazaTemp.setFechacreacion(new Date());
								plazaTemp.setZona(getCellValue(row,3));
								plazaDAO.add(plazaTemp);
							}	
							oficinaTemp.setPlaza(plazaTemp);
							
						//	System.out.println(getCellValue(row,0).toString());
							Integer codigoInt = new Integer(getCellValue(row,0));
							//String codigoOfi = getCellValue(row,0).toString().substring(0, getCellValue(row,0).toString().length()-2);
							
							String codigoOfi = String.format("%04d", codigoInt);
							oficinaTemp.setCodigo(codigoOfi);
	
							oficinaTemp.setEstado("A".charAt(0));
							oficinaTemp.setFechacreacion(new Date());
								oficinaTemp.setIp(ip);
							oficinaDAO.add(oficinaTemp);
								countRowInsert++;
							
							logMuroDAO.add(new LogMuro(null, new Privilegio(15),
									usuario,
									Constants.REPORTE_CREAR, new Date(),"", oficinaTemp.getNombre(), 
									oficinaTemp.getIdoficina(), "Oficina", "S"));
							
							
							oficinaAnterior = oficinaTemp;
						}
					}
					}else{
						errorRow = row.getRowNum() + 1;	
						break;
					}
				}
					}
					
			if(countRowInsert == 0){
				result += "No se insertaron registros";
			}else{
				result += "Se procesaron correctamente: " + countRowInsert + " registro(s).";
			}
					
					
			if(errorRow != 0){
				result += "\nError detectado en la fila " + errorRow + ".\nProceso de carga masiva detenido.";
				}
			return result;
		} catch (Exception e) { 
			logger.error("Error subiendo archivo " +e);
			e.printStackTrace();
			return result;
		}
	}
	
	public String getCellValue(Row row, int posicion){
		
		Cell cell = row.getCell(posicion);
		if(cell == null) return null;
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

	public boolean validaObligatorios(String codigo, String nombre, String plaza){
		
		if(nombre==null||nombre.trim().equals("")||nombre.trim().length()>60){
			return false;
		}
		if(codigo==null||codigo.trim().equals("")||codigo.trim().length()>4){
			return false;
		}
		if(plaza==null||plaza.trim().equals("")){
			return false;
		}
		return true;
	}

	@Override
	public String obtenerCodigoOficinaPorIP(String ip) {
		Oficina oficina = oficinaDAO.getOficinaByIp(ip);
		return oficina == null ? null : oficina.getCodigo();
	}

	@Override
	public List<Oficina> getOficinasPorPlaza(Integer idplaza) {
		// TODO Auto-generated method stub
		return oficinaDAO.getOficinasPorPlaza(idplaza);
	}
}
