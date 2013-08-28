package src;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class modifyXML {
	
	public static String modify(StringBuffer XMLBuffer) throws IOException, ParserConfigurationException, SAXException, TransformerException{
				
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();	 
		Document doc = db.parse(new ByteArrayInputStream(XMLBuffer.toString().getBytes()));
		Element rootNode = doc.getDocumentElement();
		
		NodeList priceList = rootNode.getElementsByTagName("price");
		priceList.item(0).setTextContent("190");
		System.out.println(priceList.item(0).getTextContent());
		
		NodeList inStockList = rootNode.getElementsByTagName("id_product_attribute");
		inStockList.item(0).setTextContent("100");
		System.out.println(inStockList.item(0).getTextContent());
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String output = writer.getBuffer().toString();
		
		return output;
	}



    private String convertPounds(String pounds){
        Double exchangeRate = 1.4993;  //1 pound = 1.4993 us dollars
        Double usDollars = Integer.parseInt(pounds) * exchangeRate;
        return Double.toString(usDollars);
    }
}
