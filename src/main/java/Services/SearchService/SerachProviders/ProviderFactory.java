package Services.SearchService.SerachProviders;

public class ProviderFactory {

    public static ISearchProvider getProviderByName(String name)
    {
        ISearchProvider provider = null;

        switch (name.toUpperCase()) {
            case "ALIEXPRESS":
                provider = AliExpressSearchProvider.getInstance();
                break;
            case "AMAZON":
                provider = AmazonSearchProvider.getInstance();
                break;
            case "EBAY":
                provider = EBaySearchProvider.getInstance();
                break;
            default:
                provider = null;
        }

        return provider;
    }

}
