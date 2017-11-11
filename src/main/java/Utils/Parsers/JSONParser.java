package Utils.Parsers;

import DomainEntities.Request;
import DomainEntities.SearchResultEntity;
import com.google.gson.Gson;

import java.util.List;

public class JSONParser implements IParser {

    public List<SearchResultEntity> parseSearchResults(String responseString) {
        return null;
    }
    public static Request parseRequest(String req) {
        Gson gson = new Gson();
        Request request = gson.fromJson(req, Request.class);
        return request;
    }
}
