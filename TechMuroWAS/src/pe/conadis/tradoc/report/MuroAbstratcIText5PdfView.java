package pe.conadis.tradoc.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.dom4j.NodeFilter;
import org.htmlparser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public abstract class MuroAbstratcIText5PdfView extends AbstractIText5PdfView {
	
	 public String getCssRoot(){
			String relativeWebPath = "/css-visor";
			String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);
			File file = new File(absoluteDiskPath, "reporte.css");
			return file.getPath();
	 }
	 
	 
	 public String getFontRootArial(){
			String relativeWebPath = "/fonts";
			String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);
			File file = new File(absoluteDiskPath, "ARIALN.TTF");
			return file.getPath();
	 }
	 

	 
	 public String getLogoRoot(){
			String relativeWebPath = "/images-visor";
			String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);
			File file = new File(absoluteDiskPath, "logo-plantillas-tarifario.jpg");
			return file.getPath();
	 }

	 
	 public static String html2text(String html) {
	    	if(html == null) return "";
	    	
	    	html=	html.replace("<br />","");
	    	html=	html.replace("<br/>","");
	    	html=	html.replace("<br>","<br />");
			html=	html.replace("</br>","<br />");
	    	
	    	//html = html.replace("<","&lt;");   //for <
	    	//html = html.replace(">","&gt;");  //for >
	    	
//	    	try{
//				Parser parser = new Parser(html);
//		        NodeList list = parser.parse(null);
//		        html = list.toHtml();
//			}catch(Exception e){
//				System.out.println(e);
//			}
	    	html = Jsoup.clean(html, Whitelist.basic());
	    	html = Jsoup.parse(html).body().html();
	        return html;
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
	 
	 public static String converterStringTildes(String pConverter){
		 
		 String strConverterTildes = pConverter;
		 
		 strConverterTildes = strConverterTildes.replace("á","&aacute;");
		 strConverterTildes = strConverterTildes.replace("é","&eacute;");
		 strConverterTildes = strConverterTildes.replace("í","&iacute;");
		 strConverterTildes = strConverterTildes.replace("ó","&oacute;");
		 strConverterTildes = strConverterTildes.replace("ú","&uacute;");
		 strConverterTildes = strConverterTildes.replace("ñ","&ntilde;");		 

		 strConverterTildes = strConverterTildes.replace("�?","&Aacute;");
		 strConverterTildes = strConverterTildes.replace("É","&Eacute;");
		 strConverterTildes = strConverterTildes.replace("�?","&Iacute;");
		 strConverterTildes = strConverterTildes.replace("Ó","&Oacute;");
		 strConverterTildes = strConverterTildes.replace("Ú","&Uacute;");
		 strConverterTildes = strConverterTildes.replace("Ñ","&Ntilde;");
		 	 
		 return strConverterTildes;
		 
	 }
}
