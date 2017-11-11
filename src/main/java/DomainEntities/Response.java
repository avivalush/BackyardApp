package DomainEntities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Response {
    @JsonProperty("results")
    public List<SearchResultEntity> results;
    @JsonProperty("Filters")
    public List<Filter> Filters;

    public Response() {
        results = new ArrayList<SearchResultEntity>();
        Filters = new ArrayList<Filter>();
    }
}
