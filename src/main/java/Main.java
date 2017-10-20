import static spark.Spark.*;

import DomainEntities.SearchResultEntity;
import Services.SearchService.SearchController;
import Services.SearchService.SerachProviders.AmazonSearchProvider;
import Services.SearchService.SerachProviders.ISearchProvider;
import Services.SearchService.SerachProviders.ProviderFactory;
import Utils.Common.ProvidersEnum;
import org.apache.commons.logging.impl.Log4JLogger;
import org.eclipse.jetty.util.log.Log;
import spark.Request;

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

    List<SearchResultEntity> searchAllProviders(String term) {
        List<SearchResultEntity> results = SearchController.getInstance().search(term);
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
