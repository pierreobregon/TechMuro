<%@page import="pe.conadis.tradoc.entity.LogMuro"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.itextpdf.text.Rectangle"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.MalformedURLException"%>
<%@page import="com.itextpdf.text.Image"%>
<%@page import="com.itextpdf.text.Paragraph"%>
<%@page import="com.itextpdf.text.Font"%>
<%@page import="com.itextpdf.text.Element"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="com.itextpdf.text.Phrase"%>
<%@page import="com.itextpdf.text.pdf.PdfPCell"%>
<%@page import="com.itextpdf.text.pdf.PdfPTable"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="com.itextpdf.text.pdf.PdfWriter"%>
<%@page import="com.itextpdf.text.Document"%>
<%  
try{  
response.setContentType("application/pdf");  
response.setHeader("content-disposition","attachment; filename="+"ReporteLog"+new SimpleDateFormat("ddMMyyyy").format(new Date())+".pdf");  
//What ever u write inside this JSP file will be exported as pdf file when you send request and response to this page  

Font bold = new Font(Font.FontFamily.TIMES_ROMAN, 10,
      Font.BOLD);

Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 9,
	      Font.NORMAL);

Document document = new Document();
// step 2
try {
PdfWriter.getInstance(document, new FileOutputStream("ReporteLog"+new SimpleDateFormat("ddMMyyyy").format(new Date())+".pdf"));
// step 3
document.open();
// step 4
    // We add one empty line
    
    // Lets write a big header
    Image image;
			
				image = Image.getInstance(request.getRealPath("/images/bbva.png"));
				image.setAlignment(Image.ALIGN_LEFT);
				
				PdfPTable tabletmp = new PdfPTable(2);
				tabletmp.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				tabletmp.setWidthPercentage(95);
				tabletmp.setWidths(new int[]{1,1});
		        PdfPCell cellTemp = new PdfPCell(image);
		        cellTemp.setHorizontalAlignment(Element.ALIGN_LEFT);
		        cellTemp.setBorder(Rectangle.NO_BORDER);
		        tabletmp.addCell(cellTemp);
		        
		        Paragraph pTemp = new Paragraph("Fecha y Hora de Generación : "+new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()), normal);
		        pTemp.setAlignment(Element.ALIGN_RIGHT);
		        cellTemp.addElement(pTemp);
		        
		        pTemp = new Paragraph("Usuario :     "+(String)request.getSession().getAttribute("usuario"), normal);
		        pTemp.setAlignment(Element.ALIGN_RIGHT);
		        cellTemp.addElement(pTemp); 
		    	
		        tabletmp.addCell(cellTemp);
		        
			    
			    document.add(tabletmp);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
    
    
    
    
    
    
    Paragraph p = new Paragraph("REPORTE DE ALTAS, MODIFICACION Y ELIMINACIONES EN EL MODULO ADMINISTRADOR", bold);
    p.setAlignment(Element.ALIGN_CENTER);
    document.add(p);        
	//document.add(new Phrase(" "));
	
	Paragraph p2 = new Paragraph("Desde "+new SimpleDateFormat("dd/MM/yyyy").format((Date)request.getAttribute("fechaini"))+
			" Hasta "+new SimpleDateFormat("dd/MM/yyyy").format((Date)request.getAttribute("fechafin")), normal);
    p2.setAlignment(Element.ALIGN_CENTER);
    document.add(p2);
    
	document.add(new Phrase(" "));
	document.add(new Phrase(" "));

	
	
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(95);
        table.setWidths(new int[]{1, 2, 3, 3, 3, 3, 2, 2, 2});
        PdfPCell cell = new PdfPCell(new Phrase("N°", bold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Módulo", bold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Campo", bold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Situacion Actual", bold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Situación Final", bold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Tipo de acción", bold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Usuario", bold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Fecha", bold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Hora", bold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        
        List<LogMuro> listaLog = (List<LogMuro>)request.getAttribute("listaLog");
        int i=0;
        for(LogMuro log: listaLog){
        	i++;
        	cell = new PdfPCell(new Phrase(i+"", normal));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(log.getPrivilegio().getDescripcion(), normal));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(log.getCampo(), normal));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(log.getValoranterior(), normal));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(log.getValornuevo(), normal));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(log.getOpcion(), normal));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(log.getUsuario(), normal));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(log.getFecha()), normal));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(new SimpleDateFormat("HH:mm:ss").format(log.getFecha()), normal));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        
document.add(table);

// step 5
document.close();

byte[] buf = new byte[8192];   
FileInputStream in = new FileInputStream("ReporteLog"+new SimpleDateFormat("ddMMyyyy").format(new Date())+".pdf");  
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