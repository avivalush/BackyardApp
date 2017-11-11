package DomainEntities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Request {
    @JsonProperty("searchString")
    public String searchString;
    @JsonProperty("Category")
    public String Category;
    @JsonProperty("SubCategory")
    public String SubCategory;
    @JsonProperty("filters")
    public Map<String, String> filters;
    @JsonProperty("itemsOrder")
    public String itemsOrder;
}
