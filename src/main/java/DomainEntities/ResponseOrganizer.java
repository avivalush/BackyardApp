package DomainEntities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResponseOrganizer {
    public static Response Organize(List<Response> responses, ItemsOrder orderType){
        Response response = new Response();

        List<SearchResultEntity> searchResultEntities = new ArrayList<SearchResultEntity>();
        List<Filter> filters = new ArrayList<Filter>();

        for (Response res : responses) {
            searchResultEntities.addAll(res.results);
            filters.addAll(res.Filters);
        }

        Comparator<SearchResultEntity> comp = null;

        switch(orderType) {
            case BestMatch:
                comp = new Comparator<SearchResultEntity>() {
                    @Override
                    public int compare(SearchResultEntity o1, SearchResultEntity o2) {
                        // todo : think if a way to compare with ranks
                        return (int)(o1.mRank - o2.mRank);
                    }
                };
                break;
            case PriceHighest:
                comp = new Comparator<SearchResultEntity>() {
                    @Override
                    public int compare(SearchResultEntity o1, SearchResultEntity o2) {
                        // todo : think if a way to compare with ranks
                        return 1;
                    }
                };
                break;
            case PriceLowest:
                comp = new Comparator<SearchResultEntity>() {
                    @Override
                    public int compare(SearchResultEntity o1, SearchResultEntity o2) {
                        // todo : think if a way to compare with ranks
                        return (int)(o1.mRank - o2.mRank);
                    }
                };
                break;
            case DistanceNearset:
                comp = new Comparator<SearchResultEntity>() {
                    @Override
                    public int compare(SearchResultEntity o1, SearchResultEntity o2) {
                        // todo : think if a way to compare with ranks
                        return (int)(o1.mRank - o2.mRank);
                    }
                };
                break;
        }

        searchResultEntities.sort(comp);

        response.results = searchResultEntities;
        response.Filters = filters;

        return response;
    }
}
