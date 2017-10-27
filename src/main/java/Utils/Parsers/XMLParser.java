package Utils.Parsers;

import DomainEntities.SearchResultEntity;
import Utils.Common.ProvidersEnum;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XMLParser implements IParser {
    @Override
    public List<SearchResultEntity> parseSearchResults(String responseString) throws Exception {
        List<SearchResultEntity> searchResultEntityList = new ArrayList<SearchResultEntity>();
        Document xmlDocument = loadXMLFromString(responseString);
        NodeList itemsNodes = xmlDocument.getElementsByTagName("Item");

        for (int i = 0; i < itemsNodes.getLength(); i++) {
            Element item = (Element)itemsNodes.item(i);
            if (item == null) {
                continue;
            }

            // subnodes
            NodeList picture = xmlDocument.getElementsByTagName("LargeImage");
            NodeList features = item.getElementsByTagName("Feature");
            NodeList formattedPrice = item.getElementsByTagName("FormattedPrice");
            Node priceNode = ((Element)formattedPrice.item(0));
            Element brandElement = ((Element)item.getElementsByTagName("Brand").item(0));
            Node pictureNode = ((Element)picture.item(0)).getElementsByTagName("URL").item(0);
            Node titleNode = ((Element)item.getElementsByTagName("Title").item(0));
            Node salesRankNode = item.getElementsByTagName("SalesRank").item(0);
            Node urlNode = item.getElementsByTagName("DetailPageURL").item(0);

            /*if (picture == null || features == null || formattedPrice == null || priceNode == null
                    || brandElement == null || pictureNode == null  || titleNode == null || salesRankNode == null
                    || urlNode == null) {
                continue;
            }*/

            String price = (priceNode != null) ? priceNode.getTextContent() : "";
            double numberPrice = (price != null) ? Double.parseDouble(price.substring(1, price.length())) : -1;
            String pictureUrl = (pictureNode != null) ? pictureNode.getTextContent() : "";
            String description = (features != null) ? makeDescription(features) : "";
            String brand = (brandElement != null) ? brandElement.getTextContent() : "";
            String title = (titleNode != null) ? titleNode.getTextContent() : "";
            int salesRank = (salesRankNode != null) ? Integer.parseInt(salesRankNode.getTextContent()) : -1;
            String url = (urlNode != null) ? urlNode.getTextContent() : "";

            String condition = ((Element)item.getElementsByTagName("Condition").item(0)).getTextContent();
            SearchResultEntity searchResultEntity = new SearchResultEntity(url, pictureUrl, numberPrice, salesRank,
                    -1, ProvidersEnum.Amazon, description, title, condition, -1);
            searchResultEntityList.add(searchResultEntity);
        }

        return searchResultEntityList;
    }

    private String makeDescription(NodeList features) {
        StringBuilder description = new StringBuilder();

        for (int i = 0; i < features.getLength(); i++) {
            Element item = (Element) features.item(i);
            String feature = item.getTextContent();
            description.append(feature + ". ");
        }

        return description.toString();
    }

    public static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
}
