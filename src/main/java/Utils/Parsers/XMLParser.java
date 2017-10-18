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
            String url = item.getElementsByTagName("DetailPageURL").item(0).getTextContent();
            NodeList picture = xmlDocument.getElementsByTagName("LargeImage");
            String pictureUrl = ((Element)picture.item(0)).getElementsByTagName("URL").item(0).getTextContent();
            int salesRank = Integer.parseInt(item.getElementsByTagName("SalesRank").item(0).getTextContent());
            NodeList features = item.getElementsByTagName("Feature");
            String description = makeDescription(features);
            NodeList formattedPrice = item.getElementsByTagName("FormattedPrice");
            String price = ((Element)formattedPrice.item(0)).getTextContent();
            double numberPrice = Double.parseDouble(price.substring(1, price.length()));
            String brand = ((Element)item.getElementsByTagName("Brand").item(0)).getTextContent();
            String title = ((Element)item.getElementsByTagName("Title").item(0)).getTextContent();
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
