package Services.SearchService;

import Services.SearchService.SerachProviders.*;

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

}
