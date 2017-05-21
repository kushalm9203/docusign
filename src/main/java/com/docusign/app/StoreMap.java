package main.java.com.docusign.app;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Creates the XML file and populates it with the map items. 
 * @author Kushal
 *
 */
public class StoreMap {

    public static void main(String argv[]) {
        String[] hotItems = new String[] {"sandals", "sun visor", "fail",
                "t-shirt", "fail", "shorts", 
                "leaving house", "Removing PJs"};
        String[] coldItems = new String[] {"boots", "hat", "socks",
                "shirt", "jacket", "pants",
                "leaving house", "Removing PJs"};

      try {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("mapItems");
        doc.appendChild(rootElement);   

        /* Create and populate Map elements */
        Element hotMap = doc.createElement("hotMap");
        rootElement.appendChild(hotMap);
        Element coldMap = doc.createElement("coldMap");
        rootElement.appendChild(coldMap);
        for (String s: hotItems) {
            Element ele = doc.createElement("value");
            ele.appendChild(doc.createTextNode(s));
            hotMap.appendChild(ele);           
        }     
        for (String s: coldItems) {
            Element ele = doc.createElement("value");
            ele.appendChild(doc.createTextNode(s));
            coldMap.appendChild(ele);
        }
      
        /* Write the content into XML file */
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer trans = transFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(System.getProperty("user.dir") 
                + "\\src\\main\\java\\com\\docusign\\app\\maps.xml"));
        trans.transform(source, result);
        
      } catch (ParserConfigurationException pce) {
        pce.printStackTrace();
      } catch (TransformerException tfe) {
        tfe.printStackTrace();
      }
    }
}
