package pe.conadis.tradoc.report;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Columna;
import pe.conadis.tradoc.entity.Producto;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;

import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Body;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Header;
import com.lowagie.text.html.HtmlTags;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfWriter;

public class ReporteTarifarioCompletoPDF extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter pdfWriter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String fecha = sdf.format(new Date());

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition",
				"attachment; filename=Tarifario_ " + fecha + ".pdf");

		document.open();
		
		
		
		@SuppressWarnings("unchecked")
		Map<String, List<Producto>> tarifarios =  (Map<String, List<Producto>>) model.get("tarifarios");
		
		Set<String> tiposPersona = tarifarios.keySet();
		
		String rutaWebContent = request.getServletContext().getRealPath("/");
		//document.add(new Header(HtmlTags.STYLESHEET, rutaWebContent + "css-visor/general.css"));
	//	document.add(new Header(HtmlTags.STYLESHEET, rutaWebContent + "css-visor/general.css"));
		//document.add(new Header(HtmlTags.STYLESHEET, "/css-visor/general.css"));
		//document.add(new Header(HtmlTags.STYLESHEET, "general.css"));
		
		for (String tipoPersona : tiposPersona) {
			String tipo = tipoPersona.equals("PN") ? "Tarifario PN" : "Tarifario PJ";
			generarReporte(tipo, tarifarios.get(tipoPersona), document, rutaWebContent, pdfWriter);
		}
		
		
		
		// PdfWriter.getInstance(document, outputStream);
	//	document.open();

		// document.addTitle("Bank Statement");
		// document.addSubject("Bank Statement");
		// document.addKeywords("Report, PDF, iText");
		// document.addAuthor("Satyam");
		// document.addCreator("Satyam");

		

	}

	private void generarReporte(String tipo, List<Producto> productos, Document document, String rutaWebContent, PdfWriter pdfWriter) throws Exception {


		HTMLWorker htmlWorker = new HTMLWorker(document);
	     
		StringBuffer html = new StringBuffer();

		
//		html.append("	<?xml version='1.0' encoding='UTF-8'?>");
//		html.append("	<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' ");
//		html.append("   'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
//		html.append("	<html xmlns='http://www.w3.org/1999/xhtml'>");
//		html.append("	  <head>");
//		html.append("	     <title>Example To Convert HTML to PDF in Java iText</title>");
//		html.append("	     <style type='text/css'> b { color: blue; } </style>");
//		html.append("	   </head>");
//		html.append("	   <body>        ");
//		html.append("	      <b>Hello World</b>");
//		html.append("	      We have successfully transformed a HTML File to PDF document in Java!!");
//		html.append("	   <br/>");
//		html.append("	    <b>This solution and example works </b>");
//	    html.append("	    </body>");
//		html.append("		</html>");
//	
		  String cssRoot = rutaWebContent +  "css-visor/reporte.css";
		     String style = getStyleFromCssFile(cssRoot);
	
		
	//	StringBuffer html = new StringBuffer();
		
		html.append(" <html> ");
		html.append(" <head>");

		html.append("<style  type='text/css'>");
		html.append(style);
		html.append(" </style>");
		html.append(" </head>");
		
		html.append(" <BODY>");
		html.append("  <table cellspacing='0' cellpadding='0' border='1' class='table-tarifario'>");
		html.append(" 	<tbody>");
		
		for (Producto producto : productos) {
			
			html.append(" <tr>");
			html.append(" 		<td colspan='2'><img src='");
			html.append(rutaWebContent
					+ "images-visor/logo-plantillas-tarifario.png' style='border:0;'/></td>");
			html.append(" 	    <td>&nbsp;</td>");
			html.append(" 	    <td>&nbsp;</td>");
			html.append(" 	    <td class='td-producto'>");
			html.append(producto.getNombre());
			html.append("		</td>");
			html.append(" 	</tr>");
			html.append(" 	<tr>");
			html.append("    	<td colspan='5' height='18'></td>");
			html.append("   	</tr>");
			
			List<Capitulo> capitulos = producto.getCapitulos();	
						
			for (Capitulo capitulo : capitulos) {
			
				
				if(capitulo.getNotaByIdnotainicial() != null 
						&& capitulo.getNotaByIdnotainicial().getTitulo() != null
						&& !capitulo.getNotaByIdnotainicial().getTitulo().trim().equals("") ){
					
					
					html.append("<tr>");
					html.append("<td colspan='5' class='td-notas'>");
					html.append("<table cellspacing='0' cellpadding='0' border='0' width='100%'>	");	
					html.append("	<tr>");
					html.append("	<td class='header-notas'>");
					html.append(		html2text(capitulo.getNotaByIdnotainicial().getTitulo()));
					html.append("	</td>");
					html.append("	</tr>");
					html.append("	<tr>");
					html.append("			<td>");
					html.append(			html2text(capitulo.getNotaByIdnotainicial().getDescripcion()));
					html.append("			</td>");
					html.append("		</tr>	");
					html.append("	</table>");
					html.append("	</td>");
					html.append("	</tr>");
						
				}
				
				if(capitulo.getNombre() != null && !capitulo.getNombre().trim().equals("Sin Capítulo")){
				html.append(" 		        <tr>");
				html.append(" 			    	<td colspan='5' class='td-capitulo'>");
				html.append(						capitulo.getNombre());
				html.append(" 			    	</td>");
				html.append(" 			  	</tr>");
				}
				
				
				List<Subcapitulo> subcapitulos = capitulo.getSubcapitulos();
				
				for (Subcapitulo subcapitulo : subcapitulos) {
					
					if(subcapitulo.getNombre() != null && !subcapitulo.getNombre().trim().equals("Sin Sub-Capítulo")){
						
						html.append(" <tr>");
						html.append(" <td colspan='5' class='td-sub-capitulo'>");
						html.append(	subcapitulo.getNombre());
						html.append("</td>");
						html.append(" </tr>");
							
					}
				
					List<Rubro> rubros = subcapitulo.getRubros();
					
					for (Rubro rubro : rubros) {
						
						String rowAnt = "0";
						
						html.append(" 	<tr>");
						html.append(" 	<td colspan='5' height='8'>");
						html.append(" 	<table width='100%' border='1'>");
						html.append(" 		<tr>");
						
						List<Columna> columnas = rubro.getColumnas();
						
						for (Columna columna : columnas) {
							
							if(!columna.getPosicionx().equals(rowAnt)) html.append("</tr><tr>");
							
							html.append(" <td class='td-rubros");
							if(columnas.indexOf(columna) == 0) html.append(" td-rubros-index'");
							else html.append("'");
							System.out.println("rubro " +   columna.getTitulo() + "-> ancho : " + columna.getWidth()  );
							html.append("  style='min-width: " + columna.getWidth() + "px;max-width:" + columna.getWidth() + "px' rowspan='" + columna.getRowspan() + "' colspan='" + columna.getColspan() + "'>");
							html.append(html2text(columna.getTitulo()));
							html.append("</td>");
							
							rowAnt = columna.getPosicionx();
						
						}
						html.append(" 		</tr>");
						
						List<Categoria> categorias = rubro.getCategorias();
						
						for (Categoria categoria : categorias) {
							
							if(categoria.getNombre() != null && !categoria.getNombre().trim().equals("Sin Categoría")){
								
								html.append("<tr>");
								html.append("<td colspan='20' class='td-categorias'>");
								html.append(categoria.getNombre() + "<span>-" + categoria.getDenominacion() + "</span></td>");
								html.append("</tr>");
							}
							
							List<Transaccion> transacciones = categoria.getTransaccions();
							for (Transaccion transaccion : transacciones) {
								
								String rowTransaccionAnt = "0";
								html.append("<tr class='table-transacciones td-transacciones'>");
								
								List<TransaccionDetalle> transaccionDetalles = transaccion.getTransaccionDetalles();
								
								for (TransaccionDetalle transaccionDetalle : transaccionDetalles) {
									System.out.println("posicion X : " + transaccionDetalle.getPosicionx() + ", rowspan : " +  transaccionDetalle.getRowspan()  + ", colspan : " + transaccionDetalle.getColspan());
									if(!transaccionDetalle.getPosicionx().toString().equals(rowTransaccionAnt)){
										html.append("</tr><tr class='table-transacciones td-transacciones'>");
									}
														
									html.append(" <td class='td-left' ");
									html.append("rowspan='" + transaccionDetalle.getRowspan() + "' ");
									html.append("colspan='" + transaccionDetalle.getColspan() + "'>");
									html.append(html2text(transaccionDetalle.getContenido()));
									html.append("</td>");
									
									rowTransaccionAnt = transaccionDetalle.getPosicionx().toString();
								}
								html.append("</tr>");
								
							}
							
						}
						
						html.append(" 	</table>");
						html.append(" 	</td>");
						html.append(" 	</tr>");
						
						html.append(" 	<tr>");
						html.append(" 	<td colspan='5' height='8'>");
						
						if(rubro.getNota() != null
								&& rubro.getNota().getTitulo() != null 
								&& rubro.getNota().getTitulo() != null
								&& !rubro.getNota().getTitulo().trim().equals("") ){
							
//						html.append("	<tr>");
//						html.append("	<td colspan='5' class='td-notas'>");
						html.append("	<table cellspacing='0' cellpadding='0' border='0' width='100%'>");
						html.append("		<tr>");
						html.append("			<td class='header-notas'>" + html2text(rubro.getNota().getTitulo()) + "</td>");
						html.append("		</tr>");
						html.append("		<tr>");
						html.append("			<td>" + html2text(rubro.getNota().getDescripcion())  + "</td>");
						html.append("		</tr>");
						html.append("	</table>");
//						html.append("	</td>");
//						html.append("	</tr>");
						
						}
						html.append("	</td>");
						html.append("	</tr>");
						
						
					}
					
					html.append("	<tr>");
					html.append("	<td colspan='5' height='8'>");
	        		
					if(subcapitulo.getNota() != null
							&& subcapitulo.getNota().getTitulo() != null 
							&& subcapitulo.getNota().getTitulo() != null
							&& !subcapitulo.getNota().getTitulo().trim().equals("") ){
	        		
//						html.append("	<tr>");
//						html.append("	<td colspan='5' class='td-notas'>");
						html.append("	<table cellspacing='0' cellpadding='0' border='0' width='100%'>");
						html.append("	<tr>"); 
						html.append("		<td class='header-notas'>" + html2text(subcapitulo.getNota().getTitulo()) + "</td>");
						html.append("	</tr>");
						html.append("		<tr>");
						html.append("		<td>" + html2text(subcapitulo.getNota().getDescripcion()) + "</td>");
						html.append("		</tr>");
						html.append("		</table>");
//						html.append("		</td>");
//						html.append("	</tr>");
				}
					
					html.append("	</td>");
					html.append("	</tr>");
			  }
				
				// Nota inferior del capitulo
				if(capitulo.getNotaByIdnotafinal() != null 
						&& capitulo.getNotaByIdnotafinal().getTitulo() != null
						&& !capitulo.getNotaByIdnotafinal().getTitulo().trim().equals("") ){
					
					html.append("	 <tr>");
					html.append("		<td colspan='5' class='td-notas'>");
					html.append("		<table cellspacing='0' cellpadding='0' border='0' width='100%'>");
					html.append("			<tr>");
					html.append("				<td class='header-notas'>" + html2text(capitulo.getNotaByIdnotafinal().getTitulo()) + "</td>");
					html.append("			</tr>");
					html.append("			<tr>");
					html.append("				<td>" + html2text(capitulo.getNotaByIdnotafinal().getDescripcion()) + "</td>");
					html.append("			</tr>");
					html.append("			</table>");
					html.append("			</td>");
					html.append("	 	</tr>");
				}
			}
		}
		
		html.append("     </tbody></table>");
		html.append(" </BODY>");
		html.append(" <html>");
		
		
		
	//	System.out.println(html.toString());
		htmlWorker.parse(new StringReader(html.toString()));
	
		
		StyleSheet styles = new StyleSheet(); 
		styles.loadTagStyle("body", "font", "Bitstream Vera Sans"); 
		styles.loadTagStyle("td", "background", "#2280f2"); 
		ArrayList arrayElementList = HTMLWorker.parseToList(new StringReader(html.toString()), styles); 
		for (int i = 0; i < arrayElementList.size(); ++i) { 
		Element e = (Element) arrayElementList.get(i); 
		document.add(e); 
		} 
		
		
		//htmlWorker.parseToList(new StringReader(html.toString()),styles);
		
		document.close();
		
	}

	
	 public static String html2text(String html) {
	    	if(html == null) return "";
	        return Jsoup.parse(html).text();
	    }
	
	 
	 public static String getStyleFromCssFile(String root){
				 
		 StringBuffer css = new StringBuffer();	 
		 BufferedReader br = null;	
		 
			try {
				String sCurrentLine;	 
				br = new BufferedReader(new FileReader(root)); 
				while ((sCurrentLine = br.readLine()) != null) {
					css.append(" " + sCurrentLine);
				}
	 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}		
			return  css.toString();
		 
	 }

}
