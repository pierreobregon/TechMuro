package pe.conadis.tradoc.report;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.conadis.tradoc.entity.Capitulo;
import pe.conadis.tradoc.entity.Categoria;
import pe.conadis.tradoc.entity.Columna;
import pe.conadis.tradoc.entity.Producto;
import pe.conadis.tradoc.entity.Rubro;
import pe.conadis.tradoc.entity.Subcapitulo;
import pe.conadis.tradoc.entity.Transaccion;
import pe.conadis.tradoc.entity.TransaccionDetalle;
import pe.conadis.tradoc.util.Constants;
import pe.conadis.tradoc.util.FormatTagReport;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

public class ReporteProductoPDF  extends MuroAbstratcIText5PdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String fecha = sdf.format(new Date());

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition",
				"attachment; filename=Tarifario_x_producto_ " + fecha + ".pdf");
		
		
	    Producto producto = (Producto) model.get("producto");
		
		String rutaWebContent = request.getServletContext().getRealPath("/");

		document.open();
		if(producto.getTipocliente().equals("PN")){
			writer.setPageEvent(new TableHeaderPdf(Constants.PN_FOOTER));
		}else{
			writer.setPageEvent(new TableHeaderPdf(Constants.PJ_FOOTER));
		}
		generarReporte(producto, document, rutaWebContent, writer);
		
		
		
	}

	private void generarReporte(Producto producto, Document document, String rutaWebContent, PdfWriter pdfWriter) throws IOException {

		XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
		  
		 String fontRootArial  = getFontRootArial();
		 fontProvider.register(fontRootArial);
		  
		 // CSS
	     CSSResolver cssResolver = new StyleAttrCSSResolver();
	     CssFile cssFile = XMLWorkerHelper.getCSS(new FileInputStream(getCssRoot()));
	     cssResolver.addCss(cssFile);
	           
		 CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
		 HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
	     htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
	      
	     // Pipelines
	     PdfWriterPipeline pdf = new PdfWriterPipeline(document, pdfWriter);
	     HtmlPipeline htmlPipeLine = new HtmlPipeline(htmlContext, pdf);
	     CssResolverPipeline css = new CssResolverPipeline(cssResolver, htmlPipeLine);
		   	
	     XMLWorker worker = new XMLWorker(css, true);
	     XMLParser p = new XMLParser(worker, Charset.forName("UTF-8"));
		 
	     String logoRoot = getLogoRoot();
	     
		StringBuffer html = new StringBuffer();
	
		html.append(" <html> ");
		html.append(" <head>");
		html.append(" </head>");
		
		html.append(" <BODY>");

		html.append("<table style='width:100%;' class='table-tarifario'><thead><tr></tr></thead><tbody>");
		html.append("<tr>");
		html.append("	<td colspan='2'>");
		html.append("		<img src='"+logoRoot+"' width='153px' style='border:0;'/>");
		html.append("	</td>");		     
		html.append("	<td>&nbsp;</td>");
		html.append("	<td>&nbsp;</td>");
		html.append("	<td class='td-producto'>");
		html.append(		converterStringTildes(producto.getNombre()));
		html.append("	</td>");
		html.append("</tr>");
		html.append("</tbody></table>");
		
		html.append("<table><tbody><tr><td style='height:8px;'>&nbsp;</td></tr></tbody></table>");
		
		List<Capitulo> capitulos = producto.getCapitulos();	
					
		for (Capitulo capitulo : capitulos) {
				
			if(capitulo.getNotaByIdnotainicial() != null 
					&& capitulo.getNotaByIdnotainicial().getTitulo() != null
					&& !capitulo.getNotaByIdnotainicial().getTitulo().trim().equals("") ){
				
				html.append("<table style='width:100%' class='table-tarifario'><thead><tr></tr></thead><tbody>");
				html.append("		<tr>");
				html.append("			<td class='header-notas' style='border:1px solid #333;'>");
				html.append(				html2text(capitulo.getNotaByIdnotainicial().getTitulo()));
				html.append("			</td>");
				html.append("		</tr>");
				html.append("		<tr>");
				html.append("			<td class='td-notas' style='border:1px solid #333;'><br/>");
				html.append(				html2text(capitulo.getNotaByIdnotainicial().getDescripcion()));
				html.append("				<br/>&nbsp;");
				html.append("			</td>");
				html.append("		</tr>	");
				html.append("</tbody></table>");
				
				html.append("<table><tbody><tr><td style='height:8px;'>&nbsp;</td></tr></tbody></table>");
			}
			
			if(capitulo.getNombre() != null && !capitulo.getNombre().trim().equals("Sin Capítulo")){
				html.append("<table style='width:100%' class='table-tarifario'><thead><tr></tr></thead><tbody>");
				html.append("<tr>");
				html.append("	<td colspan='5' class='td-capitulo' style='border-spacing:0px'>");
				html.append(		html2text(capitulo.getNombre()));
				html.append("	</td>");
				html.append("</tr>");
				html.append("</tbody></table>");
				
				html.append("<table><tbody><tr><td style='height:8px;'>&nbsp;</td></tr></tbody></table>");
			}
			
			
			List<Subcapitulo> subcapitulos = capitulo.getSubcapitulos();
			
			for (Subcapitulo subcapitulo : subcapitulos) {
				
				if(subcapitulo.getNombre() != null && !subcapitulo.getNombre().trim().equals("Sin Sub-Capítulo")){
					html.append("<table style='width:100%' class='table-tarifario'><thead><tr></tr></thead><tbody>");
					html.append("<tr>");
					html.append("	<td colspan='5' class='td-sub-capitulo' style='border-spacing:-1px'>");
					html.append(		html2text(subcapitulo.getNombre()));
					html.append("	</td>");
					html.append("</tr>");
					html.append("</tbody></table>");
					
					html.append("<table><tbody><tr><td style='height:8px;'>&nbsp;</td></tr></tbody></table>");
				}
			
				List<Rubro> rubros = subcapitulo.getRubros();
				
				for (Rubro rubro : rubros) {
					html.append("<table style='width:100%;repeat-header:yes;border-spacing:-1px' class='table-tarifario'>");
					html.append("<thead>");
					html.append("<tr>");

					String rowAnt = "0";
					List<Columna> columnas = rubro.getColumnas(); 
					for (int ccol = 0; ccol<columnas.size(); ccol++) {
						Columna columna = columnas.get(ccol);
						if(!columna.getPosicionx().equals(rowAnt)) html.append("</tr><tr>");
						html.append("<td class='");
						if(columnas.indexOf(columna) == 0){
							html.append("td-rubros-index'");
						}else{
							html.append("td-rubros'");
						}
						html.append(" style='border:1px solid #333; min-width: " + columna.getWidth() + "px;max-width:" + columna.getWidth() + "px;' rowspan='" + columna.getRowspan() + "' colspan='" + columna.getColspan() + "'>");
						html.append(html2text(columna.getTitulo()));
						html.append("</td>");
						rowAnt = columna.getPosicionx();
					}
					html.append("</tr>");
					html.append("</thead>");
					
					html.append("<tbody>");
					
					List<Categoria> categorias = rubro.getCategorias();
					for (Categoria categoria : categorias) {
						
						if(categoria.getNombre() != null && !categoria.getNombre().trim().equals("Sin Categoría")){								
							html.append("<tr>");
							html.append("<td colspan='5' class='td-categorias' style='padding:5px 1px;'>");
							String denominacion = categoria.getDenominacion() == null ? "" : " - " + categoria.getDenominacion();
							html.append("<b>"+html2text(categoria.getNombre()) +  "</b> " + html2text(denominacion) + "</td>");
							html.append("</tr>");
						}
						
						List<Transaccion> transacciones = categoria.getTransaccions();
						
						/*
						for (Transaccion transaccion : transacciones) {
							
							List<List<TransaccionDetalle>> listaFilas = new ArrayList<List<TransaccionDetalle>>();
		        			List<TransaccionDetalle> listaDetalle = transaccion.getTransaccionDetalles();
		        			
							int max_rows = 0;
							for(TransaccionDetalle tx_det : listaDetalle){
								if(tx_det.getPosicionx()>max_rows){
									max_rows = tx_det.getPosicionx();
								}
							}

							for(int i = 1; i <= max_rows; i++){
								List<TransaccionDetalle> lista_tx_det = new ArrayList<TransaccionDetalle>();
								for(TransaccionDetalle tx_det : listaDetalle){
									if(tx_det.getPosicionx()==i){
										lista_tx_det.add(tx_det);
									}
								}
								listaFilas.add(lista_tx_det);
							}				
							
							for(int ii=0; ii<listaFilas.size();ii++){
								List<TransaccionDetalle> fila = listaFilas.get(ii);
								if(fila.size()!=0){
									html.append("<tr class='table-transacciones'>");
									for(int jj=0; jj<fila.size();jj++){
										TransaccionDetalle columna = fila.get(jj);
										
										String contenido = columna.getContenido()==null?"":columna.getContenido().toString();
										String alineacion = "";
										
										int iof_c = 0, iof_r = 0;
										iof_c = contenido.indexOf("center;");
										iof_r = contenido.indexOf("right;");
										
										if(iof_c == -1 && iof_r == -1){
											alineacion = "left;";
										}else if(iof_c == -1 && iof_r != -1){
											alineacion = "right;";
										}else if(iof_c != -1 && iof_r == -1){
											alineacion = "center;";
										}else{
											if(iof_c < iof_r){
												alineacion = "center;";
											}else{
												alineacion = "right;";
											}
										}

										contenido = contenido.replace("center;", alineacion);
										contenido = contenido.replace("left;", alineacion);
										contenido = contenido.replace("right;", alineacion);
										
										html.append("<td class='td-transacciones' style='padding:15px 1px; border:1px solid #333;text-align:"+alineacion+"' rowspan='"+columna.getRowspan()+"' colspan='"+columna.getColspan()+"' >");
										if(ii==1 && jj==1 && transaccion.getIdtransaccion().toString().trim().equals("")){
											html.append(transaccion.getNombre());	
										}else{
											contenido=	contenido.replace("<br />","");
											contenido=	contenido.replace("<br/>","");
											contenido=	contenido.replace("<br>","<br />");
											contenido=	contenido.replace("</br>","<br />");
											contenido=  contenido.replace("<div","<p");
											contenido=  contenido.replace("/div>","/p>");
											html.append(contenido);
										}
										html.append("</td>");
									}
									html.append("</tr>");
								}
							}
							
						}			
						*/
						
						for (Transaccion transaccion : transacciones) {
	                        
	                        String rowTransaccionAnt = "0";
	                        html.append("<tr class='table-transacciones'>");
	                        
	                        List<TransaccionDetalle> transaccionDetalles = transaccion.getTransaccionDetalles();
	                        
	                        for (TransaccionDetalle transaccionDetalle : transaccionDetalles) {
	                        //        System.out.println("posicion X : " + transaccionDetalle.getPosicionx() + ", rowspan : " +  transaccionDetalle.getRowspan()  + ", colspan : " + transaccionDetalle.getColspan());
	                                if(!transaccionDetalle.getPosicionx().toString().equals(rowTransaccionAnt)){
	                                        html.append("</tr><tr class='table-transacciones'>");
	                                }
	                        
	                                html.append(" <td class='td-left td-transacciones' style='padding:15px 1px;border:1px solid #333;' ");
	                                html.append("rowspan='" + transaccionDetalle.getRowspan() + "' ");
	                                html.append("colspan='" + transaccionDetalle.getColspan() + "'>");
	                

	                                String textDetalle = transaccionDetalle.getContenido();
	                                if(textDetalle == null) textDetalle = "";
	                                else if(transaccionDetalle.getPosiciony() == 0 && transaccionDetalle.getPosicionx() == 1){
	                                        textDetalle = converterStringTildes(transaccionDetalle.getContenido());
	                                }else{
	                                        textDetalle = converterStringTildes(FormatTagReport.format(transaccionDetalle.getContenido()));
	                                }
	                                
	                                textDetalle=                                                        textDetalle.replace("<br />","");
	                                 textDetalle=                                                        textDetalle.replace("<br/>","");
	                                 textDetalle=                                                        textDetalle.replace("<br>","<br />");
	                                 textDetalle=                                                        textDetalle.replace("</br>","<br />");
	                                
	                                 if(textDetalle.contains("<div")){
	                                         textDetalle =                 textDetalle.replace("\"","'");
	                                         textDetalle = textDetalle.replace("<div","<p");
	                                         textDetalle = textDetalle.replace("/div>","/p>");
	                                         
	                                         textDetalle = textDetalle.replace("<span","<p");
	                                         textDetalle = textDetalle.replace("/span>","/p>");
	                                         
	                                         System.out.println("-> " +textDetalle);
	                                        }
	         
	                                 textDetalle = converterStringTildes(textDetalle);
	                                html.append(textDetalle);
	        
	                                html.append("</td>");
	                                
	                                rowTransaccionAnt = transaccionDetalle.getPosicionx().toString();
	                        }
	                        html.append("</tr>");
						}
						
						
						
					}
					html.append("</tbody></table>");
					html.append("<table><tbody><tr><td style='height:8px;'>&nbsp;</td></tr></tbody></table>");
					
					if(rubro.getNota() != null
							&& rubro.getNota().getTitulo() != null 
							&& rubro.getNota().getTitulo() != null
							&& !rubro.getNota().getTitulo().trim().equals("") ){
						
						html.append("<table style='width:100%' class='table-tarifario'><thead><tr></tr></thead><tbody>");
						html.append("		<tr>");
						html.append("			<td class='header-notas' style='border:1px solid #333;'>" + html2text(rubro.getNota().getTitulo()) + "</td>");
						html.append("		</tr>");
						html.append("		<tr>");
						html.append("			<td class='td-notas' style='border:1px solid #333;'><br/>"  + html2text(rubro.getNota().getDescripcion())  + "<br/>&nbsp;</td>");
						html.append("		</tr>");
						html.append("</tbody></table>");
						
						html.append("<table><tbody><tr><td style='height:8px;'>&nbsp;</td></tr></tbody></table>");
					}
					
				}
        		
				if(subcapitulo.getNota() != null
						&& subcapitulo.getNota().getTitulo() != null 
						&& subcapitulo.getNota().getTitulo() != null
						&& !subcapitulo.getNota().getTitulo().trim().equals("") ){
					
					html.append("<table style='width:100%' class='table-tarifario'><thead><tr></tr></thead><tbody>");
					html.append("<tr>"); 
					html.append("	<td class='header-notas' style='border:1px solid #333;'>" + html2text(subcapitulo.getNota().getTitulo()) + "</td>");
					html.append("</tr>");
					html.append("<tr>");
					html.append("	<td class='td-notas' style='border:1px solid #333;'><br/>"  + html2text(subcapitulo.getNota().getDescripcion()) + "<br/>&nbsp;</td>");
					html.append("</tr>");
					html.append("</tbody></table>");
					
					html.append("<table><tbody><tr><td style='height:8px;'>&nbsp;</td></tr></tbody></table>");

				}
		  }

			// Nota inferior del capitulo
			if(capitulo.getNotaByIdnotafinal() != null 
				&& capitulo.getNotaByIdnotafinal().getTitulo() != null
				&& !capitulo.getNotaByIdnotafinal().getTitulo().trim().equals("") ){
				
				html.append("<table style='width:100%' class='table-tarifario'><thead><tr></tr></thead><tbody>");
				html.append("	<tr>");
				html.append("		<td class='header-notas' style='border:1px solid #333;'>" + html2text(capitulo.getNotaByIdnotafinal().getTitulo()) + "</td>");
				html.append("	</tr>");
				html.append("	<tr>");
				html.append("		<td class='td-notas' style='border:1px solid #333;'><br/>" + html2text(capitulo.getNotaByIdnotafinal().getDescripcion()) + "<br/>&nbsp;</td>");
				html.append("	</tr>");
				html.append("</tbody></table>");
				
				html.append("<table><tbody><tr><td style='height:8px;'>&nbsp;</td></tr></tbody></table>");
			}
		}

		html.append(" </BODY>");
		html.append(" </html>");
	  
		String str = html.toString();
				
		InputStream is = new ByteArrayInputStream(str.getBytes());
		p.parse(is);

		document.close();
		
	}
}
