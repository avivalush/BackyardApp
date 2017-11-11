import static spark.Spark.*;

import DomainEntities.Request;
import DomainEntities.Response;
import DomainEntities.SearchResultEntity;
import Services.SearchService.SearchController;
import Services.SearchService.SerachProviders.AmazonSearchProvider;
import Services.SearchService.SerachProviders.ISearchProvider;
import Services.SearchService.SerachProviders.ProviderFactory;
import Utils.Common.ProvidersEnum;
import Utils.Parsers.JSONParser;
import org.eclipse.jetty.util.log.Log;

import java.util.List;

//tomer note
//avichay note
public class Main {

    public static void main(String[] args) {
        int portNumber = getHerokuAssignedPort();
        port(portNumber);

        ServerHub sh = new ServerHub();

        get("/lalaa/:name",
                (req, res) -> req.params(":name") + " ya homo", new JsonTransformer());
        get("/search/:provider/term/:term",
                (req, res) -> sh.searchSpecificProvider(req.params(":provider"), req.params(":term")),
                new JsonTransformer());
        get("search/term/:term",
                (req, res) -> { return sh.searchAllProviders(req.params(":term")); },
                new JsonTransformer());
        post("/Search",(req, res) -> { return sh.searchAllProviders(req.body()); },
                new JsonTransformer());
    }



    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }


}

class ServerHub {

    Response searchAllProviders(String term) {
        Request req = JSONParser.parseRequest(term);
        Response results = SearchController.getInstance().searchWithBody(req);
        return results;
    }

    public List<SearchResultEntity> searchSpecificProvider(String provider, String term) {
        ProvidersEnum providerEnum = SearchController.getInstance().providerStringToEnum(provider);
        if (providerEnum == ProvidersEnum.None) {
            return null;
        }
        List<SearchResultEntity> results = SearchController.getInstance().search(providerEnum, term);
        return results;
    }
}
