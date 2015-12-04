package pe.conadis.tradoc.util;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FormatTagReport {

	public static String format(String html) {

		Document doc = Jsoup.parseBodyFragment(html);
		Elements elements = doc.children();

		Element htmlTag = elements.first();

		Element bodyTag = htmlTag.child(1);

		StringBuilder sb = new StringBuilder();
		for (Element div : bodyTag.children()) {
			StringBuffer texto = new StringBuffer();
			textRecursivo(div, texto);
			sb.append("<div>").append(texto).append("</div>");
		}
		
		return sb.toString();
	}

	private static void textRecursivo(Object item, StringBuffer sb) {
		Element elemento = (Element) item;
		List<Element> nodosHijos = elemento.children();
		//System.out.println("hijos del " + elemento.nodeName() + ", cantidad de hijos " + elemento.childNodeSize());
		if(nodosHijos.size() == 0){
			sb.append(elemento);
		}
		
		for (Element children : nodosHijos) {
			Elements elementosDiv = children.select("div");
			if (elementosDiv.size() == 0) {
				
			//	System.out.println("agregando : " +elemento.toString().trim().replace("\n", "").replace("\r", ""));
				 sb.append(elemento.toString().trim());
			} else
			    textRecursivo(children,sb);
		}

		//return sb.toString();
	}

}
