package src;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;





public class AccessAmazon {
	
	/*
     * Your AWS Access Key ID, as taken from the AWS Your Account page.
     */
    private static final String AWS_ACCESS_KEY_ID = "AKIAJWXS6AXJV34JVVAA";

    /*
     * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
     * Your Account page.
     */
    private static final String AWS_SECRET_KEY = "Erg9BvfFF++PM2rNiF5rMuJTCLqiSMSV2lsxR13H";

    private static final String ENDPOINT = "webservices.amazon.co.uk";


    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {
    	updatePrestashop.getRequestToPrestashop();
    	//getProductInfo("B00CHHUVV2");
    }
    
    public static void getProductInfo(String productASIN) throws IOException, ParserConfigurationException, SAXException, TransformerException{
        updatePrestashop.getRequestToPrestashop();

    	 /*
         * Set up the signed requests helper 
         */
        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        

        /* The helper can sign requests in two forms - map form and string form */
        
        /*
         * Here is an example in map form, where the request parameters are stored in a map.
         */
        Map<String, String> params = new HashMap<String, String>();
        params.put("AssociateTag", "5044-4995-8577");
        params.put("Condition", "New");
        params.put("IdType", "ASIN");
        params.put("ItemId", productASIN);
        params.put("Operation", "ItemLookup");
        params.put("ResponseGroup", "Offers,ItemAttributes,EditorialReview");
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2011-08-01");

        
        String requestUrl = helper.sign(params);

        
        URL my_url = new URL(requestUrl);
        BufferedReader br = new BufferedReader(new InputStreamReader(my_url.openStream()));
        String strTemp = "";
        StringBuffer strBuffer = new StringBuffer();
        while((strTemp = br.readLine()) != null ){
        	strBuffer.append(strTemp);
        }
        
        System.out.println("-------------------------------------------------------------");
        System.out.println(requestUrl);
        System.out.println("-------------------------------------------------------------");


        String title = fetchTitle(strBuffer);
        System.out.println(title);
        
        String price = fetchPrice(strBuffer);
        System.out.println("The item price is: "  + price);
        
        String inStock = fetchInStock(strBuffer);
        System.out.println("The number of items left is: "  + inStock); 
        
        String description = fetchDescription(strBuffer);
        System.out.println("Longer description: "  + description);
        
        String availability = fetchAvailability(strBuffer);
        System.out.println("Availability: "  + availability);

        System.out.println("-------------------------------------------------------------");
    } 
    

    /*
     * Utility function to fetch the response from the service and extract the
     * price from the XML.
     */
    private static String fetchPrice(StringBuffer requestStringBuffer) {
        String price = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(requestStringBuffer.toString().getBytes()));
            Node priceNode = doc.getElementsByTagName("FormattedPrice").item(0);
            price = priceNode.getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return price;
    }
    
    
    /*
     * Utility function to fetch the response from the service and extract the
     * number of items left in stock from the XML.
     */
    private static String fetchInStock(StringBuffer requestStringBuffer) {
        String inStock = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(requestStringBuffer.toString().getBytes()));
            Node inStockNode = doc.getElementsByTagName("TotalNew").item(0);
            inStock = inStockNode.getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return inStock;
    }
    
    
    /*
     * Utility function to fetch the response from the service and extract the
     * title from the XML.
     */
    private static String fetchTitle(StringBuffer requestStringBuffer) {
        String title = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(requestStringBuffer.toString().getBytes()));
            Node titleNode = doc.getElementsByTagName("Title").item(0);
            title = titleNode.getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return title;
    }
    
    
    /*
     * Utility function to fetch the response from the service and extract the
     * product description from the XML.
     */
    private static String fetchDescription(StringBuffer requestStringBuffer) {
        String description = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(requestStringBuffer.toString().getBytes()));
            Node descriptionNode = doc.getElementsByTagName("Content").item(0);
            description = descriptionNode.getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return description;
    }
    
    
    /*
     * Utility function to fetch the response from the service and extract the
     * availability from the XML.
     */
    private static String fetchAvailability(StringBuffer requestStringBuffer) {
        String availability = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(requestStringBuffer.toString().getBytes()));
            Node availabilityNode = doc.getElementsByTagName("Availability").item(0);
            availability = availabilityNode.getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return availability;
    }

}
