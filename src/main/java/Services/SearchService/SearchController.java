package Services.SearchService;

import DomainEntities.SearchResultEntity;
import Services.SearchService.SerachProviders.*;
import Utils.Common.ProvidersEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchController {

    private static final Object lock = new Object();
    private static SearchController instance;
    private HashMap<ProvidersEnum, ISearchProvider> searchProviders;

    public static SearchController getInstance() {
        if (instance == null) {
            synchronized(lock) {
                if (instance == null) {
                    instance = new SearchController();
                }
            }
        }
        return instance;
    }

    private SearchController() {
        searchProviders = new HashMap<ProvidersEnum, ISearchProvider>();
        searchProviders.put(ProvidersEnum.Amazon, AmazonSearchProvider.getInstance());
        searchProviders.put(ProvidersEnum.AliExpress, AliExpressSearchProvider.getInstance());
        searchProviders.put(ProvidersEnum.EBay, EBaySearchProvider.getInstance());
    }

    public List<SearchResultEntity> search(String term) {
        List<SearchResultEntity> searchResultEntities = new ArrayList<SearchResultEntity>();

        for (ISearchProvider provider : searchProviders.values()) {
            try {
                searchResultEntities.addAll(provider.search(term));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return searchResultEntities;
    }

    public List<SearchResultEntity> search(ProvidersEnum provider, String term) {
        //ISearchProvider providerObject = ProviderFactory.getProviderByName(provider);
        ISearchProvider providerObject = searchProviders.getOrDefault(provider, null);

        if (provider == null) {
            return new ArrayList<SearchResultEntity>();
        }

        try {
            return providerObject.search(term);
        } catch (Exception e) {
            return new ArrayList<SearchResultEntity>();
        }
    }

    public ProvidersEnum providerStringToEnum(String providerString) {
        ProvidersEnum providersEnum = ProvidersEnum.None;

        switch (providerString.toUpperCase()) {
            case "ALIEXPRESS":
                providersEnum = ProvidersEnum.AliExpress;
                break;
            case "AMAZON":
                providersEnum = ProvidersEnum.Amazon;
                break;
            case "EBAY":
                providersEnum = ProvidersEnum.EBay;
                break;
            default:
                providersEnum = ProvidersEnum.None;
        }

        return providersEnum;
    }
}
