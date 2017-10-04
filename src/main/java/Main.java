import static spark.Spark.*;

import org.apache.commons.logging.impl.Log4JLogger;
import org.eclipse.jetty.util.log.Log;
//tomer note
public class Main {

    public static void main(String[] args) {
        int portNumber = getHerokuAssignedPort();
        port(portNumber);
        
        System.out.println(portNumber);
        Log.getLog().debug("!!!!!!!!!!!!!!!!%d!!!!!!!!!!!", portNumber);
        get("/lalaa/:name",
                (req, res) -> req.params(":name") + " ya homo", new JsonTransformer());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

}
