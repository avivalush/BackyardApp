package Services.SearchService.SerachProviders;

import DomainEntities.Request;
import DomainEntities.Response;
import DomainEntities.SearchResultEntity;

import java.io.IOException;
import java.util.List;

public interface ISearchProvider {

    public List<SearchResultEntity> search(String searchString) throws Exception;

    Response searchWithBody(Request req);
}
