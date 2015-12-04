package pe.conadis.tradoc.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.hibernate.hql.ast.tree.MapEntryNode;
import org.jsoup.Jsoup;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Columna;
import pe.conadis.tradoc.entity.Producto;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;
import pe.conadis.tradoc.model.CeldaRowsPan;

public class ReporteTarifarioCompletoExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest req,
			HttpServletResponse response) throws Exception {

		
		int x = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String fecha = sdf.format(new Date());
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment; filename=Tarifario_ " + fecha + ".xls");

		@SuppressWarnings("unchecked")
		Map<String, List<Producto>> tarifarios =  (Map<String, List<Producto>>) model.get("tarifarios");
		
		Set<String> tiposPersona = tarifarios.keySet();
		

		for (String tipoPersona : tiposPersona) {
			String tipo = tipoPersona.equals("PN") ? "Tarifario PN" : "Tarifario PJ";
			generarReporte(tipo, tarifarios.get(tipoPersona), workbook);
		}
	}
	
	
	private void generarReporte(String tipoPersona, List<Producto> productos, HSSFWorkbook workbook ){
		// create a wordsheet
		HSSFSheet sheet = workbook.createSheet(tipoPersona);

		CreationHelper createHelper = workbook.getCreationHelper();
		List<Integer> listaBordes = new ArrayList<Integer>();
		// Se establece un ancho a cada columna. Se toma una cantidad de 20 columnas por defecto
		for (int k = 0; k <20; k++) {
			sheet.setColumnWidth(k, 5000);
			//sheet.autoSizeColumn(k, true);
		}
		
		
		int i = 0;
		int j = 1;
		
		
		Map<String, CellStyle> mapEstilos = generarEstilos(workbook);
		//Map<String, CellStyle> styles = createStyles(workbook);
		
		for (Producto producto : productos) {
			
			generarImagenLogoProducto(sheet, i);
			
			HSSFRow rowProducto = sheet.createRow(i);
			sheet.addMergedRegion(new CellRangeAddress(i, ++i, 4,5));

			
			
			Cell cellProducto = rowProducto.createCell(4);
			cellProducto.setCellValue(producto.getNombre());
			cellProducto.setCellStyle(mapEstilos.get(PRODUCTO_STYLE));
				
			sheet.setColumnWidth(5, 256*40);
			sheet.createRow(++i);
			i++;
			
			List<Capitulo> capitulos = producto.getCapitulos();	
						
			
			for (Capitulo capitulo : capitulos) {
				
				
				
				// Nota superior del capitulo
				
				if(capitulo.getNotaByIdnotainicial() != null 
						&& capitulo.getNotaByIdnotainicial().getTitulo() != null
						&& !capitulo.getNotaByIdnotainicial().getTitulo().trim().equals("") ){
					
					HSSFRow rowTituloNota = sheet.createRow(i);
					sheet.addMergedRegion(new CellRangeAddress(i, i, 1,5));
					Cell cellTituloNota = rowTituloNota.createCell(1);
					cellTituloNota.setCellValue(capitulo.getNotaByIdnotainicial().getTitulo());
					cellTituloNota.setCellStyle(mapEstilos.get(NOTA_TITULO_CAPITULO_STYLE));

					i++;
					
					
					HSSFRow rowDescNota = sheet.createRow(i);
					sheet.addMergedRegion(new CellRangeAddress(i, i, 1,5));
					Cell cellDescNota = rowDescNota.createCell(1);
					cellDescNota.setCellValue(html2text(capitulo.getNotaByIdnotainicial().getDescripcion()));
					cellDescNota.setCellStyle(mapEstilos.get(NOTA_DESC_CAPITULO_STYLE));
					
					i++;
				}

				if(capitulo.getNombre() != null && !capitulo.getNombre().trim().equals("Sin Capítulo")){
					
					HSSFRow rowCapitulo = sheet.createRow(i);
					sheet.addMergedRegion(new CellRangeAddress(i, i, 1,5));
					
					Cell cellCapitulo = rowCapitulo.createCell(1);
					cellCapitulo.setCellValue(capitulo.getNombre());
					cellCapitulo.setCellStyle(mapEstilos.get(CAPITULO_STYLE));
								
					sheet.createRow(++i);
					i++;
				}
				
				
				List<Subcapitulo> subcapitulos = capitulo.getSubcapitulos();
				
				for (Subcapitulo subcapitulo : subcapitulos) {
						
					//if(subcapitulo.getDescripcion() == null || subcapitulo.getDescripcion().equals("Sin Sub-Capítulo")) continue;
					
					if(subcapitulo.getNombre() != null && !subcapitulo.getNombre().trim().equals("Sin Sub-Capítulo")){
						HSSFRow rowSubCapitulo = sheet.createRow(i);
						sheet.addMergedRegion(new CellRangeAddress(i, i, 1,5));
						
						Cell cellSubCapitulo = rowSubCapitulo.createCell(1);
						cellSubCapitulo.setCellStyle(mapEstilos.get(SUBCAPITULO_STYLE));
						cellSubCapitulo.setCellValue(subcapitulo.getNombre());
						
						
						sheet.createRow(++i);
						i++;	
					}
						
				
					
					List<Rubro> rubros = subcapitulo.getRubros();
					
					for (Rubro rubro : rubros) {
						
						List<Columna> columnasRubro = rubro.getColumnas();	
						
				//		if(rubro.getIdrubro() == 97){
						
						// Se declara un map para obtener el row, si se tienen rowspan en columnas 
						// quiere decir que se tienen varios rows
						Map<Integer, HSSFRow> mapRowsColumnas = new HashMap<Integer, HSSFRow>();
						Integer iMayorCR = 0;
						Integer aumento  = 0;
						List<Categoria> categorias = rubro.getCategorias();
												
						for (Columna columnaRubro : columnasRubro) {
							
							Integer posicionXCR = Integer.parseInt(columnaRubro.getPosicionx()) + i;
							Integer posicionYCR = Integer.parseInt(columnaRubro.getPosiciony()) + aumento + 1;
							Integer colspanCR = Integer.parseInt(columnaRubro.getColspan());
							Integer rowspanCR = Integer.parseInt(columnaRubro.getRowspan());
							
							//System.out.println(columnaRubro.getIdcolumna());
							CellRangeAddress cellRangeAddress = new CellRangeAddress(posicionXCR, posicionXCR + rowspanCR -1, posicionYCR, posicionYCR + colspanCR - 1);
							sheet.addMergedRegion(cellRangeAddress);
							
							HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
							HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
							HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
							HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
							
							
							if(colspanCR!=1){
								aumento += colspanCR-1;
							}
							
							// Se obtiene el i mayor en caso de tener rowspans, para luego continuar el conteo de i.
							if(iMayorCR < posicionXCR){
								iMayorCR = posicionXCR;
							}
							
							// Se obtiene el row
							HSSFRow rowColumnaRubro = mapRowsColumnas.get(posicionXCR);
							if(rowColumnaRubro == null){
								rowColumnaRubro = sheet.createRow(posicionXCR);
								mapRowsColumnas.put(posicionXCR, rowColumnaRubro);
							}
							
							Cell cellColumnaRubro = rowColumnaRubro.createCell(posicionYCR);
							cellColumnaRubro.setCellValue(html2text(columnaRubro.getTitulo()));	
							// En caso sea el primero, se pinta de azul, sino de celeste. Tal como esta en el ejemplo
							if(columnaRubro.equals(columnasRubro.get(0))){
								cellColumnaRubro.setCellStyle(mapEstilos.get(RUBRO_STYLE));
							}else{
								cellColumnaRubro.setCellStyle(mapEstilos.get(COLUMNA_RUBRO_STYLE));
							}		
							
						}
						
						// nos quedamos con	 el nro de fila mayorm porque es posible que se tengan divisiones a nivel fila.
						i = iMayorCR;
						i++;
						
						
						for (Categoria categoria : categorias) {
							
						//	if(categoria.getNombre() == null || categoria.getNombre().equals("Sin Categoría")) continue;
							
							
							if(categoria.getNombre() != null && !categoria.getNombre().trim().equals("Sin Categoría")){
								HSSFRow rowCategoria = sheet.createRow(i);
								sheet.addMergedRegion(new CellRangeAddress(i, i, 1,5));
								
								Cell cellCategoria = rowCategoria.createCell(1);
								cellCategoria.setCellStyle(mapEstilos.get(CATEGORIA_STYLE));
								
								String denominacion = categoria.getDenominacion() == null ? "" : " - " + categoria.getDenominacion();
								cellCategoria.setCellValue(categoria.getNombre() + denominacion);

							}
								
													
							
							Map<Integer, HSSFRow> mapRowsTransDetalle = new HashMap<Integer, HSSFRow>();
							Integer iMayorTD = 0;
							
							i++;
							
							List<Transaccion> transacciones = categoria.getTransaccions();
							
							for (Transaccion transaccion : transacciones) {
								
								List<TransaccionDetalle> transaccionDetalles = transaccion.getTransaccionDetalles();
							
							//	Integer posicionXTD = 0;
								Integer colspanTD = 0;
								Integer rowspanTD = 0;
								Integer posicionYTD = 1;
								Integer posicionBD = 1;
								Integer rowInt = 0;
								HSSFRow rowTransaccionDetalle = null;
			
								Integer posicionX = i;
								List<CeldaRowsPan> celdasRowsPan = new ArrayList<CeldaRowsPan>();
								
								
								for (TransaccionDetalle transaccionDetalle : transaccionDetalles) {
									
//									x++;							
//									if(x == 50) return;
									
									posicionBD = transaccionDetalle.getPosicionx() ;
									
									colspanTD =  transaccionDetalle.getColspan();
									rowspanTD = transaccionDetalle.getRowspan();
									
																		
									for (int k = i+1; k < i + rowspanTD; k++) {
										celdasRowsPan.add(new CeldaRowsPan(k, posicionYTD));
									}

									
									if(rowInt != posicionBD){
										if(rowInt!= 0 ) i++;
										rowTransaccionDetalle = sheet.createRow(i);
										
										// Se crea un arreglo de celdas disponibles, ya que las celdas que tienen rowspan ocupan nuevas celdas
										List<CeldaRowsPan> celdasDisponibles = new ArrayList<CeldaRowsPan>();
										for (CeldaRowsPan celda : celdasRowsPan) {
											if(celda.getFila() == i){
												for (int k = 1; k < posicionYTD + 1; k++) {
													if(celda.getColumna() != k){
														celdasDisponibles.add(new CeldaRowsPan(celda.getColumna(), k));
													}
												}
											}
										}
										
										if(celdasDisponibles.size() == 0) posicionYTD = 1;
										
										// Se obiene la columna de la primera celda disponible
										else posicionYTD = ((CeldaRowsPan)celdasDisponibles.get(0)).getColumna();
								
									}
							
									rowInt = posicionBD;
									
									

									System.out.println("idTransaccion = " + transaccion.getIdtransaccion() + " -- fila = " + i + ", col = " + posicionYTD + " -> " + html2text(transaccionDetalle.getContenido()));
									//System.out.println(transaccionDetalle.getIddetalle());
									System.out.println(i + "," + (i + rowspanTD -1) + "," + posicionYTD + "," + (posicionYTD + colspanTD - 1) + " -> " 
											+ html2text(transaccionDetalle.getContenido()));
									
									
									if(!(rowspanTD == 1 && colspanTD == 1)){
										
										CellRangeAddress cellRangeAddress = new CellRangeAddress(i, i + rowspanTD -1, posicionYTD, posicionYTD + colspanTD - 1);
										
										sheet.addMergedRegion(cellRangeAddress);
										
										HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
										HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
										HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
										HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
										
										if(posicionYTD==1){
											for (int k = i; k <= i + rowspanTD-1 ; k++) {
												listaBordes.add(k);
											}
										}
									}
																	
									
									Cell cellTransaccionDetalle = rowTransaccionDetalle.createCell(posicionYTD);	
									cellTransaccionDetalle.setCellStyle(mapEstilos.get(TRANSACCION_STYLE));
									String contenido = html2text(transaccionDetalle.getContenido());
																			
									cellTransaccionDetalle.setCellValue(createHelper.createRichTextString(contenido));	
									posicionYTD = posicionYTD + colspanTD;
									 
									//if(i > 200) return ;
								}
								sheet.createRow(++i);
							//	i++;
							}
							
							
//							sheet.createRow(++i);
//							i++;
							
						}
						
					// Nota inferior del rubro
						if(rubro.getNota() != null
								&& rubro.getNota().getTitulo() != null 
								&& rubro.getNota().getTitulo() != null
								&& !rubro.getNota().getTitulo().trim().equals("") ){
							
							HSSFRow rowTituloNota = sheet.createRow(i);
							sheet.addMergedRegion(new CellRangeAddress(i, i, 1,5));
							Cell cellTituloNota = rowTituloNota.createCell(1);
							cellTituloNota.setCellValue(rubro.getNota().getTitulo());
							cellTituloNota.setCellStyle(mapEstilos.get(NOTA_TITULO_CAPITULO_STYLE));

							i++;
							
							
							HSSFRow rowDescNota = sheet.createRow(i);
							sheet.addMergedRegion(new CellRangeAddress(i, i, 1,5));
							Cell cellDescNota = rowDescNota.createCell(1);
							cellDescNota.setCellValue(html2text(rubro.getNota().getDescripcion()));
							cellDescNota.setCellStyle(mapEstilos.get(NOTA_DESC_CAPITULO_STYLE));
							
							i++;
						}
					
					
					
					}
					
				
					// Nota inferior subcaptilos
				
					if(subcapitulo.getNota() != null
							&& subcapitulo.getNota().getTitulo() != null 
							&& subcapitulo.getNota().getTitulo() != null
							&& !subcapitulo.getNota().getTitulo().trim().equals("") ){
						
						HSSFRow rowTituloNota = sheet.createRow(i);
						sheet.addMergedRegion(new CellRangeAddress(i, i, 1,5));
						Cell cellTituloNota = rowTituloNota.createCell(1);
						cellTituloNota.setCellValue(subcapitulo.getNota().getTitulo());
						cellTituloNota.setCellStyle(mapEstilos.get(NOTA_TITULO_CAPITULO_STYLE));

						i++;
						
						
						HSSFRow rowDescNota = sheet.createRow(i);
						sheet.addMergedRegion(new CellRangeAddress(i, i, 1,5));
						Cell cellDescNota = rowDescNota.createCell(1);
						cellDescNota.setCellValue(html2text(subcapitulo.getNota().getDescripcion()));
						cellDescNota.setCellStyle(mapEstilos.get(NOTA_DESC_CAPITULO_STYLE));
						
						i++;
					}
					
				
				}
				
				sheet.createRow(++i);
				i++;
						
			
			
				// Nota inferior del capitulo
				if(capitulo.getNotaByIdnotafinal() != null 
						&& capitulo.getNotaByIdnotafinal().getTitulo() != null
						&& !capitulo.getNotaByIdnotafinal().getTitulo().trim().equals("") ){
					
					HSSFRow rowTituloNota = sheet.createRow(i);
					sheet.addMergedRegion(new CellRangeAddress(i, i, 1,5));
					Cell cellTituloNota = rowTituloNota.createCell(1);
					cellTituloNota.setCellValue(capitulo.getNotaByIdnotafinal().getTitulo());
					cellTituloNota.setCellStyle(mapEstilos.get(NOTA_TITULO_CAPITULO_STYLE));

					i++;
					
					
					HSSFRow rowDescNota = sheet.createRow(i);
					sheet.addMergedRegion(new CellRangeAddress(i, i, 1,5));
					Cell cellDescNota = rowDescNota.createCell(1);
					cellDescNota.setCellValue(html2text(capitulo.getNotaByIdnotafinal().getDescripcion()));
					cellDescNota.setCellStyle(mapEstilos.get(NOTA_DESC_CAPITULO_STYLE));
					
					i++;
				}
				
				
			
			}
			sheet.createRow(++i);
			i++;
		}	
		sheet.createRow(++i);
		
		CellStyle styleTemp = workbook.createCellStyle();
		styleTemp.setBorderRight(CellStyle.BORDER_THIN);
		for(int t=0;t<listaBordes.size();t++){
			
			Row tempRow = sheet.getRow(listaBordes.get(t));
			Cell cell = tempRow.createCell(0);
			cell.setCellStyle(styleTemp);
		}
		
	}
    
    
    private static CellStyle createBorderedStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }
    
    
    public static String html2text(String html) {
    	if(html == null) return "";
        return Jsoup.parse(html).text();
    }
    
    
    private static CellStyle getProductoStyle(HSSFWorkbook wb){
    	  //---------------------- Estilo para productos --------------------------------------------------
        CellStyle style = null;
        Font productoFont = wb.createFont();
        productoFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        productoFont.setColor(HSSFColor.WHITE.index);
        productoFont.setFontName("Arial Narrow");
        productoFont.setFontHeightInPoints((short) 19);
        //style = createBorderedStyle(wb);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(wb.getCustomPalette().findSimilarColor(35, 128, 242).getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(productoFont);
        style.setWrapText(true);
        return style;
        // -------------------------------------------------------------------------------------------------
    }
    
    
    private static CellStyle getCapituloStyle(HSSFWorkbook wb){
  	
      CellStyle style = null;
      Font capituloFont = wb.createFont();
      capituloFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
      capituloFont.setColor(HSSFColor.WHITE.index);
      capituloFont.setFontName("Arial Narrow");
      capituloFont.setFontHeightInPoints((short) 12);
      style = wb.createCellStyle();
      style.setFont(capituloFont);
      style.setAlignment(CellStyle.ALIGN_LEFT);
      style.setFillForegroundColor(wb.getCustomPalette().findSimilarColor(7, 59, 123).getIndex());
      style.setFillPattern(CellStyle.SOLID_FOREGROUND);
      style.setWrapText(true);
      return style;
  }
    
    private static CellStyle getNotaTituloCapituloStyle(HSSFWorkbook wb){
      	
        CellStyle style = null;
        Font notaTituloCapFont = wb.createFont();
        notaTituloCapFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        notaTituloCapFont.setColor(HSSFColor.WHITE.index);
        notaTituloCapFont.setFontName("Arial Narrow");
        notaTituloCapFont.setFontHeightInPoints((short) 11);
        style = wb.createCellStyle();
        style.setFont(notaTituloCapFont);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(wb.getCustomPalette().findSimilarColor(7, 59, 123).getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setWrapText(true);
        return style;
    }
    
    private static CellStyle getNotaDescCapituloStyle(HSSFWorkbook wb){
	   	
        CellStyle style = null;  
        Font categoriaFont = wb.createFont();
        //categoriaFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        categoriaFont.setColor(HSSFColor.BLACK.index);
        categoriaFont.setFontName("Arial Narrow");
        categoriaFont.setFontHeightInPoints((short) 11);
        style = wb.createCellStyle();
        style.setFont(categoriaFont);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setWrapText(true);
        //style.setFillForegroundColor(wb.getCustomPalette().findSimilarColor(204, 236, 255).getIndex());
       // style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return style;
    }
    
    
    
    private static CellStyle getSubCapituloStyle(HSSFWorkbook wb){
      	
        CellStyle style = null;
        //---------------------- Estilo para subcapitulos --------------------------------------------------
        Font subcapituloFont = wb.createFont();
        subcapituloFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        subcapituloFont.setColor(HSSFColor.WHITE.index);
        subcapituloFont.setFontName("Arial Narrow");
        subcapituloFont.setFontHeightInPoints((short) 11);
        style = wb.createCellStyle();
        style.setFont(subcapituloFont);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFillForegroundColor(wb.getCustomPalette().findSimilarColor(7, 59, 123).getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setWrapText(true);
        // ------------------------------------------------------------------------------------------------- 
        return style;
    }
    
    
 private static CellStyle getRubroStyle(HSSFWorkbook wb){
      	
        CellStyle style = null;

        Font rubroFont = wb.createFont();
        rubroFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        rubroFont.setColor(HSSFColor.WHITE.index);
        rubroFont.setFontName("Arial Narrow");
        rubroFont.setFontHeightInPoints((short) 11);
        style = wb.createCellStyle();
        style.setFont(rubroFont);   
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFillForegroundColor(wb.getCustomPalette().findSimilarColor(7, 59, 123).getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setWrapText(true);
        style.setBorderBottom(CellStyle.BORDER_THIN);
	     style.setBorderTop(CellStyle.BORDER_THIN);
	     style.setBorderRight(CellStyle.BORDER_THIN);
	     style.setBorderLeft(CellStyle.BORDER_THIN);
        
        return style;
    }
 
 
 private static CellStyle getColumnaRubroStyle(HSSFWorkbook wb){
   	
     CellStyle style = null;

    
     style = wb.createCellStyle();
     style.setAlignment(CellStyle.ALIGN_CENTER);
     style.setFillForegroundColor(wb.getCustomPalette().findSimilarColor(35, 128, 242).getIndex());
     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
     style.setWrapText(true);
     
     style.setBorderBottom(CellStyle.BORDER_THIN);
     style.setBorderTop(CellStyle.BORDER_THIN);
     style.setBorderRight(CellStyle.BORDER_THIN);
     style.setBorderLeft(CellStyle.BORDER_THIN);
     
     Font columnaFont = wb.createFont();
     columnaFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
     columnaFont.setColor(HSSFColor.WHITE.index);
     columnaFont.setFontName("Arial Narrow");
     columnaFont.setFontHeightInPoints((short) 11);
     
     style.setFont(columnaFont);
     return style;
 }
 
 
 private static CellStyle getCategoriaStyle(HSSFWorkbook wb){
	   	
     CellStyle style = null;  
     Font categoriaFont = wb.createFont();
     categoriaFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
     categoriaFont.setColor(HSSFColor.BLACK.index);
     categoriaFont.setFontName("Arial Narrow");
     categoriaFont.setFontHeightInPoints((short) 11);
     style = wb.createCellStyle();
     style.setFont(categoriaFont);
     style.setAlignment(CellStyle.ALIGN_LEFT);
     style.setFillForegroundColor(wb.getCustomPalette().findSimilarColor(204, 236, 255).getIndex());
     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
     style.setWrapText(true);
     
     style.setBorderBottom(CellStyle.BORDER_THIN);
     style.setBorderTop(CellStyle.BORDER_THIN);
     style.setBorderRight(CellStyle.BORDER_THIN);
     style.setBorderLeft(CellStyle.BORDER_THIN);
     return style;
 }
 
 
 
 private static CellStyle getTransaccionStyle(HSSFWorkbook wb){
	   	
     CellStyle style = null;  
     Font transaccionFont = wb.createFont();
  //   categoriaFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
     transaccionFont.setColor(HSSFColor.BLACK.index);
     transaccionFont.setFontName("Arial Narrow");
     transaccionFont.setFontHeightInPoints((short) 11);
     style = wb.createCellStyle();
     style.setFont(transaccionFont);
     style.setAlignment(CellStyle.ALIGN_CENTER);
     style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
     style.setWrapText(true);
     
     style.setBorderBottom(CellStyle.BORDER_THIN);
     style.setBorderTop(CellStyle.BORDER_THIN);
     style.setBorderRight(CellStyle.BORDER_THIN);
     style.setBorderLeft(CellStyle.BORDER_THIN);
   //  style.setFillForegroundColor(wb.getCustomPalette().findSimilarColor(204, 236, 255).getIndex());
   //  style.setFillPattern(CellStyle.SOLID_FOREGROUND);
     return style;
 }
    

    
   	private final static String PRODUCTO_STYLE = "productoStyle";
   	private final static String CAPITULO_STYLE = "capituloStyle";
   	private final static String NOTA_TITULO_CAPITULO_STYLE = "notaTituloCapituloStyle";
   	private final static String NOTA_DESC_CAPITULO_STYLE = "notaDescCapituloStyle";
   	private final static String SUBCAPITULO_STYLE = "subCapituloStyle";
   	private final static String RUBRO_STYLE = "rubroStyle";
 	private final static String CATEGORIA_STYLE = "categoriaStyle";
   	private final static String COLUMNA_RUBRO_STYLE = "columnaRubroStyle";
   	private final static String TRANSACCION_STYLE = "trasnsaccionStyle";
   	
   
    
    private Map<String , CellStyle> generarEstilos(HSSFWorkbook wb){
    	Map<String , CellStyle> estilos = new HashMap<String, CellStyle>();
    	estilos.put(PRODUCTO_STYLE, getProductoStyle(wb));
    	estilos.put(CAPITULO_STYLE, getCapituloStyle(wb));
    	estilos.put(NOTA_TITULO_CAPITULO_STYLE, getNotaTituloCapituloStyle(wb));
    	estilos.put(NOTA_DESC_CAPITULO_STYLE, getNotaDescCapituloStyle(wb));
    	estilos.put(SUBCAPITULO_STYLE, getSubCapituloStyle(wb));
    	estilos.put(RUBRO_STYLE, getRubroStyle(wb));
    	estilos.put(CATEGORIA_STYLE, getCategoriaStyle(wb));
    	estilos.put(COLUMNA_RUBRO_STYLE, getColumnaRubroStyle(wb));
    	estilos.put(TRANSACCION_STYLE, getTransaccionStyle(wb));
    	return estilos;
    }
    
    private void generarImagenLogoProducto(HSSFSheet sheet, int row){
    	
    	
    	HSSFWorkbook workbook = sheet.getWorkbook();
    	String relativeWebPath = "/images-visor";
		String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);
		File file = new File(absoluteDiskPath, "logo-plantillas-tarifario.jpg");
		
		try {
			//InputStream inputStream = new FileInputStream(req.getContextPath() + "/WebContent/images-visor/logo-plantillas-tarifario.png");
			  
			InputStream inputStream = new FileInputStream(file);
			//Get the contents of an InputStream as a byte[].
			   byte[] bytes = IOUtils.toByteArray(inputStream);
			   //Adds a picture to the workbook
			   int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
			   //close the input stream
			   inputStream.close();
			 
			   //Returns an object that handles instantiating concrete classes
			   CreationHelper helper = workbook.getCreationHelper();
			 
			   //Creates the top-level drawing patriarch.
			   Drawing drawing = sheet.createDrawingPatriarch();
			 
			   //Create an anchor that is attached to the worksheet
			   ClientAnchor anchor = helper.createClientAnchor();
			   //set top-left corner for the image
			   anchor.setCol1(1);
			   anchor.setRow1(row);
			   //Creates a picture
			   Picture pict = drawing.createPicture(anchor, pictureIdx);
			   //Reset the image to the original size
			   pict.resize();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 	
		
		
    }
    

}
