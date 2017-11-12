package Services.SearchService.SerachProviders;

import DomainEntities.Filter;
import DomainEntities.Request;
import DomainEntities.Response;
import DomainEntities.SearchResultEntity;
import Utils.Common.ProvidersEnum;
import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EBaySearchProvider implements ISearchProvider {

    private static final Object lock = new Object();
    private static EBaySearchProvider instance;
    private static final String CAMPAIGN_ID = "5338185169";
    private static final String CAMPAIGN_NAME = "TomerBoi-buyfiapp-PRD-25d8a3c47-ab9d883a";
    private static final String AFFILIATE_NETWORK_ID = "9";
    private static final int ITEMS_PER_PAGE = 10;
    private FindItemsByKeywordsResponse result;
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
        //SetOutputSelectors(request);
        //set request parameters
        request.setKeywords(searchString);
        SetItemsPerPage(request);
        SetAffiliatePrameters(request);
        //SetFilters(request);
        List<SearchResultEntity> ans  = GetSearchResults(request);

        return ans;
    }

    @Override
    public Response searchWithBody(Request req) {
        Response ans = new Response();
        FindItemsByKeywordsRequest request = new FindItemsByKeywordsRequest();
        SetOutputSelectors(request,req.filters.isEmpty());
        request.setKeywords(req.searchString);
        SetItemsPerPage(request);
        SetAffiliatePrameters(request);
        SetFilters(request,req.filters);
        ans.results = GetSearchResults(request);
        ans.Filters = (req.filters.isEmpty()) ? GetFilters(request) : null);
        return ans;
    }

    private List<Filter> GetFilters(FindItemsByKeywordsRequest request) {
        AspectHistogramContainer container = result.getAspectHistogramContainer();
        //CategoryHistogramContainer categoryHistogram = result.getCategoryHistogramContainer();
        List<Filter> ans = new ArrayList<Filter>();
        for(Aspect aspect : container.getAspect()){
            Filter filter = new Filter();
            filter.Key = aspect.getName();
            for(AspectValueHistogram aspectVal : aspect.getValueHistogram()){
                filter.Value += aspectVal.getValueName() + ',';
            }
            if(filter.Value != "" && filter.Value != null)
                filter.Value = filter.Value.substring(0,filter.Value.length() - 2);
            ans.add(filter);
        }
        return ans;
    }

    private void SetFilters(FindItemsByKeywordsRequest request, Map<String,String> filters) {
        List<AspectFilter> filter = request.getAspectFilter();
        for ( Map.Entry<String, String> entry : filters.entrySet()) {
            AspectFilter aspectFilter = new AspectFilter();
            aspectFilter.setAspectName(entry.getKey());
            aspectFilter.getAspectValueName().add(entry.getValue());
            filter.add(aspectFilter);
        }
    }

    private void SetOutputSelectors(FindItemsByKeywordsRequest request,boolean setAspectAndCategory) {
        List<OutputSelectorType> outputSelector = request.getOutputSelector();
        OutputSelectorType outputSelectorType = OutputSelectorType.SELLER_INFO;
        outputSelector.add(outputSelectorType);

        //aspect and category histogram will sent only if there is no filters specified in the request
        if(setAspectAndCategory){
            OutputSelectorType outputSelectorType1 = OutputSelectorType.ASPECT_HISTOGRAM;
            OutputSelectorType outputSelectorType2 = OutputSelectorType.CATEGORY_HISTOGRAM;
            outputSelector.add(outputSelectorType1);
            outputSelector.add(outputSelectorType2);
        }


    }

    private void SetAffiliatePrameters(FindItemsByKeywordsRequest request) {
        Affiliate affiliate = new Affiliate();
        affiliate.setNetworkId(AFFILIATE_NETWORK_ID);
        affiliate.setTrackingId(CAMPAIGN_ID);
        request.setAffiliate(affiliate);
    }

    private List<SearchResultEntity> GetSearchResults(FindItemsByKeywordsRequest request) {
        result = serviceClient.findItemsByKeywords(request);
        //output result
        System.out.println("Ack = "+result.getAck());
        System.out.println("Find " + result.getSearchResult().getCount() + " items." );
        List<SearchItem> items = result.getSearchResult().getItem();
        List<SearchResultEntity> ans = new ArrayList<SearchResultEntity>();

        for(SearchItem item : items) {
            ans.add(SetNewSearchResultEntity(item));
        }

        return null;
    }

    private SearchResultEntity SetNewSearchResultEntity(SearchItem item) {
        SearchResultEntity toAdd = new SearchResultEntity();
        toAdd.mRank = item.getSellerInfo().getFeedbackScore();
        toAdd.mAffiliateURL = item.getViewItemURL();
        toAdd.mCondition = item.getCondition().toString();
        toAdd.mDescription = item.getTitle();
        toAdd.mPictureURL = item.getGalleryURL();
        toAdd.mPrice = item.getSellingStatus().getCurrentPrice().toString();
        toAdd.mProvider = ProvidersEnum.EBay;
        return toAdd;
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
