package Utils.Parsers;

import DomainEntities.SearchResultEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.List;

public class XMLParser implements IParser {
    @Override
    public List<SearchResultEntity> parseSearchResults(String responseString) throws Exception {
        Document xmlDocument = loadXMLFromString(responseString);
        NodeList itemsNodes = xmlDocument.getElementsByTagName("Item");
        for (int i = 0; i < itemsNodes.getLength(); i++) {
            Element item = (Element)itemsNodes.item(i);
            String url = item.getElementsByTagName("DetailPageURL").item(0).getTextContent();
            System.out.println(url);
        }

        return null;
    }

    public static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
}
