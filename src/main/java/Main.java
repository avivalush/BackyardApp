import static spark.Spark.*;

import Services.SearchService.SearchController;
import Services.SearchService.SerachProviders.AmazonSearchProvider;
import Services.SearchService.SerachProviders.ProviderFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.eclipse.jetty.util.log.Log;
//tomer note
//avichay note
public class Main {

    public static void main(String[] args) {
        int portNumber = getHerokuAssignedPort();
        port(portNumber);
        
        System.out.println(portNumber);
        Log.getLog().debug("!!!!!!!!!!!!!!!!%d!!!!!!!!!!!", portNumber);
        get("/lalaa/:name",
                (req, res) -> req.params(":name") + " ya homo", new JsonTransformer());
        get("/search/:provider/term/:term",
                (req, res) -> SearchController.getInstance().search(SearchController.getInstance().providerStringToEnum(req.params(":provider")),
                        req.params(":term")),
                new JsonTransformer());
        get("search/all/term/:term",
                (req, res) -> SearchController.getInstance().search(req.params(":term")),
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
