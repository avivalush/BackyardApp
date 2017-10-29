package Services.SearchService.SerachProviders;

import DomainEntities.SearchResultEntity;
import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.*;

import java.io.IOException;
import java.util.List;

public class EBaySearchProvider implements ISearchProvider {

    private static final Object lock = new Object();
    private static EBaySearchProvider instance;
    private static final String CAMPAIGN_ID = "5338185169";
    private static final String CAMPAIGN_NAME = "TomerBoi-buyfiapp-PRD-25d8a3c47-ab9d883a";
    private static final String AFFILIATE_NETWORK_ID = "9";
    private static final int ITEMS_PER_PAGE = 10;
    private static ClientConfig config;
    private static FindingServicePortType serviceClient;
    public static EBaySearchProvider getInstance() {
        if (instance == null) {
            synchronized(lock) {
                if (instance == null) {
                    instance = new EBaySearchProvider();
                    SetConfiguration();
                    serviceClient = GetFindingService();
                }
            }
        }
        return instance;
    }

    private static void SetConfiguration() {
        config = new ClientConfig();
        config.setApplicationId(CAMPAIGN_NAME);
    }
    private static FindingServicePortType GetFindingService(){ return FindingServiceClientFactory.getServiceClient(config);}
    private EBaySearchProvider() {

    }

    @Override
    public List<SearchResultEntity> search(String searchString) throws IOException {

        FindItemsByKeywordsRequest request = new FindItemsByKeywordsRequest();
        
        //set request parameters
        request.setKeywords(searchString);
        SetItemsPerPage(request);
        SetAffiliatePrameters(request);
        List<SearchResultEntity> ans  = GetSearchResults(request);

        return ans;
    }

    private void SetAffiliatePrameters(FindItemsByKeywordsRequest request) {
        Affiliate affiliate = new Affiliate();
        affiliate.setNetworkId(AFFILIATE_NETWORK_ID);
        affiliate.setTrackingId(CAMPAIGN_ID);
        request.setAffiliate(affiliate);
    }

    private List<SearchResultEntity> GetSearchResults(FindItemsByKeywordsRequest request) {
        FindItemsByKeywordsResponse result = serviceClient.findItemsByKeywords(request);

        //output result
        System.out.println("Ack = "+result.getAck());
        System.out.println("Find " + result.getSearchResult().getCount() + " items." );
        List<SearchItem> items = result.getSearchResult().getItem();
        List<SearchResultEntity> ans;
        for(SearchItem item : items) {
              SearchResultEntity toAdd = new SearchResultEntity();
        }
        return null;
    }

    private void SetItemsPerPage(FindItemsByKeywordsRequest request) {
        PaginationInput pi = new PaginationInput();
        pi.setEntriesPerPage(ITEMS_PER_PAGE);
        request.setPaginationInput(pi);
    }
    private SearchResultEntity ParseSearchResult(SearchItem item){
        SearchResultEntity ans = new SearchResultEntity();
        return null;
    }
}
