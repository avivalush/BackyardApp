package Services.SearchService.SerachProviders;

import DomainEntities.SearchResultEntity;

import java.io.IOException;
import java.util.List;

public interface ISearchProvider {

    public List<SearchResultEntity> search(String searchString) throws IOException;

}
