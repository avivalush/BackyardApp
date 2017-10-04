package Utils.Parsers;

import DomainEntities.SearchResultEntity;

import java.util.List;

public interface IParser {

    List<SearchResultEntity> parseSearchResults(String responseString) throws Exception;

}
