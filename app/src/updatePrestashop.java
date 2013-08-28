package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.SAXException;


public class updatePrestashop {
	
	public static void getRequestToPrestashop() throws IOException, ParserConfigurationException, SAXException, TransformerException{
		URL my_Url = null;
		String strTemp = "";
		StringBuffer strBuffer = new StringBuffer();
		
		final String login ="VNIPG9YYCESFNK0CLXQNIDBJR24WQZ5E";
		final String password ="";

		Authenticator.setDefault(new Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication (login, password.toCharArray());
		    }
		});
		
		my_Url = new URL("http://127.0.0.1:4001/prestashop/api/products/8");		
				
		BufferedReader br = new BufferedReader(new InputStreamReader(my_Url.openStream()));					
		while(null != (strTemp = br.readLine())){
			strBuffer.append(strTemp);
		}
		
		//String newXML = modifyXML.modify(strBuffer);
		putRequestToPrestashop("8",strBuffer.toString());
        System.out.println(strBuffer.toString().replaceAll(" ",""));


	}
	
	/*
	 * @Params id: prestashop's item's id
	 * @Params newXML: the modified XML which includes updated info from Amazon.co.uk
	 */
	public static void putRequestToPrestashop(String id,String newXML) throws IOException {

	    final String login ="VNIPG9YYCESFNK0CLXQNIDBJR24WQZ5E";
	    final String password ="";

	    DefaultHttpClient httpClient = new DefaultHttpClient();
	    
	    httpClient.getCredentialsProvider().setCredentials(
	            new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM, "basic"),
	            new UsernamePasswordCredentials(login, password));

	    HttpPut putRequest = new HttpPut("http://127.0.0.1:4001/prestashop/api/products/"+id);


	    StringEntity input = new StringEntity(newXML);
	    input.setContentType("text/xml");

	    putRequest.setEntity(input);

	    HttpResponse response = httpClient.execute(putRequest);

	    System.out.println(response.getStatusLine());
	    httpClient.getConnectionManager().shutdown();
	}
}
