<%@page import="org.apache.poi.ss.usermodel.CellStyle"%>
<%@page import="org.apache.poi.ss.usermodel.Font"%>
<%@page import="org.apache.poi.util.IOUtils"%>
<%@page import="org.apache.poi.ss.usermodel.Picture"%>
<%@page import="org.apache.poi.ss.usermodel.ClientAnchor"%>
<%@page import="org.apache.poi.ss.usermodel.Drawing"%>
<%@page import="org.apache.poi.ss.usermodel.CreationHelper"%>
<%@page import="java.io.InputStream"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@page import="org.apache.poi.ss.usermodel.Cell"%>
<%@page import="org.apache.poi.ss.usermodel.Row"%>
<%@page import="org.apache.poi.ss.usermodel.Sheet"%>
<%@page import="org.apache.poi.ss.usermodel.Workbook"%>
<%@page import="pe.conadis.tradoc.entity.LogMuro"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.MalformedURLException"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%  
try{
	
response.setContentType("application/vnd.ms-excel");  
response.setHeader("content-disposition","attachment; filename="+"ReporteLog"+new SimpleDateFormat("ddMMyyyy").format(new Date())+".xls");  


Workbook wb = new HSSFWorkbook();
CreationHelper createHelper = wb.getCreationHelper();
Sheet sheet = wb.createSheet("Reporte Log");
sheet.setColumnWidth(0, 1500);
sheet.setColumnWidth(1, 800);
sheet.setColumnWidth(2, 3500);
sheet.setColumnWidth(3, 4500);
sheet.setColumnWidth(4, 4000);
sheet.setColumnWidth(5, 4000);
sheet.setColumnWidth(6, 3500);
sheet.setColumnWidth(7, 4500);
sheet.setColumnWidth(8, 3000);
sheet.setColumnWidth(9, 3000);

Font fontBold = wb.createFont();
fontBold.setFontHeightInPoints((short)10);
fontBold.setFontName("Times New Roman");
fontBold.setBoldweight((short)100);

Font fontNormal = wb.createFont();
fontNormal.setFontHeightInPoints((short)9);
fontNormal.setFontName("Times New Roman");

CellStyle styleBold = wb.createCellStyle();
styleBold.setFont(fontBold);

CellStyle styleBoldCenter = wb.createCellStyle();
styleBoldCenter.setFont(fontBold);
styleBoldCenter.setAlignment(styleBoldCenter.ALIGN_CENTER);

CellStyle styleNormal = wb.createCellStyle();
styleNormal.setFont(fontNormal);

CellStyle styleNormalCenter = wb.createCellStyle();
styleNormalCenter.setFont(fontNormal);
styleNormalCenter.setAlignment(styleNormalCenter.ALIGN_CENTER);

InputStream inputStream = new FileInputStream(request.getRealPath("/images/bbva.png"));
byte[] bytes = IOUtils.toByteArray(inputStream);
int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
inputStream.close();

CreationHelper helper = wb.getCreationHelper();
Drawing drawing = sheet.createDrawingPatriarch();
ClientAnchor anchor = helper.createClientAnchor();
anchor.setCol1(0);
anchor.setRow1(1);
Picture pict = drawing.createPicture(anchor, pictureIdx);
pict.resize();



Row row = sheet.createRow((short)2);
Cell cell = row.createCell(7);
cell.setCellValue(createHelper.createRichTextString("Fecha y Hora de Generación: "+new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date())));
cell.setCellStyle(styleBold);

Row row2 = sheet.createRow((short)3);
Cell cell2 = row2.createCell(7);
cell2.setCellValue(createHelper.createRichTextString("Usuario :     "+(String)request.getSession().getAttribute("usuario")));
cell2.setCellStyle(styleBold);

Row rowtitulo = sheet.createRow((short)4);
Cell celltitulo = rowtitulo.createCell(3);
celltitulo.setCellValue(createHelper.createRichTextString("REPORTE DE ALTAS, MODIFICACION Y ELIMINACIONES EN EL MODULO ADMINISTRADOR"));
celltitulo.setCellStyle(styleBold);

Row rowtitulo2 = sheet.createRow((short)5);
Cell celltitulo2 = rowtitulo2.createCell(4);
celltitulo2.setCellValue(createHelper.createRichTextString("Desde "+new SimpleDateFormat("dd/MM/yyyy").format((Date)request.getAttribute("fechaini"))+
		" Hasta "+new SimpleDateFormat("dd/MM/yyyy").format((Date)request.getAttribute("fechafin"))));
celltitulo2.setCellStyle(styleBold);


Row t1 = sheet.createRow((short)8);
Cell c1 = t1.createCell(1);
c1.setCellValue(createHelper.createRichTextString("N°"));
c1.setCellStyle(styleBoldCenter);

Cell c2 = t1.createCell(2);
c2.setCellValue(createHelper.createRichTextString("Módulo"));
c2.setCellStyle(styleBoldCenter);

Cell c3 = t1.createCell(3);
c3.setCellValue(createHelper.createRichTextString("Campo"));
c3.setCellStyle(styleBoldCenter);

Cell c4 = t1.createCell(4);
c4.setCellValue(createHelper.createRichTextString("Situacion actual"));
c4.setCellStyle(styleBoldCenter);

Cell c5 = t1.createCell(5);
c5.setCellValue(createHelper.createRichTextString("Situación Final"));
c5.setCellStyle(styleBoldCenter);

Cell c6 = t1.createCell(6);
c6.setCellValue(createHelper.createRichTextString("Tipo de acción"));
c6.setCellStyle(styleBoldCenter);

Cell c7 = t1.createCell(7);
c7.setCellValue(createHelper.createRichTextString("Usuario"));
c7.setCellStyle(styleBoldCenter);

Cell c8 = t1.createCell(8);
c8.setCellValue(createHelper.createRichTextString("Fecha"));
c8.setCellStyle(styleBoldCenter);

Cell c9 = t1.createCell(9);
c9.setCellValue(createHelper.createRichTextString("Hora"));
c9.setCellStyle(styleBoldCenter);

List<LogMuro> listaLog = (List<LogMuro>)request.getAttribute("listaLog");
int i=0;
for(LogMuro log: listaLog){
	i++;
	
	Row r = sheet.createRow((short)i+8);
	
	Cell c1Temp = r.createCell(1);
	c1Temp.setCellValue(createHelper.createRichTextString(""+i));
	c1Temp.setCellStyle(styleNormal);

	Cell c2Temp = r.createCell(2);
	c2Temp.setCellValue(createHelper.createRichTextString(log.getPrivilegio().getDescripcion()));
	c2Temp.setCellStyle(styleNormal);

	Cell c3Temp = r.createCell(3);
	c3Temp.setCellValue(createHelper.createRichTextString(log.getCampo()));
	c3Temp.setCellStyle(styleNormal);

	Cell c4Temp = r.createCell(4);
	c4Temp.setCellValue(createHelper.createRichTextString(log.getValoranterior()));
	c4Temp.setCellStyle(styleNormal);

	Cell c5Temp = r.createCell(5);
	c5Temp.setCellValue(createHelper.createRichTextString(log.getValornuevo()));
	c5Temp.setCellStyle(styleNormal);

	Cell c6Temp = r.createCell(6);
	c6Temp.setCellValue(createHelper.createRichTextString(log.getOpcion()));
	c6Temp.setCellStyle(styleNormalCenter);

	Cell c7Temp = r.createCell(7);
	c7Temp.setCellValue(createHelper.createRichTextString(log.getUsuario()));
	c7Temp.setCellStyle(styleNormalCenter);

	Cell c8Temp = r.createCell(8);
	c8Temp.setCellValue(createHelper.createRichTextString(new SimpleDateFormat("dd/MM/yyyy").format(log.getFecha())));
	c8Temp.setCellStyle(styleNormalCenter);

	Cell c9Temp = r.createCell(9);
	c9Temp.setCellValue(createHelper.createRichTextString(new SimpleDateFormat("HH:mm:ss").format(log.getFecha())));
	c9Temp.setCellStyle(styleNormalCenter);
}

FileOutputStream fileOut = new FileOutputStream("ReporteLog"+new SimpleDateFormat("ddMMyyyy").format(new Date())+".xls");
wb.write(fileOut);
fileOut.close();


byte[] buf = new byte[8192];   
FileInputStream in = new FileInputStream("ReporteLog"+new SimpleDateFormat("ddMMyyyy").format(new Date())+".xls");  
OutputStream out1 = response.getOutputStream();  
 int c;    
 while ((c = in.read(buf, 0, buf.length)) > 0) {    
   out1.write(buf, 0, c);    
 }    
 out1.flush();    
out1.close();  

}catch(Exception e){
	out.print(e);
}%>