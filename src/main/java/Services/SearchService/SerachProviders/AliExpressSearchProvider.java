package Services.SearchService.SerachProviders;

public class AliExpressSearchProvider implements ISearchProvider {

    private static final Object lock = new Object();
    private static AliExpressSearchProvider instance;

    public static AliExpressSearchProvider getInstance() {
        if (instance == null) {
            synchronized(lock) {
                if (instance == null) {
                    instance = new AliExpressSearchProvider();
                }
            }
        }
        return instance;
    }

    private AliExpressSearchProvider() {

    }
}
