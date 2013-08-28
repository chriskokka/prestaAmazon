package controllers;

import org.xml.sax.SAXException;
import play.*;
import play.mvc.*;
import src.AccessAmazon;
import java.io.IOException;
import views.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class Application extends Controller {

    public static Result index() {
        return TODO;
    }

    public static Result apiGetASIN(String ASIN) throws IOException,ParserConfigurationException, SAXException, TransformerException {
        System.out.println("ASIN FROM URL: " + ASIN);
        AccessAmazon.getProductInfo(ASIN);
        //AccessAmazon.getProductInfo("B00CHHUVV2");
        return redirect(routes.Application.index());
    }

}

