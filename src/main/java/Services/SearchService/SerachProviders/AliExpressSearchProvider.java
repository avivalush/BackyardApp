package Services.SearchService.SerachProviders;

import DomainEntities.Request;
import DomainEntities.Response;
import DomainEntities.SearchResultEntity;

import java.io.IOException;
import java.util.List;

public class AliExpressSearchProvider implements ISearchProvider {

    private static final Object lock = new Object();
    private static AliExpressSearchProvider instance;

    public static AliExpressSearchProvider getInstance() {
        if (instance == null) {
            synchronized(lock) {
                if (instance == null) {
                    instance = new AliExpressSearchProvider();
                }
            }
        }
        return instance;
    }

    private AliExpressSearchProvider() {

    }

    @Override
    public List<SearchResultEntity> search(String searchString) throws IOException {
        return null;
    }

    @Override
    public Response searchWithBody(Request req) {
        return new Response();
    }
}
