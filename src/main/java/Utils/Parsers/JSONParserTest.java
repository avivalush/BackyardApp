package Utils.Parsers;

import DomainEntities.Request;
import junit.framework.TestCase;

public class JSONParserTest extends TestCase {
    public void testParseRequest() throws Exception {
        String request = "{\n" +
                "\tsearchString:\"iphone x\",\n" +
                "\tCategory: \"electronics\",\n" +
                "\tSubCategory:\"cellular\",\n" +
                "\tfilters:{\n" +
                "\t     \"filter1\": \"16GB\",\n" +
                "\t     \"filter2\":\"5.5 inch\"\n" +
                "\t},\n" +
                "\titemsOrder:\"PriceLowest\"\n" +
                "}";
        Request requestObject = JSONParser.parseRequest(request);
    }

}